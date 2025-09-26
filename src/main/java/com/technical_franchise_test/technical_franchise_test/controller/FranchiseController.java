package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.model.Franchise;
import com.technical_franchise_test.technical_franchise_test.service.FranchiseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/franchises")
@RequiredArgsConstructor
public class FranchiseController {

    private final FranchiseService franchiseService;

    @PostMapping
    public Mono<Franchise> createFranchise(@Valid @RequestBody Franchise franchise) {
        return franchiseService.save(franchise);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteFranchise(@PathVariable Long id) {
        return franchiseService.delete(id);
    }
}
