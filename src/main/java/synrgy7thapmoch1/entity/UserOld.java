package synrgy7thapmoch1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class UserOld {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "email_address")
    private String email_address;

    @Column(name = "password")
    private String password;
}

