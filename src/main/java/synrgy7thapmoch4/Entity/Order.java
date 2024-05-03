package synrgy7thapmoch4.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Entity
@Table(name = "\"Order\"")
@Where(clause = "deleted_date is null")
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "order_time")
    private Timestamp order_time; // Consider using java.sql.Timestamp for timestamps
    @Column(name = "destination_address")
    private String destination_address;
    @ManyToOne(targetEntity = User.class, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user_id;
    @Column(name = "completed")
    private boolean completed;
}
