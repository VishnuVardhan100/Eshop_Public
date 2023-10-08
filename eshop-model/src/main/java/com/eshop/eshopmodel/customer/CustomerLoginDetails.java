package com.eshop.eshopmodel.customer;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
public class CustomerLoginDetails implements UserDetails {

	CustomerDTO customerDTOObject;
	
	public CustomerLoginDetails(CustomerDTO customerDTOObject) {
		this.customerDTOObject = customerDTOObject;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String roles = customerDTOObject.getRoles();
		return Stream.of(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		return customerDTOObject.getCustomerPassword();
	}

	@Override
	public String getUsername() {
		return customerDTOObject.getCustomerEmail();
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
