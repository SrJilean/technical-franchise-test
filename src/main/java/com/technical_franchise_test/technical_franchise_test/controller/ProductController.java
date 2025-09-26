package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.ProductStockUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.model.Product;
import com.technical_franchise_test.technical_franchise_test.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public Mono<Product> createProduct(@Valid @RequestBody Product product) {
        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteProduct(@PathVariable Long id) {
        return productService.delete(id);
    }

    @PutMapping("/update-stock/{id}")
    public Mono<Product> updateStock(@PathVariable Long id, @Valid @RequestBody ProductStockUpdateRequest product) {
        return productService.updateStock(id, product);
    }
}
