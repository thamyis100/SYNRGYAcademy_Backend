package com.core.master.controller;

import com.core.master.dto.Response;
import com.core.master.dto.product.CreateProductRequest;
import com.core.master.dto.product.ProductResponse;
import com.core.master.dto.product.UpdateProductRequest;
import com.core.master.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping(path = "/ping")
    public String hello(){
        return "Hello";
    }

    @PostMapping(path = {"/", ""})
    public Response<ProductResponse> create(@Valid @RequestBody CreateProductRequest request){
        ProductResponse productResponse = productService.create(request);
        return Response.<ProductResponse>builder()
                .status("OK")
                .message("product created successfully")
                .data(productResponse)
                .build();
    }

    @GetMapping(path = {"/{productId}", "/{productId}/"})
    public Response<ProductResponse> getById(@PathVariable String productId){
        ProductResponse productResponse = productService.getById(productId);
        return Response.<ProductResponse>builder()
                .status("OK")
                .message("product retrieved successfully")
                .data(productResponse)
                .build();
    }

    @GetMapping(path = {"/list", "/list/"})
    public Response<Page<ProductResponse>> listProducts(Pageable pageable){
        Page<ProductResponse> productResponses = productService.listProducts(pageable);
        return Response.<Page<ProductResponse>>builder()
                .status("OK")
                .message("products retrieved successfully")
                .data(productResponses)
                .build();
    }

    @PatchMapping(path = {"/{productId}", "/{productId}/"})
    public Response<ProductResponse> update(@PathVariable String productId, @Valid @RequestBody UpdateProductRequest request){
        request.setId(productId);
        ProductResponse productResponse = productService.update(request);
        return Response.<ProductResponse>builder()
                .status("OK")
                .message("product updated successfully")
                .data(productResponse)
                .build();
    }

    @DeleteMapping(path = {"/{productId}", "/{productId}/"})
    public Response<Object> delete(@PathVariable String productId){
        productService.delete(productId);
        return Response.builder()
                .status("OK")
                .message("product deleted successfully")
                .build();
    }
}
