package synrgy7thapmoch4.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Data
@Entity
@Table(name = "Merchant")
@Where(clause = "deleted_date is null")
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
