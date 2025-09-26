package com.technical_franchise_test.technical_franchise_test.service;

import com.technical_franchise_test.technical_franchise_test.model.Branch;
import com.technical_franchise_test.technical_franchise_test.model.Franchise;
import com.technical_franchise_test.technical_franchise_test.model.Product;
import com.technical_franchise_test.technical_franchise_test.repository.BranchRepository;
import com.technical_franchise_test.technical_franchise_test.repository.FranchiseRepository;
import com.technical_franchise_test.technical_franchise_test.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class FranchiseService {

    private final FranchiseRepository franchiseRepository;
    private final BranchRepository branchRepository;

    private final BranchService branchService;

    public Mono<Franchise> save(Franchise franchise) {
        return franchiseRepository.save(franchise);
    }

    public Mono<Void> delete(Long id) {
        return franchiseRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La franquicia no existe")))
                .flatMap(franchise ->
                        branchRepository.findByFranchiseId(id)
                                .flatMap(branch -> branchService.delete(branch.getId()))
                                .then(franchiseRepository.delete(franchise))
                );
    }
}
