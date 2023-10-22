package com.eshop.eshoporderservice.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;

import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.model.logistics.OrderProduct;

@FeignClient("ESHOP-INVENTORY-SERVICE")
public interface InventoryInterface {

	void performInventoryQuantityCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList) throws InventoryProductException;
	
}
