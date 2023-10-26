package com.eshop.eshopuserservice.service;

import java.util.List;
import java.util.Locale;

import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.model.customer.Customer;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;
import com.eshop.eshopuserservice.model.customer.CustomerSignUpDTO;

public interface CustomerServiceInterface {
	
	CustomerDTO createCustomer(CustomerSignUpDTO customerSignUpDTOObject, Locale locale) throws CustomerException;
	
	boolean emailExists(String customerEmail);
	
	CustomerDTO loadCustomerByEmail(String customerEmail) throws CustomerException;
	
	Customer loadCustomerObjectByEmail(String customerEmail) throws CustomerException;

	CustomerDTO retrieveCustomerByID(long customerID, Locale locale) throws CustomerException;
	
	Customer retrieveCustomerByID(long customerID) throws CustomerException;

	boolean customerExists(long customerID) throws CustomerException;
	
	List<CustomerDTO> retrieveCustomersByFirstName(String firstName);

	List<CustomerDTO> retrieveCustomersByLastName(String lastName);	

	List<CustomerDTO> retrieveAllCustomers();

	CustomerDTO updateCustomerInfo(long customerID, CustomerDTO customerDTO) throws CustomerException;
	
	void updateCustomerPassword(long customerID, String customerOldPassword, String customerNewPassword) throws CustomerException;

	void deleteCustomer (long customerID) throws CustomerException, IllegalArgumentException;

}
