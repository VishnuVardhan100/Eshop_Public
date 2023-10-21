package com.eshop.eshopcommunicationsservice.service;

import com.eshop.eshopcommunicationsservice.exception.CustomerException;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;

public interface CommunicationsServiceInterface {

	void sendOrderSummaryViaMail(long customerID, long orderID) throws CustomerException, AddressException, MessagingException;
}
