package com.eshop.eshopcommunicationsservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopcommunicationsservice.model.customer.Customer;
import com.eshop.eshopcommunicationsservice.model.logistics.Order;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;

@Service
public class CommunicationsService implements CommunicationsServiceInterface {

	@Autowired   
	private MessageSource messageSource;
	
	@Autowired
	private CustomerInterface customerInterface;

	@Autowired
	private LogisticsInterface logisticsInterface;

	@Autowired
	private OrderMailService orderMailService;
	
	@Override
	public void sendOrderSummaryViaMail(long customerID, long orderID) throws CustomerException, AddressException, MessagingException {

		Customer customerObject = customerInterface.retrieveCustomerByID(customerID)
				.orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));
		
		Order orderObject = logisticsInterface.retrieveOrderObjectByCustomerID(customerID, orderID);

		orderMailService.sendOrderSummaryViaMail(customerObject, orderObject);
	}

}
