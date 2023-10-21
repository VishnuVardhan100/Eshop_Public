package com.eshop.eshopuserservice.model.customer;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Customer information sent by client to create customer account
 */

@Data
@Validated
@NoArgsConstructor
public class CustomerSignUpDTO {

	private long customerID;

	@NotBlank(message="Password cannot be blank")
	@Pattern(regexp="[a-zA-Z0-9 ]{8,15}",message="Alphabets, Digits and space. Limit 8 to 15 characters")
	private String customerPassword;

	@NotBlank(message="First Name cannot be blank")
	@Pattern(regexp="^[a-zA-Z ]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	private String customerFirstName;

	@NotBlank(message="Last Name cannot be blank")
	@Pattern(regexp="^[a-zA-Z ]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	private String customerLastName;

	@NotBlank(message="Email cannot be blank")
	@Email(message="Email must be valid")
	private String customerEmail;

	@NotNull(message="Mobile Number cannot be blank")
	@Pattern(regexp="^[6-9]{1}[0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	@Digits(fraction=0, integer=10)
	private String customerMobileNumber;
	
	private Date customerCreatedDate;
	
	@Enumerated(EnumType.STRING)
	private CustomerSubscription customerSubscription;

	@NotBlank(message="Roles cannot be blank")
	private String roles;

	public long getCustomerID() {
		return customerID;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public Date getCustomerCreatedDate() {
		return customerCreatedDate;
	}

	public CustomerSubscription getCustomerSubscription() {
		return customerSubscription;
	}

	public String getRoles() {
		return roles;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public void setCustomerPassword(String customerPassword) {
		this.customerPassword = customerPassword;
	}
	
	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public void setCustomerCreatedDate(Date customerCreatedDate) {
		this.customerCreatedDate = customerCreatedDate;
	}

	public void setCustomerSubscription(CustomerSubscription customerSubscription) {
		this.customerSubscription = customerSubscription;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
	
}
