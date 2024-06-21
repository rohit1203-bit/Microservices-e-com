package dev.project.inventoryservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "inv_product")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class InventoryProduct {

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String productName;
    private String skuCode;
    private Integer quantity;
}
