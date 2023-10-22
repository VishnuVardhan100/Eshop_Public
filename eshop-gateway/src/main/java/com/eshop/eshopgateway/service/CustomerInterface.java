package com.eshop.eshopgateway.service;

import java.util.Optional;

import com.eshop.eshopgateway.model.customer.Customer;

public interface CustomerInterface {

	Optional<Customer> loadCustomerByEmail(String email);
}
