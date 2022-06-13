package com.frizzycode.productservice.services;

import com.frizzycode.productservice.exceptions.ProductAlreadyExistsException;
import com.frizzycode.productservice.models.Product;
import com.frizzycode.productservice.models.dto.ProductRequest;
import com.frizzycode.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    @Override
    public List<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).getContent();
    }

    @Override
    public void createProduct(ProductRequest productRequest) {
        productRepository.findByName(productRequest.getName())
                .ifPresentOrElse(product -> {
                    log.error("{} already exists", product);
                    throw new ProductAlreadyExistsException("Product already exists");
                        },
                        () -> {
                            log.info("Creating new product...");
                            productRepository.save(Product.builder()
                                    .name(productRequest.getName())
                                    .description(productRequest.getDescription())
                                    .price(productRequest.getPrice())
                                    .build());
                            log.info("Product created successfully");
                        }
                );
    }
}
