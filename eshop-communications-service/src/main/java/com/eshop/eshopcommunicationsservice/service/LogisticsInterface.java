package com.eshop.eshopcommunicationsservice.service;

import org.springframework.stereotype.Service;

import com.eshop.eshopcommunicationsservice.model.logistics.Order;
import com.eshop.eshopcommunicationsservice.exception.CustomerException;

@Service
public interface LogisticsInterface {

	Order retrieveOrderObjectByCustomerID(long customerID, long orderID) throws CustomerException;
}
