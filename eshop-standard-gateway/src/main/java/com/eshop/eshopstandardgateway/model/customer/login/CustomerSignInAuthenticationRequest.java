package com.eshop.eshopstandardgateway.model.customer.login;

/**
 * Customer details for SignIn authentication
 */

public class CustomerSignInAuthenticationRequest {

	private String username;
	private String password;

	/**
	 * No Arguments Constructor
	 */
	public CustomerSignInAuthenticationRequest() {
		
	}
	
	/**
	 * Parameterized Constructor
	 * @param username
	 * @param password
	 */
	public CustomerSignInAuthenticationRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
