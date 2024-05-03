package synrgy7thapmoch4.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import synrgy7thapmoch4.Entity.Employee;
import synrgy7thapmoch4.Entity.User;

public interface UserRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    //store procedure
    @Procedure("create_user")
    @Transactional
    void createUserSP(
            @Param("user_id") Long userId,
            @Param("user_name") String userName,
            @Param("user_email") String userEmail
    );

    @Procedure("read_user")
    User readUserSP(@Param("user_id") Long userId);

    @Procedure("update_user")
    @Transactional
    void updateUserSP(
            @Param("user_id") Long userId,
            @Param("user_name") String userName,
            @Param("user_email") String userEmail
    );

    @Procedure("delete_user")
    @Transactional
    void deleteUserSP(@Param("user_id") Long userId);

    // Custom query method to find a user by email
    @Query("SELECT u FROM User u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);
}
