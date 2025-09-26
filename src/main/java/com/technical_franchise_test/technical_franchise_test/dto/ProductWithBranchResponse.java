package com.technical_franchise_test.technical_franchise_test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWithBranchResponse {
    private Long branchId;
    private String branchName;
    private Long productId;
    private String productName;
    private Integer stock;
}
