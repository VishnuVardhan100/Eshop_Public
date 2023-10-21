package com.eshop.eshopcommunicationsservice.model.customer;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopcommunicationsservice.model.logistics.Order;
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
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Base Entity Class for Customer.
 */

@Data
@Entity
@Table( name="Customer",
		uniqueConstraints= {
				@UniqueConstraint(name="UniqueEmail", columnNames = { "Customer_Email" })
		})
@Validated
public class Customer implements Serializable{

	/**
	 * Default Version
	 */
	private static final long serialVersionUID = 1L;

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Customer_ID")
	private long customerID;
	
	@NotBlank(message="Password cannot be empty or white space blanks")
	//@Pattern(regexp="^[a-zA-Z0-9 ]{8,20}$" , message="Password can be lower , upper alphabets, digits and whitespace. Length must be between 8 and 20 ,both inclusive")
	@Column(name="Customer_Password")
	private String customerPassword;

	@NotBlank(message="First Name cannot be blank")
	@Pattern(regexp="^[a-zA-Z ]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	@Column(name="Customer_First_Name")
	private String customerFirstName;

	@NotBlank(message="Last Name cannot be blank")
	@Pattern(regexp="^[a-zA-Z ]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	@Column(name="Customer_Last_Name")
	private String customerLastName;

	@NotBlank(message="Email cannot be blank")
	@Email(message="Email must be valid")
	@Column(name="Customer_Email")
	private String customerEmail;

	@NotBlank(message="Mobile Number cannot be blank")
	@Pattern(regexp="^[6-9]{1}[0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	@Digits(fraction=0, integer=10)
	@Column(name="Customer_Mobile_Number")
	private String customerMobileNumber;
	
	@Column(name="Customer_Created_Date")
	private Date customerCreatedDate;

	@OneToMany(mappedBy="customer",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<CustomerAddress> customerAddresses = new ArrayList<CustomerAddress>();
	
	@OneToMany(mappedBy="customer",fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JsonProperty(access = Access.WRITE_ONLY)
	private List<Order> ordersList = new ArrayList<Order>();
	
	@Enumerated(EnumType.STRING)
	@Column(name="Customer_Subscription")
	private CustomerSubscription customerSubscription;

	@NotBlank(message="Roles cannot be blank")
	@Column(name="Roles")
	private String roles;
	
	/**
	 * No argument Constructor for customer class
	 */
	public Customer() {
		
	}

	/**
	 * Parameterized Constructor
	 * @param customerID
	 * @param customerPassword
	 * @param customerFirstName
	 * @param customerLastName
	 * @param customerEmail
	 * @param customerMobileNumber
	 * @param customerCreatedDate
	 * @param customerAddresses
	 * @param ordersList
	 * @param customerSubscription
	 * @param roles
	 */
	public Customer(long customerID,
			@NotBlank(message="Password cannot be empty or white space blanks") 
			/*@Pattern(regexp="^[a-zA-Z0-9 ]{8,20}$" , message="Password can be lower , upper alphabets, digits and whitespace. Length must be between 8 and 20 ,both inclusive")*/
			String customerPassword,
			@NotBlank(message = "First Name cannot be blank") @Pattern(regexp = "^[a-zA-Z ]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String customerFirstName,
			@NotBlank(message = "Last Name cannot be blank") @Pattern(regexp = "^[a-zA-Z ]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String customerLastName,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email must be valid") String customerEmail,
			@NotBlank(message = "Mobile Number cannot be blank") @Pattern(regexp = "^[6-9]{1}[0-9]{9}$", message = "Must start with 6,7,8 or 9 and be 10 digits long") @Digits(fraction = 0, integer = 10) String customerMobileNumber,
			Date customerCreatedDate, 
			List<CustomerAddress> customerAddresses, 
			List<Order> ordersList,
			CustomerSubscription customerSubscription,
			@NotBlank(message="Roles cannot be blank") String roles) {
		super();
		this.customerID = customerID;
		this.customerPassword = customerPassword;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerEmail = customerEmail;
		this.customerMobileNumber = customerMobileNumber;
		this.customerCreatedDate = customerCreatedDate;
		this.customerAddresses = customerAddresses;
		this.ordersList = ordersList;
		this.customerSubscription = customerSubscription;
		this.roles=roles;
	}

	/**
	 * @return customer id
	 */
	public long getCustomerID() {
		return customerID;
	}

	/**
	 * Set customer id
	 * @param id
	 */
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
	
	/**
	 * @return customer password
	 */
	public String getCustomerPassword() {
		return customerPassword;
	}

	/**
	 * set customer password
	 * @param customer password
	 */
	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}

	/**
	 * @return customer first name
	 */
	public String getCustomerFirstName() {
		return customerFirstName;
	}

	/**
	 * set customer first name
	 * @param firstName
	 */
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	/**
	 * @return customer last name
	 */
	public String getCustomerLastName() {
		return customerLastName;
	}

	/**
	 * set customer last name
	 * @param lastName
	 */
	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	/**
	 * @return customer email
	 */
	public String getCustomerEmail() {
		return customerEmail;
	}

	/**
	 * set customer email
	 * @param email
	 */
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	/**
	 * @return customer mobile number
	 */
	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	/**
	 * set customer mobile number
	 * @param mobileNumber
	 */
	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	/**
	 * @return customer create date
	 */
	public Date getCustomerCreatedDate() {
		return customerCreatedDate;
	}

	/**
	 * set customer create date
	 * @param createdDate
	 */
	public void setCustomerCreatedDate(Date customerCreatedDate) {
		this.customerCreatedDate = customerCreatedDate;
	}

	/**
	 * @return customer addresses list
	 */
	public List<CustomerAddress> getCustomerAddresses() {
		return customerAddresses;
	}

	/**
	 * set customer addresses list
	 * @param customerAddresses
	 */
	public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
		this.customerAddresses = customerAddresses;
	}

	/**
	 * @return customer orders list
	 */
	public List<Order> getOrdersList() {
		return ordersList;
	}

	/**
	 * set customer orders list
	 * @param ordersList
	 */
	public void setOrdersList(List<Order> ordersList) {
		this.ordersList = ordersList;
	}

	/**
	 * @return customer Subscription
	 */
	public CustomerSubscription getCustomerSubscription() {
		return customerSubscription;
	}

	/**
	 * set customer Subscription
	 * @param customerSubscription
	 */
	public void setCustomerSubscription(CustomerSubscription customerSubscription) {
		this.customerSubscription = customerSubscription;
	}

	public String getRoles() {
		return roles;
	}
	
	public void setRoles(String roles) {
		this.roles = roles;
	}
	
	/**
	 * Returns Customer ID, first name , last name, email, mobile number and created date
	 */
	@Override
	public String toString() {
		return "Customer [ID = " + customerID + ", First Name = " + customerFirstName + ", Last Name = " + customerLastName + ", Email = " + customerEmail
				+ ", Mobile Number = " + customerMobileNumber + ", Created Date = " + customerCreatedDate + ", Customer Subscription = " + customerSubscription +
				", Roles = " + roles +  "]";
	}

}
