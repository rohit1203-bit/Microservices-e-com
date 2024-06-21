package dev.project.inventoryservice.repository;

import dev.project.inventoryservice.model.InventoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryProduct, Long> {

    Optional<InventoryProduct> findBySkuCode(String skuCode);
}
