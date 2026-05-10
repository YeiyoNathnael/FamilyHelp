package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "community_posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "family_id")
    private long familyId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "post_type")
    private String postType;

    @Column(name = "status")
    private String status;

    @Column(name = "urgency")
    private Boolean urgency;

    @Column(name = "availability")
    private String availability;

    @Column(name = "needed_by")
    private LocalDateTime neededBy;

    @Column(name = "application_count")
    private Integer applicationCount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}