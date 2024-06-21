package dev.project.orderservice.proxy;

import dev.project.orderservice.dto.InvProductReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${INVENTORY_SERVICE_HOST:http://localhost}:8200/api/v1/inventory")
public interface InventoryProxy {
    @PostMapping("/check")
    public boolean check(@RequestBody InvProductReqDto request);
}
