package com.technical_franchise_test.technical_franchise_test.service;

import com.technical_franchise_test.technical_franchise_test.dto.BranchNameUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.model.Branch;
import com.technical_franchise_test.technical_franchise_test.repository.BranchRepository;
import com.technical_franchise_test.technical_franchise_test.repository.FranchiseRepository;
import com.technical_franchise_test.technical_franchise_test.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BranchService {

    private final BranchRepository branchRepository;
    private final FranchiseRepository franchiseRepository;
    private final ProductRepository productRepository;

    private final ProductService productService;

    public Mono<Branch> save(Branch branch) {
        return franchiseRepository.findById(branch.getFranchiseId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La franquicia no existe")))
                .flatMap(f -> branchRepository.save(branch));
    }

    public Mono<Branch> updateName(Long id, BranchNameUpdateRequest newBranch) {
        return branchRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La sucursal no existe")))
                .flatMap(branch -> {
                    branch.setName(newBranch.getName());
                    return branchRepository.save(branch);
                });
    }

    public Mono<Void> delete(Long id) {
        return branchRepository.findById(id)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("La sucursal no existe")))
                .flatMap(branch ->
                        productRepository.findByBranchId(id)
                                .flatMap(product -> productService.delete(product.getId()))
                                .then(branchRepository.delete(branch))
                );
    }

}
