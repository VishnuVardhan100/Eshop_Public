package com.eshop.eshopcommunicationsservice.model.logistics.event;

import lombok.Data;

/**
 * 
 */

@Data
public class OrderPlacedEvent {
	
	private long customerID;
	private long orderID;

	public OrderPlacedEvent() {
		
	}

	public OrderPlacedEvent(long customerID, long orderID) {
		this.customerID = customerID;
		this.orderID = orderID;
	}

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
