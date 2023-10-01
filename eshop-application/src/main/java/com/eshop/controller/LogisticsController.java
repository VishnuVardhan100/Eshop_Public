package com.eshop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.eshopmodel.logistics.OrderProductDTO;
import com.eshop.eshopmodel.logistics.WrapperOrderProduct;
import com.eshop.eshopservice.service.LogisticsService;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;

import jakarta.validation.Valid;

/**
 * Controller for handling web requests regarding order and order products
 */

@RestController
public class LogisticsController {

	@Autowired
	private LogisticsService logisticsService;
	
	/**
	 * Place an order from customer
	 * @param customerID
	 * @param wrapperOrderProduct having OrderDTO object, list of Inventory Product IDs, list of concurrent Order Products
	 * @return OrderDTO object if create successful
	 * @throws CustomerException
	 * @throws OrderException
	 * @throws InvalidInputException
	 */
	@PostMapping("/customers/orders/create/{customerID}")
	public ResponseEntity<OrderDTO> placeOrder(@PathVariable(name="customerID", required=true) long customerID, 
			@RequestBody(required = true) @Valid WrapperOrderProduct wrapperOrderProduct)
		throws CustomerException, InventoryProductException, OrderException, InvalidInputException {
		OrderDTO orderDTOObject = wrapperOrderProduct.getOrderDTOObject();
		List<Long> inventoryProductIDList = wrapperOrderProduct.getListOfInventoryProductIDs();
		List<OrderProductDTO> orderProductDTOList = wrapperOrderProduct.getListOfOrderProductDTOs();
		return new ResponseEntity<OrderDTO> (logisticsService.placeOrder(customerID, orderDTOObject, inventoryProductIDList, orderProductDTOList), HttpStatus.CREATED);
	}

	
	/**
	 * ADMIN PRIVILEDGE : Get all orders
	 * @return all orders
	 */
	@GetMapping(path = "/customers/orders/search")
	public ResponseEntity<List<OrderDTO>> retrieveAllOrders() {
		return new ResponseEntity<List<OrderDTO>> (logisticsService.retrieveAllOrders(), HttpStatus.OK);
	}
	
	/**
	 * Get order based on customer ID and order ID
	 * @param customerID
	 * @param orderID
	 * @return OrderDTO object if get successful
	 * @throws CustomerException
	 */
	/*@GetMapping(path = "/customers/orders/search/{customerID}" , params = "orderID")
	public ResponseEntity<OrderDTO> getOrderByCustomerID(@PathVariable(name="customerID", required=true) long customerID, 
			@RequestParam(name="orderID", required=true) long orderID) throws CustomerException {
		return new ResponseEntity<OrderDTO> (logisticsService.retrieveOrderByCustomerID(customerID, orderID), HttpStatus.OK);
	}*/
	
	/**
	 * Get all orders of particular customer
	 * @param customerID
	 * @return list of all orderDTOs for particular customer
	 * @throws CustomerException
	 */
	@GetMapping("/customers/orders/search/{customerID}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersByCustomerID(@PathVariable(name="customerID", required=true) long customerID) throws CustomerException {
		return new ResponseEntity<List<OrderDTO>> (logisticsService.retrieveAllOrdersByCustomerID(customerID),HttpStatus.OK);
	}
}
