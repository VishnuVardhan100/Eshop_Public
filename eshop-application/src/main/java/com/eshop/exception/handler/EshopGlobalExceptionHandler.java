package com.eshop.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductNotFoundException;
import com.eshop.exception.OrderNotFoundException;
import com.eshop.exception.UserNotFoundException;

/**
 * Base handler for all exceptions in Eshop Application.
 */

@RestControllerAdvice
public class EshopGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<Object> invalidInputException(InvalidInputException invalidInputException, WebRequest webRequest){
		return new ResponseEntity<>(invalidInputException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InventoryProductNotFoundException.class)
	public ResponseEntity<Object> inventoryProductNotFoundException(InventoryProductNotFoundException inventoryProductNotFoundException, 
			WebRequest webRequest){
		return new ResponseEntity<>(inventoryProductNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = OrderNotFoundException.class)
	public ResponseEntity<Object> orderNotFoundException(OrderNotFoundException orderNotFoundException, WebRequest webRequest){
		return new ResponseEntity<>(orderNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> userNotFoundException(UserNotFoundException userNotFoundException, WebRequest webRequest){
		return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> eshopGlobalExceptionHandler(Exception exception, WebRequest webRequest){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
}
