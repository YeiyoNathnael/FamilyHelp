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
	private String neededBy;

	@Column(name = "application_count")
	private Integer applicationCount;

	@Column(name = "created_at")
	private String createdAt;

}


