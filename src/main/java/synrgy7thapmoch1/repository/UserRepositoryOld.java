package synrgy7thapmoch1.repository;

import com.example.challenge6.entity.UserOld;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepositoryOld extends JpaRepository<UserOld, UUID>, JpaSpecificationExecutor<UserOld> {
    @Query("FROM User")
    public Page<UserOld> getAllDataPage(Pageable pageable);

    @Query(value = "SELECT p FROM User p WHERE p.id = :id")
    public Optional<UserOld> getByIdUser(@Param("id") UUID id);
}
