package com.eshop.eshopgateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eshop.eshopgateway.model.customer.Customer;
import com.eshop.eshopgateway.model.customer.login.CustomerLoginDetails;

/**
 * Service class for Customer Login
 */

@Service
public class CustomerLoginService implements UserDetailsService {

	@Autowired
	private CustomerInterface customerInterface;
	
	/**
	 * Method to fetch customer by unique value.
	 * return UserDetails object for further authentication
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Customer customerObject = null;

		customerObject = customerInterface.getCustomerObjectByEmail(email);
		if(customerObject == null) {
			throw new UsernameNotFoundException(email);
		}

		return new CustomerLoginDetails(customerObject);
	}

}
