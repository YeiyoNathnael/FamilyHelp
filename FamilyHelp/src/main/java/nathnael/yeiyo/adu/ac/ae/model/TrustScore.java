package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "crdt_trust_contributions")
public class TrustScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "family_id")
    private long familyId;

    @Column(name = "node_id")
    private long nodeId;

    @Column(name = "contribution_value")
    private Double contributionValue;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}