package com.eshop.eshopcommunicationsservice.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.customer.Customer;

@FeignClient("ESHOP-USER-SERVICE")
public interface CustomerInterface {

	Optional<Customer> retrieveCustomerByID(long customerID) throws CustomerException;
}
