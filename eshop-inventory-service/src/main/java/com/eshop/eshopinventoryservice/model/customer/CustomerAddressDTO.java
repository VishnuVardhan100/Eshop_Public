package com.eshop.eshopinventoryservice.model.customer;

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
	
	public CustomerAddressDTO() {
		
	}

	public CustomerAddressDTO(long addressID,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[-_:0-9 ]{1,8}$", message = "Digits allowed and special characters(_ , -) allowed. Between 1 to 8 characters") String houseNo,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9 ]{5,20}$", message = "Alphabets, spaces and digits allowed. Between 5-20 characters") String street,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z ]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String city,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[a-zA-Z0-9 ]{3,15}$", message = "Alphabets, spaces and digits allowed. Between 3-15 characters") String state,
			@NotBlank(message = "Cannot be Blank") @Pattern(regexp = "^[0-9]{6}$", message = "Only Digits and must be exact 6 characters") String pincode,
			@NotNull(message = "Customer Address has to be for a respective customer") Customer customer) {
		super();
		this.addressID = addressID;
		this.houseNo = houseNo;
		this.street = street;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
		this.customer = customer;
	}

	public long getAddressID() {
		return addressID;
	}

	public void setAddressID(long addressID) {
		this.addressID = addressID;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
