package com.technical_franchise_test.technical_franchise_test.repository;

import com.technical_franchise_test.technical_franchise_test.model.Franchise;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface FranchiseRepository extends ReactiveCrudRepository<Franchise, Long> {
}