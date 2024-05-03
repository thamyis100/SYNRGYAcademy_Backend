package synrgy7thapmoch4.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Entity
@Table(name = "detail_employee")
@Where(clause = "deleted_date is null")
public class DetailEmployee extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 45)
    private String nik;

    @Column(name = "npwp", length = 10)
    private String npwp;



}
