package com.eshop.eshopmodel.consumer;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO class for User class.
 */

@Data
@Validated
public class UserDTO {

	private int id;

	@NotBlank(message="First Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	private String firstName;

	@NotBlank(message="Last Name cannot be Null")
	@Pattern(regexp="^[a-zA-Z\\s]{3,20}$", message="For Last name, regular alphabet and spaces are allowed. Between 3-20 characters")
	private String lastName;

	@NotBlank(message="Email Cannot be blank")
	@Email(message="Email must be valid")
	private String email;

	@NotNull(message="Mobile Number Cannot be blank")
	@Pattern(regexp="^[6-9]{1}[0-9]{9}$", message="Must start with 6,7,8 or 9 and be 10 digits long")
	private String mobileNumber;
	
	@Column(name="CreatedDate")
	private Date createdDate;
	
	@Enumerated(EnumType.STRING)
	private UserSubscription userSubscription;
	
}
