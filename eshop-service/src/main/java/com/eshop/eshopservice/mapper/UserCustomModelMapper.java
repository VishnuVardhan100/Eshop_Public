package com.eshop.eshopservice.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.consumer.User;
import com.eshop.eshopmodel.consumer.UserAddress;
import com.eshop.eshopmodel.consumer.UserAddressDTO;
import com.eshop.eshopmodel.consumer.UserDTO;

/**
 * Custom Model Mapper class to map:
 * 	User to UserDTO and vice versa.
 * 	UserAddress to UserAddressDTO and vice versa.
 */

@Service
public class UserCustomModelMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * No argument Constructor
	 */
	public UserCustomModelMapper() {
		
	}

	/**
	 * To map user to a userDTO Object
	 * @param userObject
	 * @return User DTO Object
	 */
	public UserDTO mapUserToUserDTO(User userObject) {
		UserDTO userDTOReturnObject = modelMapper.map(userObject, UserDTO.class);
		return  userDTOReturnObject;
	}
	
	/**
	 * To map userDTO to a user Object
	 * @param userDTO Object
	 * @return User Object
	 */
	public User mapUserDTOToUser(UserDTO userDTOObject) {
		User userReturnObject = modelMapper.map(userDTOObject, User.class);
		return  userReturnObject;
	}

	/**
	 * To map userAddress to a userAddress DTO Object
	 * @param userAddress Object
	 * @return UserAddress DTO Object
	 */
	public UserAddressDTO mapUserAddressToUserAddressDTO(UserAddress userAddressObject) {
		UserAddressDTO userAddressDTOReturnObject = modelMapper.map(userAddressObject, UserAddressDTO.class);
		return  userAddressDTOReturnObject;
	}
	
	/**
	 * To map userAddress DTO object to a userAddress Object
	 * @param userAddress DTO Object
	 * @return userAddress Object
	 */
	public UserAddress mapUserAddressDTOToUserAddress(UserAddressDTO userAddressDTOObject) {
		UserAddress userAddressObject = modelMapper.map(userAddressDTOObject, UserAddress.class);
		return  userAddressObject;
	}
	
}
