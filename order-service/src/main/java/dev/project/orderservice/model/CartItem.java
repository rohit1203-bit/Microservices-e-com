package dev.project.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "cart_items")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class CartItem {

    @Id @GeneratedValue(strategy = AUTO)
    private Long itemId;
    private String skuCode; // company-type
    private String productName;
    private String description;
    private BigDecimal price;
    private Integer quantity;

    @ManyToOne(cascade=CascadeType.ALL)
    private Order order;

}
