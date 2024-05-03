package synrgy7thapmoch4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import synrgy7thapmoch4.Entity.Product;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findById(UUID id);

    // No need to implement save, deleteById, or findAll methods since they're already provided by JpaRepository
}
