package dev.project.inventoryservice.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class InvProductReqDto {
    private String skuCode;
    private String productName;
    private Integer quantity;
}
