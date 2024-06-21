package dev.project.inventoryservice.service;

import dev.project.inventoryservice.dto.InvProductReqDto;
import dev.project.inventoryservice.model.InventoryProduct;
import dev.project.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public List<InventoryProduct> getAll() {
        var products = inventoryRepository.findAll();
        return products;
    }

    public void addProduct(InvProductReqDto reqDto) {
        var product = mapToProduct(reqDto);
        inventoryRepository.save(product);
    }

    public void updateProduct(InvProductReqDto reqDto) {

        var product = inventoryRepository.findBySkuCode(reqDto.getSkuCode());
        if(product.isEmpty()) throw new RuntimeException("Product Not Found");

        Integer newQuantity = product.get().getQuantity() + reqDto.getQuantity();
        product.get().setQuantity(newQuantity);
    }

    private InventoryProduct mapToProduct(InvProductReqDto dto) {

        return InventoryProduct.builder()
                .productName(dto.getProductName())
                .quantity(dto.getQuantity())
                .skuCode(dto.getSkuCode())
                .build();
    }

    public boolean check(InvProductReqDto request) {

        var product = inventoryRepository.findBySkuCode(request.getSkuCode());
        if(product.isEmpty()) return false;

        // Check if in stock
        if(request.getQuantity() <= product.get().getQuantity()) {
            request.setQuantity(-1 * request.getQuantity());
            // update amount
            updateProduct(request);
            return true;
        }

        return false;
    }
}
