package com.eshop.eshopuserservice.model.logistics;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO class for Order class 
 */

@Data
@Validated
public class OrderDTO {

	private long orderID;

	@NotNull(message="Order Placed Date is mandatory")
	private Date orderPlacedDate;

	@Min(value=1, message="Order total amount cannot be less than one")
	private long orderTotalAmount;

	public OrderDTO() {
		super();
	}

	public OrderDTO(long orderID, @NotNull(message = "Order Placed Date is mandatory") Date orderPlacedDate,
			@Min(value = 1, message = "Order total amount cannot be less than one") long orderTotalAmount) {
		super();
		this.orderID = orderID;
		this.orderPlacedDate = orderPlacedDate;
		this.orderTotalAmount = orderTotalAmount;
	}

	public long getOrderID() {
		return orderID;
	}

	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	public Date getOrderPlacedDate() {
		return orderPlacedDate;
	}

	public void setOrderPlacedDate(Date orderPlacedDate) {
		this.orderPlacedDate = orderPlacedDate;
	}

	public long getOrderTotalAmount() {
		return orderTotalAmount;
	}

	public void setOrderTotalAmount(long orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	@Override
	public String toString() {
		return "OrderDTO [orderID=" + orderID + ", orderPlacedDate=" + orderPlacedDate + ", orderTotalAmount=" + orderTotalAmount
				+ "]";
	}
	
}
