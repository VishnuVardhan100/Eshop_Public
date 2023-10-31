package com.eshop.eshopcommunicationsservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.customer.Customer;
import com.eshop.eshopcommunicationsservice.model.logistics.Order;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

@Service
public class CommunicationsService implements CommunicationsServiceInterface {
	
	@Autowired
	private CustomerInterface customerInterface;

	@Autowired
	private LogisticsInterface logisticsInterface;

	@Autowired
	private OrderMailService orderMailService;
	
	private Logger logger = LoggerFactory.getLogger(CommunicationsService.class.getName());

	@Override
	public void sendOrderSummaryViaMail(long customerID, long orderID) throws CustomerException, AddressException, MessagingException {
		logger.trace("Inside Communications Service sendOrderSummaryViaMail method()");
		Customer customerObject = customerInterface.getCustomerObjectByID(customerID).getBody();
		logger.trace("Customer exists");
		Order orderObject = logisticsInterface.retrieveOrderObjectByCustomerID(customerID, orderID).getBody();
		logger.trace("Order exists");
		orderMailService.sendOrderSummaryViaMail(customerObject, orderObject);
		logger.trace("Exiting Communications Service sendOrderSummaryViaMail method()");
	}

}
