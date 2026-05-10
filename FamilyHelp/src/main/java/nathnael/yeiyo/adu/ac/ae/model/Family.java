package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "families")
public class Family {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "family_size")
    private Integer familySize;

    @Column(name = "address")
    private String address;

    @Column(name = "community_id")
    private Long communityId;

    @Column(name = "languages")
    private String languages;

    @Column(name = "last_active")
    private LocalDateTime lastActive;
}