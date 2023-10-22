package com.eshop.eshoporderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshop.eshoporderservice.exception.CustomerException;
import com.eshop.eshoporderservice.model.customer.Customer;

@FeignClient("ESHOP-USER-SERVICE")
public interface CustomerInterface {

	@GetMapping(path= "customers/searchbyid" , params= {"customerID"})
	Customer getCustomerByID(@RequestParam(name="customerID", required=true) long customerID) throws CustomerException;

}
