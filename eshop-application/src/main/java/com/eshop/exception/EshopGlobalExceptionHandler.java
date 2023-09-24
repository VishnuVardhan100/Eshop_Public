package com.eshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * Base handler for all exceptions in Eshop Application.
 */

@ControllerAdvice
public class EshopGlobalExceptionHandler {
	
	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<Object> invalidInputException(InvalidInputException invalidInputException, WebRequest webRequest){
		return new ResponseEntity<Object>(invalidInputException, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InventoryProductNotFoundException.class)
	public ResponseEntity<Object> inventoryProductNotFoundException(InventoryProductNotFoundException inventoryProductNotFoundException, 
			WebRequest webRequest){
		return new ResponseEntity<Object>(inventoryProductNotFoundException, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = OrderNotFoundException.class)
	public ResponseEntity<Object> orderNotFoundException(OrderNotFoundException orderNotFoundException, WebRequest webRequest){
		return new ResponseEntity<Object>(orderNotFoundException, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> userNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest){
		return new ResponseEntity<Object>(userNotFoundException, HttpStatus.NOT_FOUND);
	}
	
}
