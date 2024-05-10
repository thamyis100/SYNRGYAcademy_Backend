package synrgy7thapmoch4.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
}
