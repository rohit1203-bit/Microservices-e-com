package dev.project.productservice.service;

import dev.project.productservice.dto.InvProductReqDto;
import dev.project.productservice.dto.ProductReqDto;
import dev.project.productservice.dto.ProductResDto;
import dev.project.productservice.model.Product;
import dev.project.productservice.proxy.InventoryProxy;
import dev.project.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final InventoryProxy inventoryProxy;

    public List<ProductResDto> getAll() {
        return productRepository.findAll().stream().map(this::mapToDto).toList();
    }

    public ProductResDto getOne(String skuCode) {
        var product = productRepository.findBySkuCode(skuCode);
        if(product.isEmpty()) throw new RuntimeException("Product Not Found");

        return mapToDto(product.get());
    }

    public void create(ProductReqDto productReqDto) {
        var exists = productRepository.findBySkuCode(productReqDto.getSkuCode());

        var inventoryProduct = InvProductReqDto.builder()
                .productName(productReqDto.getProductName())
                .skuCode(productReqDto.getSkuCode())
                .quantity(productReqDto.getQuantity())
                .build();

        if(exists.isPresent()) {
            // call to inventory service to increase the quantity
            inventoryProxy.update(inventoryProduct);
            return;
        }
        var product = mapToProduct(productReqDto);
        productRepository.save(product);

        // call to Inventory service to create new Entry
        inventoryProxy.addItem(inventoryProduct);
    }

    private Product mapToProduct(ProductReqDto productReqDto) {
        return Product.builder()
                .productName(productReqDto.getProductName())
                .description(productReqDto.getDescription())
                .skuCode(productReqDto.getSkuCode())
                .price(productReqDto.getPrice())
                .build();
    }

    private ProductResDto mapToDto(Product product) {
        return ProductResDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .skuCode(product.getSkuCode())
                .price(product.getPrice())
                .build();
    }

}
