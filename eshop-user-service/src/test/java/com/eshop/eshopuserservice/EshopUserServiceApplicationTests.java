package com.eshop.eshopuserservice;

import java.sql.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eshop.eshopuserservice.controller.CustomerController;
import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.model.customer.CustomerSignUpDTO;
import com.eshop.eshopuserservice.model.customer.CustomerSubscription;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Unit test class for Eshop User Service
 */

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = true)
@WebMvcTest(controllers = CustomerController.class)
class EshopUserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private static CustomerSignUpDTO customerSignUpDTOObj1 = null;
	private static CustomerSignUpDTO customerSignUpDTOObj2 = null;
	
	@BeforeAll
	public static void setUpRequiredBean() {		
		customerSignUpDTOObj1 = new CustomerSignUpDTO();
		customerSignUpDTOObj1.setCustomerPassword("yesH0wdy1");
		customerSignUpDTOObj1.setCustomerFirstName("User");
		customerSignUpDTOObj1.setCustomerLastName("ABC");
		customerSignUpDTOObj1.setCustomerEmail("abc@gmail.com");
		customerSignUpDTOObj1.setCustomerMobileNumber("9000080000");
		customerSignUpDTOObj1.setCustomerCreatedDate(new Date(2023, 9, 23));
		customerSignUpDTOObj1.setCustomerSubscription(CustomerSubscription.NORMAL);
		customerSignUpDTOObj1.setRoles("ROLE_CUSTOMER");

		customerSignUpDTOObj2 = new CustomerSignUpDTO();
		customerSignUpDTOObj2.setCustomerPassword("yesH0wdy2");
		customerSignUpDTOObj2.setCustomerFirstName("User");
		customerSignUpDTOObj2.setCustomerLastName("ABD");
		customerSignUpDTOObj2.setCustomerEmail("abd@gmail.com");
		customerSignUpDTOObj2.setCustomerMobileNumber("9000081000");
		customerSignUpDTOObj2.setCustomerCreatedDate(new Date(2023, 9, 23));
		customerSignUpDTOObj2.setCustomerSubscription(CustomerSubscription.NORMAL);
		customerSignUpDTOObj2.setRoles("ROLE_CUSTOMER");
	}

	@Test
	public void testSignUpCustomer() {
		String url = "/signup/customers";
		
		try {
			ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post(url)
					.header("accept-language", "en-us")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(customerSignUpDTOObj1))
					.accept(MediaType.APPLICATION_JSON));
			response.andExpect(MockMvcResultMatchers.status().isCreated());
		}
		catch(CustomerException ce) {
			System.out.println(ce.getMessage());
		}
		catch(Exception e) {
			System.out.println(e.getMessage());			
		}
	}
	
	
	
}
