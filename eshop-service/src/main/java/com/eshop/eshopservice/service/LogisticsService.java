package com.eshop.eshopservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.logistics.Order;
import com.eshop.eshopmodel.logistics.OrderDTO;
import com.eshop.eshopmodel.logistics.OrderProduct;
import com.eshop.eshopmodel.logistics.OrderProductDTO;
import com.eshop.eshoprepository.CustomerRepository;
import com.eshop.eshoprepository.OrderRepository;
import com.eshop.eshopservice.manipulator.InventoryProductAccountant;
import com.eshop.eshopservice.manipulator.OrderTotalCostCalculator;
import com.eshop.eshopservice.mapper.LogisticsCustomModelMapper;
import com.eshop.exception.CustomerException;
import com.eshop.exception.InvalidInputException;
import com.eshop.exception.InventoryProductException;
import com.eshop.exception.OrderException;

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
	private CustomerRepository customerRepository;
	
	@Autowired
	private OrderRepository orderRepository;
		
	@Autowired
	private InventoryProductAccountant inventoryProductAccountant; 

	/**
	 * Place an Order
	 * @param Customer ID for whom to place order
	 * @param orderDTO object , the order object
	 * @param list of inventory product IDs
	 * @param List of products in order, must be greater than zero
	 * @return placed orderDTO object
	 */
	@Override
	public OrderDTO placeOrder(long customerID, OrderDTO orderDTOObject, List<Long> inventoryProductIDList, 
			List<OrderProductDTO> orderProductDTOList) throws CustomerException, InventoryProductException, OrderException, InvalidInputException {
		
		//first check for Customer
		Customer customerRetrieveObject = customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));
		
		//map orderDTO to order and set Customer
		Order orderObject = logisticsCustomModelMapper.mapOrderDTOToOrder(orderDTOObject);
		orderObject.setCustomer(customerRetrieveObject);
		
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
		inventoryProductAccountant.performInventoryQuantityCheckAndAdjust(inventoryProductIDList, orderProductList);
				
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
		customerRetrieveObject = null;
		
		return logisticsCustomModelMapper.mapOrderToOrderDTO(orderSecondReturnObject);
	}

	/**
	 * ADMIN PRIVILEDGE : Retrieve all orders from all customers
	 * @return list of all OrderDTOs
	 */
	@Override
	public List<OrderDTO> retrieveAllOrders() {
		return orderRepository.findAll().stream().
				map(order -> logisticsCustomModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());
	}

	/**
	 * Retrieve specific Order of particular Customer
	 * @param Customer ID
	 * @param order ID
	 * @return OrderDTO object if exist
	 */
	@Override
	public OrderDTO retrieveOrderByCustomerID(long customerID, long orderID) throws CustomerException {
		
		//first check for Customer
		if(!customerRepository.existsById(customerID)) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}

		//get the order
		Order orderObject = orderRepository.findOrderByCustomerID(customerID, orderID);
		
		return logisticsCustomModelMapper.mapOrderToOrderDTO(orderObject);		
	}

	/**
	 * CAUTION : INTERNAL USE ONLY
	 * Retrieve specific Order of particular Customer
	 * @param Customer ID
	 * @param order ID
	 * @return Order object if exist
	 */
	@Override
	public Order retrieveOrderObjectByCustomerID(long customerID, long orderID) throws CustomerException {
		
		//first check for customer
		if(!customerRepository.existsById(customerID)) {
			throw new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale()));
		}

		//get the order
		Order orderObject = orderRepository.findOrderByCustomerID(customerID, orderID);
		
		return orderObject;
	}
	
	/**
	 * Retrieve all orders of particular Customer
	 * @param Customer ID
	 * @return List of OrderDTO if they exist
	 */
	@Override
	public List<OrderDTO> retrieveAllOrdersByCustomerID(long customerID) throws CustomerException {
		
		//first check for Customer
		Customer customerObject = customerRepository.findById(customerID).
				orElseThrow(() -> new CustomerException(messageSource.getMessage("CustomerNotFound", null, LocaleContextHolder.getLocale())));

		//get List of orders
		//List<Order> orderList = orderRepository.findAllOrdersByCustomerID(customerID);
		
		//return orderList.stream().map(order -> logisticsCustomModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());
		return customerObject.getOrdersList().stream().
				map(order -> logisticsCustomModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());		
	}
	
}
