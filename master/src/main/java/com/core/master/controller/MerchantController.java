package com.core.master.controller;

import com.core.master.dto.Response;
import com.core.master.dto.merchant.CreateMerchantRequest;
import com.core.master.dto.merchant.MerchantResponse;
import com.core.master.dto.merchant.UpdateMerchantRequest;
import com.core.master.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "/merchants")
public class MerchantController {
    @Autowired
    private IMerchantService merchantService;

    @GetMapping(path = "/ping")
    public String hello(){
        return "Hello";
    }

    @PostMapping(path = {"/", ""})
    public Response<MerchantResponse> create(@Valid @RequestBody CreateMerchantRequest request){
        MerchantResponse merchantResponse = merchantService.create(request);
        return Response.<MerchantResponse>builder()
                .status("OK")
                .message("merchant created successfully")
                .data(merchantResponse)
                .build();
    }

    @GetMapping(path = {"/{merchantId}", "/{merchantId}/"})
    public Response<MerchantResponse> getById(@PathVariable String merchantId){
        MerchantResponse merchantResponse = merchantService.getById(merchantId);
        return Response.<MerchantResponse>builder()
                .status("OK")
                .message("merchant retrieved successfully")
                .data(merchantResponse)
                .build();
    }

    @GetMapping(path = {"/list", "/list/"})
    public Response<Page<MerchantResponse>> listMerchants(Pageable pageable, @RequestParam(name = "is_open", defaultValue = "true") Boolean isOpen){
        Page<MerchantResponse> merchantResponses = merchantService.listOpenMerchant(isOpen, pageable);
        return Response.<Page<MerchantResponse>>builder()
                .status("OK")
                .message("merchants retrieved successfully")
                .data(merchantResponses)
                .build();
    }

    @PatchMapping(path = {"/{merchantId}", "/{merchantId}/"})
    public Response<MerchantResponse> update(@PathVariable String merchantId, @Valid @RequestBody UpdateMerchantRequest request){
        request.setId(merchantId);
        MerchantResponse merchantResponse = merchantService.update(request);
        return Response.<MerchantResponse>builder()
                .status("OK")
                .message("merchant updated successfully")
                .data(merchantResponse)
                .build();
    }

    @DeleteMapping(path = {"/{merchantId}", "/{merchantId}/"})
    public Response<Object> delete(@PathVariable String merchantId){
        merchantService.delete(merchantId);
        return Response.builder()
                .status("OK")
                .message("merchant deleted successfully")
                .build();
    }
}
