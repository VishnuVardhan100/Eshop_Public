package com.eshop.eshoporderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.eshoporderservice.model.logistics.Order;
import com.eshop.eshoporderservice.model.logistics.OrderDTO;
import com.eshop.eshoporderservice.model.logistics.OrderProductDTO;
import com.eshop.eshoporderservice.model.logistics.WrapperOrderProduct;
import com.eshop.eshoporderservice.service.LogisticsService;
import com.eshop.eshoporderservice.exception.CustomerException;
import com.eshop.eshoporderservice.exception.InvalidInputException;
import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.exception.OrderException;

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
	 * @param CustomerID customerID
	 * @param wrapperOrderProduct having OrderDTO object, list of Inventory Product IDs, list of concurrent Order Products
	 * @return OrderDTO object if create successful
	 * @throws CustomerException customerException
	 * @throws OrderException orderException
	 * @throws InvalidInputException invalidInputException
	 */
	@PostMapping("/orders/{customerID}")
	public ResponseEntity<OrderDTO> placeOrder(@PathVariable(name="customerID", required=true) long customerID, 
			@RequestBody(required = true) @Valid WrapperOrderProduct wrapperOrderProduct)
		throws CustomerException, InventoryProductException, OrderException, InvalidInputException {
		OrderDTO orderDTOObject = wrapperOrderProduct.getOrderDTOObject();
		List<Long> inventoryProductIDList = wrapperOrderProduct.getListOfInventoryProductIDs();
		List<OrderProductDTO> orderProductDTOList = wrapperOrderProduct.getListOfOrderProductDTOs();
		return new ResponseEntity<> (logisticsService.placeOrder(customerID, orderDTOObject, inventoryProductIDList, orderProductDTOList), HttpStatus.CREATED);
	}

	
	/**
	 * ADMIN PRIVILEGE : Get all orders
	 * @return all orders
	 */
	@GetMapping(path = "/admin/orders/search")
	public ResponseEntity<List<OrderDTO>> retrieveAllOrders() {
		return new ResponseEntity<> (logisticsService.retrieveAllOrders(), HttpStatus.OK);
	}
	
	/**
	 * Get order based on customer ID and order ID
	 * @param CustomerID customerID
	 * @param OrderID orderID
	 * @return OrderDTO object if get successful
	 * @throws CustomerException customerException
	 */
	@GetMapping(path = "/orders/search/" , params = {"customerID","orderID"})
	public ResponseEntity<OrderDTO> getOrderByCustomerID(@RequestParam(name="customerID", required=true) long customerID, 
			@RequestParam(name="orderID", required=true) long orderID) throws CustomerException {
		return new ResponseEntity<> (logisticsService.retrieveOrderByCustomerID(customerID, orderID), HttpStatus.OK);
	}

	/**
	 * CAUTION : INTERNAL USE ONLY
	 * Get the raw order object based on customerID and orderID
	 * @param CustomerID customerID
	 * @param OrderID orderID
	 * @return Raw order object
	 * @throws CustomerException customerException
	 */
	@GetMapping(value = "/orders/search/ordersobject", params= {"customerID", "orderID"})
	public ResponseEntity<Order> getOrderObjectByCustomerID(@RequestParam(name="customerID", required=true) long customerID,
			@RequestParam(name="orderID", required=true) long orderID) throws CustomerException {
		return new ResponseEntity<> (logisticsService.retrieveOrderObjectByCustomerID(customerID, orderID), HttpStatus.OK);
	}
	
	/**
	 * Get all orders of particular customer
	 * @param CustomerID customerID
	 * @return list of all orderDTOs for particular customer
	 * @throws CustomerException customerException
	 */
	@GetMapping("/orders/search/{customerID}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersByCustomerID(@PathVariable(name="customerID", required=true) long customerID) throws CustomerException {
		return new ResponseEntity<> (logisticsService.retrieveAllOrdersByCustomerID(customerID),HttpStatus.OK);
	}
}
