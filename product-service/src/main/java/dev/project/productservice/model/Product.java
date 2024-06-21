package dev.project.productservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "products")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String skuCode; // company-type
    private String productName;
    private String description;
    private BigDecimal price;
}
