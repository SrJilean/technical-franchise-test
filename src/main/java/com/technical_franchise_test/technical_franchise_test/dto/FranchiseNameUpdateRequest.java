package com.technical_franchise_test.technical_franchise_test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class FranchiseNameUpdateRequest {
    @NotNull(message = "El nombre es obligatorio")
    private String name;
}
