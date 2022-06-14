package com.example.inventoryservice.controllers;

import com.example.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory/")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    public ResponseEntity<Object> isInStock(@RequestParam List<String> skuCode){
        try {
            log.info("Checking if {} is in stock", skuCode);
            return ResponseEntity.ok().body(inventoryService.isInStock(skuCode));
        } catch (Exception exception){
            log.error("Message:{}", exception.getMessage());
            return ResponseEntity.badRequest().body(false);
        }
    }
}
