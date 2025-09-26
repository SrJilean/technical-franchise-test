package com.technical_franchise_test.technical_franchise_test.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("franchises")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Schema(description = "Entidad que representa a una franquicia")
public class Franchise {

    @Id
    @Schema(description = "Identificador único de la franquicia", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre de la franquicia es obligatorio")
    @Schema(description = "Nombre de la franquicia", example = "McDonald's")
    private String name;

    public Franchise(String name) {
        this.name = name;
    }
}
