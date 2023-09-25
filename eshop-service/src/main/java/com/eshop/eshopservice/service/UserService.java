package com.eshop.eshopservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.consumer.User;
import com.eshop.eshopmodel.consumer.UserAddress;
import com.eshop.eshopmodel.consumer.UserAddressDTO;
import com.eshop.eshopmodel.consumer.UserDTO;
import com.eshop.eshoprepository.UserAddressRepository;
import com.eshop.eshoprepository.UserRepository;
import com.eshop.eshopservice.mapper.UserCustomModelMapper;
import com.eshop.exception.UserNotFoundException;

/**
 * Service class for User related operations
 */

@Service
public class UserService implements UserServiceInterface{

	@Autowired   
	private MessageSource messageSource;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserAddressRepository userAddressRepository;
	
	@Autowired
	private UserCustomModelMapper userCustomModelMapper;
	
	/**
	 * Connects to Repository to create user
	 * @param userDTO Object from client
	 * @return userDTO Object after successful user creation
	 */
	@Override
	public UserDTO createUser(UserDTO userDTOObject) throws UserNotFoundException{
		User userObject = userCustomModelMapper.mapUserDTOToUser(userDTOObject);
		User userReturnObject = userRepository.save(userObject);
		userObject = null;
		
		if(userReturnObject == null) {
			throw new UserNotFoundException(messageSource.getMessage("InvalidInput", null, LocaleContextHolder.getLocale()));
		}
		
		return userCustomModelMapper.mapUserToUserDTO(userReturnObject);
	}

	/**
	 * Retrieve a user by their ID
	 * @param user ID
	 * @return UserDTO object
	 */
	@Override
	public UserDTO retrieveUserByID(int userId) throws UserNotFoundException {
		Optional<User> userReturnObject = Optional.of(userRepository.findById(userId).
				orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale()))));
		return userCustomModelMapper.mapUserToUserDTO(userReturnObject.get());
	}

	/**
	 * Retrieve all users
	 * return list of all user DTOs
	 */
	@Override
	public List<UserDTO> retrieveAllUsers() {
		List<User> allUsers = userRepository.findAll();
		List<UserDTO> allReturnUserDTO = new ArrayList<UserDTO>(5);
		
		for(User user : allUsers) {
			allReturnUserDTO.add(userCustomModelMapper.mapUserToUserDTO(user));
		}
		allUsers = null;
		return allReturnUserDTO;
	}

	/**
	 * To retrieve users based on first name
	 * @param First Name of user
	 * @return list of matched user DTOs
	 */
	@Override
	public List<UserDTO> retreiveUsersByFirstName(String firstName) {
		List<User> usersByFirstName = userRepository.retreiveUsersByFirstName("%" + firstName + "%");
		List<UserDTO> allReturnUserDTO = new ArrayList<UserDTO>(5);
		
		for(User user : usersByFirstName) {
			allReturnUserDTO.add(userCustomModelMapper.mapUserToUserDTO(user));
		}
		usersByFirstName = null;
		return allReturnUserDTO;
	}

	/**
	 * To retrieve users based on last name
	 * @param Last Name of user
	 * @return list of matched user DTOs
	 */
	@Override
	public List<UserDTO> retreiveUsersByLastName(String lastName) {
		List<User> usersByLastName = userRepository.retreiveUsersByLastName("%" + lastName + "%");
		List<UserDTO> allReturnUserDTO = new ArrayList<UserDTO>(5);
		
		for(User user : usersByLastName) {
			allReturnUserDTO.add(userCustomModelMapper.mapUserToUserDTO(user));
		}
		usersByLastName = null;
		return allReturnUserDTO;
	}

	/**
	 * To retrieve users based on email
	 * @param email of user
	 * @return list of matched user DTOs
	 */
	@Override
	public List<UserDTO> retreiveUsersByEmail(String email) {
		List<User> usersByEmail = userRepository.retreiveUsersByEmail("%" + email + "%");
		List<UserDTO> allReturnUserDTO = new ArrayList<UserDTO>(5);
		
		for(User user : usersByEmail) {
			allReturnUserDTO.add(userCustomModelMapper.mapUserToUserDTO(user));
		}
		usersByEmail = null;
		return allReturnUserDTO;
	}
	
	/**
	 * Update any user specific base info
	 * @param userID to find if user exists
	 * @param userDTO object to take user info from
	 * @return updated instance of user as userDTO
	 */
	@Override
	public UserDTO updateUserInfo(int userID, UserDTO userDTOObject) throws UserNotFoundException {
		User userRetrieveObject = userRepository.findById(userID).
				orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));
		User userObject = userCustomModelMapper.mapUserDTOToUser(userDTOObject);

		userRetrieveObject.setFirstName(userObject.getFirstName());
		userRetrieveObject.setLastName(userObject.getLastName());
		userRetrieveObject.setEmail(userObject.getEmail());
		userRetrieveObject.setMobileNumber(userObject.getMobileNumber());
		
		User userReturnObject = userRepository.save(userRetrieveObject);
		userRetrieveObject = null;
		userObject = null;
		
		return userCustomModelMapper.mapUserToUserDTO(userReturnObject);
	}

	/**
	 * Delete the user based on userID
	 * @param userID
	 */
	@Override
	public void deleteUser(int userId) throws UserNotFoundException {
		userRepository.findById(userId).
				orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));
		userRepository.deleteById(userId);
	}

	/**
	 * Add a user address
	 * @param userAddress DTO object
	 * @return the added userAddress DTO object
	 */
	@Override
	public UserAddressDTO addUserAddress(UserAddressDTO userAddressDTOObject) throws UserNotFoundException {
		
		UserAddress userAddressObject = userCustomModelMapper.mapUserAddressDTOToUserAddress(userAddressDTOObject);
		userRepository.findById(userAddressObject.getUser().getId()).
				orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserForUserAddressNotFound", null, LocaleContextHolder.getLocale())));

		UserAddress userAddressReturnObject = userAddressRepository.save(userAddressObject);
		userAddressObject = null;
		
		if(userAddressReturnObject == null) {
			throw new UserNotFoundException(messageSource.getMessage("InvalidInput", null, LocaleContextHolder.getLocale()));
		}
		
		return userCustomModelMapper.mapUserAddressToUserAddressDTO(userAddressReturnObject);
	}

	/**
	 * Get all the added user addresses for a valid user
	 * @param user Id of valid user
	 * @return list of user address DTOs for said user
	 */
	@Override
	public List<UserAddressDTO> retrieveAllUserAddressesByUserID(int userID) throws UserNotFoundException {
		User user = userRepository.findById(userID).
				orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		List<UserAddress> allUserAddresses = user.getUserAddresses();
		List<UserAddressDTO> allReturnUserAddressDTO = new ArrayList<UserAddressDTO>(5);
		
		for(UserAddress userAddress : allUserAddresses) {
			allReturnUserAddressDTO.add(userCustomModelMapper.mapUserAddressToUserAddressDTO(userAddress));
		}
		allUserAddresses = null;
		user = null;
		return allReturnUserAddressDTO;
	}

	/**
	 * Update an existing user address for valid user
	 * @param the userAddress DTO object
	 * @return the updated userAddress DTO object
	 */
	@Override
	public UserAddressDTO updateUserAddressInfo(int userAddressID, UserAddressDTO userAddressDTOObject) throws UserNotFoundException {

		UserAddress userAddressObject = userCustomModelMapper.mapUserAddressDTOToUserAddress(userAddressDTOObject);
		if(userAddressID != userAddressObject.getUser().getId()) {
			throw new UserNotFoundException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
		}

		userRepository.findById(userAddressObject.getUser().getId()).
		orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserForUserAddressNotFound", null, LocaleContextHolder.getLocale())));
		
		UserAddress userAddressRetrieveObject = userAddressRepository.findById(userAddressID).
				orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserAddressNotFound", null, LocaleContextHolder.getLocale())));
		
		userAddressRetrieveObject.setId(userAddressObject.getId());
		userAddressRetrieveObject.setHouseNo(userAddressObject.getHouseNo());
		userAddressRetrieveObject.setStreet(userAddressObject.getStreet());
		userAddressRetrieveObject.setCity(userAddressObject.getCity());
		userAddressRetrieveObject.setState(userAddressObject.getState());
		userAddressRetrieveObject.setPincode(userAddressObject.getPincode());
		userAddressRetrieveObject.setUser(userAddressObject.getUser());
		
		UserAddress userAddressReturnObject = userAddressRepository.save(userAddressRetrieveObject);
		userAddressRetrieveObject = null;
		userAddressObject = null;
		
		return userCustomModelMapper.mapUserAddressToUserAddressDTO(userAddressReturnObject);
	}

	/**
	 * Delete a user address by given user address ID
	 * @param user address ID
	 */
	@Override
	public void deleteUserAddress(int userId, int userAddressId) throws UserNotFoundException {
		User userObject = userRepository.findById(userId).
		orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		UserAddress userAddressObject = userAddressRepository.findById(userAddressId).
		orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserAddressNotFound", null, LocaleContextHolder.getLocale())));

		if(userObject.getId() != userAddressObject.getUser().getId()) {
			throw new UserNotFoundException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
		}
		userAddressRepository.deleteById(userAddressId);
	}

	/**
	 * Delete a list of user addresses by their IDs
	 * @param user ID for whom the user addresses need to be deleted
	 * @param list of user address IDs
	 */
	@Override
	public void deleteAllUserAddresses(int userId, List<Integer> userAddressIDs) throws UserNotFoundException {
		
		User userObject = userRepository.findById(userId).
		orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		for(Integer userAddressID : userAddressIDs) {
			UserAddress userAddressObject = userAddressRepository.findById(userAddressID).
			orElseThrow(() -> new UserNotFoundException(messageSource.getMessage("UserAddressNotFound", null, LocaleContextHolder.getLocale())));
			
			if(userObject.getId() != userAddressObject.getUser().getId()) {
				throw new UserNotFoundException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
			}
			userAddressRepository.deleteById(userAddressID);
		}		
	}
	
}
