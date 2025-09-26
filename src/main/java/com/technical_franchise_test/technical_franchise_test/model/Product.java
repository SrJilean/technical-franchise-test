package com.technical_franchise_test.technical_franchise_test.model;

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
public class Product {

    @Id
    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    private String name;

    private Integer stock = 0;

    @NotNull(message = "El id de la sucursal es obligatorio")
    private Long branchId;
}
