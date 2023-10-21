package com.eshop.eshoporderservice.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eshop.eshoporderservice.exception.CustomerException;
import com.eshop.eshoporderservice.model.customer.Customer;

@Service
public interface CustomerInterface {

	Optional<Customer> retrieveCustomerByID(long customerID) throws CustomerException;

	boolean customerExists(long customerID) throws CustomerException;

}
