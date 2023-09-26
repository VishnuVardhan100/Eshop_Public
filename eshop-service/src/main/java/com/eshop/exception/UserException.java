package com.eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for User issues.
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class UserException extends Exception {

	/**
	 * No argument constructor
	 */
	public UserException() {
		super();
	}

	/**
	 * Parameterized constructor. Used to set message shown to user.
	 * @param message
	 */
	public UserException(String message) {
		super(message);
	}

}
