package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.BranchNameUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.model.Branch;
import com.technical_franchise_test.technical_franchise_test.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @Operation(
            summary = "Listar sucursales",
            description = "Obtiene todas las sucursales registradas"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de sucursales",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Branch.class))
            )
    )
    @GetMapping
    public Flux<Branch> getAllBranches() {
        return branchService.findAll();
    }

    @Operation(
            summary = "Obtener una sucursal por ID",
            description = "Devuelve una sucursal específica según su ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursal encontrada",
            content = @Content(schema = @Schema(implementation = Branch.class))
    )
    @ApiResponse(responseCode = "400", description = "Sucursal no encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"La sucursal con id 99 no existe\" }"
                    )
            )
    )
    @GetMapping("/{id}")
    public Mono<Branch> getBranchById(@PathVariable Long id) {
        return branchService.findById(id);
    }

    @Operation(
            summary = "Crear una nueva sucursal",
            description = "Registra una nueva sucursal asociada a una franquicia"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Sucursal creada exitosamente",
            content = @Content(schema = @Schema(implementation = Branch.class))
    )
    @PostMapping
    public Mono<Branch> createBranch(
            @Valid @RequestBody Branch branch) {
        return branchService.save(branch);
    }

    @Operation(
            summary = "Actualizar el nombre de una sucursal",
            description = "Permite modificar únicamente el nombre de la sucursal"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Sucursal actualizada",
            content = @Content(schema = @Schema(implementation = Branch.class))
    )
    @ApiResponse(responseCode = "400", description = "Sucursal no encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"La sucursal con id 99 no existe\" }"
                    )
            )
    )
    @PutMapping("/update-name/{id}")
    public Mono<Branch> updateName(
            @PathVariable Long id,
            @Valid @RequestBody BranchNameUpdateRequest branch) {
        return branchService.updateName(id, branch);
    }

    @Operation(
            summary = "Eliminar una sucursal",
            description = "Elimina una sucursal existente por ID"
    )
    @ApiResponse(responseCode = "204", description = "Sucursal eliminada exitosamente")
    @ApiResponse(responseCode = "400", description = "Sucursal no encontrada",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"La sucursal con id 99 no existe\" }"
                    )
            )
    )
    @DeleteMapping("/{id}")
    public Mono<Void> deleteBranch(@PathVariable Long id) {
        return branchService.delete(id);
    }
}
