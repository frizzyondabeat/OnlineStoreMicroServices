package com.example.inventoryservice.config;

import com.example.inventoryservice.models.Inventory;
import com.example.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StartUp {

    private final InventoryRepository inventoryRepository;

    @PostConstruct
    public void init(){
        String iPhone13 = "iPhone13";
        String iPhone13Blue = iPhone13 + "_blue";
        inventoryRepository.findBySkuCode(iPhone13).ifPresentOrElse(
                inventory -> log.error("SkuCode already exists"),
                () -> inventoryRepository.save(Inventory.builder()
                                .skuCode(iPhone13)
                                .quantity(100)
                        .build()
                )
        );
        inventoryRepository.findBySkuCode(iPhone13Blue).ifPresentOrElse(
                inventory -> log.error("SkuCode already exists"),
                () -> inventoryRepository.save(Inventory.builder()
                        .skuCode(iPhone13Blue)
                        .quantity(78)
                        .build()
                )
        );
    }

}
