package com.eshop.eshopservice.service;

import java.util.List;

import com.eshop.eshopmodel.customer.CustomerAddressDTO;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.exception.CustomerAddressException;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;

public interface CustomerAddressServiceInterface {

	CustomerAddressDTO addCustomerAddress (CustomerDTO customerDTOObject , CustomerAddressDTO customerAddressDTOObject) throws CustomerException,
	InvalidInputException;
	
	List<CustomerAddressDTO> retrieveAllCustomerAddressesByCustomerID(long customerID) throws CustomerException;

	CustomerAddressDTO updateCustomerAddressInfo(long customerID, CustomerAddressDTO customerAddressDTO) throws CustomerException, CustomerAddressException;

	void deleteCustomerAddress (long customerId, long customerAddressId) throws CustomerException, CustomerAddressException;

	void deleteAllCustomerAddresses(long customerID, List<Long> customerAddressIDs) throws CustomerException, CustomerAddressException;

}
