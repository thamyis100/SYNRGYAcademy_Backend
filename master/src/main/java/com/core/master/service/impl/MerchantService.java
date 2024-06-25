package com.core.master.service.impl;

import com.core.master.entity.Merchant;
import com.core.master.dto.merchant.CreateMerchantRequest;
import com.core.master.dto.merchant.MerchantResponse;
import com.core.master.dto.merchant.UpdateMerchantRequest;
import com.core.master.repository.IMerchantRepository;
import com.core.master.service.IMerchantService;
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
import java.util.UUID;

@Service
public class MerchantService implements IMerchantService {

    @Autowired
    private IMerchantRepository merchantRepository;

    @Override
    @Transactional
    public MerchantResponse create(CreateMerchantRequest req) {
        if(merchantRepository.existsByMerchantName(req.getMerchantName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "merchant name already exists");
        }

        Merchant merchant = new Merchant();
        merchant.setMerchantName(req.getMerchantName());
        merchant.setMerchantLocation(req.getMerchantLocation());
        merchant.setOpen(req.getIsOpen());
        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .id(merchant.getId())
                .merchantName(merchant.getMerchantName())
                .merchantLocation(merchant.getMerchantLocation())
                .isOpen(merchant.isOpen())
                .build();
    }

    @Override
    public MerchantResponse getById(String merchantId) {
        UUID castedMerchantId = Util.convertStringIntoUUID(merchantId);

        Merchant merchant = merchantRepository.findById(castedMerchantId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));

        return MerchantResponse.builder()
                .id(merchant.getId())
                .merchantName(merchant.getMerchantName())
                .merchantLocation(merchant.getMerchantLocation())
                .isOpen(merchant.isOpen())
                .build();
    }

    @Override
    public Page<MerchantResponse> listOpenMerchant(Boolean isOpen, Pageable pageable) {
        Page<Merchant> pageMerchants = merchantRepository.findAllByIsOpen(isOpen, pageable);

        List<MerchantResponse> merchantResponseList = pageMerchants.getContent().stream().map(merchant -> MerchantResponse.builder()
                .id(merchant.getId())
                .merchantName(merchant.getMerchantName())
                .merchantLocation(merchant.getMerchantLocation())
                .isOpen(merchant.isOpen())
                .build()).toList();

        return new PageImpl<>(merchantResponseList, pageMerchants.getPageable(), pageMerchants.getTotalElements());
    }

    @Override
    @Transactional
    public MerchantResponse update(UpdateMerchantRequest req) {
        UUID merchantId = Util.convertStringIntoUUID(req.getId());

        Merchant merchant = merchantRepository.findById(merchantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));

        merchant.setOpen(req.getIsOpen());

        merchantRepository.save(merchant);

        return MerchantResponse.builder()
                .id(merchant.getId())
                .merchantName(merchant.getMerchantName())
                .merchantLocation(merchant.getMerchantLocation())
                .build();
    }

    @Override
    @Transactional
    public void delete(String merchantId) {
        UUID merchantUUID = Util.convertStringIntoUUID(merchantId);

        merchantRepository.findById(merchantUUID).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "merchant not found"));

        merchantRepository.deleteById(merchantUUID);
    }
}
