package dev.project.productservice.proxy;

import dev.project.productservice.dto.InvProductReqDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", url = "${INVENTORY_SERVICE_HOST:localhost}:8200/api/v1/inventory")
public interface InventoryProxy {

    @PostMapping("/add")
    public void addItem(@RequestBody InvProductReqDto request);

    @PutMapping("/update")
    public void update(@RequestBody InvProductReqDto request);
}
