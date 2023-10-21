package com.eshop.eshopuserservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopuserservice.exception.CustomerAddressException;
import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.exception.InvalidInputException;
import com.eshop.eshopuserservice.model.customer.Customer;
import com.eshop.eshopuserservice.model.customer.CustomerAddress;
import com.eshop.eshopuserservice.model.customer.CustomerAddressDTO;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;
import com.eshop.eshopuserservice.repository.CustomerAddressRepository;
import com.eshop.eshopuserservice.repository.CustomerRepository;
import com.eshop.eshopuserservices.service.helper.CustomerModelMapper;

@Service
public class CustomerAddressService implements CustomerAddressServiceInterface {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private CustomerModelMapper customerModelMapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private CustomerAddressRepository customerAddressRepository;
	
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
		Customer customerObject = customerModelMapper.mapCustomerDTOToCustomer(customerDTOObject);
		if(!customerRepository.existsById(customerObject.getCustomerID())) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}
		
		CustomerAddress customerAddressObject = customerModelMapper.mapCustomerAddressDTOToCustomerAddress(customerAddressDTOObject);
		customerAddressObject.setCustomer(customerObject);
		CustomerAddress customerAddressReturnObject = customerAddressRepository.save(customerAddressObject);
		customerObject = null;
		customerAddressObject = null;
		
		return customerModelMapper.mapCustomerAddressToCustomerAddressDTO(customerAddressReturnObject);
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
			allReturnCustomerAddressDTO.add(customerModelMapper.mapCustomerAddressToCustomerAddressDTO(customerAddress));
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

		CustomerAddress customerAddressObject = customerModelMapper.mapCustomerAddressDTOToCustomerAddress(customerAddressDTOObject);
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

		return customerModelMapper.mapCustomerAddressToCustomerAddressDTO(customerAddressReturnObject);
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
