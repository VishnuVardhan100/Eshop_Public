package com.eshop.eshoporderservice.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.model.logistics.OrderProduct;

@FeignClient("ESHOP-INVENTORY-SERVICE")
public interface InventoryInterface {

	@PostMapping(value= "/admin/inventory/update/checkandadjust", params= {"inventoryProductIDList","orderProductList"})
	void performInventoryQuantityCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList) throws InventoryProductException;
	
}
