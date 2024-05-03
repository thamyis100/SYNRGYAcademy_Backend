package synrgy7thapmoch4.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import synrgy7thapmoch4.Entity.Merchant;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {
    // Find merchants by their open status with pagination
    Page<Merchant> findByOpen(boolean open, Pageable pageable);

    Optional<Merchant> findById(UUID id);
}
