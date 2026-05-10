package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "task_id")
    private long taskId;

    @Column(name = "reviewer_family_id")
    private long reviewerFamilyId;

    @Column(name = "reviewed_family_id")
    private long reviewedFamilyId;

    @Column(name = "numerical_rating")
    private Integer numericalRating;

    @Column(name = "sentiment_score")
    private Double sentimentScore;

    @Column(name = "legitimacy_weight")
    private Double legitimacyWeight;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}