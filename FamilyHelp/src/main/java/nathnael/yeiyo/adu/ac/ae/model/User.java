package nathnael.yeiyo.adu.ac.ae.model;

import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_locked")
    private Boolean isLocked;

    @Column(name = "login_attempts")
    private Integer loginAttempts;

    @JsonIgnore
    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "family_id")
    private Long familyId;
}