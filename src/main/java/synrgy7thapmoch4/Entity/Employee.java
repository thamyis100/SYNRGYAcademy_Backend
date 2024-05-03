package synrgy7thapmoch4.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;

import java.io.Serializable;
import java.util.Date;

//@EntityScan
@Data
@Entity
@Table(name = "employee")
@Where(clause = "deleted_date is null")

public class Employee extends  AbstractDate implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Column(name = "address", columnDefinition = "TEXT")
    public String address;

    // 2016-01-01
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public  String status= "active";

    @OneToOne(targetEntity = DetailEmployee.class, cascade = CascadeType.ALL)
    @JoinColumn(name="id_employee_detail", referencedColumnName = "id")
    private DetailEmployee DetailEmployee;
}

