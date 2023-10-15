package com.eshop.eshopservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.eshopmodel.customer.CustomerSignUpDTO;
import com.eshop.eshoprepository.CustomerRepository;
import com.eshop.eshopservice.mapper.CustomerModelMapper;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;

/**
 * Service class for Customer related operations
 */

@Service
public class CustomerService implements CustomerServiceInterface{

	@Autowired   
	private MessageSource messageSource;

	@Autowired
	private CustomerModelMapper customerModelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Connects to Repository to create Customer
	 * @param CustomerSignUpDTO Object from Client side
	 * @param locale
	 * @return CustomerDTO Object after successful Customer creation
	 * @throws InvalidInputException
	 * @throws CustomerException 
	 * @throws NoSuchMessageException 
	 */
	@Override
	public CustomerDTO createCustomer(CustomerSignUpDTO customerSignUpDTOObject, Locale locale) throws CustomerException{
		String hashedPassword = "";
		Customer customerObject = null;
		Customer customerReturnObject = null;
		
		customerObject = customerModelMapper.mapCustomerSignUpDTOToCustomer(customerSignUpDTOObject);
		if(emailExists(customerObject.getCustomerEmail())) {
			throw new CustomerException(messageSource.getMessage("EmailExists", null, locale));
		}
		hashedPassword = passwordEncoder.encode(customerObject.getCustomerPassword());
		customerObject.setCustomerPassword(hashedPassword);
		customerReturnObject = customerRepository.save(customerObject);
		customerObject = null;

		return customerModelMapper.mapCustomerToCustomerDTO(customerReturnObject);
	}

	/**
	 * Check if email already exists for another customer
	 * @param customer email
	 * @return true if exists or false otherwise
	 */
	@Override
	public boolean emailExists(String customerEmail) {
		if(customerRepository.emailExists(customerEmail) != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Retrieve Customer by email
	 * @param Customer Email
	 * @return CustomerDTO object, if customer exists
	 */
	@Override
	public CustomerDTO loadCustomerByEmail(String customerEmail) throws CustomerException {
		Customer customerRetrieveObject  = customerRepository.loadCustomerByEmail(customerEmail);
		if(customerRetrieveObject == null) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		CustomerDTO customerDTOObject = customerModelMapper.mapCustomerToCustomerDTO(customerRetrieveObject);
		return customerDTOObject;
	}
	
	/**
	 * Retrieve a Customer by their ID
	 * @param long Customer ID
	 * @param locale
	 * @return CustomerDTO object
	 * @throws CustomerException
	 */
	@Override
	public CustomerDTO retrieveCustomerByID(long customerID, Locale locale) throws CustomerException {
		Optional<Customer> customerReturnObject = Optional.of(customerRepository.findById(customerID)
				.orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, locale))));
		return customerModelMapper.mapCustomerToCustomerDTO(customerReturnObject.get());
	}

	/**
	 * CAUTION : INTERNAL USE ONLY
	 * Retrieve a Customer by their ID
	 * @param long Customer ID
	 * @param locale
	 * @return Customer object
	 * @throws CustomerException
	 */
	@Override
	public Customer retrieveCustomerObjectByID(long customerID, Locale locale) throws CustomerException {
		Optional<Customer> customerReturnObject = Optional.of(customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, locale))));
		return customerReturnObject.get();
	}
	
	/**
	 * ADMIN PRIVILEDGE : To retrieve Customers based on first name
	 * @param First Name of Customer
	 * @return list of matched Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveCustomersByFirstName(String firstName) {
		List<Customer> customersByFirstName = customerRepository.retrieveCustomersByFirstName("%" + firstName + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : customersByFirstName) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		customersByFirstName = null;
		return allReturnCustomerDTO;
	}

	/**
	 * ADMIN PRIVILEDGE : To retrieve Customers based on last name
	 * @param Last Name of Customer
	 * @return list of matched Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveCustomersByLastName(String lastName) {
		List<Customer> customersByLastName = customerRepository.retrieveCustomersByLastName("%" + lastName + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : customersByLastName) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		customersByLastName = null;
		return allReturnCustomerDTO;
	}

	/**
	 * ADMIN PRIVILEDGE : To retrieve Customers based on email
	 * @param email of Customer
	 * @return list of matched Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveCustomersByEmail(String email) {
		List<Customer> customersByEmail = customerRepository.retrieveCustomersByEmail("%" + email + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : customersByEmail) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		customersByEmail = null;
		return allReturnCustomerDTO;
	}

	/**
	 * ADMIN PRIVILEDGE : Retrieve all Customers
	 * @return list of all Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveAllCustomers() {
		List<Customer> allCustomers = customerRepository.findAll();
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : allCustomers) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		allCustomers = null;
		return allReturnCustomerDTO;
	}

	/**
	 * Update any Customer specific base info
	 * @param customerID to find if Customer exists
	 * @param customerDTO object to take Customer info from
	 * @return updated instance of customer as customerDTO
	 * @throws CustomerException
	 */
	@Override
	public CustomerDTO updateCustomerInfo(long customerID, CustomerDTO customerDTOObject) throws CustomerException {
		Customer customerRetrieveObject = customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		Customer customerObject = customerModelMapper.mapCustomerDTOToCustomer(customerDTOObject);
		if(customerID != customerObject.getCustomerID()) {
			throw new CustomerException(messageSource.getMessage("CustomerIDAndCustomerMismatch", null, LocaleContextHolder.getLocale()));
		}

		//CAUTION: We should not change the Customer ID - primary identifier. It stays same as when created
		//CAUTION: We should not change the Customer password .It stays hashed unless we want it changed.
		customerRetrieveObject.setCustomerFirstName(customerObject.getCustomerFirstName());
		customerRetrieveObject.setCustomerLastName(customerObject.getCustomerLastName());
		customerRetrieveObject.setCustomerEmail(customerObject.getCustomerEmail());
		customerRetrieveObject.setCustomerMobileNumber(customerObject.getCustomerMobileNumber());
		//CAUTION: We should not change the Customer Created Date. It stays same as when created
		//CAUTION: We should not change roles of customer as well. It stays same as when created

		Customer customerReturnObject = customerRepository.save(customerRetrieveObject);
		customerRetrieveObject = null;
		customerObject = null;

		return customerModelMapper.mapCustomerToCustomerDTO(customerReturnObject);
	}
	
	/**
	 * Resets/Update customer password.
	 * @param customer ID for whom to reset/update password
	 * @param old password to check if credentials match
	 * @param new password to be updated
	 */
	@Override
	public void updateCustomerPassword(long customerID, String customerOldPasword, String customerNewPasword) throws CustomerException {
		Customer customerRetrieveObject = customerRepository.findById(customerID)
				.orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));
		
		if(passwordEncoder.matches(customerOldPasword, customerRetrieveObject.getCustomerPassword())) {
			customerRetrieveObject.setCustomerPassword(passwordEncoder.encode(customerNewPasword));
			customerRepository.save(customerRetrieveObject);
		}
		else {
			throw new CustomerException(messageSource.getMessage("WrongCustomerCredentials", null, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Delete the Customer based on CustomerID
	 * @param customerID
	 * @throws CustomerException
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteCustomer(long customerID) throws CustomerException, IllegalArgumentException {		
		if(!customerRepository.existsById(customerID)) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		customerRepository.deleteById(customerID);
	}

}
