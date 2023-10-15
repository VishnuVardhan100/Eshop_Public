package com.eshop.eshopapplication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopmodel.communication.WrapperCommunication;
import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.logistics.Order;
import com.eshop.eshopservice.communication.CustomerMailService;
import com.eshop.eshopservice.service.CustomerService;
import com.eshop.eshopservice.service.LogisticsService;
import com.eshop.exception.CustomerException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.validation.Valid;

/**
 * Controller to handle send mail requests
 */

@RestController
public class CommunicationController {
	
	@Autowired
	private CustomerService customerService;

	@Autowired
	private LogisticsService logisticsService;
	
	@Autowired
	private CustomerMailService customerMailService;
	
	/**
	 * Send a mail to customer about an order. Can be after placing order or separately too.
	 * @param wrapperCommunication Object having customerID and OrderID
	 * @return OK status, if successful
	 * @throws CustomerException
	 * @throws AddressException
	 * @throws MessagingException
	 */
	@GetMapping(value="/customers/communications/sendOrderMail")
	public ResponseEntity<Object> sendOrderSummaryViaMail(@RequestBody(required=true) @Valid WrapperCommunication wrapperCommunicationObject)
		throws CustomerException, AddressException, MessagingException {
		long customerID = wrapperCommunicationObject.getCustomerID();
		long orderID = wrapperCommunicationObject.getOrderID();
		
		Customer customerObject = customerService.retrieveCustomerObjectByID(customerID, LocaleContextHolder.getLocale());
		Order orderObject = logisticsService.retrieveOrderObjectByCustomerID(customerID, orderID);
		
		customerMailService.sendOrderSummaryViaMail(customerObject, orderObject);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
