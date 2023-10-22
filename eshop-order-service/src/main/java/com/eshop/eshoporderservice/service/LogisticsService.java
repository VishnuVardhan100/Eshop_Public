package com.eshop.eshoporderservice.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.eshop.eshoporderservice.exception.CustomerException;
import com.eshop.eshoporderservice.exception.InvalidInputException;
import com.eshop.eshoporderservice.exception.InventoryProductException;
import com.eshop.eshoporderservice.exception.OrderException;
import com.eshop.eshoporderservice.model.customer.Customer;
import com.eshop.eshoporderservice.model.inventory.WrapperPerformCheckAndAdjust;
import com.eshop.eshoporderservice.model.logistics.Order;
import com.eshop.eshoporderservice.model.logistics.OrderDTO;
import com.eshop.eshoporderservice.model.logistics.OrderProduct;
import com.eshop.eshoporderservice.model.logistics.OrderProductDTO;
import com.eshop.eshoporderservice.repository.OrderRepository;
import com.eshop.eshoporderservice.service.helper.LogisticsModelMapper;
import com.eshop.eshoporderservice.service.helper.OrderTotalCostCalculator;

/**
 * Service for Orders and Order Products
 */

@Service
public class LogisticsService implements LogisticsServiceInterface {

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private LogisticsModelMapper logisticsModelMapper;
	
	@Autowired
	private OrderTotalCostCalculator orderTotalCostCalculator;
	
	@Autowired
	private CustomerInterface customerInterface;
	
	@Autowired
	private InventoryInterface inventoryInterface; 

	@Autowired
	private OrderRepository orderRepository;

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
		Customer customerRetrieveObject = customerInterface.getCustomerByID(customerID);
		
		//map orderDTO to order and set Customer
		Order orderObject = logisticsModelMapper.mapOrderDTOToOrder(orderDTOObject);
		orderObject.setCustomer(customerRetrieveObject);
		
		//check number of products in order product list
		if(orderProductDTOList.size()<1) {
			throw new OrderException(messageSource.getMessage("OrderProductListSizeLessThanZero", null, LocaleContextHolder.getLocale()));
		}
		
		//perform map orderProductDTO to orderProduct
		List<OrderProduct> orderProductList= orderProductDTOList.
		stream().
		map(orderProductDTO -> logisticsModelMapper.mapOrderProductDTOToOrderProduct(orderProductDTO)).
		collect(Collectors.toList());

		//check if each of the products' quantity ordered are available against same product's quantity in inventory
		WrapperPerformCheckAndAdjust wrapperPerformCheckAndAdjustObject = new WrapperPerformCheckAndAdjust(inventoryProductIDList, orderProductList); 
		inventoryInterface.performInventoryQuantityCheckAndAdjust(wrapperPerformCheckAndAdjustObject);
				
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
		
		return logisticsModelMapper.mapOrderToOrderDTO(orderSecondReturnObject);
	}

	/**
	 * ADMIN PRIVILEDGE : Retrieve all orders from all customers
	 * @return list of all OrderDTOs
	 */
	@Override
	public List<OrderDTO> retrieveAllOrders() {
		return orderRepository.findAll().stream().
				map(order -> logisticsModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());
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
		Customer customerObject = customerInterface.getCustomerByID(customerID);
		
		//get the order
		Order orderObject = orderRepository.findOrderByCustomerID(customerID, orderID);
		
		return logisticsModelMapper.mapOrderToOrderDTO(orderObject);		
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
		Customer customerObject = customerInterface.getCustomerByID(customerID);
		
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
		Customer customerObject = customerInterface.getCustomerByID(customerID);

		//get List of orders
		//List<Order> orderList = orderRepository.findAllOrdersByCustomerID(customerID);
		
		//return orderList.stream().map(order -> logisticsModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());
		return customerObject.getOrdersList().stream().
				map(order -> logisticsModelMapper.mapOrderToOrderDTO(order)).collect(Collectors.toList());		
	}
	
}
