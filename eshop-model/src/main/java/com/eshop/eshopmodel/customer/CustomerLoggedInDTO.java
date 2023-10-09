package com.eshop.eshopmodel.customer;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */

@Data
@Validated
@NoArgsConstructor
public class CustomerLoggedInDTO {

	private long customerID;

	@NotBlank(message="Password cannot be blank")
	private String customerPassword;

	@NotBlank(message="Email cannot be blank")
	@Email(message="Email must be valid")
	private String customerEmail;

	@NotBlank(message="Roles cannot be blank")
	private String roles;

	public long getCustomerID() {
		return customerID;
	}

	public String getCustomerPassword() {
		return customerPassword;
	}

	public String getCustomerEmail() {
		return customerEmail;
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

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
