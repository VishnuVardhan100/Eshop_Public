package com.eshop.eshopgateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.eshop.eshopgateway.model.customer.Customer;

@FeignClient("ESHOP-USER-SERVICE")
public interface CustomerInterface {

	@GetMapping(path = "/customers/loadobject", params={"customerEmail"})
	Customer getCustomerObjectByEmail(String email);
}
