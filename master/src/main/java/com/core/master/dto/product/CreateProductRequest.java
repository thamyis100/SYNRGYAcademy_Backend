package com.core.master.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateProductRequest {

    @NotBlank(message = "product name can't be empty")
    private String productName;

    @NotNull(message = "product price can't be empty")
    @Min(value = 0, message = "product price can't have minus price")
    private Long price;

    @NotBlank(message = "merchant id can't be empty")
    private String merchantId;
}
