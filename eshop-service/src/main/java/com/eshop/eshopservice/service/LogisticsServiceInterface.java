package com.eshop.eshopservice.service;

import java.util.List;

import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.OrderException;
import com.eshop.exception.UserException;

public interface LogisticsServiceInterface {

	OrderDTO placeOrder(int userID, OrderDTO orderDTObject) throws UserException, OrderException, InvalidInputException;
	
	List<OrderDTO> retrieveAllOrders() throws UserException;

	List<OrderDTO> retrieveAllOrdersByUserID(int userID) throws UserException;
	
}
