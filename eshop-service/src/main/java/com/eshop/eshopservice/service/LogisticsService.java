package com.eshop.eshopservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.eshoprepository.OrderProductRepository;
import com.eshop.eshoprepository.OrderRepository;
import com.eshop.eshoprepository.UserRepository;
import com.eshop.eshopservice.mapper.LogisticsCustomModelMapper;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.OrderException;
import com.eshop.exception.UserException;

/**
 * Service for Orders and Order Products
 */

@Service
public class LogisticsService implements LogisticsServiceInterface {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LogisticsCustomModelMapper logisticsCustomModelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderProductRepository orderProductRepository;

	@Override
	public OrderDTO placeOrder(int userID, OrderDTO orderDTObject)
			throws UserException, OrderException, InvalidInputException {
		
		return null;
	}

	@Override
	public List<OrderDTO> retrieveAllOrders() throws UserException {
		
		return null;
	}

	@Override
	public List<OrderDTO> retrieveAllOrdersByUserID(int userID) throws UserException {
		
		return null;
	}
	
}
