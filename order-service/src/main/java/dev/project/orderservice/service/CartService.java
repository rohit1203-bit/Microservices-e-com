package dev.project.orderservice.service;

import dev.project.orderservice.model.CartItem;
import dev.project.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final OrderRepository orderRepository;

    public List<CartItem> getCart(Long userId) {
        var order = orderRepository.findByUserId(userId);
        if(order.isEmpty()) throw new RuntimeException("Order not found");

        return order.get().getCart();
    }
}
