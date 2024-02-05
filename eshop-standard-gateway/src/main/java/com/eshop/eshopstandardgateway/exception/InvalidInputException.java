package com.eshop.eshopstandardgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception for when provided Input does not match constraints or field criteria.
 */

@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class InvalidInputException extends Exception {

	/**
	 * No argument constructor
	 */
	public InvalidInputException() {
		super();
	}

	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message message
	 */
	public InvalidInputException(String message) {
		super(message);
	}

}
