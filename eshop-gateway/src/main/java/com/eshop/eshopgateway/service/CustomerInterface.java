package com.eshop.eshopgateway.service;

import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;

import com.eshop.eshopgateway.model.customer.Customer;

@FeignClient("ESHOP-USER-SERVICE")
public interface CustomerInterface {

	Optional<Customer> loadCustomerByEmail(String email);
}
