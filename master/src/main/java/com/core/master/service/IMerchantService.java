package com.core.master.service;

import com.core.master.dto.merchant.CreateMerchantRequest;
import com.core.master.dto.merchant.MerchantResponse;
import com.core.master.dto.merchant.UpdateMerchantRequest;
import com.core.master.dto.merchant.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IMerchantService {
    MerchantResponse create(CreateMerchantRequest req);

    MerchantResponse getById(String merchantId);

    MerchantResponse update(UpdateMerchantRequest req);

    void delete(String merchantId);

    Page<MerchantResponse> listOpenMerchant(Boolean isOpen, Pageable pageable);
}
