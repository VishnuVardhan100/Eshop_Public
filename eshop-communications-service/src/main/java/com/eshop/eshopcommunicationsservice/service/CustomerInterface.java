package com.eshop.eshopcommunicationsservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.customer.Customer;

@Service
public interface CustomerInterface {

	Optional<Customer> retrieveCustomerByID(long customerID) throws CustomerException;
}
