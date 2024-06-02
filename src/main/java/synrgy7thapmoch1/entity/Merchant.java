package synrgy7thapmoch1.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;
@Data
@Entity
@Table(name = "merchants")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    private UUID id;

    @Column(name = "merchant_name")
    private String merchant_name;

    @Column(name = "merchant_location")
    private String merchant_location;

    @Column(name = "open")
    private Boolean open;
}