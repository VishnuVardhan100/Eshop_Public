package com.eshop.eshopuserservice.controller;

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

import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.exception.InvalidInputException;
import com.eshop.eshopuserservice.model.customer.Customer;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;
import com.eshop.eshopuserservice.model.customer.CustomerPasswordUpdateDTO;
import com.eshop.eshopuserservice.model.customer.CustomerSignUpDTO;
import com.eshop.eshopuserservice.service.CustomerService;

import jakarta.validation.Valid;

/**
 * Customers respective controller for handling web requests for Customers and Customer Addresses.
 */

@RestController()
public class CustomerController {

	/*@Autowired
	private AuthenticationManager authenticationManager; 
		
	@Autowired
	private CustomerLoginService customerLoginService;*/
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * Sign in end point for customers
	 * @param customerSignInAuthenticationRequest object containing user name and password
	 * @return JSON Web Token if successful
	 * @throws Exception
	 */
	/*@PostMapping("/signin/customers")
	public ResponseEntity<?> customerSignInAuthenticate(@RequestBody(required=true) @Valid CustomerSignInAuthenticationRequest customerSignInAuthenticationRequest)
		throws Exception {
		try	{
			Authentication authentication = authenticationManager
			        .authenticate(new UsernamePasswordAuthenticationToken(customerSignInAuthenticationRequest.getUsername(), customerSignInAuthenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(BadCredentialsException bce) {
			throw new BadCredentialsException("Incorrect Email/Username or Password",bce);
		}

		final UserDetails userDetails = customerLoginService.loadUserByUsername(customerSignInAuthenticationRequest.getUsername());		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new CustomerSignInAuthenticationResponse(jwt));

		//final String jwt = jwtUtil.generateTokenFromUsername(customerSignInAuthenticationRequest.getUsername());
		//ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);
		//return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString());

	}*/
	
	/**
	 * Method to create new Customer
	 * @param CustomerSignUpDTO Object
	 * @param locale
	 * @return Response Entity Object having new CustomerDTO object and created status
	 * @throws InvalidInputException
	 * @throws CustomerException
	 */
	@PostMapping("/signup/customers")
	public ResponseEntity<CustomerDTO> createCustomer(@RequestBody(required=true) @Valid CustomerSignUpDTO customerSignUpDTOObject,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws CustomerException {
		CustomerDTO customerDTOReturnObject = customerService.createCustomer(customerSignUpDTOObject, locale);
		return new ResponseEntity<CustomerDTO> (customerDTOReturnObject, HttpStatus.CREATED);
	}

	/**
	 * Get Customer by email
	 * @param customerEmail
	 * @return CustomerDTO object, if such customer exists
	 * @throws CustomerException
	 */
	@GetMapping(path = "/customers/load", params={"customerEmail"})
	public ResponseEntity<CustomerDTO> getCustomerByEmail(@RequestParam(name="customerEmail", required=true) String customerEmail) throws CustomerException {
		return new ResponseEntity<CustomerDTO> (customerService.loadCustomerByEmail(customerEmail), HttpStatus.OK);
	}

	/**
	 * CAUTION: INTERNAL USE ONLY
	 * Get Customer by email
	 * @param customerEmail
	 * @return Customer raw object, if such customer exists
	 * @throws CustomerException
	 */
	@GetMapping(path = "/customers/loadobject", params={"customerEmail"})
	public ResponseEntity<Customer> getCustomerObjectByEmail(@RequestParam(name="customerEmail", required=true) String customerEmail) throws CustomerException {
		return new ResponseEntity<Customer> (customerService.loadCustomerObjectByEmail(customerEmail), HttpStatus.OK);
	}
	
	/**
	 * Retrieve CustomerDTO by ID
	 * @param Customer ID
	 * @param locale
	 * @return CustomerDTO object if exists
	 * @throws CustomerException
	 */
	@GetMapping("customers/search/{customerID}")
	public ResponseEntity<CustomerDTO> getCustomerByID(@PathVariable(name="customerID", required=true) long customerID,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws CustomerException {
		return new ResponseEntity<CustomerDTO> (customerService.retrieveCustomerByID(customerID, locale),HttpStatus.OK);
	}

	/**
	 * CAUION: INTERNAL USE ONLY
	 * Retrieve Customer by ID
	 * @param customerID
	 * @return customer object if exists
	 * @throws CustomerException
	 */
	@GetMapping(path= "customers/searchbyid" , params= {"customerID"})
	public ResponseEntity<Customer> getCustomerByID(@RequestParam(name="customerID", required=true) long customerID
			) throws CustomerException {
		return new ResponseEntity<Customer> (customerService.retrieveCustomerByID(customerID),HttpStatus.OK);
	}
	
	/**
	 * ADMIN PRIVILEDGE : Get Customers based on first name
	 * @param first name
	 * @return list of Customers matching first name criteria
	 */
	@GetMapping(path="/admin/customers/search", params={"firstName"})
	public ResponseEntity<List<CustomerDTO>> getCustomersByFirstName(@RequestParam(value="firstName", required=true) String firstName) {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveCustomersByFirstName(firstName), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get Customers based on last name
	 * @param lastName
	 * @return list of Customers matching last name criteria
	 */
	@GetMapping(path="/admin/customers/search", params={"lastName"})
	public ResponseEntity<List<CustomerDTO>> getCustomersByLastName(@RequestParam(value="lastName", required=true) String lastName) {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveCustomersByLastName(lastName), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get Customers based on email
	 * @param email
	 * @return list of Customers matching email criteria
	 */
	@GetMapping(path="/admin/customers/search", params={"email"})
	public ResponseEntity<List<CustomerDTO>> getCustomersByEmail(@RequestParam(value="email", required=true) String email) {
		return new ResponseEntity<List<CustomerDTO>> (customerService.retrieveCustomersByEmail(email), HttpStatus.OK);
	}

	/**
	 * ADMIN PRIVILEDGE : Get all Customers
	 * @return list of all Customers present
	 */
	@GetMapping(path="/admin/customers/search")
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
	@PutMapping("/customers/update/info/{customerID}")
	public ResponseEntity<CustomerDTO> updateCustomerInfo(@PathVariable(value="customerID", required=true) long customerID, 
			@RequestBody(required=true) @Valid CustomerDTO customerDTOObject) throws CustomerException {
		return new ResponseEntity<CustomerDTO> (customerService.updateCustomerInfo(customerID, customerDTOObject),HttpStatus.OK);
	}
	
	/**
	 * Update the customer password
	 * @param CustomerPasswordUpdateDTO Object containing id and passwords 
	 * @return confirmation message 
	 * @throws CustomerException
	 */
	@PutMapping("/customers/update/credential")
	public ResponseEntity<Object> updateCustomerPassword(@RequestBody(required = true) @Valid CustomerPasswordUpdateDTO CustomerPasswordUpdateDTOObject) 
		throws CustomerException{
		long customerID = CustomerPasswordUpdateDTOObject.getCustomerID();
		String customerOldPassword = CustomerPasswordUpdateDTOObject.getCustomerOldPassword();
		String customerNewPassword = CustomerPasswordUpdateDTOObject.getCustomerNewPassword();
		customerService.updateCustomerPassword(customerID, customerOldPassword, customerNewPassword);
		return new ResponseEntity<>("Customer password reset Successfully", HttpStatus.OK);
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
		return new ResponseEntity<Object>("Customer deleted successfully", HttpStatus.OK);
	}

}
