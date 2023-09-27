package com.eshop.eshopservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
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
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.UserAddressException;
import com.eshop.exception.UserException;

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
	 * Connects to Repository to create User
	 * @param UserDTO Object from Client side
	 * @param locale
	 * @return User DTO Object after successful User creation
	 * @throws UserNotFoundException
	 */
	@Override
	public UserDTO createUser(UserDTO userDTOObject, Locale locale) throws InvalidInputException{
		User userObject = userCustomModelMapper.mapUserDTOToUser(userDTOObject);
		User userReturnObject = userRepository.save(userObject);
		userObject = null;

		if(userReturnObject == null) {
			throw new InvalidInputException(messageSource.getMessage("InvalidInput", null, locale));
		}

		return userCustomModelMapper.mapUserToUserDTO(userReturnObject);
	}

	/**
	 * Retrieve a user by their ID
	 * @param integer user ID
	 * @param locale
	 * @return UserDTO object
	 * @throws UserNotFoundException
	 */
	@Override
	public UserDTO retrieveUserByID(int userID, Locale locale) throws UserException {
		Optional<User> userReturnObject = Optional.of(userRepository.findById(userID).
				orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, locale))));
		return userCustomModelMapper.mapUserToUserDTO(userReturnObject.get());
	}

	/**
	 * To retrieve users based on first name
	 * @param First Name of user
	 * @return list of matched user DTOs
	 */
	@Override
	public List<UserDTO> retrieveUsersByFirstName(String firstName) {
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
	public List<UserDTO> retrieveUsersByLastName(String lastName) {
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
	public List<UserDTO> retrieveUsersByEmail(String email) {
		List<User> usersByEmail = userRepository.retreiveUsersByEmail("%" + email + "%");
		List<UserDTO> allReturnUserDTO = new ArrayList<UserDTO>(5);

		for(User user : usersByEmail) {
			allReturnUserDTO.add(userCustomModelMapper.mapUserToUserDTO(user));
		}
		usersByEmail = null;
		return allReturnUserDTO;
	}

	/**
	 * Retrieve all users
	 * @return list of all user DTOs
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
	 * Update any user specific base info
	 * @param userID to find if user exists
	 * @param userDTO object to take user info from
	 * @return updated instance of user as userDTO
	 * @throws UserException
	 */
	@Override
	public UserDTO updateUserInfo(int userID, UserDTO userDTOObject) throws UserException, UserAddressException {
		User userRetrieveObject = userRepository.findById(userID).
				orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		User userObject = userCustomModelMapper.mapUserDTOToUser(userDTOObject);
		if(userID != userObject.getId()) {
			throw new UserAddressException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
		}

		//CAUTION: We should not change the User ID - primary identifier. It stays same as when created
		userRetrieveObject.setFirstName(userObject.getFirstName());
		userRetrieveObject.setLastName(userObject.getLastName());
		userRetrieveObject.setEmail(userObject.getEmail());
		userRetrieveObject.setMobileNumber(userObject.getMobileNumber());
		//CAUTION: We should not change the User Created Date. It stays same as when created

		User userReturnObject = userRepository.save(userRetrieveObject);
		userRetrieveObject = null;
		userObject = null;

		return userCustomModelMapper.mapUserToUserDTO(userReturnObject);
	}

	/**
	 * Delete the user based on userID
	 * @param userID
	 * @throws UserException
	 * @throws IllegalArgumentException
	 */
	@Override
	public void deleteUser(int userID) throws UserException, IllegalArgumentException {		
		if(!userRepository.existsById(userID)) {
			throw new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale()));
		}
		
		userRepository.deleteById(userID);
	}

	/**
	 * Add a user address
	 * @param userAddress DTO object
	 * @return the added userAddress DTO object
	 * @throws UserAddressException
	 * @throws InvalidInputException
	 */
	@Override
	public UserAddressDTO addUserAddress(UserAddressDTO userAddressDTOObject) throws UserException, 
		InvalidInputException {		
		UserAddress userAddressObject = userCustomModelMapper.mapUserAddressDTOToUserAddress(userAddressDTOObject);
		userRepository.findById(userAddressObject.getUser().getId()).
				orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		//userObject.getUserAddresses().add(userAddressObject);
		//userObject = userRepository.save(userObject);
		UserAddress userAddressReturnObject = userAddressRepository.save(userAddressObject);

		if(userAddressReturnObject == null) {
			throw new InvalidInputException(messageSource.getMessage("InvalidInput", null, LocaleContextHolder.getLocale()));
		}
		userAddressObject = null;
		//userObject = null;

		return userCustomModelMapper.mapUserAddressToUserAddressDTO(userAddressReturnObject);
	}

	/**
	 * Get all the added user addresses for a valid user
	 * @param user Id of valid user
	 * @return list of user address DTOs for said user
	 * @throws UserException
	 */
	@Override
	public List<UserAddressDTO> retrieveAllUserAddressesByUserID(int userID) throws UserException {
		User user = userRepository.findById(userID).
				orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

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
	 * @throws UserException
	 * @throws UserAddresssException
	 */
	@Override
	public UserAddressDTO updateUserAddressInfo(int userID, UserAddressDTO userAddressDTOObject) throws UserException, UserAddressException {
		if(!userRepository.existsById(userID)) {
			throw new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale()));
		}

		UserAddress userAddressObject = userCustomModelMapper.mapUserAddressDTOToUserAddress(userAddressDTOObject);
		if(userID != userAddressObject.getUser().getId()) {
			throw new UserAddressException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
		}

		UserAddress userAddressRetrieveObject = userAddressRepository.findById(userAddressObject.getId()).
				orElseThrow(() -> new UserAddressException(messageSource.getMessage("UserAddressNotFound", null, LocaleContextHolder.getLocale())));

		//CAUTION: We should not change the UserAddress ID - primary identifier. It stays same as when created
		userAddressRetrieveObject.setHouseNo(userAddressObject.getHouseNo());
		userAddressRetrieveObject.setStreet(userAddressObject.getStreet());
		userAddressRetrieveObject.setCity(userAddressObject.getCity());
		userAddressRetrieveObject.setState(userAddressObject.getState());
		userAddressRetrieveObject.setPincode(userAddressObject.getPincode());
		//CAUTION: We should not change the User Entity - It should be created/updated/deleted separately.

		UserAddress userAddressReturnObject = userAddressRepository.save(userAddressRetrieveObject);
		userAddressRetrieveObject = null;
		userAddressObject = null;

		return userCustomModelMapper.mapUserAddressToUserAddressDTO(userAddressReturnObject);
	}

	/**
	 * Delete a user address by given user address ID
	 * @param user address ID
	 * @throws UserException
	 * @throws UserAddressException
	 */
	@Override
	public void deleteUserAddress(int userId, int userAddressId) throws UserException, UserAddressException {
		User userObject = userRepository.findById(userId).
		orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		UserAddress userAddressObject = userAddressRepository.findById(userAddressId).
		orElseThrow(() -> new UserAddressException(messageSource.getMessage("UserAddressNotFound", null, LocaleContextHolder.getLocale())));

		if(userObject.getId() != userAddressObject.getUser().getId()) {
			throw new UserAddressException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
		}

		userObject.getUserAddresses().remove(userAddressObject);
		userRepository.save(userObject);
		//userAddressObject.setUser(null);
		userAddressRepository.deleteById(userAddressId);
		userAddressObject = null;
		userObject = null;
	}

	/**
	 * Delete a list of user addresses by their IDs
	 * @param user ID for whom the user addresses need to be deleted
	 * @param list of user address IDs
	 * @throws UserException
	 * @throws UserAddressException
	 */
	@Override
	public void deleteAllUserAddresses(int userID, List<Integer> userAddressIDs) throws UserException, UserAddressException {
		User userObject = userRepository.findById(userID).
		orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		for(Integer userAddressID : userAddressIDs) {
			System.out.println(userAddressID);
			UserAddress userAddressObject = userAddressRepository.findById(userAddressID).
			orElseThrow(() -> new UserAddressException(messageSource.getMessage("UserAddressNotFound", null, LocaleContextHolder.getLocale())));

			if(userObject.getId() != userAddressObject.getUser().getId()) {
				throw new UserAddressException(messageSource.getMessage("UserAndUserAddressMismatch", null, LocaleContextHolder.getLocale()));
			}

			userObject.getUserAddresses().remove(userAddressObject);
			userRepository.save(userObject);
			//userAddressObject.setUser(null);
			userAddressRepository.deleteById(userAddressID);
			userAddressObject = null;
		}
		userObject = null;
	}

}
