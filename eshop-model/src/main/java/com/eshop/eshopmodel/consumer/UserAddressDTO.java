package com.eshop.eshopmodel.consumer;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO class for User Address
 */

@Data
@Validated
public class UserAddressDTO {

	private int id;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[0-9_-:\\s]{1,8}$", message="Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters")
	private String houseNo;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9\\s]{5,20}$", message="Alphabets, spaces and digits allowed. Between 5-20 characters")
	private String street;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z\\s]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	private String city;

	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9\\s]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	private String state;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[0-9]{6}$", message="Only Digits and must be exact 6 characters")
	private String pincode;
	
	@NotNull(message="User Address has to be for a respective user")
	private User user;
	
}