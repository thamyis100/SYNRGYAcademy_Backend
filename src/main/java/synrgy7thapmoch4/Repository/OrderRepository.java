package synrgy7thapmoch4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synrgy7thapmoch4.Entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // No need to implement findAll method since it's already provided by JpaRepository
}
