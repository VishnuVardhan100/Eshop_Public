package com.eshop.eshopmodel.customer;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO class for Customer Address
 */

@Data
@Validated
public class CustomerAddressDTO {

	@SuppressWarnings("unused")
	private long addressID;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[-_:0-9 ]{1,8}$", message="Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters")
	private String houseNo;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9 ]{5,20}$", message="Alphabets, spaces and digits allowed. Between 5-20 characters")
	private String street;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z ]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	private String city;

	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[a-zA-Z0-9 ]{3,15}$", message="Alphabets, spaces and digits allowed. Between 3-15 characters")
	private String state;
	
	@NotBlank(message="Cannot be Blank")
	@Pattern(regexp="^[0-9]{6}$", message="Only Digits and must be exact 6 characters")
	private String pincode;
	
	@NotNull(message="Customer Address has to be for a respective customer")
	private Customer customer;
	
}
