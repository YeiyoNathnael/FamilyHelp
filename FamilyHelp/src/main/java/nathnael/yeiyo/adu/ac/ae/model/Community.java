package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "communities")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String communityName;

    @Column(name = "compound_name")
    private String compoundName;

    @Column(name = "area")
    private String communityArea;

    @Column(name = "city")
    private String communityCity;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}