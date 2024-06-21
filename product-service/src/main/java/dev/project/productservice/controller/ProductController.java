package dev.project.productservice.controller;

import dev.project.productservice.dto.ProductReqDto;
import dev.project.productservice.dto.ProductResDto;
import dev.project.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductResDto> getAll() {
        return productService.getAll();
    }

    @GetMapping("/{skuCode}")
    public ProductResDto getOne(@PathVariable("skuCode") String skuCode) {
        return productService.getOne(skuCode);
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody ProductReqDto productReqDto) {
        productService.create(productReqDto);
    }
}
