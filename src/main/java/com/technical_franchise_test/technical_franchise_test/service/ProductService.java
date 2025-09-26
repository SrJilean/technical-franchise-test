package com.technical_franchise_test.technical_franchise_test.service;

import com.technical_franchise_test.technical_franchise_test.dto.ProductStockUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.model.Product;
import com.technical_franchise_test.technical_franchise_test.repository.BranchRepository;
import com.technical_franchise_test.technical_franchise_test.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;

    public Mono<Product> save(Product product) {
        return branchRepository.findById(product.getBranchId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La sucursal no existe")))
                .flatMap(f -> productRepository.save(product));
    }

    public Mono<Void> delete(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no existe")))
                .flatMap(f -> productRepository.deleteById(id));
    }

    public Mono<Product> updateStock(Long id, ProductStockUpdateRequest newProduct) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("El producto no existe")))
                .flatMap(product -> {
                    product.setStock(newProduct.getStock());
                    return productRepository.save(product);
                });
    }
}
