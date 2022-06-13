package com.frizzycode.productservice.services;

import com.frizzycode.productservice.models.Product;
import com.frizzycode.productservice.models.dto.ProductRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts(Pageable pageable);

    void createProduct(ProductRequest productRequest);
}
