package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.ProductNameUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.dto.ProductStockUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.dto.ProductWithBranchResponse;
import com.technical_franchise_test.technical_franchise_test.model.Product;
import com.technical_franchise_test.technical_franchise_test.service.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(
            summary = "Listar productos",
            description = "Obtiene todos los productos registrados"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Listado de productos",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Product.class))
            )
    )
    @GetMapping
    public Flux<Product> getAllProducts() {
        return productService.findAll();
    }

    @Operation(
            summary = "Obtener un producto por ID",
            description = "Devuelve un producto específico según su ID"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto encontrado",
            content = @Content(schema = @Schema(implementation = Product.class))
    )
    @ApiResponse(responseCode = "400", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"El producto con id 99 no existe\" }"
                    )
            )
    )
    @GetMapping("/{id}")
    public Mono<Product> getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @Operation(summary = "Obtener productos con mayor stock por franquicia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de productos con stock",
                    content = @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = ProductWithBranchResponse.class))
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Franquicia no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"message\": \"La franquicia con id 99 no existe\" }")
                    )
            )
    })
    @GetMapping("/most-stocked/{idFranchise}")
    public Flux<ProductWithBranchResponse> getMostStockedProduct(@PathVariable Long idFranchise) {
        return productService.mostStockedProduct(idFranchise);
    }

    @Operation(
            summary = "Crear un nuevo producto",
            description = "Registra un nuevo producto en una sucursal"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Producto creado exitosamente",
            content = @Content(schema = @Schema(implementation = Product.class))
    )
    @PostMapping
    public Mono<Product> createProduct(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @Operation(
            summary = "Actualizar nombre de un producto",
            description = "Permite modificar únicamente el nombre de un producto existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado",
            content = @Content(schema = @Schema(implementation = Product.class))
    )
    @ApiResponse(responseCode = "400", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"El producto con id 99 no existe\" }"
                    )
            )
    )
    @PutMapping("/update-name/{id}")
    public Mono<Product> updateName(
            @PathVariable Long id,
            @Valid @RequestBody ProductNameUpdateRequest product) {
        return productService.updateName(id, product);
    }

    @Operation(
            summary = "Actualizar stock de un producto",
            description = "Permite modificar únicamente el stock de un producto existente"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Producto actualizado",
            content = @Content(schema = @Schema(implementation = Product.class))
    )
    @ApiResponse(responseCode = "400", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"El producto con id 99 no existe\" }"
                    )
            )
    )
    @PutMapping("/update-stock/{id}")
    public Mono<Product> updateStock(
            @PathVariable Long id,
            @Valid @RequestBody ProductStockUpdateRequest product) {
        return productService.updateStock(id, product);
    }

    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto existente por ID"
    )
    @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "400", description = "Producto no encontrado",
            content = @Content(mediaType = "application/json",
                    examples = @ExampleObject(
                            value = "{ \"message\": \"El producto con id 99 no existe\" }"
                    )
            )
    )
    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }
}
