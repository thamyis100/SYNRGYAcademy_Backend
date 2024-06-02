package synrgy7thapmoch1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "price")
    private double price;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
}
