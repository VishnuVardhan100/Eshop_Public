package com.eshop.eshoporderservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.model.logistics.OrderProduct;

@Service
public interface InventoryInterface {

	void performInventoryQuantityCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList) throws InventoryProductException;
	
}
