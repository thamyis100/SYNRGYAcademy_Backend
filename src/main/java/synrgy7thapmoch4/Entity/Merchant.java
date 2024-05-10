package synrgy7thapmoch4.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "Merchant")
public class Merchant {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "location")
    private String location;
    @Column(name = "open")
    private boolean open;
}
