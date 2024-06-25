package com.core.auth.repository.oauth;

import com.core.auth.entity.oauth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u WHERE LOWER(u.username) = LOWER(?1)")
    Optional<User> findOneByUsername(String username);

    @Query("FROM User u WHERE u.otp = ?1")
    Optional<User> findOneByOTP(String otp);

    @Query("FROM User u WHERE LOWER(u.username) = LOWER(:username)")
    Optional<User> checkExistingEmail(String username);
}

