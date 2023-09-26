package com.eshop.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopmodel.consumer.UserAddressDTO;
import com.eshop.eshopmodel.consumer.UserDTO;
import com.eshop.eshopservice.service.UserService;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.UserAddressException;
import com.eshop.exception.UserException;

import jakarta.validation.Valid;

/**
 * Users respective controller for handling web requests for Users and User Addresses.
 */

@RestController()
public class UserController {

	@Autowired
	private UserService userService;
	
	/**
	 * Method to create new User
	 * @param userDTOObject
	 * @param locale
	 * @return Response Entity Object having new UserDTO object and created status
	 * @throws UserNotFoundException
	 */
	@PostMapping("/users/create")
	public ResponseEntity<UserDTO> createUser(@RequestBody(required=true) @Valid UserDTO userDTOObject,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws InvalidInputException {
		UserDTO userDTOReturnObject = userService.createUser(userDTOObject, locale);
		return new ResponseEntity<UserDTO> (userDTOReturnObject, HttpStatus.CREATED);
	}

	/**
	 * Retrieve user by ID
	 * @param user ID
	 * @param locale
	 * @return user object if exists
	 * @throws UserNotFoundException
	 */
	@GetMapping("/users/search/{userID}")
	public ResponseEntity<UserDTO> getUserByID(@PathVariable(name="userID", required=true) int userID,
			@RequestHeader(name="Accept-Language", required=false) Locale locale) throws UserException {
		return new ResponseEntity<UserDTO> (userService.retrieveUserByID(userID, locale),HttpStatus.OK);
	}

	/**
	 * Get all users
	 * @return list of all users present
	 */
	@GetMapping(path="/users/search")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		return new ResponseEntity<List<UserDTO>> (userService.retrieveAllUsers(), HttpStatus.OK);
	}

	/**
	 * Get users based on first name
	 * @param first name
	 * @return list of users matching first name criteria
	 */
	@GetMapping(path="/users/search", params={"firstName"})
	public ResponseEntity<List<UserDTO>> getUsersByFirstName(@RequestParam(value="firstName", required=true) String firstName) {
		return new ResponseEntity<List<UserDTO>> (userService.retrieveUsersByFirstName(firstName), HttpStatus.OK);
	}

	/**
	 * Get users based on last name
	 * @param lastName
	 * @return list of users matching last name criteria
	 */
	@GetMapping(path="/users/search", params={"lastName"})
	public ResponseEntity<List<UserDTO>> getUsersByLastName(@RequestParam(value="lastName", required=true) String lastName) {
		return new ResponseEntity<List<UserDTO>> (userService.retrieveUsersByLastName(lastName), HttpStatus.OK);
	}

	/**
	 * Get users based on email
	 * @param email
	 * @return list of users matching email criteria
	 */
	@GetMapping(path="/users/search", params={"email"})
	public ResponseEntity<List<UserDTO>> getUsersByEmail(@RequestParam(value="email", required=true) String email) {
		return new ResponseEntity<List<UserDTO>> (userService.retrieveUsersByEmail(email), HttpStatus.OK);
	}

	/**
	 * Update user info
	 * @param user ID to ientify user to update
	 * @param userDTO Object
	 * @return
	 * @throws UserNotFoundException
	 */
	@PutMapping("/users/{userID}")
	public ResponseEntity<UserDTO> updateUserInfo(@PathVariable(value="userID", required=true) int userID, 
			@RequestBody(required=true) @Valid UserDTO userDTOObject) throws UserException {
		return new ResponseEntity<UserDTO> (userService.updateUserInfo(userID, userDTOObject),HttpStatus.OK);
	}

	/**
	 * Delete user based on user ID
	 * @param user ID
	 * @return OK status if successful
	 * @throws UserNotFoundException
	 */
	@DeleteMapping("/users/{userID}")
	public ResponseEntity<Object> deleteUser(@PathVariable(value="userID", required=true) int userID) throws UserException,
		IllegalArgumentException {
		userService.deleteUser(userID);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	/**
	 * Add user address for user
	 * @param userAddressDTO Object
	 * @return userAddressDTO object that is added
	 * @throws UserAndUserAddressMismatchException
	 * @throws InvalidInputException
	 */
	@PostMapping("/users/addresses/create")
	public ResponseEntity<UserAddressDTO> addUserAddress(@RequestBody(required=true) @Valid UserAddressDTO userAddressDTOObject) 
			throws UserException, InvalidInputException {
		return new ResponseEntity<UserAddressDTO>(userService.addUserAddress(userAddressDTOObject),HttpStatus.CREATED);
	}

	/**
	 * Get all user address for a repsective user
	 * @param user ID
	 * @return list of added user addresses for required user
	 * @throws UserNotFoundException
	 */
	@GetMapping("/users/addresses/search/{userID}")
	public ResponseEntity<List<UserAddressDTO>> retrieveAllUserAddressesByUserID(@PathVariable(name="userID", required=true) int userID) 
			throws UserException {
		return new ResponseEntity<List<UserAddressDTO>> (userService.retrieveAllUserAddressesByUserID(userID), HttpStatus.OK);
	}

	/**
	 * Update specific user address for required user
	 * @param userAddress ID
	 * @param userAddressDTO Object
	 * @return updated user address
	 * @throws UserNotFoundException
	 * @throws UserAndUserAddressMismatchException
	 */
	@PutMapping("/users/addresses/{userID}")
	public ResponseEntity<UserAddressDTO> updateUserAddressInfo(@PathVariable(name="userID", required=true) int userAddressID, 
			@RequestBody(required=true) @Valid UserAddressDTO userAddressDTOObject) throws UserAddressException {
		return new ResponseEntity<UserAddressDTO> (userService.updateUserAddressInfo(userAddressID, userAddressDTOObject), HttpStatus.OK);
	}

	/**
	 * Delete specified user Address for required user
	 * @param user ID
	 * @param userAddress ID
	 * @return OK status if successful
	 * @throws UserException
	 * @throws UserAddressException
	 */
	@DeleteMapping(path="/users/addresses", params={"userID","userAddressID"})
	public ResponseEntity<Object> deleteUserAddress (@RequestParam(name="userID", required=true) int userID, 
			@RequestParam(name="userAddressID", required=true) int userAddressID) throws UserException, UserAddressException {
		userService.deleteUserAddress(userID, userAddressID);
		return new ResponseEntity<Object> (HttpStatus.OK);
	}

	/**
	 * Delete user addresses based on list of user IDs
	 * @param user ID
	 * @param userAddress IDs
	 * @return OK status if successful
	 * @throws UserException
	 * @throws UserAddressException
	 */
	@DeleteMapping(path="/users/addresses", params={"userID","userAddressIDs"})
	public ResponseEntity<Object> deleteAllUserAddresses(@RequestParam(name="userID", required=true) int userID, 
			@RequestParam(name="userAddressIDs", required=true) List<Integer> userAddressIDs) throws UserException, UserAddressException {
		return new ResponseEntity<Object> (HttpStatus.OK);
	}

}
