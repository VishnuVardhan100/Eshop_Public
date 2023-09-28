package com.eshop.eshopservice.service;

import java.util.List;

import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.eshopmodel.logistics.OrderProductDTO;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;
import com.eshop.exception.UserException;

public interface LogisticsServiceInterface {

	OrderDTO placeOrder(int userID, OrderDTO orderDTObject, List<Integer> inventoryProductIDList, List<OrderProductDTO> orderProductList) 
			throws UserException, InventoryProductException, OrderException, InvalidInputException;
	
	List<OrderDTO> retrieveAllOrders();
	
	OrderDTO retrieveOrderByUserID(int userID, int orderID) throws UserException;

	List<OrderDTO> retrieveAllOrdersByUserID(int userID) throws UserException;
	
}
