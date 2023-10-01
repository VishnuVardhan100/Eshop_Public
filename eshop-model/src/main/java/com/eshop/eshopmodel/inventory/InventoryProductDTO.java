package com.eshop.eshopmodel.inventory;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO class for Inventory Product 
 */

@Data
@Validated
public class InventoryProductDTO {

	@SuppressWarnings("unused")
	private long inventoryProductID;

	@NotBlank(message="Inventory Product name cannot be empty or blank")
	private String inventoryProductName;
	
	@Min(value=1, message="Inventory Product Quantity cannot be less than one")
	private long inventoryProductQuantity;

	@Min(value=1, message="Inventory Product Price cannot be less than one")
	private long inventoryProductPrice;
	
	/*@NotBlank(message="Inventory Product category cannot be empty or blank")
	@Enumerated(EnumType.STRING)
	private ProductCategory inventoryProductCategory;*/

}
