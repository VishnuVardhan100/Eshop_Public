package com.eshop.eshopservice.service;

import java.util.List;

import com.eshop.eshopmodel.consumer.UserAddressDTO;
import com.eshop.eshopmodel.consumer.UserDTO;
import com.eshop.exception.UserNotFoundException;

public interface UserServiceInterface {
	
	UserDTO createUser(UserDTO userDTO) throws UserNotFoundException;
	
	UserDTO retrieveUserByID(int userId) throws UserNotFoundException;
	
	List<UserDTO> retrieveAllUsers();

	List<UserDTO> retreiveUsersByFirstName(String firstName);

	List<UserDTO> retreiveUsersByLastName(String lastName);	
	
	List<UserDTO> retreiveUsersByEmail(String email);
	
	UserDTO updateUserInfo(int userID, UserDTO userDTO) throws UserNotFoundException;

	void deleteUser (int userId) throws UserNotFoundException;

	UserAddressDTO addUserAddress (UserAddressDTO userAddressDTO) throws UserNotFoundException;

	List<UserAddressDTO> retrieveAllUserAddressesByUserID(int userID) throws UserNotFoundException;
	
	UserAddressDTO updateUserAddressInfo(int userAddressID, UserAddressDTO userAddressDTO) throws UserNotFoundException;

	void deleteUserAddress (int userId, int userAddressId) throws UserNotFoundException;
	
	void deleteAllUserAddresses(int userID, List<Integer> userAddressIDs) throws UserNotFoundException;
	
}
