package com.eshop.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopmodel.consumer.UserDTO;
import com.eshop.eshopservice.service.UserService;
import com.eshop.exception.UserNotFoundException;

import jakarta.validation.Valid;

/**
 * Users respective controller for handling web requests for Users and User Addresses.
 */

@RestController("/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * Method to create new User
	 * @param userDTOObject
	 * @return Response Entity Object having new UserDTO object and created status
	 */
	@PostMapping("/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTOObject,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws UserNotFoundException {
		UserDTO userDTOReturnObject = userService.createUser(userDTOObject);
		return new ResponseEntity<UserDTO> (userDTOReturnObject, HttpStatus.CREATED);
		
	}
	
}
