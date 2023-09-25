package com.eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for Order not found.
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends Exception {

	/**
	 * No argument constructor
	 */
	public OrderNotFoundException() {
		super();
	}

	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message
	 */
	public OrderNotFoundException(String message) {
		super(message);
	}

}
