package com.eshop.eshopapplication.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.eshop.exception.CustomerAddressException;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;

/**
 * Base handler for all exceptions in Eshop Application.
 */

@RestControllerAdvice
public class EshopGlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(value = InvalidInputException.class)
	public ResponseEntity<Object> handleInvalidInputException(InvalidInputException invalidInputException, WebRequest webRequest){
		return new ResponseEntity<>(invalidInputException.getMessage(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = CustomerException.class)
	public ResponseEntity<Object> handleCustomerException(CustomerException customerException, WebRequest webRequest){
		return new ResponseEntity<>(customerException.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(value = CustomerAddressException.class)
	public ResponseEntity<Object> handleCustomerAddressException(CustomerAddressException customerAddressException, WebRequest webRequest){
		return new ResponseEntity<>(customerAddressException.getMessage(), HttpStatus.OK);
	}
	
	@ExceptionHandler(value = InventoryProductException.class)
	public ResponseEntity<Object> handleInventoryProductException(InventoryProductException inventoryProductException, 
			WebRequest webRequest){
		return new ResponseEntity<>(inventoryProductException.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(value = OrderException.class)
	public ResponseEntity<Object> handleOrderException(OrderException orderException, WebRequest webRequest){
		return new ResponseEntity<>(orderException.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(value = AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException authenticationException) {
        return new ResponseEntity<>(authenticationException.getMessage(), HttpStatus.UNAUTHORIZED);
    }
	
	/*@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> eshopGlobalExceptionHandler(Exception exception, WebRequest webRequest){
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}*/
	
}
