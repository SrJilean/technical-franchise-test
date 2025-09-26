package com.technical_franchise_test.technical_franchise_test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Respuesta que contiene información del producto junto con su sucursal")
public class ProductWithBranchResponse {

    @Schema(description = "ID de la sucursal", example = "1")
    private Long branchId;

    @Schema(description = "Nombre de la sucursal", example = "Sucursal Central")
    private String branchName;

    @Schema(description = "ID del producto", example = "10")
    private Long productId;

    @Schema(description = "Nombre del producto", example = "Coca Cola 1L")
    private String productName;

    @Schema(description = "Cantidad de stock disponible", example = "50")
    private Integer stock;
}

