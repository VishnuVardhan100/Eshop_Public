package com.eshop.eshopstandardgateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshopstandardgateway.model.customer.Customer;
import com.eshop.eshopstandardgateway.repository.CustomerRepository;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	/**
	 * To get Customer object from repo
	 * @param customerEmail
	 * @return customer class object
	 */
	public Customer loadCustomerObjectByEmail(String customerEmail) {
		Customer customerRetrieveObject  = customerRepository.loadCustomerByEmail(customerEmail);
		return customerRetrieveObject;
	}

}
