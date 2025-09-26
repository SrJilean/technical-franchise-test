package com.technical_franchise_test.technical_franchise_test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductStockUpdateRequest {
    @NotNull(message = "El stock es obligatorio")
    private Integer stock;
}
