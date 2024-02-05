package com.eshop.eshopinventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Base handler for all exceptions in Eshop Application.
 */

@RestControllerAdvice
public class EshopGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<Object> handleInvalidInputException(InvalidInputException invalidInputException, WebRequest webRequest){
		return new ResponseEntity<>(invalidInputException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InventoryProductException.class)
	public ResponseEntity<Object> handleInventoryProductException(InventoryProductException inventoryProductException, 
			WebRequest webRequest){
		return new ResponseEntity<>(inventoryProductException.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> eshopGlobalExceptionHandler(Exception exception, WebRequest webRequest){
		return new ResponseEntity<>(exception.getStackTrace(), HttpStatus.BAD_REQUEST);
	}
	
}
