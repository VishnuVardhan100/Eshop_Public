package com.eshop.eshopinventoryservice.model.logistics;

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

	@SuppressWarnings("unused")
	private long orderProductID;

	@NotBlank(message="Order Product Name cannot be empty or blank")
	private String orderProductName;	
	
	@Min(value=1, message="Order Product Quantity cannot be less than one")
	private long orderProductQuantity;

	@Min(value=1, message="Order Product unit cost cannot be less than one")
	private long orderProductUnitCost;
	
}
