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
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;
import com.eshop.exception.UserException;

/**
 * Controller for handling web requests regarding order and order products
 */

@RestController
public class LogisticsController {

	@Autowired
	private LogisticsService logisticsService;
	
	/**
	 * Place an order form user
	 * @param userID
	 * @param wrapperOrderProduct
	 * @return OrderDTO object if create successful
	 * @throws UserException
	 * @throws OrderException
	 * @throws InvalidInputException
	 */
	@PostMapping("/users/orders/create/{userID}")
	public ResponseEntity<OrderDTO> placeOrder(@PathVariable(name="userID", required=true) int userID, 
			@RequestBody(required = true) WrapperOrderProduct wrapperOrderProduct)
		throws UserException, InventoryProductException, OrderException, InvalidInputException {
		OrderDTO orderDTOObject = wrapperOrderProduct.getOrderDTOObject();
		List<Integer> inventoryProductIDList = wrapperOrderProduct.getListOfInventoryProductIDs();
		List<OrderProductDTO> orderProductDTOList = wrapperOrderProduct.getListOfOrderProductDTOs();
		return new ResponseEntity<OrderDTO> (logisticsService.placeOrder(userID, orderDTOObject, inventoryProductIDList, orderProductDTOList), HttpStatus.CREATED);
	}

	
	/**
	 * ADMIN PRIVILEDGE : Get all orders
	 * @return all orders
	 */
	@GetMapping(path = "/users/orders/search")
	public ResponseEntity<List<OrderDTO>> retrieveAllOrders() {
		return new ResponseEntity<List<OrderDTO>> (logisticsService.retrieveAllOrders(), HttpStatus.OK);
	}
	
	/**
	 * Get order based on user ID and order ID
	 * @param userID
	 * @param orderID
	 * @return OrderDTO object if get successful
	 * @throws UserException
	 */
	/*@GetMapping(path = "/users/orders/search/{userID}" , params = "orderID")
	public ResponseEntity<OrderDTO> getOrderByUserID(@PathVariable(name="userID", required=true) int userID, 
			@RequestParam(name="orderID", required=true) int orderID) throws UserException {
		return new ResponseEntity<OrderDTO> (logisticsService.retrieveOrderByUserID(userID, orderID), HttpStatus.OK);
	}*/
	
	/**
	 * Get all orders of particular user
	 * @param userID
	 * @return list of all orderDTOs for particular user
	 * @throws UserException
	 */
	@GetMapping("/users/orders/search/{userID}")
	public ResponseEntity<List<OrderDTO>> getAllOrdersByUserID(@PathVariable(name="userID", required=true) int userID) throws UserException {
		return new ResponseEntity<List<OrderDTO>> (logisticsService.retrieveAllOrdersByUserID(userID),HttpStatus.OK);
	}
}
