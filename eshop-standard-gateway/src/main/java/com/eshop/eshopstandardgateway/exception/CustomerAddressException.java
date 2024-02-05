package com.eshop.eshopstandardgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception related to Customer Address
 */

@ResponseStatus(value=HttpStatus.OK)
public class CustomerAddressException extends Exception {
	
	/**
	 * No argument constructor
	 */
	public CustomerAddressException() {
		
	}
	
	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message message
	 */
	public CustomerAddressException(String message) {
		super(message);
	}
	
}
