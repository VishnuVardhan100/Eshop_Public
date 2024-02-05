package com.eshop.eshopstandardgateway.model.customer;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.sql.Date;

/**
 * DTO class for customer class.
 */

@Data
@Validated
public class CustomerDTO {

	private long customerID;

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
	@Pattern(regexp="^[6-9][0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	@Digits(fraction=0, integer=10)
	private String customerMobileNumber;
	
	private Date customerCreatedDate;
	
	@Enumerated(EnumType.STRING)
	private CustomerSubscription customerSubscription;

	@NotBlank(message="Roles cannot be blank")
	private String roles;

	public CustomerDTO() {
		
	}
	
	public CustomerDTO(long customerID,
			@NotBlank(message = "First Name cannot be blank") @Pattern(regexp = "^[a-zA-Z ]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String customerFirstName,
			@NotBlank(message = "Last Name cannot be blank") @Pattern(regexp = "^[a-zA-Z ]{3,20}$", message = "For Last name, regular alphabet and spaces are allowed. Between 3-20 characters") String customerLastName,
			@NotBlank(message = "Email cannot be blank") @Email(message = "Email must be valid") String customerEmail,
			@NotNull(message = "Mobile Number cannot be blank") @Pattern(regexp = "^[6-9][0-9]{9}$", message = "Must start with 6,7,8 or 9 and be 10 digits long") @Digits(fraction = 0, integer = 10) String customerMobileNumber,
			Date customerCreatedDate, CustomerSubscription customerSubscription,
			@NotBlank(message = "Roles cannot be blank") String roles) {
		super();
		this.customerID = customerID;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerEmail = customerEmail;
		this.customerMobileNumber = customerMobileNumber;
		this.customerCreatedDate = customerCreatedDate;
		this.customerSubscription = customerSubscription;
		this.roles = roles;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerMobileNumber() {
		return customerMobileNumber;
	}

	public void setCustomerMobileNumber(String customerMobileNumber) {
		this.customerMobileNumber = customerMobileNumber;
	}

	public Date getCustomerCreatedDate() {
		return customerCreatedDate;
	}

	public void setCustomerCreatedDate(Date customerCreatedDate) {
		this.customerCreatedDate = customerCreatedDate;
	}

	public CustomerSubscription getCustomerSubscription() {
		return customerSubscription;
	}

	public void setCustomerSubscription(CustomerSubscription customerSubscription) {
		this.customerSubscription = customerSubscription;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
