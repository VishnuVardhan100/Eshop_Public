package com.eshop.eshopstandardgateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopstandardgateway.authentication.JwtUtil;
import com.eshop.eshopstandardgateway.model.customer.login.CustomerLoginDetails;
import com.eshop.eshopstandardgateway.model.customer.login.CustomerSignInAuthenticationRequest;
import com.eshop.eshopstandardgateway.model.customer.login.CustomerSignInAuthenticationResponse;
import com.eshop.eshopstandardgateway.service.CustomerLoginService;

import jakarta.validation.Valid;
import reactor.core.publisher.Mono;

@RestController
public class GatewayController {

	@Autowired
	private CustomerLoginService customerLoginService;
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * Sign in end point for customers
	 * @param customerSignInAuthenticationRequest object containing user name and password
	 * @return JSON Web Token if successful
	 * @throws Exception
	 */
	@PostMapping("/signin/customers")
	public Mono<ResponseEntity<?>> customerSignInAuthenticate(@RequestBody(required=true) @Valid CustomerSignInAuthenticationRequest customerSignInAuthenticationRequest)
		throws Exception {

		String jwt = null;
		CustomerLoginDetails customerLoginDetails = (CustomerLoginDetails) customerLoginService.loadUserByUsername(customerSignInAuthenticationRequest.getUsername());
		String existingPassword = customerLoginDetails.getPassword();
		
		if(passwordEncoder.matches(customerSignInAuthenticationRequest.getPassword(), existingPassword)) {
			jwt = jwtUtil.generateToken(customerLoginDetails);
			return Mono.just(ResponseEntity.ok(new CustomerSignInAuthenticationResponse(jwt)));
		}
		else {
			return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
		}
	}

}
