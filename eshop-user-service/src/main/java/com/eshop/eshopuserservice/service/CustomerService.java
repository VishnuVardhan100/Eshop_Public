package com.eshop.eshopuserservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.model.customer.Customer;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;
import com.eshop.eshopuserservice.model.customer.CustomerSignUpDTO;
import com.eshop.eshopuserservice.repository.CustomerRepository;
import com.eshop.eshopuserservices.service.helper.CustomerModelMapper;

/**
 * Service class for Customer related operations
 */

@Service
public class CustomerService implements CustomerServiceInterface{

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CustomerModelMapper customerModelMapper;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private CustomerRepository customerRepository;

	/**
	 * Connects to Repository to create Customer
	 * @param CustomerSignUpDTO Object from Client side
	 * @param Locale locale
	 * @return CustomerDTO Object after successful Customer creation
	 * @throws CustomerException customerException
	 * @throws NoSuchMessageException noSuchMessageException
	 */
	@Override
	public CustomerDTO createCustomer(CustomerSignUpDTO customerSignUpDTOObject, Locale locale) throws CustomerException{
		String hashedPassword ;
		Customer customerObject ;
		Customer customerReturnObject ;
		
		customerObject = customerModelMapper.mapCustomerSignUpDTOToCustomer(customerSignUpDTOObject);
		if(emailExists(customerObject.getCustomerEmail())) {
			throw new CustomerException(messageSource.getMessage("EmailExists", null, locale));
		}
		hashedPassword = passwordEncoder.encode(customerObject.getCustomerPassword());
		customerObject.setCustomerPassword(hashedPassword);
		customerReturnObject = customerRepository.save(customerObject);

		//customerMailService.sendCustomerCreatedMail(customerReturnObject);

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
	 * @throws CustomerException customerException
	 */
	@Override
	public CustomerDTO loadCustomerByEmail(String customerEmail) throws CustomerException {
		Customer customerRetrieveObject  = customerRepository.loadCustomerByEmail(customerEmail);
		if(customerRetrieveObject == null) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
        return customerModelMapper.mapCustomerToCustomerDTO(customerRetrieveObject);
	}

	/**
	 * CAUTION: INTERNAL USE ONLY
	 * Retrieve Customer raw object by email
	 * @param Customer Email
	 * @return CustomerDTO object, if customer exists
	 * @throws CustomerException customerException
	 */
	@Override
	public Customer loadCustomerObjectByEmail(String customerEmail) throws CustomerException {
		Customer customerRetrieveObject  = customerRepository.loadCustomerByEmail(customerEmail);
		if(customerRetrieveObject == null) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		return customerRetrieveObject;
	}
	
	/**
	 * Retrieve a Customer by their ID
	 * @param long Customer ID
	 * @param Locale locale
	 * @return CustomerDTO object
	 * @throws CustomerException customerException
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
	 * @return Customer object
	 * @throws CustomerException customerException
	 */
	@Override
	public Customer retrieveCustomerByID(long customerID) throws CustomerException {
		Optional<Customer> customerReturnObject = Optional.of(customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()))));
		return customerReturnObject.get();
	}

	/**
	 * Check if customer exists by ID
	 * @param CustomerID customerID
	 * @return true or false of customer exists
	 */
	@Override
	public boolean customerExists(long customerID) {
		return customerRepository.existsById(customerID);
	}
	
	/**
	 * ADMIN PRIVILEGE : To retrieve Customers based on first name
	 * @param First Name of Customer
	 * @return list of matched Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveCustomersByFirstName(String firstName) {
		List<Customer> customersByFirstName = customerRepository.retrieveCustomersByFirstName("%" + firstName + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<>(5);

		for(Customer customer : customersByFirstName) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		return allReturnCustomerDTO;
	}

	/**
	 * ADMIN PRIVILEGE : To retrieve Customers based on last name
	 * @param Last Name of Customer
	 * @return list of matched Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveCustomersByLastName(String lastName) {
		List<Customer> customersByLastName = customerRepository.retrieveCustomersByLastName("%" + lastName + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<>(5);

		for(Customer customer : customersByLastName) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		return allReturnCustomerDTO;
	}

	/**
	 * ADMIN PRIVILEGE : Retrieve all Customers
	 * @return list of all Customer DTOs
	 */
	@Override
	public List<CustomerDTO> retrieveAllCustomers() {
		List<Customer> allCustomers = customerRepository.findAll();
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<>(5);

		for(Customer customer : allCustomers) {
			allReturnCustomerDTO.add(customerModelMapper.mapCustomerToCustomerDTO(customer));
		}
		return allReturnCustomerDTO;
	}

	/**
	 * Update any Customer specific base info
	 * @param customerDTO object to take Customer info from
	 * @return updated instance of customer as customerDTO
	 * @throws CustomerException customerException
	 */
	@Override
	public CustomerDTO updateCustomerInfo(CustomerDTO customerDTOObject) throws CustomerException {
		Optional<Customer> customerObject = Optional.of(customerRepository.findById(customerDTOObject.getCustomerID()))
				.orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		Customer customerRetrieveObject ;

		if(customerObject.isEmpty()){
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		else{
			customerRetrieveObject = customerObject.get();
		}

		//CAUTION: We should not change the Customer ID - primary identifier. It stays same as when created
		//CAUTION: We should not change the Customer password .It stays hashed unless we want it changed.
		customerRetrieveObject.setCustomerFirstName(customerDTOObject.getCustomerFirstName());
		customerRetrieveObject.setCustomerLastName(customerDTOObject.getCustomerLastName());
		customerRetrieveObject.setCustomerEmail(customerDTOObject.getCustomerEmail());
		customerRetrieveObject.setCustomerMobileNumber(customerDTOObject.getCustomerMobileNumber());
		//CAUTION: We should not change the Customer Created Date. It stays same as when created
		//CAUTION: We should not change roles of customer as well. It stays same as when created

		Customer customerReturnObject = customerRepository.save(customerRetrieveObject);

		return customerModelMapper.mapCustomerToCustomerDTO(customerReturnObject);
	}
	
	/**
	 * Resets/Update customer password.
	 * @param customer ID for whom to reset/update password
	 * @param old password to check if credentials match
	 * @param new password to be updated
	 */
	@Override
	public void updateCustomerPassword(long customerID, String customerOldPassword, String customerNewPassword) throws CustomerException {
		Optional<Customer> customerObject = Optional.of(customerRepository.findById(customerID))
				.orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		Customer customerRetrieveObject ;

		if(customerObject.isEmpty()){
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		else{
			customerRetrieveObject = customerObject.get();
		}

		if(passwordEncoder.matches(customerOldPassword, customerRetrieveObject.getCustomerPassword())) {
			customerRetrieveObject.setCustomerPassword(passwordEncoder.encode(customerNewPassword));
			customerRepository.save(customerRetrieveObject);
			//customerMailService.sendCustomerPasswordResetMail(customerRetrieveObject);
		}
		else {
			throw new CustomerException(messageSource.getMessage("WrongCustomerCredentials", null, LocaleContextHolder.getLocale()));
		}
	}

	/**
	 * Delete the Customer based on CustomerID
	 * @param CustomerID customerID
	 * @throws CustomerException customerException
	 * @throws IllegalArgumentException illegalArgumentException
	 */
	@Override
	public void deleteCustomer(long customerID) throws CustomerException, IllegalArgumentException {		
		if(!customerRepository.existsById(customerID)) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		/*
		 * Customer customerRetrieveObject = customerRepository.findById(customerID)
		 * .orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));
		 * customerMailService.sendCustomerDeletedMail(customerRetrieveObject);
		 */
		customerRepository.deleteById(customerID);
	}

}
