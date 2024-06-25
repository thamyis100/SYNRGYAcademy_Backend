package com.core.master.service;

import com.core.master.dto.product.CreateProductRequest;
import com.core.master.dto.product.ProductResponse;
import com.core.master.dto.product.UpdateProductRequest;
import com.core.master.dto.product.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    ProductResponse create(CreateProductRequest request);

    ProductResponse getById(String productId);

    Page<ProductResponse> listProducts(Pageable pageable);

    ProductResponse update(UpdateProductRequest request);

    void delete(String productId);
}
