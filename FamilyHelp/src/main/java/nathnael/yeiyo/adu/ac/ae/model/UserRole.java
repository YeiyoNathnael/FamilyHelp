package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user_roles")
public class UserRole {

	@Id
	@Column(name = "user_id")
	private Long userId;

	@Column(name = "role")
	private String role;

}


