package nathnael.yeiyo.adu.ac.ae.config;

import org.springframework.stereotype.Component;

@Component
public class ClusterContext {

    private Long nodeId;
    private String host;
    private Integer port;

    // getters and setters for all three

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public boolean isInitialized() {
        return nodeId != null;
    }
}