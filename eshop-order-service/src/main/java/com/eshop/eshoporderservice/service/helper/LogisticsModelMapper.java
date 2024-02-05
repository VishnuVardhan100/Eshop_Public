package com.eshop.eshoporderservice.service.helper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.eshoporderservice.model.logistics.Order;
import com.eshop.eshoporderservice.model.logistics.OrderDTO;
import com.eshop.eshoporderservice.model.logistics.OrderProduct;
import com.eshop.eshoporderservice.model.logistics.OrderProductDTO;

/**
 *  Custom Model Mapper class to map:
 * 	Order to OrderDTO and vice versa.
 * 	OrderProduct to OrderProductDTO and vice versa.
 */

@Service
public class LogisticsModelMapper {

	@Autowired
	private ModelMapper modelMapper;
	
	/**
	 * No arguments constructor
	 */
	public LogisticsModelMapper() {
		
	}
	
	/**Map OrderDTO To Order object
	 * @param OrderDTO object
	 * @return Order object
	 */
	public Order mapOrderDTOToOrder(OrderDTO orderDTOObject) {
		return modelMapper.map(orderDTOObject, Order.class);
	}
	
	/**
	 * Map Order To OrderDTO
	 * @param Order object
	 * @return OrderDTO object
	 */
	public OrderDTO mapOrderToOrderDTO(Order orderObject) {
		return modelMapper.map(orderObject, OrderDTO.class);
	}
	
	/**
	 * Map OrderProductDTO To OrderProduct
	 * @param OrderProductDTO object
	 * @return OrderProduct object
	 */
	public OrderProduct mapOrderProductDTOToOrderProduct(OrderProductDTO orderProductDTOObject) {
		return modelMapper.map(orderProductDTOObject, OrderProduct.class);
	}
	
	/**
	 * Map OrderProduct To OrderProductDTO
	 * @param OrderProduct Object
	 * @return OrderProductDTO object
	 */
	public OrderProductDTO mapOrderProductToOrderProductDTO(OrderProduct orderProductObject) {
		return modelMapper.map(orderProductObject, OrderProductDTO.class);
	}
	
}
