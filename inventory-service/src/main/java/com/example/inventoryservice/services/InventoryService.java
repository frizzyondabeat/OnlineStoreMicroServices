package com.example.inventoryservice.services;

import com.example.inventoryservice.models.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {
    List<InventoryResponse> isInStock(List<String> skuCode);
}
