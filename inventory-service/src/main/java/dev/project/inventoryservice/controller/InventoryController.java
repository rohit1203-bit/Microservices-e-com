package dev.project.inventoryservice.controller;

import dev.project.inventoryservice.dto.InvProductReqDto;
import dev.project.inventoryservice.model.InventoryProduct;
import dev.project.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    // All routes only for testing purpose
    // Not available for clients to directly access

    @GetMapping
    public List<InventoryProduct> getAll() {
        return inventoryService.getAll();
    }

    @PostMapping("/add") @ResponseStatus(HttpStatus.CREATED)
    public void addItem(@RequestBody InvProductReqDto request) {
        inventoryService.addProduct(request);
    }

    @PutMapping("/update") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody InvProductReqDto request) {
        inventoryService.updateProduct(request);
    }

    @PostMapping("/check")
    public boolean check(@RequestBody InvProductReqDto request) {
        return inventoryService.check(request);
    }
}
