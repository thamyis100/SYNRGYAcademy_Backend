package synrgy7thapmoch1.repository;

import com.example.challenge6.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
    @Query("FROM Product")
    public Page<Product> getAllDataPage(Pageable pageable);

    @Query(value = "SELECT p FROM Product p WHERE p.id = :id")
    public Optional<Product> getByIdProduct(@Param("id") UUID id);

}
