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
@Table(name = "raft_log")
public class RaftLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "entry_index")
	private Long entryIndex;

	@Column(name = "term")
	private Integer term;

	@Column(name = "command")
	private String command;

	@Column(name = "resource_id")
	private Long resourceId;

	@Column(name = "status")
	private String status;

	@Column(name = "node_id")
	private Long nodeId;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

}


