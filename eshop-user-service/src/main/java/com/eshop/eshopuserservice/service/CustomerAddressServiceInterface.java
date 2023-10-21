package com.eshop.eshopuserservice.service;

import java.util.List;

import com.eshop.eshopuserservice.exception.CustomerAddressException;
import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.exception.InvalidInputException;
import com.eshop.eshopuserservice.model.customer.CustomerAddressDTO;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;

public interface CustomerAddressServiceInterface {

	CustomerAddressDTO addCustomerAddress (CustomerDTO customerDTOObject , CustomerAddressDTO customerAddressDTOObject) throws CustomerException,
	InvalidInputException;
	
	List<CustomerAddressDTO> retrieveAllCustomerAddressesByCustomerID(long customerID) throws CustomerException;

	CustomerAddressDTO updateCustomerAddressInfo(long customerID, CustomerAddressDTO customerAddressDTO) throws CustomerException, CustomerAddressException;

	void deleteCustomerAddress (long customerId, long customerAddressId) throws CustomerException, CustomerAddressException;

	void deleteAllCustomerAddresses(long customerID, List<Long> customerAddressIDs) throws CustomerException, CustomerAddressException;

}
