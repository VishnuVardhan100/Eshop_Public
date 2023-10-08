package com.eshop.eshopmodel.customer;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class CustomerLoginDetails implements UserDetails {

	private long customerID;
	private String customerEmail;
	private String customerPassword;
	private String firstName;
	private String roles;
	
	public CustomerLoginDetails(CustomerDTO customerDTOObject) {
		this.customerID = customerDTOObject.getCustomerID();
		this.customerEmail = customerDTOObject.getCustomerEmail();
		this.customerPassword = customerDTOObject.getCustomerPassword();
		this.firstName = customerDTOObject.getCustomerFirstName();
		this.roles = customerDTOObject.getRoles();
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
