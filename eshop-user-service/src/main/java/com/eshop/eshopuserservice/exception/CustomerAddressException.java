package com.eshop.eshopuserservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception related to Customer Address
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.OK)
public class CustomerAddressException extends Exception {
	
	/**
	 * No argument constructor
	 */
	public CustomerAddressException() {
		
	}
	
	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message
	 */
	public CustomerAddressException(String message) {
		super(message);
	}
	
}
