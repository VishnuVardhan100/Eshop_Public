package com.eshop.eshoprepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eshop.eshopmodel.inventory.InventoryProduct;

/**
 * Repository for Inventory Products
 */

@Repository
public interface InventoryProductRepository extends JpaRepository<InventoryProduct, Integer>{

}
