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
@Table(name = "interaction_edges")
public class Interaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "family_a_id")
	private long familyAId;

	@Column(name = "family_b_id")
	private long familyBId;

	@Column(name = "interaction_count")
	private Integer interactionCount;

	@Column(name = "interaction_weight")
	private Double interactionWeight;

	@Column(name = "last_interaction")
	private String lastInteraction;

}


