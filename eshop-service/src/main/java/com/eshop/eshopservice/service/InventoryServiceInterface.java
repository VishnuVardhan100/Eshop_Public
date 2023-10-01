package com.eshop.eshopservice.service;

import java.util.List;

import com.eshop.eshopmodel.inventory.InventoryProductDTO;
import com.eshop.exception.InventoryProductException;

public interface InventoryServiceInterface {

	InventoryProductDTO addInventoryProduct(InventoryProductDTO inventoryProductDTOObject);
	
	List<InventoryProductDTO> getAllInventoryProducts();
	
	List<InventoryProductDTO> getAllInventoryProductsByName(String inventoryProductName);

	List<InventoryProductDTO> getAllInventoryProductsByPriceRange(long lowerBoundPrice , long upperBoundPrice);
	
	InventoryProductDTO updateInventoryProduct(long inventoryProductID, InventoryProductDTO inventoryProductDTOObject) throws InventoryProductException;
	
	void removeInventoryProduct(long inventoryProductID) throws InventoryProductException;
	
}
