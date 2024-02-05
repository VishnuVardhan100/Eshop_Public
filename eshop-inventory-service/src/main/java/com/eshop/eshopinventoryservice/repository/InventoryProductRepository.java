package com.eshop.eshopinventoryservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.eshop.eshopinventoryservice.model.inventory.InventoryProduct;

/**
 * Repository for Inventory Products
 */

@Repository
public interface InventoryProductRepository extends JpaRepository<InventoryProduct, Long>{

	/**
	 * Get all Products in inventory by name criteria
	 * @param InventoryProductName inventoryProductName
	 * @return matching list of inventory products
	 */
	@Query(value="Select * from Inventory_Product inv_Pro where inv_Pro.Inventory_Product_Name like :inventoryProductName", nativeQuery=true)
	List<InventoryProduct> getAllInventoryProductsByName(@Param("inventoryProductName") String inventoryProductName);
	
	/**
	 * Get all Products in inventory by price criteria specified in range
	 * @param LowerBoundPrice lowerBoundPrice
	 * @param UpperBoundPrice upperBoundPrice
	 * @return matching list of inventory products
	 */
	@Query(value="Select * from Inventory_Product inv_Pro where inv_Pro.Inventory_Product_Price "
			+ "between :lowerBoundPrice and :upperBoundPrice", nativeQuery=true)
	List<InventoryProduct> getAllInventoryProductsByPriceRange(@Param("lowerBoundPrice") long lowerBoundPrice,
			@Param("upperBoundPrice") long upperBoundPrice);
	
	
	/**
	 * @param InventoryProductName inventoryProductName
	 * @return inventory product with matching inventory product name
	 */
	InventoryProduct findByInventoryProductName(String inventoryProductName);
	
}
