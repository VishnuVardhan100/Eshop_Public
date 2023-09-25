package com.eshop.eshopmodel.consumer;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopmodel.logistics.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Base Entity Class for User.
 */

@Data
@Entity
@Table(name="ConsumerUser")
@Validated
public class User {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserID")
	private int id;

	@NotBlank(message="First Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	@Column(name="FirstName")
	private String firstName;

	@NotBlank(message="Last Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	@Column(name="LastName")
	private String lastName;

	@NotBlank(message="Email Cannot be blank")
	@Email(message="Email must be valid")
	@Column(name="Email")
	private String email;

	@NotBlank(message="Mobile Number Cannot be blank")
	@Pattern(regexp="^[6-9]{1}[0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	@Column(name="MobileNumber")
	private String mobileNumber;
	
	@Column(name="CreatedDate")
	private Date createdDate;

	@OneToMany(mappedBy="user",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)
	@Column(name="UserAddressID")
	private List<UserAddress> userAddresses = new ArrayList<UserAddress>();
	
	@OneToMany(mappedBy="user",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)	
	@Column(name="OrderID")	
	private List<Order> ordersList = new ArrayList<Order>();
	
	@Enumerated(EnumType.STRING)
	@Column(name="UserSubscription")
	private UserSubscription userSubscription;
	
	/**
	 * No argument Constructor for user class
	 */
	public User() {
		
	}

	/**
	 * Parameterized Constructor
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param email
	 * @param mobileNumber
	 * @param createdDate
	 * @param userAddresses
	 * @param ordersList
	 * @param userSubscription
	 */
	public User(int id,
			@NotBlank(message = "First Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String firstName,
			@NotBlank(message = "Last Name cannot be Null") @Pattern(regexp = "^[a-zA-Z\\s]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String lastName,
			@NotBlank(message = "Email Cannot be blank") @Email(message = "Email must be valid") String email,
			@NotBlank(message = "Mobile Number Cannot be blank") @Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Must start with 6,7,8 or 9 and be 10 digits long") String mobileNumber,
			Date createdDate, List<UserAddress> userAddresses, List<Order> ordersList,
			UserSubscription userSubscription) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.createdDate = createdDate;
		this.userAddresses = userAddresses;
		this.ordersList = ordersList;
		this.userSubscription = userSubscription;
	}

	/**
	 * @return user id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set user id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return user first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * set user first name
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return user last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * set user last name
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return user email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * set user email
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * @return user mobile number
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * set user mobile number
	 * @param mobileNumber
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return user create date
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * set user create date
	 * @param createdDate
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return user addresses list
	 */
	public List<UserAddress> getUserAddresses() {
		return userAddresses;
	}

	/**
	 * set user addresses list
	 * @param userAddresses
	 */
	public void setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}

	/**
	 * @return user orders list
	 */
	public List<Order> getOrdersList() {
		return ordersList;
	}

	/**
	 * set user orders list
	 * @param ordersList
	 */
	public void setOrdersList(List<Order> ordersList) {
		this.ordersList = ordersList;
	}

	/**
	 * @return User Subscription
	 */
	public UserSubscription getUserSubscription() {
		return userSubscription;
	}

	/**
	 * set User Subscription
	 * @param userSubscription
	 */
	public void setUserSubscription(UserSubscription userSubscription) {
		this.userSubscription = userSubscription;
	}

	/**
	 * Returns User ID, first name , last name, email, mobile number and created date
	 */
	@Override
	public String toString() {
		return "User [ID=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", mobileNumber=" + mobileNumber + ", createdDate=" + createdDate + "]";
	}

}
