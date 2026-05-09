package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "task_transaction")
public class TaskTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "application_id")
	private long applicationId;

	@Column(name = "status")
	private String status;

	@Column(name = "scheduled_date")
	private String scheduledDate;

	@Column(name = "completed_at")
	private String completedAt;

}


