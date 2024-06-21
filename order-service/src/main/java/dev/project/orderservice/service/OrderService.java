package dev.project.orderservice.service;

import dev.project.orderservice.dto.InvProductReqDto;
import dev.project.orderservice.model.CartItem;
import dev.project.orderservice.model.Order;
import dev.project.orderservice.proxy.InventoryProxy;
import dev.project.orderservice.proxy.ProductProxy;
import dev.project.orderservice.repository.CartItemRepository;
import dev.project.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional @Slf4j
public class OrderService {
    private final InventoryProxy inventoryProxy;
    private final ProductProxy productProxy;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    public String placeOrder(InvProductReqDto orderReqDto, Long userId, String username) {
        // check if in stock
        if(!inventoryProxy.check(orderReqDto)) {
            return "Product out of stock";
        }
        // check if user is present
        var order = orderRepository.findByUserId(userId);
        var item = productProxy.getOne(orderReqDto.getSkuCode());
        item.setQuantity(orderReqDto.getQuantity());
        if(order.isPresent()) {
            log.info("Order already present");
            item.setOrder(order.get());
            
        }else {
            log.info("Order Not Found Creating new one");
            var newOrder = Order.builder()
                    .userId(userId)
                    .userName(username)
                    .build();
            item.setOrder(newOrder);
        }

        cartItemRepository.save(item);

        return "Order Place successfully";
    }
}
