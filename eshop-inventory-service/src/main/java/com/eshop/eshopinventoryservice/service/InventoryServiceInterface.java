package com.eshop.eshopinventoryservice.service;

import java.util.List;

import com.eshop.eshopinventoryservice.exception.InventoryProductException;
import com.eshop.eshopinventoryservice.model.inventory.InventoryProductDTO;

public interface InventoryServiceInterface {

	InventoryProductDTO addInventoryProduct(InventoryProductDTO inventoryProductDTOObject);
	
	List<InventoryProductDTO> getAllInventoryProducts();
	
	List<InventoryProductDTO> getAllInventoryProductsByName(String inventoryProductName);

	List<InventoryProductDTO> getAllInventoryProductsByPriceRange(long lowerBoundPrice , long upperBoundPrice);
	
	InventoryProductDTO updateInventoryProduct(long inventoryProductID, InventoryProductDTO inventoryProductDTOObject) throws InventoryProductException;
	
	void removeInventoryProduct(long inventoryProductID) throws InventoryProductException;
	
}
