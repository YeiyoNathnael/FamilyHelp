package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nathnael.yeiyo.adu.ac.ae.config.ClusterContext;
import nathnael.yeiyo.adu.ac.ae.exception.RaftConsensusException;
import nathnael.yeiyo.adu.ac.ae.model.Node;
import nathnael.yeiyo.adu.ac.ae.model.RaftLog;
import nathnael.yeiyo.adu.ac.ae.repository.NodeRepository;
import nathnael.yeiyo.adu.ac.ae.repository.RaftLogRepository;
import nathnael.yeiyo.adu.ac.ae.service.RaftService;

@Service
public class RaftServiceImpl implements RaftService {

    @Autowired private RaftLogRepository raftLogRepository;
    @Autowired private NodeRepository nodeRepository;
    @Autowired private ClusterContext clusterContext;
    @Autowired private RestTemplate restTemplate; // for HTTP to other nodes

    // Current Raft state — in memory
    private String role = "FOLLOWER"; // FOLLOWER | CANDIDATE | LEADER
    private int currentTerm = 0;
    private Long votedFor = null;
    private long lastEntryIndex = 0;

    // ── Called by ApplicationServiceImpl when a family accepts a request ──
    @Override
    public RaftLog proposeAcceptance(Long applicationId) {
        if (!role.equals("LEADER")) {
            // forward to leader
            Node leader = findLeader();
            String url = "http://" + leader.getHost() + ":" + leader.getPort()
                       + "/raft/append-entries";
            return restTemplate.postForObject(url,
                buildEntry("ACCEPT_REQUEST", applicationId), RaftLog.class);
        }

        // we are the leader — write uncommitted entry
        RaftLog entry = new RaftLog();
        entry.setEntryIndex(++lastEntryIndex);
        entry.setTerm(currentTerm);
        entry.setCommand("ACCEPT_REQUEST");
        entry.setResourceId(applicationId);
        entry.setStatus("UNCOMMITTED");
        entry.setNodeId(clusterContext.getNodeId());
        entry.setCreatedAt(LocalDateTime.now());
        raftLogRepository.save(entry);

        // replicate to followers and wait for majority
        List<Node> followers = nodeRepository.findByStatus("ALIVE")
            .stream()
            .filter(n -> n.getId() != clusterContext.getNodeId())
            .collect(Collectors.toList());

        int acks = 1; // count ourselves
        for (Node follower : followers) {
            try {
                String url = "http://" + follower.getHost() + ":"
                           + follower.getPort() + "/raft/append-entries";
                Boolean success = restTemplate.postForObject(url, entry, Boolean.class);
                if (Boolean.TRUE.equals(success)) acks++;
            } catch (RuntimeException e) {
                // follower unreachable — continue
            }
        }

        int totalNodes = nodeRepository.findByStatus("ALIVE").size();
        int majority = (totalNodes / 2) + 1;

        if (acks >= majority) {
            entry.setStatus("COMMITTED");
            raftLogRepository.save(entry);
            return entry;
        } else {
            // failed to reach majority — roll back
            raftLogRepository.delete(entry);
            throw new RaftConsensusException("Failed to reach quorum. Acks: " + acks);
        }
    }

    // ── Called on followers via POST /raft/append-entries ──
    @Override
    public boolean appendEntry(RaftLog entry) {
        // reject stale terms
        if (entry.getTerm() < currentTerm) return false;

        currentTerm = entry.getTerm();
        role = "FOLLOWER";

        // write to local log
        RaftLog local = new RaftLog();
        local.setEntryIndex(entry.getEntryIndex());
        local.setTerm(entry.getTerm());
        local.setCommand(entry.getCommand());
        local.setResourceId(entry.getResourceId());
        local.setStatus("UNCOMMITTED");
        local.setNodeId(clusterContext.getNodeId());
        local.setCreatedAt(LocalDateTime.now());
        raftLogRepository.save(local);

        return true;
    }

    // ── Called on candidates via POST /raft/request-vote ──
    @Override
    public boolean requestVote(Long candidateId, int term, long lastLogIndex) {
        if (term < currentTerm) return false;
        if (votedFor != null && !votedFor.equals(candidateId)) return false;

        // check candidate log is at least as up to date as ours
        long ourLastIndex = raftLogRepository.findTopByOrderByEntryIndexDesc()
            .map(RaftLog::getEntryIndex).orElse(0L);

        if (lastLogIndex < ourLastIndex) return false;

        votedFor = candidateId;
        currentTerm = term;
        return true;
    }

    // ── Election trigger — called by PhiAccrualService when leader suspected ──
    @Override
    public void startElection() {
        role = "CANDIDATE";
        currentTerm++;
        votedFor = clusterContext.getNodeId();
        int votes = 1; // vote for self

        List<Node> others = nodeRepository.findByStatus("ALIVE")
            .stream()
            .filter(n -> n.getId() != clusterContext.getNodeId())
            .collect(Collectors.toList());

        long myLastIndex = raftLogRepository.findTopByOrderByEntryIndexDesc()
            .map(RaftLog::getEntryIndex).orElse(0L);

        for (Node node : others) {
            try {
                String url = "http://" + node.getHost() + ":" + node.getPort()
                           + "/raft/request-vote";
                // send candidateId, term, lastLogIndex
                Boolean granted = restTemplate.postForObject(url,
                    Map.of("candidateId", clusterContext.getNodeId(),
                           "term", currentTerm,
                           "lastLogIndex", myLastIndex),
                    Boolean.class);
                if (Boolean.TRUE.equals(granted)) votes++;
            } catch (RuntimeException ignored) {}
        }

        int majority = (others.size() + 1) / 2 + 1;
        if (votes >= majority) {
            role = "LEADER";
            System.out.println("This node is now LEADER for term " + currentTerm);
        } else {
            role = "FOLLOWER";
        }
    }

    private Node findLeader() {
        return nodeRepository.findAll().stream()
            .filter(n -> n.getStatus().equals("ALIVE"))
            .findFirst()
            .orElseThrow(() -> new RaftConsensusException("No leader found"));
    }

    private RaftLog buildEntry(String command, Long resourceId) {
        RaftLog entry = new RaftLog();
        entry.setEntryIndex(++lastEntryIndex);
        entry.setTerm(currentTerm);
        entry.setCommand(command);
        entry.setResourceId(resourceId);
        entry.setStatus("UNCOMMITTED");
        entry.setNodeId(clusterContext.getNodeId());
        entry.setCreatedAt(LocalDateTime.now());
        return entry;
    }
}