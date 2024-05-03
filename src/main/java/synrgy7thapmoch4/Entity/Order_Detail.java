package synrgy7thapmoch4.Entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Data
@Entity
@Table(name = "\"Order_Detail\"")
@Where(clause = "deleted_date is null")
public class Order_Detail {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne(targetEntity = Order.class, cascade = CascadeType.ALL)
    @JoinColumn(name="order_id", referencedColumnName = "id")
    private Product order_id;
    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name="id_product", referencedColumnName = "id")
    private Product product_id;
    @Column(name = "quantity")
    private double quantity;
    @Column(name = "total_price")
    private double total_price;
}
