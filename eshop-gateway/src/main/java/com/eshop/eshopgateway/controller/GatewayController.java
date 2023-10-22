package com.eshop.eshopgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eshop.eshopgateway.authentication.JwtUtilOld;
import com.eshop.eshopgateway.model.customer.login.CustomerSignInAuthenticationRequest;
import com.eshop.eshopgateway.model.customer.login.CustomerSignInAuthenticationResponse;
import com.eshop.eshopgateway.service.CustomerLoginService;

import jakarta.validation.Valid;

public class GatewayController {

	@Autowired
	private AuthenticationManager authenticationManager; 
		
	@Autowired
	private CustomerLoginService customerLoginService;
	
	@Autowired
	private JwtUtilOld jwtUtil;

	/**
	 * Sign in end point for customers
	 * @param customerSignInAuthenticationRequest object containing user name and password
	 * @return JSON Web Token if successful
	 * @throws Exception
	 */
	@PostMapping("/signin/customers")
	public ResponseEntity<?> customerSignInAuthenticate(@RequestBody(required=true) @Valid CustomerSignInAuthenticationRequest customerSignInAuthenticationRequest)
		throws Exception {
		try	{
			Authentication authentication = authenticationManager
			        .authenticate(new UsernamePasswordAuthenticationToken(customerSignInAuthenticationRequest.getUsername(), customerSignInAuthenticationRequest.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(BadCredentialsException bce) {
			throw new BadCredentialsException("Incorrect Email/Username or Password",bce);
		}

		final UserDetails userDetails = customerLoginService.loadUserByUsername(customerSignInAuthenticationRequest.getUsername());		
		final String jwt = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new CustomerSignInAuthenticationResponse(jwt));

		//final String jwt = jwtUtil.generateTokenFromUsername(customerSignInAuthenticationRequest.getUsername());
		//ResponseCookie jwtCookie = jwtUtil.generateJwtCookie(userDetails);
		//return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString());

	}

}
