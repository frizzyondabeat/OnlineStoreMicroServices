package com.frizzycode.productservice.controllers.integration;

import com.frizzycode.productservice.models.dto.ProductRequest;
import com.frizzycode.productservice.models.dto.ProductResponse;
import com.frizzycode.productservice.repositories.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "integration")
class ProductControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    public void tearDown() {
        productRepository.deleteAll();
    }

    static ProductRequest request = ProductRequest.builder()
            .name("Xbox console")
            .description("A video game console")
            .price(BigDecimal.valueOf(250500.67))
            .build();

    @Nested
    class gettingProducts{
        @Test
        void getAllProducts() {
            ResponseEntity<List<ProductResponse>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/v1/product/", HttpMethod.GET, null, new ParameterizedTypeReference<>(){});
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }

        @Test
        void unableToGetAllProducts() {
            ResponseEntity<List<ProductResponse>> responseEntity = testRestTemplate.exchange("http://localhost:" + port + "/api/v1/product/", HttpMethod.GET, null, new ParameterizedTypeReference<>(){}, Map.of("sort", "progress"));
            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        }
    }

    @Nested
    class creatingProduct{
        @Test
        void createProduct() {
            ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/product/",
                    request,
                    String.class
            );
            assertEquals(HttpStatus.CREATED, response.getStatusCode());
        }

        @Test
        void unableToCreateProduct() {
            ResponseEntity<String> response = testRestTemplate.postForEntity("http://localhost:" + port + "/api/v1/product/",
                    null,
                    String.class
            );
            assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
        }
    }

}