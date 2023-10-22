package com.eshop.eshopgateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshop.eshopgateway.model.customer.Customer;

@FeignClient("ESHOP-USER-SERVICE")
public interface CustomerInterface {

	@GetMapping(path = "/customers/loadobject", params={"customerEmail"})
	Customer getCustomerObjectByEmail(@RequestParam(name="customerEmail", required=true) String customerEmail);
}
