package com.eshop.eshopservice.service;

import java.util.List;
import java.util.Locale;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.eshopmodel.customer.CustomerSignUpDTO;
import com.eshop.exception.CustomerException;

public interface CustomerServiceInterface {
	
	CustomerDTO createCustomer(CustomerSignUpDTO customerSignUpDTOObject, Locale locale) throws CustomerException;
	
	boolean emailExists(String customerEmail);
	
	CustomerDTO loadCustomerByEmail(String customerEmail) throws CustomerException;

	CustomerDTO retrieveCustomerByID(long customerID, Locale locale) throws CustomerException;

	Customer retrieveCustomerObjectByID(long customerID, Locale locale) throws CustomerException;
	
	List<CustomerDTO> retrieveCustomersByFirstName(String firstName);

	List<CustomerDTO> retrieveCustomersByLastName(String lastName);	

	List<CustomerDTO> retrieveCustomersByEmail(String email);

	List<CustomerDTO> retrieveAllCustomers();

	CustomerDTO updateCustomerInfo(long customerID, CustomerDTO customerDTO) throws CustomerException;
	
	void updateCustomerPassword(long customerID, String customerOldPassword, String customerNewPassword) throws CustomerException;

	void deleteCustomer (long customerID) throws CustomerException, IllegalArgumentException;

}
