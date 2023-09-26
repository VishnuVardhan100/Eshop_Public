package com.eshop.eshopservice.service;

import java.util.List;
import java.util.Locale;

import com.eshop.eshopmodel.consumer.UserAddressDTO;
import com.eshop.eshopmodel.consumer.UserDTO;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.UserAddressException;
import com.eshop.exception.UserException;

public interface UserServiceInterface {
	
	UserDTO createUser(UserDTO userDTO, Locale locale) throws InvalidInputException;

	UserDTO retrieveUserByID(int userId, Locale locale) throws UserException;

	List<UserDTO> retrieveAllUsers();

	List<UserDTO> retrieveUsersByFirstName(String firstName);

	List<UserDTO> retrieveUsersByLastName(String lastName);	

	List<UserDTO> retrieveUsersByEmail(String email);

	UserDTO updateUserInfo(int userID, UserDTO userDTO) throws UserException;

	void deleteUser (int userID) throws UserException, IllegalArgumentException;

	UserAddressDTO addUserAddress (UserAddressDTO userAddressDTO) throws UserException,
	InvalidInputException;

	List<UserAddressDTO> retrieveAllUserAddressesByUserID(int userID) throws UserException;

	UserAddressDTO updateUserAddressInfo(int userAddressID, UserAddressDTO userAddressDTO) throws UserAddressException;

	void deleteUserAddress (int userId, int userAddressId) throws UserException, UserAddressException;

	void deleteAllUserAddresses(int userID, List<Integer> userAddressIDs) throws UserException, UserAddressException;

}
