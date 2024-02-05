package com.eshop.eshopstandardgateway.model.logistics;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO class for Order Product
 */

@Data
@Validated
public class OrderProductDTO {

	private long orderProductID;

	@NotBlank(message="Order Product Name cannot be empty or blank")
	private String orderProductName;	
	
	@Min(value=1, message="Order Product Quantity cannot be less than one")
	private long orderProductQuantity;

	@Min(value=1, message="Order Product unit cost cannot be less than one")
	private long orderProductUnitCost;

	public OrderProductDTO() {
		super();
	}

	public OrderProductDTO(long orderProductID,
			@NotBlank(message = "Order Product Name cannot be empty or blank") String orderProductName,
			@Min(value = 1, message = "Order Product Quantity cannot be less than one") long orderProductQuantity,
			@Min(value = 1, message = "Order Product unit cost cannot be less than one") long orderProductUnitCost) {
		super();
		this.orderProductID = orderProductID;
		this.orderProductName = orderProductName;
		this.orderProductQuantity = orderProductQuantity;
		this.orderProductUnitCost = orderProductUnitCost;
	}

	public long getOrderProductID() {
		return orderProductID;
	}

	public void setOrderProductID(long orderProductID) {
		this.orderProductID = orderProductID;
	}

	public String getOrderProductName() {
		return orderProductName;
	}

	public void setOrderProductName(String orderProductName) {
		this.orderProductName = orderProductName;
	}

	public long getOrderProductQuantity() {
		return orderProductQuantity;
	}

	public void setOrderProductQuantity(long orderProductQuantity) {
		this.orderProductQuantity = orderProductQuantity;
	}

	public long getOrderProductUnitCost() {
		return orderProductUnitCost;
	}

	public void setOrderProductUnitCost(long orderProductUnitCost) {
		this.orderProductUnitCost = orderProductUnitCost;
	}

	@Override
	public String toString() {
		return "OrderProductDTO [orderProductID=" + orderProductID + ", orderProductName=" + orderProductName
				+ ", orderProductQuantity=" + orderProductQuantity + ", orderProductUnitCost=" + orderProductUnitCost
				+ "]";
	}

}
