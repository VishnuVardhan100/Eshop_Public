package com.eshop.eshopservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.customer.CustomerAddress;
import com.eshop.eshopmodel.customer.CustomerAddressDTO;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.eshopmodel.customer.CustomerSignUpDTO;

/**
 * Custom Model Mapper class to map:
 * 	Customer to CustomerDTO and vice versa.
 * 	CustomerAddress to CustomerAddressDTO and vice versa.
 */

@Service
public class CustomerModelMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * No argument Constructor
	 */
	public CustomerModelMapper() {
		
	}

	/**
	 * To map Customer to a CustomerDTO Object
	 * @param Customer Object
	 * @return CustomerDTO Object
	 */
	public CustomerDTO mapCustomerToCustomerDTO(Customer customerObject) {
		CustomerDTO customerDTOReturnObject = modelMapper.map(customerObject, CustomerDTO.class);
		return  customerDTOReturnObject;
	}
	
	/**
	 * To map CustomerDTO to a Customer Object
	 * @param CustomerDTO Object
	 * @return Customer Object
	 */
	public Customer mapCustomerDTOToCustomer(CustomerDTO CustomerDTOObject) {
		Customer customerReturnObject = modelMapper.map(CustomerDTOObject, Customer.class);
		return  customerReturnObject;
	}

	/**
	 * To map CustomerSignUpDTO to a Customer Object
	 * @param CustomerSignUpDTO Object
	 * @return Customer Object
	 */
	public Customer mapCustomerSignUpDTOToCustomer(CustomerSignUpDTO CustomerSignUpDTOObject) {
		Customer customerReturnObject = modelMapper.map(CustomerSignUpDTOObject, Customer.class);
		return  customerReturnObject;
	}

	/**
	 * To map CustomerAddress to a CustomerAddressDTO Object
	 * @param CustomerAddress Object
	 * @return CustomerAddressDTO Object
	 */
	public CustomerAddressDTO mapCustomerAddressToCustomerAddressDTO(CustomerAddress customerAddressObject) {
		CustomerAddressDTO customerAddressDTOReturnObject = modelMapper.map(customerAddressObject, CustomerAddressDTO.class);
		return  customerAddressDTOReturnObject;
	}
	
	/**
	 * To map CustomerAddressDTO object to a CustomerAddress Object
	 * @param CustomerAddressDTO Object
	 * @return CustomerAddress Object
	 */
	public CustomerAddress mapCustomerAddressDTOToCustomerAddress(CustomerAddressDTO customerAddressDTOObject) {
		CustomerAddress customerAddressObject = modelMapper.map(customerAddressDTOObject, CustomerAddress.class);
		return  customerAddressObject;
	}
	
}
