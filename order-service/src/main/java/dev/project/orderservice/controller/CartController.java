package dev.project.orderservice.controller;

import dev.project.orderservice.model.CartItem;
import dev.project.orderservice.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping
    public List<CartItem> getCart(@RequestHeader("X-userId") Long userId) {
        return cartService.getCart(userId);
    }
}
