package com.frizzycode.productservice.config;

import com.frizzycode.productservice.models.Product;
import com.frizzycode.productservice.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OnStartUp {

    private final ProductRepository productRepository;

    @PostConstruct
    public void init(){
        productRepository.findByName("iPhoneXS-max")
                .ifPresentOrElse(product -> log.error("{} already exists", product),
                        () -> {
                            log.info("Creating new product...");
                            productRepository.save(Product.builder()
                                    .name("iPhoneXS-max")
                                    .description("Amazing phone")
                                    .price(BigDecimal.valueOf(300_000.00))
                                    .build());
                            log.info("Product created successfully");
                        }
                );
    }
}
