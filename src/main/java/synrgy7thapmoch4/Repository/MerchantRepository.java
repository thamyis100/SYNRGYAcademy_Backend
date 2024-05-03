package synrgy7thapmoch4.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import synrgy7thapmoch4.Entity.Merchant;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {

    // Update method
    Merchant save(Merchant merchant);

    // Delete method by ID
    void deleteById(Long merchantId);

    // Add method (assuming it's similar to save)
    Merchant saveAndFlush(Merchant merchant);

    // Find merchants by their open status with pagination
    Page<Merchant> findByOpen(boolean open, Pageable pageable);
}
