package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="communities")

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
  private String createdAt;

}
