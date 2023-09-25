package com.eshop.eshopservice.manipulator;

import java.sql.Date;

import com.eshop.eshopmodel.consumer.User;
import com.eshop.eshopmodel.consumer.UserAddress;
import com.eshop.eshopmodel.logistics.Order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Manipulator Class for User. Allows manipulation of user addresses and orders lists.
 */

public class UserAdditionalManipulator extends User {

	/**
	 * No argument constructor for User Manipulator
	 */
	public UserAdditionalManipulator() {
		super();
	}

	/**
	 * Parameterized constructor for User Manipulator
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param mobileNumber
	 * @param createdDate
	 */
	public UserAdditionalManipulator(
			@NotBlank(message = "First Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String firstName,
			@NotBlank(message = "Last Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String lastName,
			@NotBlank(message = "Email Cannot be blank") @Email(message = "Email must be valid") String email,
			@NotBlank(message = "Mobile Number Cannot be blank") @Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Must start with 6,7,8 or 9 and be 10 digits long") long mobileNumber,
			Date createdDate) {
		super();
	}

	/**
	 * Add a user address to existing list of user addresses
	 * @param userAddress
	 */
	public void addUserAddress(UserAddress userAddress) {
		userAddress.setUser(this);
		getUserAddresses().add(userAddress);
	}

	/**
	 * Remove a user address to existing list of user addresses
	 * @param userAddress
	 */
	public void deleteUserAddress(UserAddress userAddress) {
		getUserAddresses().remove(userAddress);
		userAddress.setUser(null);
	}

	/**
	 * Add an order to existing list of user orders
	 * @param order
	 */
	public void addOrder(Order order) {
		order.setUser(this);
		getOrdersList().add(order);
		
	}
	
	/**
	 * Remove an order to existing list of user orders
	 * @param order
	 */
	public void deleteOrder(Order order) {
		getOrdersList().remove(order);
		order.setUser(null);
	}
	
	
	
}
