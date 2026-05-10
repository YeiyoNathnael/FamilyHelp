package nathnael.yeiyo.adu.ac.ae.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import nathnael.yeiyo.adu.ac.ae.config.ClusterContext;
import nathnael.yeiyo.adu.ac.ae.model.Node;
import nathnael.yeiyo.adu.ac.ae.model.TrustScore;
import nathnael.yeiyo.adu.ac.ae.repository.NodeRepository;
import nathnael.yeiyo.adu.ac.ae.repository.TrustScoreRepository;
import nathnael.yeiyo.adu.ac.ae.service.GossipService;

@Service
public class GossipServiceImpl implements GossipService {

    @Autowired private TrustScoreRepository trustScoreRepository;
    @Autowired private NodeRepository nodeRepository;
    @Autowired private ClusterContext clusterContext;
    @Autowired private RestTemplate restTemplate;

    // runs every 10 seconds automatically
    @Override
    @Scheduled(fixedRate = 10000)
    public void gossipRound() {
        if (!clusterContext.isInitialized()) return;

        // get all alive nodes except self
        List<Node> peers = nodeRepository.findByStatus("ALIVE")
            .stream()
            .filter(n -> n.getId() != clusterContext.getNodeId())
            .collect(Collectors.toList());

        if (peers.isEmpty()) return;

        // pick 2 random peers
        Collections.shuffle(peers);
        List<Node> targets = peers.subList(0, Math.min(2, peers.size()));

        // get our own CRDT contributions to share
        List<TrustScore> ourContributions = trustScoreRepository
            .findByNodeId(clusterContext.getNodeId());

        // send to each target
        for (Node target : targets) {
            try {
                String url = "http://" + target.getHost() + ":"
                           + target.getPort() + "/gossip/sync";
                restTemplate.postForObject(url, ourContributions, Void.class);
            } catch (RuntimeException e) {
                // target unreachable — skip
            }
        }
    }

    // ── Called when another node pushes its CRDT state to us ──
    @Override
    public void receiveAndMerge(List<TrustScore> incoming) {
        for (TrustScore incomingEntry : incoming) {
            Optional<TrustScore> existing = trustScoreRepository
                .findByFamilyIdAndNodeId(
                    incomingEntry.getFamilyId(),
                    incomingEntry.getNodeId()
                );

            if (existing.isEmpty()) {
                // new entry we didn't have — save it
                trustScoreRepository.save(incomingEntry);
            } else {
                TrustScore local = existing.get();
                // only update if incoming is newer
                if (incomingEntry.getLastUpdated()
                        .compareTo(local.getLastUpdated()) > 0) {
                    local.setContributionValue(incomingEntry.getContributionValue());
                    local.setLastUpdated(incomingEntry.getLastUpdated());
                    trustScoreRepository.save(local);
                }
                // if ours is newer — ignore incoming, ours wins
            }
        }
    }
}