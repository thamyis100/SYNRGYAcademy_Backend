package com.core.master.dto.merchant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMerchantRequest {
    @NotBlank(message = "merchant name can't be empty")
    private String merchantName;

    @NotBlank(message = "merchant name can't be empty")
    private String merchantLocation;

    @NotNull(message = "merchant name can't be empty")
    private Boolean isOpen;
}
