package com.eshop.eshopmodel.customer;

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

/**
 * DTO class for customer class.
 */

@Data
@Validated
public class CustomerDTO {

	private long customerID;

	@NotBlank(message="Password cannot be empty or white space blanks")
	@Pattern(regexp="^[a-zA-Z0-9 ]{8,20}$" , message="Password can be lower , upper alphabets, digits and whitespace. Length must be between 8 and 20 ,both inclusive")
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

	//ALL GETTERS
	
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

}
