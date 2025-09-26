package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.dto.BranchNameUpdateRequest;
import com.technical_franchise_test.technical_franchise_test.model.Branch;
import com.technical_franchise_test.technical_franchise_test.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @GetMapping()
    public Flux<Branch> getAllProducts(){
        return branchService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Branch> getProductById(@PathVariable Long id){
        return branchService.findById(id);
    }

    @PostMapping
    public Mono<Branch> createBranch(@Valid @RequestBody Branch branch) {
        return branchService.save(branch);
    }

    @PutMapping("/update-name/{id}")
    public Mono<Branch> updateName(@PathVariable Long id, @Valid @RequestBody BranchNameUpdateRequest branch) {
        return branchService.updateName(id, branch);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBranch(@PathVariable Long id) {
        return branchService.delete(id);
    }
}
