package com.eshop.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;
import com.eshop.exception.UserAddressException;
import com.eshop.exception.UserException;

/**
 * Base handler for all exceptions in Eshop Application.
 */

@RestControllerAdvice
public class EshopGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<Object> invalidInputException(InvalidInputException invalidInputException, WebRequest webRequest){
		return new ResponseEntity<>(invalidInputException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = InventoryProductException.class)
	public ResponseEntity<Object> inventoryProductException(InventoryProductException inventoryProductException, 
			WebRequest webRequest){
		return new ResponseEntity<>(inventoryProductException.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = OrderException.class)
	public ResponseEntity<Object> orderException(OrderException orderException, WebRequest webRequest){
		return new ResponseEntity<>(orderException.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = UserAddressException.class)
	public ResponseEntity<Object> userAddressException(UserAddressException userAddressException, WebRequest webRequest){
		return new ResponseEntity<>(userAddressException.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = UserException.class)
	public ResponseEntity<Object> userException(UserException userException, WebRequest webRequest){
		return new ResponseEntity<>(userException.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> eshopGlobalExceptionHandler(Exception exception, WebRequest webRequest){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	
}
