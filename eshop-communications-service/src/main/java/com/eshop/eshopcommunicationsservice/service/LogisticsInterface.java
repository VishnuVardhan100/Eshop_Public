package com.eshop.eshopcommunicationsservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.logistics.Order;

@FeignClient("ESHOP-ORDER-SERVICE")
public interface LogisticsInterface {

	@GetMapping(value= "/orders/search/ordersobject", params= {"customerID", "orderID"})
	public ResponseEntity<Order> retrieveOrderObjectByCustomerID(@RequestParam(name="customerID", required=true) long customerID, @RequestParam(name="orderID", required=true)long orderID)
		throws CustomerException;
}
