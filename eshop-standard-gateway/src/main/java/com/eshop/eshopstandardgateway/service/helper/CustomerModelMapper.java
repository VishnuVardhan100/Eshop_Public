package com.eshop.eshopstandardgateway.service.helper;

import com.eshop.eshopuserservice.model.customer.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return modelMapper.map(customerObject, CustomerDTO.class);
	}
	
	/**
	 * To map CustomerDTO to a Customer Object
	 * @param CustomerDTO Object
	 * @return Customer Object
	 */
	public Customer mapCustomerDTOToCustomer(CustomerDTO customerDTOObject) {
        return modelMapper.map(customerDTOObject, Customer.class);
	}

	/**
	 * To map CustomerSignUpDTO to a Customer Object
	 * @param CustomerSignUpDTO Object
	 * @return Customer Object
	 */
	public Customer mapCustomerSignUpDTOToCustomer(CustomerSignUpDTO customerSignUpDTOObject) {
        return modelMapper.map(customerSignUpDTOObject, Customer.class);
	}

	/**
	 * To map CustomerAddress to a CustomerAddressDTO Object
	 * @param CustomerAddress Object
	 * @return CustomerAddressDTO Object
	 */
	public CustomerAddressDTO mapCustomerAddressToCustomerAddressDTO(CustomerAddress customerAddressObject) {
        return modelMapper.map(customerAddressObject, CustomerAddressDTO.class);
	}
	
	/**
	 * To map CustomerAddressDTO object to a CustomerAddress Object
	 * @param CustomerAddressDTO Object
	 * @return CustomerAddress Object
	 */
	public CustomerAddress mapCustomerAddressDTOToCustomerAddress(CustomerAddressDTO customerAddressDTOObject) {
        return modelMapper.map(customerAddressDTOObject, CustomerAddress.class);
	}
	
}
