package com.eshop.eshopstandardgateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eshop.eshopstandardgateway.model.customer.Customer;
import com.eshop.eshopstandardgateway.model.customer.login.CustomerLoginDetails;

/**
 * Service class for Customer Login
 */

@Service
public class CustomerLoginService implements UserDetailsService {
	
	@Autowired
	private CustomerService customerService; 
	
	/**
	 * Method to fetch customer by unique value.
	 * return UserDetails object for further authentication
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customerObject = null;

		customerObject = customerService.loadCustomerObjectByEmail(email);
		
		if(customerObject == null) {
			throw new UsernameNotFoundException(email);
		}

		return new CustomerLoginDetails(customerObject);
	}

}
