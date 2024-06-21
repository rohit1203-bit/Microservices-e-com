package dev.project.productservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data @Builder
public class ProductReqDto {
    private String productName;
    private String description;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
