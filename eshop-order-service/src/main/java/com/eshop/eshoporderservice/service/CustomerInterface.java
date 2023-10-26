package com.eshop.eshoporderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshop.eshoporderservice.exception.CustomerException;
import com.eshop.eshoporderservice.model.customer.Customer;

@FeignClient("ESHOP-USER-SERVICE")
public interface CustomerInterface {

	@GetMapping(path= "/customers/searchbyid" , params= {"customerID"})
	public ResponseEntity<Customer> getCustomerObjectByID(@RequestParam(name="customerID", required=true) long customerID) throws CustomerException;

}
