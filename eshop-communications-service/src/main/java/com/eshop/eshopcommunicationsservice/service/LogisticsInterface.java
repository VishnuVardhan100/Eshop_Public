package com.eshop.eshopcommunicationsservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.logistics.Order;

@FeignClient("ESHOP-ORDER-SERVICE")
public interface LogisticsInterface {

	@GetMapping(value = "/customers/ordersobject", params= {"customerID", "orderID"})
	Order retrieveOrderObjectByCustomerID(long customerID, long orderID) throws CustomerException;
}
