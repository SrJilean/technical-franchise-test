package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.ProductNameUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.dto.ProductStockUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.dto.ProductWithBranchResponse;
import com.technical_franchise_test.technical_franchise_test.model.Product;
import com.technical_franchise_test.technical_franchise_test.service.ProductService;
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

    @GetMapping("/most-stocked/{idFranchise}")
    public Flux<ProductWithBranchResponse> getMostStockedProduct(@PathVariable Long idFranchise) {
        return productService.mostStockedProduct(idFranchise);
    }

    @PostMapping
    public Mono<Product> createProduct(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/update-name/{id}")
    public Mono<Product> updateName(@PathVariable Long id, @Valid @RequestBody ProductNameUpdateRequest product) {
        return productService.updateName(id, product);
    }

    @PutMapping("/update-stock/{id}")
    public Mono<Product> updateStock(@PathVariable Long id, @Valid @RequestBody ProductStockUpdateRequest product) {
        return productService.updateStock(id, product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }
}
