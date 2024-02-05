package com.eshop.eshopuserservice.model.customer;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * Customer information sent to update password
 */

@Data
@Validated
public class CustomerPasswordUpdateDTO {

	private long customerID;

	@NotBlank(message="Password cannot be blank")
	@Pattern(regexp="[a-zA-Z0-9 ]{8,15}",message="Alphabets, Digits and space. Limit 8 to 15 characters")
	private String customerOldPassword;

	@NotBlank(message="Password cannot be blank")
	@Pattern(regexp="[a-zA-Z0-9 ]{8,15}",message="Alphabets, Digits and space. Limit 8 to 15 characters")
	private String customerNewPassword;
	
	public CustomerPasswordUpdateDTO() {
		
	}

	public CustomerPasswordUpdateDTO(long customerID,
			@NotBlank(message = "Password cannot be blank") @Pattern(regexp = "[a-zA-Z0-9 ]{8,15}", message = "Alphabets, Digits and space. Limit 8 to 15 characters") String customerOldPassword,
			@NotBlank(message = "Password cannot be blank") @Pattern(regexp = "[a-zA-Z0-9 ]{8,15}", message = "Alphabets, Digits and space. Limit 8 to 15 characters") String customerNewPassword) {
		super();
		this.customerID = customerID;
		this.customerOldPassword = customerOldPassword;
		this.customerNewPassword = customerNewPassword;
	}

	public long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}

	public String getCustomerOldPassword() {
		return customerOldPassword;
	}

	public void setCustomerOldPassword(String customerOldPassword) {
		this.customerOldPassword = customerOldPassword;
	}

	public String getCustomerNewPassword() {
		return customerNewPassword;
	}

	public void setCustomerNewPassword(String customerNewPassword) {
		this.customerNewPassword = customerNewPassword;
	}
	
}
