package com.eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for Inventory Product not found.
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class InventoryProductNotFoundException extends Exception {

	/**
	 * No argument constructor
	 */
	public InventoryProductNotFoundException() {
		super();
	}

	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message
	 */
	public InventoryProductNotFoundException(String message) {
		super(message);
	}

}
