package com.eshop.eshopservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.eshopmodel.customer.CustomerLoginDetails;
import com.eshop.eshoprepository.CustomerRepository;
import com.eshop.eshopservice.mapper.CustomerCustomModelMapper;

/**
 * Service class for Customer Login
 */

@Service
public class CustomerLoginService implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerCustomModelMapper customerCustomModelMapper;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Customer> customerObject = null;
		CustomerDTO customerDTOObject = null;

		customerObject = Optional.of(customerRepository.loadCustomerByEmail(email));
		if(customerObject.isPresent()) {
			customerDTOObject = customerCustomModelMapper.mapCustomerToCustomerDTO(customerObject.get());
		}

		return new CustomerLoginDetails(customerDTOObject);
	}

}
