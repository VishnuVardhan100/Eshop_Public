package com.eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception with suitable message for Inventory Product issues.
 */

@SuppressWarnings("serial")
@ResponseStatus(value=HttpStatus.OK)
public class InventoryProductException extends Exception {

	/**
	 * No argument constructor
	 */
	public InventoryProductException() {
		super();
	}

	/**
	 * Parameterized constructor. Sets message appropriate for invalid input.
	 * @param message
	 */
	public InventoryProductException(String message) {
		super(message);
	}

}
