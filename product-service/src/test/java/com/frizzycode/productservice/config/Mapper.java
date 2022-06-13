package com.frizzycode.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class Mapper {

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
}
