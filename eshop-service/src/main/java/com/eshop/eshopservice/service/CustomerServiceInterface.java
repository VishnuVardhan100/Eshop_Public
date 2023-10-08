package com.eshop.eshopservice.service;

import java.util.List;
import java.util.Locale;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.exception.CustomerAddressException;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;

public interface CustomerServiceInterface {
	
	CustomerDTO createCustomer(CustomerDTO customerDTO, Locale locale) throws InvalidInputException, CustomerException;
	
	boolean emailExists(String customerEmail);

	CustomerDTO retrieveCustomerByID(long customerID, Locale locale) throws CustomerException;

	Customer retrieveCustomerObjectByID(long customerID, Locale locale) throws CustomerException;
	
	List<CustomerDTO> retrieveCustomersByFirstName(String firstName);

	List<CustomerDTO> retrieveCustomersByLastName(String lastName);	

	List<CustomerDTO> retrieveCustomersByEmail(String email);

	List<CustomerDTO> retrieveAllCustomers();

	CustomerDTO updateCustomerInfo(long customerID, CustomerDTO customerDTO) throws CustomerException, CustomerAddressException;

	void deleteCustomer (long customerID) throws CustomerException, IllegalArgumentException;

}
