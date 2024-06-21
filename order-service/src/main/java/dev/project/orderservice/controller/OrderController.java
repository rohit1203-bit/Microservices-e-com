package dev.project.orderservice.controller;

import dev.project.orderservice.dto.InvProductReqDto;
import dev.project.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public String placeOrder(@RequestBody InvProductReqDto orderReqDto,
                             @RequestHeader("X-userId") Long userId,
                             @RequestHeader("X-userName") String username) {
        return orderService.placeOrder(orderReqDto, userId, username);
    }

}
