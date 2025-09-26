package com.technical_franchise_test.technical_franchise_test.controller;

import com.technical_franchise_test.technical_franchise_test.model.Branch;
import com.technical_franchise_test.technical_franchise_test.model.Franchise;
import com.technical_franchise_test.technical_franchise_test.service.BranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
public class BranchController {

    private final BranchService branchService;

    @PostMapping
    public Mono<Branch> createBranch(@Valid @RequestBody Branch branch) {
        return branchService.save(branch);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteBranch(@PathVariable Long id) {
        return branchService.delete(id);
    }
}
