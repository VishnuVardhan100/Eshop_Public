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

	@SuppressWarnings("unused")
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
	@Pattern(regexp="^[6-9]{1}[0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	@Digits(fraction=0, integer=10)
	private String customerMobileNumber;
	
	@SuppressWarnings("unused")
	private Date customerCreatedDate;
	
	@Enumerated(EnumType.STRING)
	private CustomerSubscription customerSubscription;
	
}
