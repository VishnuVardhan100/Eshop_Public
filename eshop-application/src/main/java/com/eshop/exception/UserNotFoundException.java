package com.eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for User not found.
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

	/**
	 * No argument constructor
	 */
	public UserNotFoundException() {
		super();
	}

	/**
	 * Parameterized constructor. Used to set message shown to user.
	 * @param message
	 */
	public UserNotFoundException(String message) {
		super(message);
	}

}
