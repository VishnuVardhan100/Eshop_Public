package com.eshop.eshoporderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.model.inventory.WrapperPerformCheckAndAdjust;

import jakarta.validation.Valid;

@FeignClient("ESHOP-INVENTORY-SERVICE")
public interface InventoryInterface {

	@PostMapping(value= "/admin/inventory/update/checkandadjust")
	void performInventoryQuantityCheckAndAdjust(@RequestBody(required=true) @Valid WrapperPerformCheckAndAdjust wrapperPerformCheckAndAdjustObject) 
			throws InventoryProductException;
	
}
