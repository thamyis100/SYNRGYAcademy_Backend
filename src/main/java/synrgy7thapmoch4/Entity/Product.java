package synrgy7thapmoch4.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@Data
@Entity
@Table(name = "Product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private double price;
    @ManyToOne(targetEntity = Merchant.class, cascade = CascadeType.ALL)
    @JoinColumn(name="merchant_id", referencedColumnName = "id")
    private Merchant merchant_id;
}