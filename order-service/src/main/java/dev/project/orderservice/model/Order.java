package dev.project.orderservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.AUTO;

@Entity(name = "orders")
@Data @Builder
@AllArgsConstructor @NoArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = AUTO)
    private Long orderId;
    private Long userId;
    private String userName;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL) @JsonIgnore
    private List<CartItem> cart;
}
