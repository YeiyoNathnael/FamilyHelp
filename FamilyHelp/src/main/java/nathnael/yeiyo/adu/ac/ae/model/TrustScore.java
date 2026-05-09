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
	private String lastUpdated;

}


