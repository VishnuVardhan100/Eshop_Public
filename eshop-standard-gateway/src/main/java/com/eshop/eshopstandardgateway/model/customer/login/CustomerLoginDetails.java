package com.eshop.eshopstandardgateway.model.customer.login;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.eshop.eshopstandardgateway.model.customer.Customer;

/**
 * This is the class sent from client-side upon user-authentication login.
 * It takes details from a Customer raw class
 */

@SuppressWarnings("serial")
public class CustomerLoginDetails implements UserDetails {

	private long customerID;
	private String customerEmail;
	private String customerPassword;
	private List<String> roles;
	
	/**
	 * Parameterized constructor
	 * @param customerObject
	 */
	public CustomerLoginDetails(Customer customerObject) {
		this.customerID = customerObject.getCustomerID();
		this.customerEmail = customerObject.getCustomerEmail();
		this.customerPassword = customerObject.getCustomerPassword();
		this.roles = Stream.of(customerObject.getRoles().split(",")).collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
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

	public List<String> getRoles() {
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
