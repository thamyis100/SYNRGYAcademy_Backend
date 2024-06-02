package synrgy7thapmoch1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "order_details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "total_price")
    private double total_price;

    public void calculateTotalPrice() {
        if (product != null) {
            this.total_price = this.quantity*product.getPrice();
        }
    }
}