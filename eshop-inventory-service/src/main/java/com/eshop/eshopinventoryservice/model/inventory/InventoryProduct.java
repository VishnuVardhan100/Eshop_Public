package com.eshop.eshopinventoryservice.model.inventory;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Base Entity class for inventory Product.
 * It is used to keep track of products in the inventory
 */

@Data
@Entity
@Table( name="Inventory_Product",
uniqueConstraints= {
		@UniqueConstraint(name="UniqueInventoryProductName", columnNames = { "Inventory_Product_Name" })
})

@Validated
public class InventoryProduct {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Inventory_Product_ID")
	private long inventoryProductID;
	
	@NotBlank(message="Inventory Product name cannot be empty or blank")
	@Column(name="Inventory_Product_Name")
	private String inventoryProductName;
	
	@Column(name="Inventory_Product_Quantity")
	@Min(value=1, message="Inventory Product Quantity cannot be less than one")
	private long inventoryProductQuantity;

	@Column(name="Inventory_Product_Price")
	@Min(value=1, message="Inventory Product Price cannot be less than one")
	private long inventoryProductPrice;
	
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
	public InventoryProduct(long inventoryProductID,
			@NotBlank(message = "Inventory Product name cannot be empty or blank") String inventoryProductName,
			@Min(value = 1, message = "Inventory Product Quantity cannot be less than one") long inventoryProductQuantity,
			@Min(value = 1, message="Inventory Product Price cannot be less than one") long inventoryProductPrice,
			@NotBlank(message = "Inventory Product category cannot be empty or blank") ProductCategory inventoryProductCategory){
		super();
		this.inventoryProductID = inventoryProductID;
		this.inventoryProductName = inventoryProductName;
		this.inventoryProductQuantity = inventoryProductQuantity;
		this.inventoryProductPrice = inventoryProductPrice;
		this.inventoryProductCategory = inventoryProductCategory;
	}

	/**
	 * @return Inventory Product ID
	 */
	public long getInventoryProductID() {
		return inventoryProductID;
	}

	/**
	 * set Inventory Product ID
	 * @param inventoryProductID
	 */
	public void setInventoryProductID(long inventoryProductID) {
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
	 * @return Inventory Product Price
	 */
	public long getInventoryProductPrice() {
		return inventoryProductPrice;
	}

	/**
	 * set Inventory Product Price
	 * @param inventoryProductPrice
	 */
	public void setInventoryProductPrice(long inventoryProductPrice) {
		this.inventoryProductPrice = inventoryProductPrice;
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
