package com.core.master.repository;

import com.core.master.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IMerchantRepository extends JpaRepository<Merchant, UUID> {
    Boolean existsByMerchantName(String merchantName);
    @Query("SELECT m FROM Merchant m WHERE m.isOpen = :isOpen")
    Page<Merchant> findAllByIsOpen(Boolean isOpen, Pageable pageable);
}
