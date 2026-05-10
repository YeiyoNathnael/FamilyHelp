package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import nathnael.yeiyo.adu.ac.ae.config.ClusterContext;
import nathnael.yeiyo.adu.ac.ae.exception.ResourceNotFoundException;
import nathnael.yeiyo.adu.ac.ae.model.Node;
import nathnael.yeiyo.adu.ac.ae.repository.NodeRepository;
import nathnael.yeiyo.adu.ac.ae.service.NodeService;
import nathnael.yeiyo.adu.ac.ae.service.RaftService;

@Service
public class NodeServiceImpl implements NodeService {

  @Autowired
  private NodeRepository nodeRepository;

  @Autowired
  private ClusterContext clusterContext;

  @Autowired
  private RaftService raftService;

  @Override
  public Node save(Node node) {
    return nodeRepository.save(node);
  }

  @Override
  public Optional<Node> findById(Long id) {
    return nodeRepository.findById(id);
  }

  @Override
  public List<Node> findAll() {
    return nodeRepository.findAll();
  }

  @Override
  public Node update(Node node) {
    if (!nodeRepository.existsById(node.getId())) {
      throw new ResourceNotFoundException("Node not found with id: " + node.getId());
    }
    return nodeRepository.save(node);
  }

  @Override
  public void deleteById(Long id) {
    if (!nodeRepository.existsById(id)) {
      throw new ResourceNotFoundException("Node not found with id: " + id);
    }
    nodeRepository.deleteById(id);
  }

  @Override
  public List<Node> findByStatus(String status) {
    return nodeRepository.findByStatus(status);
  }

  @Override
  public Optional<Node> findByHostAndPort(String host, Integer port) {
    return nodeRepository.findByHostAndPort(host, port);
  }

  // add to NodeServiceImpl

// in-memory heartbeat history per node — last 100 arrival timestamps
private final Map<Long, LinkedList<Long>> heartbeatHistory = new ConcurrentHashMap<>();

@Override
public void recordHeartbeat(Long fromNodeId) {
    heartbeatHistory.computeIfAbsent(fromNodeId, k -> new LinkedList<>());
    LinkedList<Long> history = heartbeatHistory.get(fromNodeId);
    history.addLast(System.currentTimeMillis());
    if (history.size() > 100) history.removeFirst(); // keep last 100

    // update last_heartbeat in DB
    nodeRepository.findById(fromNodeId).ifPresent(node -> {
        node.setLastHeartbeat(LocalDateTime.now());
        nodeRepository.save(node);
    });
}

@Override
public double calculatePhi(Long nodeId) {
    LinkedList<Long> history = heartbeatHistory.get(nodeId);
    if (history == null || history.size() < 2) return 0.0;

    // compute intervals between heartbeats
    List<Long> intervals = new ArrayList<>();
    for (int i = 1; i < history.size(); i++) {
      intervals.add(history.get(i) - history.get(i - 1));
    }

    double mean = intervals.stream().mapToLong(Long::longValue).average().orElse(1000);
    double variance = intervals.stream()
        .mapToDouble(iv -> Math.pow(iv - mean, 2))
        .average().orElse(0);
    double stddev = Math.sqrt(variance);

    long timeSinceLast = System.currentTimeMillis() - history.get(history.size() - 1);

    if (stddev == 0) return timeSinceLast > mean ? 10.0 : 0.0;

    // phi = -log10(1 - CDF(timeSinceLast, mean, stddev))
    double z = (timeSinceLast - mean) / stddev;
    double cdf = 0.5 * (1 + erf(z / Math.sqrt(2)));
    double phi = -Math.log10(1 - Math.min(cdf, 0.9999));

    return phi;
}

// ── Runs every 2 seconds, checks phi for all nodes ──
@Scheduled(fixedRate = 2000)
public void checkAllNodes() {
    if (!clusterContext.isInitialized()) return;

    List<Node> nodes = nodeRepository.findAll();
    for (Node node : nodes) {
        if (node.getId() == clusterContext.getNodeId()) continue; // skip self

        double phi = calculatePhi(node.getId());
        node.setCurrentPhi(phi);

        if (phi > 8.0) {
            node.setStatus("DEAD");
            // if dead node was leader, trigger election
            raftService.startElection();
        } else if (phi > 2.0) {
            node.setStatus("SUSPECT");
        } else {
            node.setStatus("ALIVE");
        }

        nodeRepository.save(node);
    }
}

// error function approximation for normal CDF
private double erf(double z) {
    double t = 1.0 / (1.0 + 0.5 * Math.abs(z));
    double tau = t * Math.exp(-z * z - 1.26551223
        + t * (1.00002368 + t * (0.37409196 + t * (0.09678418
        + t * (-0.18628806 + t * (0.27886807 + t * (-1.13520398
        + t * (1.48851587 + t * (-0.82215223 + t * 0.17087294)))))))));
    return z >= 0 ? 1 - tau : tau - 1;
}
}
