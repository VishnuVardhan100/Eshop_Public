package com.eshop.eshopmodel.customer.login;

/**
 * Class for when Customer signs/logs  in, providing Authentication Response
 */

public class CustomerSignInAuthenticationResponse {

	private String jwt;
	
	public CustomerSignInAuthenticationResponse(String jwt) {
		this.jwt = jwt;
	}
	
	public String getJwt() {
		return jwt;
	}
	
}
