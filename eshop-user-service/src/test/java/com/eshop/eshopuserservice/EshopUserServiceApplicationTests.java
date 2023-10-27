package com.eshop.eshopuserservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.eshop.eshopuserservice.controller.CustomerAddressController;
import com.eshop.eshopuserservice.controller.CustomerController;
import com.eshop.eshopuserservice.exception.CustomerException;
import com.eshop.eshopuserservice.model.customer.CustomerAddressDTO;
import com.eshop.eshopuserservice.model.customer.CustomerDTO;
import com.eshop.eshopuserservice.model.customer.CustomerSignUpDTO;
import com.eshop.eshopuserservice.model.customer.CustomerSubscription;
import com.eshop.eshopuserservice.model.customer.WrapperCustomerAddress;
import com.eshop.eshopuserservices.service.helper.CustomerModelMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.ws.rs.core.Response.Status;

/**
 * Unit test class for Eshop User Service
 */

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EshopUserServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private CustomerModelMapper customerModelMapper;
	
	@MockBean
	private CustomerController customerController;

	@MockBean
	private CustomerAddressController customerAddressController;
	
	private static CustomerSignUpDTO customerSignUpDTOObj1 = null;
	private static CustomerDTO customerDTOObj1 = null;
	private static CustomerSignUpDTO customerSignUpDTOObj2 = null;
	private static CustomerDTO customerDTOObj2 = null;
	private static CustomerAddressDTO customerAddressDTOObj1 = null;
	private static CustomerAddressDTO customerAddressDTOObj2 = null;
	private static WrapperCustomerAddress wrapperCustomerAddressObj1 = null;
	private static WrapperCustomerAddress wrapperCustomerAddressObj2 = null;

	@BeforeAll
	public static void setUpRequiredBean() {
		customerSignUpDTOObj1 = new CustomerSignUpDTO();
		customerSignUpDTOObj1.setCustomerID(1l);
		customerSignUpDTOObj1.setCustomerPassword("yesH0wdy1");
		customerSignUpDTOObj1.setCustomerFirstName("User");
		customerSignUpDTOObj1.setCustomerLastName("ABC");
		customerSignUpDTOObj1.setCustomerEmail("abc@gmail.com");
		customerSignUpDTOObj1.setCustomerMobileNumber("9000080000");
		customerSignUpDTOObj1.setCustomerCreatedDate(Date.valueOf("2023-09-23"));
		customerSignUpDTOObj1.setCustomerSubscription(CustomerSubscription.NORMAL);
		customerSignUpDTOObj1.setRoles("ROLE_CUSTOMER");

		customerDTOObj1 = new CustomerDTO();
		customerDTOObj1.setCustomerID(1l);
		customerDTOObj1.setCustomerFirstName("User");
		customerDTOObj1.setCustomerLastName("ABC");
		customerDTOObj1.setCustomerEmail("abc@gmail.com");
		customerDTOObj1.setCustomerMobileNumber("9000080000");
		customerDTOObj1.setCustomerCreatedDate(Date.valueOf("2023-09-23"));
		customerDTOObj1.setCustomerSubscription(CustomerSubscription.NORMAL);
		customerDTOObj1.setRoles("ROLE_CUSTOMER");

		customerSignUpDTOObj2 = new CustomerSignUpDTO();
		customerSignUpDTOObj2.setCustomerID(2l);
		customerSignUpDTOObj2.setCustomerPassword("yesH0wdy2");
		customerSignUpDTOObj2.setCustomerFirstName("User");
		customerSignUpDTOObj2.setCustomerLastName("ABD");
		customerSignUpDTOObj2.setCustomerEmail("abd@gmail.com");
		customerSignUpDTOObj2.setCustomerMobileNumber("9000081000");
		customerSignUpDTOObj2.setCustomerCreatedDate(Date.valueOf("2023-09-23"));
		customerSignUpDTOObj2.setCustomerSubscription(CustomerSubscription.NORMAL);
		customerSignUpDTOObj2.setRoles("ROLE_CUSTOMER");

		customerDTOObj2 = new CustomerDTO();
		customerDTOObj2.setCustomerID(2l);
		customerDTOObj2.setCustomerFirstName("User");
		customerDTOObj2.setCustomerLastName("ABD");
		customerDTOObj2.setCustomerEmail("abd@gmail.com");
		customerDTOObj2.setCustomerMobileNumber("9000081000");
		customerDTOObj2.setCustomerCreatedDate(Date.valueOf("2023-09-23"));
		customerDTOObj2.setCustomerSubscription(CustomerSubscription.NORMAL);
		customerDTOObj2.setRoles("ROLE_CUSTOMER");
		
		customerAddressDTOObj1 = new CustomerAddressDTO();
		customerAddressDTOObj1.setAddressID(1l);
		customerAddressDTOObj1.setHouseNo("133-23");
		customerAddressDTOObj1.setStreet("2nd Beach Road");
		customerAddressDTOObj1.setCity("Vizag");
		customerAddressDTOObj1.setState("Andhra Pradesh");
		customerAddressDTOObj1.setPincode("500232");
		
		customerAddressDTOObj2 = new CustomerAddressDTO();
		customerAddressDTOObj2.setAddressID(2l);
		customerAddressDTOObj2.setHouseNo("78-2");
		customerAddressDTOObj2.setStreet("4th Hillside");
		customerAddressDTOObj2.setCity("Vizag");
		customerAddressDTOObj2.setState("Andhra Pradesh");
		customerAddressDTOObj2.setPincode("500234");
	}

	@Test
	@Order(1)
	public void testSignUpCustomer1() throws Exception {
		String url = "/signup/customers";

		ResponseEntity<CustomerDTO> response = new ResponseEntity<CustomerDTO>(customerDTOObj1, HttpStatus.CREATED);
		Mockito.when(customerController.createCustomer(Mockito.any(CustomerSignUpDTO.class), Mockito.any(Locale.class)))
				.thenReturn(response);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).header("accept-language", "en-us")
							.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customerSignUpDTOObj1)).accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print()).andReturn();

		MockHttpServletResponse mockResponse = result.getResponse();
		String responseAsString = mockResponse.getContentAsString();

		assertThat(responseAsString).isEqualTo(objectMapper.writeValueAsString(customerDTOObj1));
		assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(201));
	}

	@Test
	@Order(2)
	public void testSignUpCustomer2() throws Exception {
		String url = "/signup/customers";

		ResponseEntity<CustomerDTO> response = new ResponseEntity<CustomerDTO>(customerDTOObj2, HttpStatus.CREATED);
		Mockito.when(customerController.createCustomer(Mockito.any(CustomerSignUpDTO.class), Mockito.any(Locale.class)))
				.thenReturn(response);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url).header("accept-language", "en-us")
							.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(customerSignUpDTOObj2)).accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print()).andReturn();

		MockHttpServletResponse mockResponse = result.getResponse();
		String responseAsString = mockResponse.getContentAsString();

		assertThat(responseAsString).isEqualTo(objectMapper.writeValueAsString(customerDTOObj2));
		assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(201));
	}

	@Test
	@Order(3)
	public void testGetCustomerByEmail() throws Exception {
		String url = "/customers/load";

		ResponseEntity<CustomerDTO> response = new ResponseEntity<CustomerDTO>(customerDTOObj1, HttpStatus.OK);
		Mockito.when(customerController.getCustomerByEmail(Mockito.any(String.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("customerEmail", "abc@gmail.com").accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		String expectedJson = objectMapper.writeValueAsString(customerDTOObj1);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}

	@Test
	@Order(4)
	public void testGetCustomerByID() throws Exception {
		String url = "/customers/search/{customerID}";

		ResponseEntity<CustomerDTO> response = new ResponseEntity<CustomerDTO>(customerDTOObj1, HttpStatus.OK);
		Mockito.when(customerController.getCustomerByID(Mockito.any(Long.class),Mockito.any(Locale.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url,1l).header("accept-language", "en-us").accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		String expectedJson = objectMapper.writeValueAsString(customerDTOObj1);
		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}

	@Test
	@Order(5)
	public void testGetCustomersByFirstName() throws Exception {
		String url = "/admin/customers/search";

		List<CustomerDTO> responseList = Stream.of(customerDTOObj1, customerDTOObj2).collect(Collectors.toList());
		ResponseEntity<List<CustomerDTO>> response = new ResponseEntity<List<CustomerDTO>>(responseList, HttpStatus.OK);
		Mockito.when(customerController.getCustomersByFirstName(Mockito.any(String.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("firstName", "User").accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		String expectedJson = objectMapper.writeValueAsString(List.of(customerDTOObj1,customerDTOObj2));
		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}
	
	@Test
	@Order(6)
	public void testGetCustomerByLastName() throws Exception {
		String url = "/admin/customers/search";

		List<CustomerDTO> responseList = Stream.of(customerDTOObj2).collect(Collectors.toList());
		ResponseEntity<List<CustomerDTO>> response = new ResponseEntity<List<CustomerDTO>>(responseList, HttpStatus.OK);
		Mockito.when(customerController.getCustomersByLastName(Mockito.any(String.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url).param("lastName", "ABD").accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		String expectedJson = objectMapper.writeValueAsString(List.of(customerDTOObj2));
		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}
	
	@Test
	@Order(7)
	public void testGetAllCustomers() throws Exception {
		String url = "/admin/customers/search";

		List<CustomerDTO> responseList = Stream.of(customerDTOObj1, customerDTOObj2).collect(Collectors.toList());
		ResponseEntity<List<CustomerDTO>> response = new ResponseEntity<List<CustomerDTO>>(responseList, HttpStatus.OK);
		Mockito.when(customerController.getAllCustomers()).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
							.andDo(MockMvcResultHandlers.print()).andReturn();

		String expectedJson = objectMapper.writeValueAsString(List.of(customerDTOObj1,customerDTOObj2));
		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}
	
	@Test
	@Order(8)
	public void testUpdateCustomerInfo() throws Exception {
		String url = "/customers/update/info/{customerID}";
		long customerID = customerDTOObj1.getCustomerID();
		CustomerDTO customerDTOUpdateObj1 = customerDTOObj1;
		customerDTOUpdateObj1.setCustomerMobileNumber("9000080001");
		String expectedJSON = objectMapper.writeValueAsString(customerDTOUpdateObj1);
		
		ResponseEntity<CustomerDTO> response = new ResponseEntity<CustomerDTO>(customerDTOUpdateObj1, HttpStatus.OK);
		Mockito.when(customerController.updateCustomerInfo(Mockito.any(Long.class), Mockito.any(CustomerDTO.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(url, customerID).contentType(MediaType.APPLICATION_JSON)
							.content(expectedJSON).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
							.andDo(MockMvcResultHandlers.print()).andReturn();
	
		assertThat(expectedJSON).isEqualTo(result.getResponse().getContentAsString());
	}

	@Test
	@Order(9)
	public void testAddCustomerAddress1() throws Exception {
		customerAddressDTOObj1.setCustomer(customerModelMapper.mapCustomerDTOToCustomer(customerDTOObj1));
		wrapperCustomerAddressObj1 = new WrapperCustomerAddress(customerDTOObj1, customerAddressDTOObj1);

		String uri = "/customers/addresses";
		String inputJson = objectMapper.writeValueAsString(wrapperCustomerAddressObj1);
		String expectedJson = objectMapper.writeValueAsString(customerAddressDTOObj1);

		ResponseEntity<CustomerAddressDTO> response = new ResponseEntity<CustomerAddressDTO> (customerAddressDTOObj1, HttpStatus.CREATED);
		Mockito.when(customerAddressController.addCustomerAddress(Mockito.any(WrapperCustomerAddress.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
							.content(inputJson).accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print()).andReturn();

		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}

	@Test
	@Order(9)
	public void testAddCustomerAddress2() throws Exception {
		customerAddressDTOObj2.setCustomer(customerModelMapper.mapCustomerDTOToCustomer(customerDTOObj2));
		wrapperCustomerAddressObj2 = new WrapperCustomerAddress(customerDTOObj2, customerAddressDTOObj2);

		String uri = "/customers/addresses";
		String inputJson = objectMapper.writeValueAsString(wrapperCustomerAddressObj2);
		String expectedJson = objectMapper.writeValueAsString(customerAddressDTOObj2);
		
		ResponseEntity<CustomerAddressDTO> response = new ResponseEntity<CustomerAddressDTO> (customerAddressDTOObj2, HttpStatus.CREATED);
		Mockito.when(customerAddressController.addCustomerAddress(Mockito.any(WrapperCustomerAddress.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
							.content(inputJson).accept(MediaType.APPLICATION_JSON))
							.andExpect(MockMvcResultMatchers.status().isCreated()).andDo(MockMvcResultHandlers.print()).andReturn();
		
		String outputJson = result.getResponse().getContentAsString();
		assertThat(expectedJson).isEqualTo(outputJson);
	}
	
	@Test
	@Order(10)
	public void testGetAllCustomerAddressesForCustomer() throws Exception {
		String uri = "/customers/addresses/search/{customerID}";
		List<CustomerAddressDTO> responseList = Stream.of(customerAddressDTOObj1, customerAddressDTOObj2).collect(Collectors.toList());
		String expectedJson = objectMapper.writeValueAsString(responseList);
		
		ResponseEntity<List<CustomerAddressDTO>> response = new ResponseEntity<List<CustomerAddressDTO>> (responseList, HttpStatus.OK);
		Mockito.when(customerAddressController.retrieveAllCustomerAddressesByCustomerID(Mockito.any(long.class))).thenReturn(response);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri, 1l).accept(MediaType.APPLICATION_JSON))
								.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();
		
		assertThat(expectedJson).isEqualTo(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	@Order(11)
	public void testUpdateCustomerAddress() throws Exception {
		String url = "/customers/addresses/{customerID}";
		long customerID = customerDTOObj1.getCustomerID();
		CustomerAddressDTO customerAddressDTOUpdateObj1 = customerAddressDTOObj1;
		customerAddressDTOUpdateObj1.setPincode("500235");
		String expectedJSON = objectMapper.writeValueAsString(customerAddressDTOUpdateObj1);
		
		ResponseEntity<CustomerAddressDTO> response = new ResponseEntity<CustomerAddressDTO>(customerAddressDTOUpdateObj1, HttpStatus.OK);
		Mockito.when(customerAddressController.updateCustomerAddressInfo(Mockito.any(Long.class), Mockito.any(CustomerAddressDTO.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(url, customerID).contentType(MediaType.APPLICATION_JSON)
							.content(expectedJSON).accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
							.andDo(MockMvcResultHandlers.print()).andReturn();
	
		assertThat(expectedJSON).isEqualTo(result.getResponse().getContentAsString());
	}

	//@Test
	@Order(12)
	public void testDeleteCustomerAddress() throws Exception {
		
	}
	
	//@Test
	@Order(13)
	public void testDeleteCustomer() throws Exception {
		String url = "/customers/{customerID}";
		
		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.OK);
		Mockito.when(customerController.deleteCustomer(Mockito.any(long.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url, 1).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		String expectedJSON = "Customer deleted successfully";
		String outputJSON = result.getResponse().getContentAsString();
		assertEquals(expectedJSON,outputJSON);
	}

	//@Test
	@Order(14)
	public void testDeleteCustomerThrowsCustomerException() throws Exception {
		String url = "/customers/{customerID}";
		
		ResponseEntity<Object> response = new ResponseEntity<Object>(HttpStatus.OK);
		Mockito.when(customerController.deleteCustomer(Mockito.any(long.class))).thenReturn(response);
		
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(url, 1000).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

		assertThrows(CustomerException.class, () -> Mockito.when(customerController.deleteCustomer(Mockito.any(long.class))).thenReturn(response));
	}
	
	@AfterAll
	public static void cleanSetUpBeans() {
		customerSignUpDTOObj1 = null;
		customerDTOObj1 = null;
		customerSignUpDTOObj2 = null;
		customerDTOObj2 = null;
		customerAddressDTOObj1 = null;
		customerAddressDTOObj2 = null;
		wrapperCustomerAddressObj1 = null;
		wrapperCustomerAddressObj2 = null;
	}
	
}
