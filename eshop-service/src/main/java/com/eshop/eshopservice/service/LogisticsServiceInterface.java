package com.eshop.eshopservice.service;

import java.util.List;

import com.eshop.eshopmodel.logistics.Order;
import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.eshopmodel.logistics.OrderProductDTO;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;

public interface LogisticsServiceInterface {

	OrderDTO placeOrder(long customerID, OrderDTO orderDTObject, List<Long> inventoryProductIDList, List<OrderProductDTO> orderProductList) 
			throws CustomerException, InventoryProductException, OrderException, InvalidInputException;
	
	List<OrderDTO> retrieveAllOrders();

	List<OrderDTO> retrieveAllOrdersByCustomerID(long customerID) throws CustomerException;
	
	OrderDTO retrieveOrderByCustomerID(long customerID, long orderID) throws CustomerException, OrderException;

	Order retrieveOrderObjectByCustomerID(long customerID, long orderID) throws CustomerException, OrderException;
	
}
