package com.technical_franchise_test.technical_franchise_test.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("branches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Branch {

    @Id
    private Long id;

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    @NotNull(message = "El id de la sucursal es obligatorio")
    private Long franchiseId;
}
