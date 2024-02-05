
package com.eshop.eshoporderservice.model.inventory;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO class for Inventory Product 
 */

@Data
@Validated
public class InventoryProductDTO {

	private long inventoryProductID;

	@NotBlank(message="Inventory Product name cannot be empty or blank")
	private String inventoryProductName;
	
	@Min(value=1, message="Inventory Product Quantity cannot be less than one")
	private long inventoryProductQuantity;

	@Min(value=1, message="Inventory Product Price cannot be less than one")
	private long inventoryProductPrice;
	
	@NotNull(message="Inventory Product category cannot be empty or blank")
	@Enumerated(EnumType.STRING)
	private ProductCategory inventoryProductCategory;

	public InventoryProductDTO() {
		
	}

	public InventoryProductDTO(long inventoryProductID,
			@NotBlank(message = "Inventory Product name cannot be empty or blank") String inventoryProductName,
			@Min(value = 1, message = "Inventory Product Quantity cannot be less than one") long inventoryProductQuantity,
			@Min(value = 1, message = "Inventory Product Price cannot be less than one") long inventoryProductPrice,
			@NotNull(message = "Inventory Product category cannot be empty or blank") ProductCategory inventoryProductCategory) {
		super();
		this.inventoryProductID = inventoryProductID;
		this.inventoryProductName = inventoryProductName;
		this.inventoryProductQuantity = inventoryProductQuantity;
		this.inventoryProductPrice = inventoryProductPrice;
		this.inventoryProductCategory = inventoryProductCategory;
	}

	public long getInventoryProductID() {
		return inventoryProductID;
	}

	public void setInventoryProductID(long inventoryProductID) {
		this.inventoryProductID = inventoryProductID;
	}

	public String getInventoryProductName() {
		return inventoryProductName;
	}

	public void setInventoryProductName(String inventoryProductName) {
		this.inventoryProductName = inventoryProductName;
	}

	public long getInventoryProductQuantity() {
		return inventoryProductQuantity;
	}

	public void setInventoryProductQuantity(long inventoryProductQuantity) {
		this.inventoryProductQuantity = inventoryProductQuantity;
	}

	public long getInventoryProductPrice() {
		return inventoryProductPrice;
	}

	public void setInventoryProductPrice(long inventoryProductPrice) {
		this.inventoryProductPrice = inventoryProductPrice;
	}

	public ProductCategory getInventoryProductCategory() {
		return inventoryProductCategory;
	}

	public void setInventoryProductCategory(ProductCategory inventoryProductCategory) {
		this.inventoryProductCategory = inventoryProductCategory;
	}
	
}
