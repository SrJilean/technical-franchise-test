package com.technical_franchise_test.technical_franchise_test.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Entidad que representa un producto asociado a una sucursal")
public class Product {

    @Id
    @Schema(
            description = "Identificador único del producto",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    @Schema(description = "Nombre del producto", example = "Hamburguesa Doble")
    private String name;

    @Schema(description = "Stock disponible del producto", example = "25")
    private Integer stock = 0;

    @NotNull(message = "El id de la sucursal es obligatorio")
    @Schema(description = "ID de la sucursal a la que pertenece", example = "5")
    private Long branchId;
}
