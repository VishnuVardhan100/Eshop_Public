package com.eshop.eshopcommunicationsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for Customer issues.
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.OK)
public class CustomerException extends Exception {

	/**
	 * No argument constructor
	 */
	public CustomerException() {
		super();
	}

	/**
	 * Parameterized constructor. Used to set message shown to customer
	 * @param message
	 */
	public CustomerException(String message) {
		super(message);
	}

}
