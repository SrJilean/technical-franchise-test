package com.technical_franchise_test.technical_franchise_test.repository;

import com.technical_franchise_test.technical_franchise_test.model.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
    Flux<Product> findByBranchId(Long branchId);
}
