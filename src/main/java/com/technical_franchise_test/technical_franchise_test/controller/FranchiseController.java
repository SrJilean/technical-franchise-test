package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.FranchiseNameUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.model.Franchise;
import com.technical_franchise_test.technical_franchise_test.service.FranchiseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @Operation(summary = "Obtener todas las franquicias", description = "Devuelve un listado de todas las franquicias")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Franchise.class))))
    @GetMapping
    public Flux<Franchise> getAllFranchises() {
        return franchiseService.findAll();
    }

    @Operation(summary = "Obtener franquicia por ID", description = "Devuelve una franquicia según su identificador único")
    @ApiResponse(responseCode = "200", description = "Franquicia encontrada",
            content = @Content(schema = @Schema(implementation = Franchise.class)))
    @ApiResponse(responseCode = "400", description = "Franquicia no encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"La franquicia con id 99 no existe\" }"
                    )
            )
    )
    @GetMapping("/{id}")
    public Mono<Franchise> getFranchiseById(@PathVariable Long id) {
        return franchiseService.findById(id);
    }

    @Operation(summary = "Crear franquicia", description = "Crea una nueva franquicia. No se debe enviar `id`")
    @ApiResponse(responseCode = "201", description = "Franquicia creada exitosamente",
            content = @Content(schema = @Schema(implementation = Franchise.class)))
    @PostMapping
    public Mono<Franchise> createFranchise(
            @Valid @RequestBody Franchise franchise) {
        return franchiseService.save(franchise);
    }

    @Operation(summary = "Actualizar nombre de franquicia", description = "Actualiza únicamente el nombre de una franquicia")
    @ApiResponse(responseCode = "200", description = "Nombre actualizado exitosamente",
            content = @Content(schema = @Schema(implementation = Franchise.class)))
    @ApiResponse(responseCode = "400", description = "Franquicia no encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"La franquicia con id 99 no existe\" }"
                    )
            )
    )
    @PutMapping("/update-name/{id}")
    public Mono<Franchise> updateName(@PathVariable Long id,
                                      @Valid @RequestBody FranchiseNameUpdateRequest franchise) {
        return franchiseService.updateName(id, franchise);
    }

    @Operation(summary = "Eliminar franquicia", description = "Elimina una franquicia por su identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Franquicia eliminada correctamente"),
            @ApiResponse(responseCode = "400", description = "Franquicia no encontrada",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = "{ \"message\": \"La franquicia con id 99 no existe\" }"
                            )
                    )
            )
    })
    @DeleteMapping("/{id}")
    public Mono<Void> deleteFranchise(@PathVariable Long id) {
        return franchiseService.delete(id);
    }
}
