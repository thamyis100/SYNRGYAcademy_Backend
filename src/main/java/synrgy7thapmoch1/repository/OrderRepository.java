package synrgy7thapmoch1.repository;

import com.example.challenge6.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    @Query("FROM Order")
    public Page<Order> getAllDataPage(Pageable pageable);

    @Query(value = "SELECT p FROM Order p WHERE p.id = :id")
    public Optional<Order> getByIdOrder(@Param("id") UUID id);

}
