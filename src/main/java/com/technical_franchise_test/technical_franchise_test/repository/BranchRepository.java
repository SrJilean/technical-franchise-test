package com.technical_franchise_test.technical_franchise_test.repository;

import com.technical_franchise_test.technical_franchise_test.model.Branch;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface BranchRepository extends ReactiveCrudRepository<Branch, Long> {
    Flux<Branch> findByFranchiseId(Long franchiseId);
}
