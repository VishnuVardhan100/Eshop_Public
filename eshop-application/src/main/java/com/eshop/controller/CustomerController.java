package com.eshop.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopmodel.customer.CustomerAddressDTO;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.eshopservice.service.CustomerService;
import com.eshop.exception.CustomerAddressException;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;

import jakarta.validation.Valid;

/**
 * Customers respective controller for handling web requests for Customers and Customer Addresses.
 */

@RestController()
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	/**
	 * Method to create new Customer
	 * @param CustomerDTO Object
	 * @param locale
	 * @return Response Entity Object having new CustomerDTO object and created status
	 * @throws InvalidInputException
	 */
	@PostMapping("/customers/create")
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody(required=true) @Valid CustomerDTO customerDTOObject,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws InvalidInputException {
		CustomerDTO customerDTOReturnObject = customerService.createCustomer(customerDTOObject, locale);
		return new ResponseEntity<CustomerDTO> (customerDTOReturnObject, HttpStatus.CREATED);
	}

	/**
	 * Retrieve Customer by ID
	 * @param Customer ID
	 * @param locale
	 * @return Customer object if exists
	 * @throws CustomerException
	 */
	@GetMapping("/customers/search/{customerID}")
	public ResponseEntity<CustomerDTO> getCustomerByID(@PathVariable(name="customerID", required=true) long customerID,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws CustomerException {
		return new ResponseEntity<CustomerDTO> (customerService.retrieveCustomerByID(customerID, locale),HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get Customers based on first name
	 * @param first name
	 * @return list of Customers matching first name criteria
	 */
	@GetMapping(path="/customers/search", params={"firstName"})
	public ResponseEntity<List<CustomerDTO>> getCustomersByFirstName(@RequestParam(value="firstName", required=true) String firstName) {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveCustomersByFirstName(firstName), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get Customers based on last name
	 * @param lastName
	 * @return list of Customers matching last name criteria
	 */
	@GetMapping(path="/customers/search", params={"lastName"})
	public ResponseEntity<List<CustomerDTO>> getCustomersByLastName(@RequestParam(value="lastName", required=true) String lastName) {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveCustomersByLastName(lastName), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get Customers based on email
	 * @param email
	 * @return list of Customers matching email criteria
	 */
	@GetMapping(path="/customers/search", params={"email"})
	public ResponseEntity<List<CustomerDTO>> getCustomersByEmail(@RequestParam(value="email", required=true) String email) {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveCustomersByEmail(email), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get all Customers
	 * @return list of all Customers present
	 */
	@GetMapping(path="/customers/search")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveAllCustomers(), HttpStatus.OK);
	}
	
	/**
	 * Update Customer info
	 * @param Customer ID to identify Customer to update
	 * @param CustomerDTO Object
	 * @return CustomerDTO updated object
	 * @throws CustomerException
	 */
	@PutMapping("/customers/{customerID}")
	public ResponseEntity<CustomerDTO> updateCustomerInfo(@PathVariable(value="customerID", required=true) long customerID, 
			@RequestBody(required=true) @Valid CustomerDTO customerDTOObject) throws CustomerException, CustomerAddressException {
		return new ResponseEntity<CustomerDTO> (customerService.updateCustomerInfo(customerID, customerDTOObject),HttpStatus.OK);
	}

	/**
	 * Delete Customer based on Customer ID
	 * @param Customer ID
	 * @return OK status if successful
	 * @throws CustomerException
	 */
	@DeleteMapping("/customers/{customerID}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable(value="customerID", required=true) long customerID) throws CustomerException,
		IllegalArgumentException {
		customerService.deleteCustomer(customerID);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Add customer address for customer
	 * @param CustomerAddressDTO Object
	 * @return CustomerAddressDTO object that is added
	 * @throws CustomerException
	 * @throws InvalidInputException
	 */
	@PostMapping("/customers/addresses/create")
	public ResponseEntity<CustomerAddressDTO> addCustomerAddress(@RequestBody(required=true) @Valid CustomerAddressDTO customerAddressDTOObject) 
			throws CustomerException, InvalidInputException {
		return new ResponseEntity<CustomerAddressDTO>(customerService.addCustomerAddress(customerAddressDTOObject),HttpStatus.CREATED);
	}

	/**
	 * Get all Customer address for a respective Customer
	 * @param Customer ID
	 * @return list of added Customer addresses for required Customer
	 * @throws CustomerException
	 */
	@GetMapping("/customers/addresses/search/{customerID}")
	public ResponseEntity<List<CustomerAddressDTO>> retrieveAllCustomerAddressesByCustomerID(@PathVariable(name="customerID", required=true) long customerID) 
			throws CustomerException {
		return new ResponseEntity<List<CustomerAddressDTO>> (customerService.retrieveAllCustomerAddressesByCustomerID(customerID), HttpStatus.OK);
	}

	/**
	 * Update specific Customer address for required Customer
	 * @param CustomerAddress ID
	 * @param CustomerAddressDTO Object
	 * @return updated Customer address
	 * @throws CustomerException
	 * @throws CustomerAddressException
	 */
	@PutMapping("/customers/addresses/{customerID}")
	public ResponseEntity<CustomerAddressDTO> updateCustomerAddressInfo(@PathVariable(name="customerID", required=true) long customerID, 
			@RequestBody(required=true) @Valid CustomerAddressDTO customerAddressDTOObject) throws CustomerException, CustomerAddressException {
		return new ResponseEntity<CustomerAddressDTO> (customerService.updateCustomerAddressInfo(customerID, customerAddressDTOObject), HttpStatus.OK);
	}

	/**
	 * Delete specified Customer Address for required Customer
	 * @param Customer ID
	 * @param CustomerAddress ID
	 * @return OK status if successful
	 * @throws CustomerException
	 * @throws CustomerAddressException
	 */
	@DeleteMapping(path="/customers/addresses", params={"customerID","customerAddressID"})
	public ResponseEntity<Object> deleteCustomerAddress (@RequestParam(name="customerID", required=true) long customerID, 
			@RequestParam(name="customerAddressID", required=true) long customerAddressID) throws CustomerException, CustomerAddressException {
		customerService.deleteCustomerAddress(customerID, customerAddressID);
		return new ResponseEntity<Object> (HttpStatus.OK);
	}

	/**
	 * Delete Customer addresses based on list of Customer IDs
	 * @param Customer ID
	 * @param CustomerAddress IDs
	 * @return OK status if successful
	 * @throws CustomerException
	 * @throws CustomerAddressException
	 */
	/*@DeleteMapping(path="/customers/addresses", params={"customerID","customerAddressIDs"})
	public ResponseEntity<Object> deleteAllCustomerAddresses(@RequestParam(name="customerID", required=true) long customerID, 
			@RequestParam(name="customerAddressIDs", required=true) List<Long> customerAddressIDs) throws CustomerException, CustomerAddressException {
		return new ResponseEntity<Object> (HttpStatus.OK);
	}*/

}
