package nathnael.yeiyo.adu.ac.ae.config;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import nathnael.yeiyo.adu.ac.ae.model.Node;
import nathnael.yeiyo.adu.ac.ae.service.NodeService;

@Component
public class NodeStartupConfig {

    @Value("${cluster.node.host:localhost}")
    private String host;

    @Value("${cluster.node.port:${server.port:8080}}")
    private Integer port;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private ClusterContext clusterContext;

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        Optional<Node> existing = nodeService.findByHostAndPort(host, port);
        Node node;

        if (existing.isPresent()) {
            node = existing.get();
            node.setStatus("ALIVE");
            node.setCurrentPhi(0.0);
            node.setLastHeartbeat(LocalDateTime.now());
            node = nodeService.save(node);
            System.out.println("[Cluster] Node re-registered: id=" + node.getId()
                + " at " + host + ":" + port);
        } else {
            node = new Node();
            node.setHost(host);
            node.setPort(port);
            node.setStatus("ALIVE");
            node.setCurrentPhi(0.0);
            node.setLastHeartbeat(LocalDateTime.now());
            node.setJoinedAt(LocalDateTime.now());
            node = nodeService.save(node);
            System.out.println("[Cluster] Node registered: id=" + node.getId()
                + " at " + host + ":" + port);
        }

        clusterContext.setNodeId(node.getId());
        clusterContext.setHost(host);
        clusterContext.setPort(port);

        System.out.println("[Cluster] Context initialized. Ready.");
    }
}
