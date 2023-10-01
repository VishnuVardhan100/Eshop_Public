package com.eshop.eshopservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.customer.CustomerAddress;
import com.eshop.eshopmodel.customer.CustomerAddressDTO;
import com.eshop.eshopmodel.customer.CustomerDTO;
import com.eshop.eshoprepository.CustomerAddressRepository;
import com.eshop.eshoprepository.CustomerRepository;
import com.eshop.eshopservice.mapper.CustomerCustomModelMapper;
import com.eshop.exception.CustomerAddressException;
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
	private CustomerRepository customerRepository;

	@Autowired
	private CustomerAddressRepository customerAddressRepository;

	@Autowired
	private CustomerCustomModelMapper customerCustomModelMapper;

	/**
	 * Connects to Repository to create Customer
	 * @param CustomerDTO Object from Client side
	 * @param locale
	 * @return CustomerDTO Object after successful Customer creation
	 * @throws InvalidInputException
	 */
	@Override
	public CustomerDTO createCustomer(CustomerDTO customerDTOObject, Locale locale) throws InvalidInputException{
		Customer customerObject = customerCustomModelMapper.mapCustomerDTOToCustomer(customerDTOObject);
		Customer customerReturnObject = customerRepository.save(customerObject);
		customerObject = null;

		if(customerReturnObject == null) {
			throw new InvalidInputException(messageSource.getMessage("InvalidInput", null, locale));
		}

		return customerCustomModelMapper.mapCustomerToCustomerDTO(customerReturnObject);
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
		Optional<Customer> customerReturnObject = Optional.of(customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, locale))));
		return customerCustomModelMapper.mapCustomerToCustomerDTO(customerReturnObject.get());
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
		List<Customer> customersByFirstName = customerRepository.retreiveCustomersByFirstName("%" + firstName + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : customersByFirstName) {
			allReturnCustomerDTO.add(customerCustomModelMapper.mapCustomerToCustomerDTO(customer));
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
		List<Customer> customersByLastName = customerRepository.retreiveCustomersByLastName("%" + lastName + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : customersByLastName) {
			allReturnCustomerDTO.add(customerCustomModelMapper.mapCustomerToCustomerDTO(customer));
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
		List<Customer> customersByEmail = customerRepository.retreiveCustomersByEmail("%" + email + "%");
		List<CustomerDTO> allReturnCustomerDTO = new ArrayList<CustomerDTO>(5);

		for(Customer customer : customersByEmail) {
			allReturnCustomerDTO.add(customerCustomModelMapper.mapCustomerToCustomerDTO(customer));
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
			allReturnCustomerDTO.add(customerCustomModelMapper.mapCustomerToCustomerDTO(customer));
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
	public CustomerDTO updateCustomerInfo(long customerID, CustomerDTO customerDTOObject) throws CustomerException, CustomerAddressException {
		Customer customerRetrieveObject = customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		Customer customerObject = customerCustomModelMapper.mapCustomerDTOToCustomer(customerDTOObject);
		if(customerID != customerObject.getCustomerID()) {
			throw new CustomerAddressException(messageSource.getMessage("CustomerIDAndCustomerMismatch", null, LocaleContextHolder.getLocale()));
		}

		//CAUTION: We should not change the Customer ID - primary identifier. It stays same as when created
		customerRetrieveObject.setCustomerFirstName(customerObject.getCustomerFirstName());
		customerRetrieveObject.setCustomerLastName(customerObject.getCustomerLastName());
		customerRetrieveObject.setCustomerEmail(customerObject.getCustomerEmail());
		customerRetrieveObject.setCustomerMobileNumber(customerObject.getCustomerMobileNumber());
		//CAUTION: We should not change the Customer Created Date. It stays same as when created

		Customer customerReturnObject = customerRepository.save(customerRetrieveObject);
		customerRetrieveObject = null;
		customerObject = null;

		return customerCustomModelMapper.mapCustomerToCustomerDTO(customerReturnObject);
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

	/**
	 * Add a Customer address
	 * @param customerDTO Object
	 * @param customerAddress DTO object
	 * @return the added customerAddress DTO object
	 * @throws CustomerAddressException
	 * @throws InvalidInputException
	 */
	@Override
	public CustomerAddressDTO addCustomerAddress(CustomerDTO customerDTOObject, CustomerAddressDTO customerAddressDTOObject) throws CustomerException, 
		InvalidInputException {
		Customer customerObject = customerCustomModelMapper.mapCustomerDTOToCustomer(customerDTOObject);
		if(!customerRepository.existsById(customerObject.getCustomerID())) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		
		CustomerAddress customerAddressObject = customerCustomModelMapper.mapCustomerAddressDTOToCustomerAddress(customerAddressDTOObject);
		customerAddressObject.setCustomer(customerObject);
		CustomerAddress customerAddressReturnObject = customerAddressRepository.save(customerAddressObject);
		customerObject = null;
		customerAddressObject = null;
		
		return customerCustomModelMapper.mapCustomerAddressToCustomerAddressDTO(customerAddressReturnObject);
	}	
	
	
	/**
	 * Get all the added Customer addresses for a valid Customer
	 * @param customerID of valid Customer
	 * @return list of customer address DTOs for said Customer
	 * @throws CustomerException
	 */
	@Override
	public List<CustomerAddressDTO> retrieveAllCustomerAddressesByCustomerID(long customerID) throws CustomerException {
		Customer customer = customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		List<CustomerAddress> allCustomerAddresses = customer.getCustomerAddresses();
		List<CustomerAddressDTO> allReturnCustomerAddressDTO = new ArrayList<CustomerAddressDTO>(5);

		for(CustomerAddress customerAddress : allCustomerAddresses) {
			allReturnCustomerAddressDTO.add(customerCustomModelMapper.mapCustomerAddressToCustomerAddressDTO(customerAddress));
		}
		allCustomerAddresses = null;
		customer = null;
		return allReturnCustomerAddressDTO;
	}

	/**
	 * Update an existing Customer address for valid Customer
	 * @param the customerAddress DTO object
	 * @return the updated customerAddress DTO object
	 * @throws CustomerException
	 * @throws CustomerAddresssException
	 */
	@Override
	public CustomerAddressDTO updateCustomerAddressInfo(long customerID, CustomerAddressDTO customerAddressDTOObject) throws CustomerException, CustomerAddressException {
		if(!customerRepository.existsById(customerID)) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}

		CustomerAddress customerAddressObject = customerCustomModelMapper.mapCustomerAddressDTOToCustomerAddress(customerAddressDTOObject);
		if(customerID != customerAddressObject.getCustomer().getCustomerID()) {
			throw new CustomerAddressException(messageSource.getMessage("CustomerAndCustomerAddressMismatch", null, LocaleContextHolder.getLocale()));
		}

		CustomerAddress customerAddressRetrieveObject = customerAddressRepository.findById(customerAddressObject.getAddressID()).
				orElseThrow(() -> new CustomerAddressException(messageSource.getMessage("CustomerAddressNotFound", null, LocaleContextHolder.getLocale())));

		//CAUTION: We should not change the CustomerAddress ID - primary identifier. It stays same as when created
		customerAddressRetrieveObject.setHouseNo(customerAddressObject.getHouseNo());
		customerAddressRetrieveObject.setStreet(customerAddressObject.getStreet());
		customerAddressRetrieveObject.setCity(customerAddressObject.getCity());
		customerAddressRetrieveObject.setState(customerAddressObject.getState());
		customerAddressRetrieveObject.setPincode(customerAddressObject.getPincode());
		//CAUTION: We should not change the Customer Entity - It should be created/updated/deleted separately.

		CustomerAddress customerAddressReturnObject = customerAddressRepository.save(customerAddressRetrieveObject);
		customerAddressRetrieveObject = null;
		customerAddressObject = null;

		return customerCustomModelMapper.mapCustomerAddressToCustomerAddressDTO(customerAddressReturnObject);
	}

	/**
	 * Delete a Customer address by given Customer address ID
	 * @param Customer address ID
	 * @throws CustomerException
	 * @throws CustomerAddressException
	 */
	@Override
	public void deleteCustomerAddress(long customerID, long customerAddressID) throws CustomerException, CustomerAddressException {
		Customer customerObject = customerRepository.findById(customerID).
		orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		CustomerAddress customerAddressObject = customerAddressRepository.findById(customerAddressID).
		orElseThrow(() -> new CustomerAddressException(messageSource.getMessage("CustomerAddressNotFound", null, LocaleContextHolder.getLocale())));

		if(customerObject.getCustomerID() != customerAddressObject.getCustomer().getCustomerID()) {
			throw new CustomerAddressException(messageSource.getMessage("CustomerAndCustomerAddressMismatch", null, LocaleContextHolder.getLocale()));
		}

		customerObject.getCustomerAddresses().remove(customerAddressObject);
		customerRepository.save(customerObject);
		//customerAddressObject.setCustomer(null);
		customerAddressRepository.deleteById(customerAddressID);
		customerAddressObject = null;
		customerObject = null;
	}

	/**
	 * Delete a list of Customer addresses by their IDs
	 * @param Customer ID for whom the Customer addresses need to be deleted
	 * @param list of Customer address IDs
	 * @throws CustomerException
	 * @throws CustomerAddressException
	 */
	@Override
	public void deleteAllCustomerAddresses(long customerID, List<Long> customerAddressIDs) throws CustomerException, CustomerAddressException {
		Customer customerObject = customerRepository.findById(customerID).
		orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		for(Long customerAddressID : customerAddressIDs) {
			CustomerAddress customerAddressObject = customerAddressRepository.findById(customerAddressID).
			orElseThrow(() -> new CustomerAddressException(messageSource.getMessage("CustomerAddressNotFound", null, LocaleContextHolder.getLocale())));

			if(customerObject.getCustomerID() != customerAddressObject.getCustomer().getCustomerID()) {
				throw new CustomerAddressException(messageSource.getMessage("CustomerAndCustomerAddressMismatch", null, LocaleContextHolder.getLocale()));
			}

			customerObject.getCustomerAddresses().remove(customerAddressObject);
			customerRepository.save(customerObject);
			//customerAddressObject.setCustomer(null);
			customerAddressRepository.deleteById(customerAddressID);
			customerAddressObject = null;
		}
		customerObject = null;
	}

}
