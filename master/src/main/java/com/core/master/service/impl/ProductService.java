package com.core.master.service.impl;

import com.core.master.entity.Merchant;
import com.core.master.dto.product.CreateProductRequest;
import com.core.master.dto.product.ProductResponse;
import com.core.master.dto.product.UpdateProductRequest;
import com.core.master.entity.Product;
import com.core.master.repository.IMerchantRepository;
import com.core.master.repository.IProductRepository;
import com.core.master.service.IProductService;
import com.core.master.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IMerchantRepository merchantRepository;

    @Override
    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        UUID merchantId = Util.convertStringIntoUUID(request.getMerchantId());

        Merchant merchantDB = merchantRepository.findById(merchantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));

        if(productRepository.existsByProductNameAndMerchantId(request.getProductName(), merchantId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "product name already exists");
        }

        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setMerchant(merchantDB);
        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

    @Override
    public ProductResponse getById(String productId) {
        UUID productUUID = Util.convertStringIntoUUID(productId);

        Product product = productRepository.findById(productUUID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

    @Override
    public Page<ProductResponse> listProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findAll(pageable);

        List<ProductResponse> productResponseList = productPage.getContent().stream().map(product -> ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build()).toList();

        return new PageImpl<>(productResponseList, productPage.getPageable(), productPage.getTotalElements());
    }

    @Override
    @Transactional
    public ProductResponse update(UpdateProductRequest request) {
        UUID productUUID = Util.convertStringIntoUUID(request.getId());

        // checker for product availability using product id
        Product product = productRepository.findById(productUUID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        // checker for product accessibility using user id from token and merchant id from product that has been collected
        if(!merchantRepository.existsById(product.getMerchant().getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }

        if(Objects.nonNull(request.getProductName())){
            product.setProductName(request.getProductName());
        }

        if(Objects.nonNull(request.getPrice())){
            product.setPrice(request.getPrice());
        }

        productRepository.save(product);

        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .price(product.getPrice())
                .merchantId(product.getMerchant().getId())
                .build();
    }

    @Override
    @Transactional
    public void delete(String productId) {
        UUID productUUID = Util.convertStringIntoUUID(productId);

        Product product = productRepository.findById(productUUID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found"));

        if(!merchantRepository.existsById(product.getMerchant().getId())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "product not found");
        }

        productRepository.deleteById(productUUID);
    }
}
