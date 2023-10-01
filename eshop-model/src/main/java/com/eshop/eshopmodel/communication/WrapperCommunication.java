package com.eshop.eshopmodel.communication;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Wrapper class for sending communication requests
 */

@NoArgsConstructor
@AllArgsConstructor
public class WrapperCommunication {

	private long customerID;
	private long orderID;
	
	public long getCustomerID() {
		return customerID;
	}
	public void setCustomerID(long customerID) {
		this.customerID = customerID;
	}
	public long getOrderID() {
		return orderID;
	}
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}
	
}
