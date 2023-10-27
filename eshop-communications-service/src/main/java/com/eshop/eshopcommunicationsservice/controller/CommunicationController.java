package com.eshop.eshopcommunicationsservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;
import com.eshop.eshopcommunicationsservice.model.communication.WrapperCommunication;
import com.eshop.eshopcommunicationsservice.service.CommunicationsService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.validation.Valid;

/**
 * Controller to handle send mail requests
 */

@RestController
public class CommunicationController {
	
	@Autowired
	private CommunicationsService communicationsService; 
	
	/**
	 * Send a mail to customer about an order. Can be after placing order or separately too.
	 * @param wrapperCommunication Object having customerID and OrderID
	 * @return OK status, if successful
	 * @throws CustomerException
	 * @throws AddressException
	 * @throws MessagingException
	 */
	@GetMapping(value="/communications/customers/sendOrderMail")
	public ResponseEntity<Object> sendOrderSummaryViaMail(@RequestBody(required=true) @Valid WrapperCommunication wrapperCommunicationObject)
		throws CustomerException, AddressException, MessagingException {
		long customerID = wrapperCommunicationObject.getCustomerID();
		long orderID = wrapperCommunicationObject.getOrderID();
		
		communicationsService.sendOrderSummaryViaMail(customerID, orderID);
		return new ResponseEntity<Object>("Customer's Order Summary sent via Mail", HttpStatus.OK);
	}

}
