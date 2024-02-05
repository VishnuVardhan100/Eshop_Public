package com.eshop.eshoporderservice.service;

import java.util.List;

import com.eshop.eshoporderservice.exception.CustomerException;
import com.eshop.eshoporderservice.exception.InvalidInputException;
import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.exception.OrderException;
import com.eshop.eshoporderservice.model.logistics.Order;
import com.eshop.eshoporderservice.model.logistics.OrderDTO;
import com.eshop.eshoporderservice.model.logistics.OrderProductDTO;

public interface LogisticsServiceInterface {

	OrderDTO placeOrder(long customerID, OrderDTO orderDTObject, List<Long> inventoryProductIDList, List<OrderProductDTO> orderProductList) 
			throws CustomerException, InventoryProductException, OrderException, InvalidInputException;
	
	List<OrderDTO> retrieveAllOrders();

	List<OrderDTO> retrieveAllOrdersByCustomerID(long customerID) throws CustomerException;
	
	OrderDTO retrieveOrderByCustomerID(long customerID, long orderID) throws CustomerException, OrderException;

	Order retrieveOrderObjectByCustomerID(long customerID, long orderID) throws CustomerException, OrderException;
	
}
