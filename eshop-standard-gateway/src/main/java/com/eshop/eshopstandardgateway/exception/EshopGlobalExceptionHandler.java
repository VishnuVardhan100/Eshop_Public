package com.eshop.eshopstandardgateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import io.jsonwebtoken.ExpiredJwtException;

/**
 * Base handler for all exceptions in Eshop Application.
 */

@RestControllerAdvice
public class EshopGlobalExceptionHandler /*extends ResponseEntityExceptionHandler*/ {
	
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

	@ExceptionHandler(value = OrderException.class)
	public ResponseEntity<Object> handleOrderException(OrderException orderException, WebRequest webRequest){
		return new ResponseEntity<>(orderException.getMessage(), HttpStatus.OK);
	}

	@ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException authenticationException) {
        return new ResponseEntity<>(authenticationException.getMessage(), HttpStatus. UNAUTHORIZED);
    }

	@ExceptionHandler(value = AccessDeniedException.class)
	public ProblemDetail handleAccessDeniedException(AccessDeniedException bce) {
		ProblemDetail errorDetail =  ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), bce.getMessage());
		errorDetail.setProperty("access denied reason", "Not Authorized");
		return errorDetail;
	}

	@ExceptionHandler(value = BadCredentialsException.class)
	public ProblemDetail handleBadCredentialsException(BadCredentialsException aec) {
		ProblemDetail errorDetail =  ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), aec.getMessage());
		errorDetail.setProperty("access denied reason", "Bad Credentials");
		return errorDetail;
	}
	
	@ExceptionHandler(value = ExpiredJwtException.class)
	public ProblemDetail handleExpiredJwtException(ExpiredJwtException ejwte) {
		ProblemDetail errorDetail =  ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), ejwte.getMessage());
		errorDetail.setProperty("access denied reason", "Expired JWT");
		return errorDetail;
	}

	/*@ExceptionHandler(value = Exception.class)
	public ProblemDetail eshopGlobalExceptionHandler(Exception exception){
		ProblemDetail errorDetail =  ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), exception.getMessage());
		errorDetail.setProperty("Exception", "Exception");
		return errorDetail;
	}*/

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<Object> eshopGlobalExceptionHandler(Exception exception, WebRequest webRequest){
		return new ResponseEntity<>(exception.getStackTrace(), HttpStatus.BAD_REQUEST);
	}
	
}
