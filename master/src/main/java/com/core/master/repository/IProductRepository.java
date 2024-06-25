package com.core.master.repository;

import com.core.master.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    Boolean existsByProductNameAndMerchantId(String productName, UUID merchantId);
    Page<Product> findAll(Pageable pageable);
}
