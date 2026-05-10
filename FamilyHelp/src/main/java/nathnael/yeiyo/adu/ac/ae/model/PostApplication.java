package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

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
    private LocalDateTime createdAt;

    @Column(name = "selection_date")
    private LocalDateTime selectionDate;
}