package com.eshop.eshopmodel.inventory;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Base Entity class for inventory Product.
 * It is used to keep track of products in the inventory
 */

@Data
@Entity
@Table(name="InventoryProduct")
@Validated
public class InventoryProduct {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="InventoryProductID")
	private int inventoryProductID;
	
	@NotBlank(message="Inventory Product name cannot be empty or blank")
	@Column(name="InventoryProductName")
	private String inventoryProductName;
	
	@Column(name="InventoryProductQuantity")
	@Min(value=0, message="Inventory Product Quantity cannot be less than zero")
	private long inventoryProductQuantity;
	
	@NotBlank(message="Inventory Product category cannot be empty or blank")
	@Enumerated(EnumType.STRING)
	@Column(name="InventoryProductCategory")
	private ProductCategory inventoryProductCategory;
	
	/**
	 * No argument constructor
	 */
	public InventoryProduct() {
		
	}

	/**
	 * Parameterized Constructor
	 * @param inventoryProductID
	 * @param inventoryProductName
	 * @param inventoryProductQuantity
	 * @param inventoryProductCategory
	 */
	public InventoryProduct(int inventoryProductID,
			@NotBlank(message = "Inventory Product name cannot be empty or blank") String inventoryProductName,
			@Min(value = 0, message = "Inventory Product Quantity cannot be less than zero") long inventoryProductQuantity,
			@NotBlank(message = "Inventory Product category cannot be empty or blank") ProductCategory inventoryProductCategory) {
		super();
		this.inventoryProductID = inventoryProductID;
		this.inventoryProductName = inventoryProductName;
		this.inventoryProductQuantity = inventoryProductQuantity;
		this.inventoryProductCategory = inventoryProductCategory;
	}

	/**
	 * @return Inventory Product ID
	 */
	public int getInventoryProductID() {
		return inventoryProductID;
	}

	/**
	 * set Inventory Product ID
	 * @param inventoryProductID
	 */
	public void setInventoryProductID(int inventoryProductID) {
		this.inventoryProductID = inventoryProductID;
	}

	/**
	 * @return Inventory Product Name
	 */
	public String getInventoryProductName() {
		return inventoryProductName;
	}

	/**
	 * set Inventory Product Name
	 * @param inventoryProductName
	 */
	public void setInventoryProductName(String inventoryProductName) {
		this.inventoryProductName = inventoryProductName;
	}

	/**
	 * @return Inventory Product Quantity
	 */
	public long getInventoryProductQuantity() {
		return inventoryProductQuantity;
	}

	/**
	 * set Inventory Product Quantity
	 * @param inventoryProductQuantity
	 */
	public void setInventoryProductQuantity(long inventoryProductQuantity) {
		this.inventoryProductQuantity = inventoryProductQuantity;
	}

	/**
	 * @return Inventory Product Category
	 */
	public ProductCategory getInventoryProductCategory() {
		return inventoryProductCategory;
	}

	/**
	 * set Inventory Product Category
	 * @param Inventory Product Category
	 */
	public void setInventoryProductCategory(ProductCategory inventoryProductCategory) {
		this.inventoryProductCategory = inventoryProductCategory;
	}

	/**
	 * Returns Inventory Product with ID, Name and Quantity
	 */
	@Override
	public String toString() {
		return "InventoryProduct [InventoryProductID=" + inventoryProductID + ", InventoryProductName=" + inventoryProductName + ", InventoryProductQuantity=" + inventoryProductQuantity +
				", InventoryProductCategory" + inventoryProductCategory + "]";
	}
	
}
