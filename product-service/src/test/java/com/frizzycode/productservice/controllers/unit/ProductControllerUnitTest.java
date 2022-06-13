package com.frizzycode.productservice.controllers.unit;


import com.frizzycode.productservice.exceptions.ProductAlreadyExistsException;
import com.frizzycode.productservice.models.dto.ProductRequest;
import com.frizzycode.productservice.repositories.ProductRepository;
import com.frizzycode.productservice.services.ProductService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerUnitTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository repository;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getAllProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product/")).andExpect(status().isOk());
    }

    @Nested
    class creatingProduct{
        @Test
        void createProduct() throws Exception {
            ProductRequest productRequest = ProductRequest.builder()
                    .name("Sony Television")
                    .description("A new age smart television")
                    .price(BigDecimal.valueOf(150000.99))
                    .build();
            mockMvc.perform(
                            MockMvcRequestBuilders.post("/api/v1/product/")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(objectMapper.writeValueAsString(productRequest))
                    )
                    .andExpect(status().isCreated());
            assertThrows(ProductAlreadyExistsException.class, () -> productService.createProduct(productRequest), "Product already exists");
        }
    }


}
