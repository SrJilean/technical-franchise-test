package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.FranchiseNameUpdateRequest;
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

    @GetMapping()
    public Flux<Franchise> getAllProducts(){
        return franchiseService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Franchise> getProductById(@PathVariable Long id){
        return franchiseService.findById(id);
    }

    @PostMapping
    public Mono<Franchise> createFranchise(@Valid @RequestBody Franchise franchise) {
        return franchiseService.save(franchise);
    }

    @PutMapping("/update-name/{id}")
    public Mono<Franchise> updateName(@PathVariable Long id, @Valid @RequestBody FranchiseNameUpdateRequest franchise) {
        return franchiseService.updateName(id, franchise);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteFranchise(@PathVariable Long id) {
        return franchiseService.delete(id);
    }
}
