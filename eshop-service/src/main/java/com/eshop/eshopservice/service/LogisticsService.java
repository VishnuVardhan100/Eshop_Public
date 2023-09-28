package com.eshop.eshopservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.consumer.User;
import com.eshop.eshopmodel.logistics.Order;
import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.eshopmodel.logistics.OrderProduct;
import com.eshop.eshopmodel.logistics.OrderProductDTO;
import com.eshop.eshoprepository.OrderRepository;
import com.eshop.eshoprepository.UserRepository;
import com.eshop.eshopservice.manipulator.InventoryProductQuantityAccountant;
import com.eshop.eshopservice.manipulator.OrderTotalCostCalculator;
import com.eshop.eshopservice.mapper.LogisticsCustomModelMapper;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
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
	private OrderTotalCostCalculator orderTotalCostCalculator;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
		
	@Autowired
	private InventoryProductQuantityAccountant inventoryProductQuantityAccountant; 

	/**
	 * Place an Order
	 * @param user ID for whom to place order
	 * @param orderDTO object , the order object
	 * @param list of inventory product IDs
	 * @param List of products in order, must be greater than zero
	 * @return placed orderDTO object
	 */
	@Override
	public OrderDTO placeOrder(int userID, OrderDTO orderDTOObject, List<Integer> inventoryProductIDList, 
			List<OrderProductDTO> orderProductDTOList) throws UserException, InventoryProductException, OrderException, InvalidInputException {
		
		//first check for user
		User userRetrieveObject = userRepository.findById(userID).
				orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));
		
		//map orderDTO to order and set user
		Order orderObject = logisticsCustomModelMapper.mapOrderDTOToOrder(orderDTOObject);
		orderObject.setUser(userRetrieveObject);
		
		//check number of products in order product list
		if(orderProductDTOList.size()<1) {
			throw new OrderException(messageSource.getMessage("OrderProductListSizeLessThanZero", null, LocaleContextHolder.getLocale()));
		}
		
		//perform map orderProductDTO to orderProduct
		List<OrderProduct> orderProductList= orderProductDTOList.
		stream().
		map(orderProductDTO -> logisticsCustomModelMapper.mapOrderProductDTOToOrderProduct(orderProductDTO)).
		collect(Collectors.toList());

		//check if each of the products' quantity ordered are available against same product's quantity in inventory 
		inventoryProductQuantityAccountant.performInventoryQuantityCheckAndAdjust(inventoryProductIDList, orderProductList);
				
		//perform calculation for orderProduct to set total cost for each product in list
		//Then add all total costs to set total amount for order
		orderProductList.forEach(orderProduct -> 
		orderProduct.setOrderProductTotalCost(orderTotalCostCalculator.getOrderProductTotalCost(orderProduct.getOrderProductQuantity(), 
				orderProduct.getOrderProductUnitCost())));
		orderObject.setOrderTotalAmount(orderTotalCostCalculator.getOrderTotalAmount(orderProductList));
		
		//create the order object
		Order orderReturnObject = orderRepository.save(orderObject);
		
		//With the persisted orderID , set it to all products in list
		//And conversely , set list to order
		orderProductList.forEach(orderProduct -> orderProduct.setOrder(orderReturnObject));
		orderReturnObject.setProductsList(orderProductList);
		
		//Merge or Update the order object
		Order orderSecondReturnObject = orderRepository.save(orderReturnObject);
		orderProductList = null;
		orderObject = null;
		userRetrieveObject = null;
		
		return logisticsCustomModelMapper.mapOrderToOrderDTO(orderSecondReturnObject);
	}

	/**
	 * ADMIN PRIVILEDGE : Retrieve all orders from all users
	 * @return list of all OrderDTOs
	 */
	@Override
	public List<OrderDTO> retrieveAllOrders() {
		return orderRepository.findAll().stream().
				map(order -> logisticsCustomModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());
	}

	/**
	 * Retrieve specific Order of particular user
	 * @param user ID
	 * @param order ID
	 * @return OrderDTO object if exist
	 */
	@Override
	public OrderDTO retrieveOrderByUserID(int userID, int orderID) throws UserException {
		
		//first check for user
		if(!userRepository.existsById(userID)) {
			throw new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale()));
		}

		//get the order
		Order orderObject = orderRepository.findOrderByUserID(userID, orderID);
		
		return logisticsCustomModelMapper.mapOrderToOrderDTO(orderObject);		
	}
	
	/**
	 * Retrieve all orders of particular user
	 * @param user ID
	 * @return List of OrderDTO if they exist
	 */
	@Override
	public List<OrderDTO> retrieveAllOrdersByUserID(int userID) throws UserException {
		
		//first check for user
		User userObject = userRepository.findById(userID).
				orElseThrow(() -> new UserException(messageSource.getMessage("UserNotFound", null, LocaleContextHolder.getLocale())));

		//get List of orders
		//List<Order> orderList = orderRepository.findAllOrdersByUserID(userID);
		
		//return orderList.stream().map(order -> logisticsCustomModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());
		return userObject.getOrdersList().stream().
				map(order -> logisticsCustomModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());		
	}
	
}
