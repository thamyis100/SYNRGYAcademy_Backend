package synrgy7thapmoch1.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import synrgy7thapmoch1.entity.Merchant;

import java.util.UUID;

public interface MerchantRepository extends JpaRepository<Merchant, UUID>, JpaSpecificationExecutor<Merchant> {
    @Query("FROM Merchant")
    public Page<Merchant> getAllDataPage(Pageable pageable);

}
