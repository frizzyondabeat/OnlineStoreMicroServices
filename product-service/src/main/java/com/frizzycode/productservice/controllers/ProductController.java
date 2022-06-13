package com.frizzycode.productservice.controllers;

import com.frizzycode.productservice.models.dto.ProductRequest;
import com.frizzycode.productservice.models.dto.ProductResponse;
import com.frizzycode.productservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("api/v1/product/")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getAllProducts(@PageableDefault(sort = "name") Pageable pageable){
        try {
            log.info("Fetching all products.....");
            return ResponseEntity.ok().body(productService.getAllProducts(pageable).stream()
                    .map(product -> ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .description(product.getDescription())
                            .price(product.getPrice())
                            .build()
                    ).toList());
        } catch (Exception exception){
            log.error("Error fetching products.\nMessage:{}", exception.getMessage());
            return new ResponseEntity<>("Unable to get products.", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@NotNull @RequestBody ProductRequest productRequest){
        try {
            log.info("Creating product {}", productRequest);
            productService.createProduct(productRequest);
            return new ResponseEntity<>("Product Created Successfully", HttpStatus.CREATED);
        } catch (Exception exception){
            log.error("Error creating product:{}.\nMessage:{}", productRequest, exception.getMessage());
            return new ResponseEntity<>("Unable to create product. " + exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
