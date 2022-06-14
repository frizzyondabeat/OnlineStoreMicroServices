package com.example.inventoryservice.repositories;

import com.example.inventoryservice.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
    @Query("select i from Inventory i where i.skuCode = ?1")
    List<Inventory> findInventoriesBySkuCode(List<String> skuCode);
}
