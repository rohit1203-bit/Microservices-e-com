package dev.project.orderservice.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class InvProductReqDto {
    private String productName;
    private String skuCode;
    private Integer quantity;
}
