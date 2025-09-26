package com.technical_franchise_test.technical_franchise_test.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidad que representa una sucursal asociada a una franquicia")
public class Branch {

    @Id
    @Schema(
            description = "Identificador único de la sucursal",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY // 👈 No aparece en los POST
    )
    private Long id;

    @NotBlank(message = "El nombre de la sucursal es obligatorio")
    @Schema(description = "Nombre de la sucursal", example = "Sucursal Centro")
    private String name;

    @NotNull(message = "El id de la franquicia es obligatorio")
    @Schema(description = "ID de la franquicia a la que pertenece", example = "10")
    private Long franchiseId;
}
