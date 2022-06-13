package com.example.inventoryservice.controllers;

import com.example.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/inventory/")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("{skuCode}")
    public ResponseEntity<Object> isInStock(@PathVariable(value = "skuCode") String skuCode){
        try {
            log.info("Checking if {} is in stock", skuCode);
            return ResponseEntity.ok().body(inventoryService.isInStock(skuCode));
        } catch (Exception exception){
            log.error("Message:{}", exception.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }
}
