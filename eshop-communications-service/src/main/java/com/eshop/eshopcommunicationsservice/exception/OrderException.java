package com.eshop.eshopcommunicationsservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for Order issues.
 */

@ResponseStatus(value=HttpStatus.OK)
public class OrderException extends Exception {

	/**
	 * No argument constructor
	 */
	public OrderException() {
		super();
	}

	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message message
	 */
	public OrderException(String message) {
		super(message);
	}

}
