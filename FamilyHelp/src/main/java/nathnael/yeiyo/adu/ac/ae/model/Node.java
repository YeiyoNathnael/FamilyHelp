package nathnael.yeiyo.adu.ac.ae.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cluster_nodes")
public class Node {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "host")
	private String host;

	@Column(name = "port")
	private Integer port;

	@Column(name = "status")
	private String status;

	@Column(name = "current_phi")
	private Double currentPhi;

	@Column(name = "last_heartbeat")
	private LocalDateTime lastHeartbeat;

	@Column(name = "joined_at")
	private LocalDateTime joinedAt;

}


