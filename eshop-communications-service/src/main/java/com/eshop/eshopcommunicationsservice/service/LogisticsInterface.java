package com.eshop.eshopcommunicationsservice.service;

import org.springframework.cloud.openfeign.FeignClient;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.logistics.Order;

@FeignClient("ESHOP-ORDER-SERVICE")
public interface LogisticsInterface {

	Order retrieveOrderObjectByCustomerID(long customerID, long orderID) throws CustomerException;
}
