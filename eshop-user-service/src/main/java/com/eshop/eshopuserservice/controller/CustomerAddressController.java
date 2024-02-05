package com.eshop.eshopuserservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopuserservice.exception.CustomerAddressException;
import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.exception.InvalidInputException;
import com.eshop.eshopuserservice.model.customer.CustomerAddressDTO;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;
import com.eshop.eshopuserservice.model.customer.WrapperCustomerAddress;
import com.eshop.eshopuserservice.service.CustomerAddressService;

import jakarta.validation.Valid;

@RestController
public class CustomerAddressController {
	
	@Autowired
	private CustomerAddressService customerAddressService;

	/**
	 * Add customer address for customer
	 * @param WrapperCustomerAddress Object
	 * @return CustomerAddressDTO object that is added
	 * @throws CustomerException customerException
	 * @throws InvalidInputException invalidInputException
	 */
	@PostMapping("/customers/addresses")
	public ResponseEntity<CustomerAddressDTO> addCustomerAddress(@RequestBody(required=true) @Valid WrapperCustomerAddress wrapperCustomerAddressObject) 
			throws CustomerException, InvalidInputException {
		CustomerDTO customerDTOObject = wrapperCustomerAddressObject.getCustomerDTOObject();
		CustomerAddressDTO customerAddressDTOObject = wrapperCustomerAddressObject.getCustomerAddressDTOObject();
		return new ResponseEntity<>(customerAddressService.addCustomerAddress(customerDTOObject, customerAddressDTOObject),HttpStatus.CREATED);
	}
	
	/**
	 * Get all Customer address for a respective Customer
	 * @param Customer ID
	 * @return list of added Customer addresses for required Customer
	 * @throws CustomerException customerException
	 */
	@GetMapping("/customers/addresses/search/{customerID}")
	public ResponseEntity<List<CustomerAddressDTO>> retrieveAllCustomerAddressesByCustomerID(@PathVariable(name="customerID", required=true) long customerID) 
			throws CustomerException {
		return new ResponseEntity<> (customerAddressService.retrieveAllCustomerAddressesByCustomerID(customerID), HttpStatus.OK);
	}

	/**
	 * Update specific Customer address for required Customer
	 * @param CustomerAddress ID
	 * @param CustomerAddressDTO Object
	 * @return updated Customer address
	 * @throws CustomerException customerException
	 * @throws CustomerAddressException customerAddressException
	 */
	@PutMapping("/customers/addresses/{customerID}")
	public ResponseEntity<CustomerAddressDTO> updateCustomerAddressInfo(@PathVariable(name="customerID", required=true) long customerID, 
			@RequestBody(required=true) @Valid CustomerAddressDTO customerAddressDTOObject) throws CustomerException, CustomerAddressException {
		return new ResponseEntity<> (customerAddressService.updateCustomerAddressInfo(customerID, customerAddressDTOObject), HttpStatus.OK);
	}

	/**
	 * Delete specified Customer Address for required Customer
	 * @param Customer ID
	 * @param CustomerAddress ID
	 * @return OK status if successful
	 * @throws CustomerException customerException
	 * @throws CustomerAddressException customerAddressException
	 */
	@DeleteMapping(path="/customers/addresses", params={"customerID","customerAddressID"})
	public ResponseEntity<Object> deleteCustomerAddress (@RequestParam(name="customerID", required=true) long customerID, 
			@RequestParam(name="customerAddressID", required=true) long customerAddressID) throws CustomerException, CustomerAddressException {
		customerAddressService.deleteCustomerAddress(customerID, customerAddressID);
		return new ResponseEntity<> ("Selected customer address has been deleted successfully", HttpStatus.OK);
	}

	/**
	 * Delete Customer addresses based on list of Customer IDs
	 * @param Customer ID
	 * @param CustomerAddress IDs
	 * @return OK status if successful
	 * @throws CustomerException customerException
	 * @throws CustomerAddressException customerAddressException
	 */
	@DeleteMapping(path="/customers/addresses", params={"customerID","customerAddressIDs"})
	public ResponseEntity<Object> deleteAllCustomerAddresses(@RequestParam(name="customerID", required=true) long customerID, 
			@RequestBody(required=true) List<Long> customerAddressIDs) throws CustomerException, CustomerAddressException {
		customerAddressService.deleteAllCustomerAddresses(customerID, customerAddressIDs);		
		return new ResponseEntity<> ("All selected customer addresses have been deleted successfully", HttpStatus.OK);
	}

}
