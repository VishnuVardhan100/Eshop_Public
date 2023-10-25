package com.eshop.eshopinventoryservice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.eshop.eshopinventoryservice.exception.InventoryProductException;
import com.eshop.eshopinventoryservice.model.inventory.InventoryProductDTO;
import com.eshop.eshopinventoryservice.model.logistics.OrderProduct;

public interface InventoryServiceInterface {

	InventoryProductDTO addInventoryProduct(InventoryProductDTO inventoryProductDTOObject);
	
	List<InventoryProductDTO> addMultipleInventoryProducts(MultipartFile file) throws IOException;
	
	List<InventoryProductDTO> getAllInventoryProducts();
	
	List<InventoryProductDTO> getAllInventoryProductsByName(String inventoryProductName);

	List<InventoryProductDTO> getAllInventoryProductsByPriceRange(long lowerBoundPrice , long upperBoundPrice);
	
	InventoryProductDTO updateInventoryProduct(long inventoryProductID, InventoryProductDTO inventoryProductDTOObject) throws InventoryProductException;
	
	void removeInventoryProduct(long inventoryProductID) throws InventoryProductException;
	
	void performInventoryQuantityCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList)
			throws InventoryProductException;
	
}
