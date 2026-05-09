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
@Table(name = "post_application")
public class PostApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "post_id")
	private long postId;

	@Column(name = "applicant_family_id")
	private long applicantFamilyId;

	@Column(name = "status")
	private String status;

	@Column(name = "raft_entry_index")
	private Long raftEntryIndex;

	@Column(name = "message")
	private String message;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "selection_date")
	private String selectionDate;

}


