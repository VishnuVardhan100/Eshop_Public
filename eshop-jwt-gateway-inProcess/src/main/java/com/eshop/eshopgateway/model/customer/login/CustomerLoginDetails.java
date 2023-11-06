package com.eshop.eshopgateway.model.customer.login;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eshop.eshopgateway.model.customer.Customer;

/**
 * This is the class sen for user authentication during login.
 * It takes details from a Customer raw class.
 */

@SuppressWarnings("serial")
public class CustomerLoginDetails implements UserDetails {

	private long customerID;
	private String customerEmail;
	private String customerPassword;
	private String roles;
	
	public CustomerLoginDetails(Customer customerObject) {
		this.customerID = customerObject.getCustomerID();
		this.customerEmail = customerObject.getCustomerEmail();
		this.customerPassword = customerObject.getCustomerPassword();
		this.roles = customerObject.getRoles();
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Stream.of(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return customerPassword;
	}

	@Override
	public String getUsername() {
		return customerEmail;
	}
	
	public long getId() {
		return customerID;
	}

	public String getRoles() {
		return roles;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
