package com.eshop.eshopservice.manipulator;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.logistics.OrderProduct;

/**
 * Class to calculate cost operations for an order.
 */

@Service
public class OrderTotalCostCalculator {
	
	/**
	 * Calculate total cost of a product in order by quantity and base per unit cost
	 * @param order product quantity
	 * @param order product unit cost
	 * @return total cost of an order item in order
	 */
	public long getOrderProductTotalCost(long orderProductQuantity, long orderProductUnitCost) {
		return orderProductQuantity * orderProductUnitCost;
	}
	
	/**
	 * Calculate the total amount for the order
	 * based on total cost of each product in the list of order products
	 * @param list Of OrderProducts
	 * @return total amount for the order
	 */
	public long getOrderTotalAmount(List<OrderProduct> listOfOrderProducts) {
		long totalOrderAmount = 0;
		
		for(OrderProduct orderProductObject : listOfOrderProducts ) {
			totalOrderAmount += orderProductObject.getOrderProductTotalCost();
		}
		return totalOrderAmount;
	}
}
