package com.eshop.eshopmodel.inventory;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopmodel.logistics.Order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO class for Order Product
 */

@Data
@Validated
public class OrderProductDTO {

	private int orderProductID;

	@NotBlank(message="Order Product Name cannot be empty or blank")
	private String orderProductName;	
	
	@Min(value=0, message="Inventory Product Quantity cannot be less than zero")
	private long orderProductQuantity;	

	@NotNull(message="Respective Order cannot be null")
	private Order order;
	
}
