package com.eshop.eshopmodel.logistics;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Order Product entity class which is derived from Inventory Product class.
 * This class is for product that user places in cart and orders.
 */

@Data
@Entity
@Table(name="Logisitics_Order_Product")
@Validated
public class OrderProduct{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Order_Product_ID")
	private int orderProductID;

	@NotBlank(message="Order Product Name cannot be empty or blank")
	@Column(name="Order_Product_Name")
	private String orderProductName;	
	
	@Column(name="Order_Product_Quantity")
	@Min(value=1, message="Order Product Quantity cannot be less than one")
	private long orderProductQuantity;

	@Column(name="Order_Product_Unit_Cost")
	@Min(value=1, message="Order Product unit cost cannot be less than one")
	private long orderProductUnitCost;

	@Column(name="Order_Product_Total_Cost")
	@Min(value=1, message="Order Product total cost cannot be less than one")
	private long orderProductTotalCost;

	@JsonProperty(access=Access.WRITE_ONLY)
	@NotNull(message="Respective Order cannot be null")
	@ManyToOne
	@JoinTable(	name="Logistics_Order_Logistics_Order_Product",
				joinColumns= {@JoinColumn(name="Order_Product_ID" , referencedColumnName = "Order_Product_ID")},
				inverseJoinColumns = {@JoinColumn(name="Order_ID", referencedColumnName="Order_ID")})
	private Order order;
	
	/**
	 * No argument constructor
	 */
	public OrderProduct() {
		super();
	}

	/**
	 * Parameterized Constructor
	 * @param orderProductID
	 * @param orderProductName
	 * @param orderProductQuantity
	 * @param orderProductUnitCost,
	 * @param orderProductTotalCost
	 * @param order
	 */
	public OrderProduct(int orderProductID,
			@NotBlank(message = "Order Product Name cannot be empty or blank") String orderProductName,
			@Min(value = 1, message = "Order Product Quantity cannot be less than one") long orderProductQuantity,
			@Min(value = 1, message = "Order Product unit cost cannot be less than one") long orderProductUnitCost,
			@Min(value = 1, message = "Order Product total cost cannot be less than one") long orderProductTotalCost,
			@NotNull(message = "Respective Order cannot be null") Order order) {
		super();
		this.orderProductID = orderProductID;
		this.orderProductName = orderProductName;
		this.orderProductQuantity = orderProductQuantity;
		this.orderProductUnitCost = orderProductUnitCost;
		this.orderProductTotalCost = orderProductTotalCost;
		this.order = order;
	}

	/**
	 * @return Order Product ID
	 */
	public int getOrderProductID() {
		return orderProductID;
	}

	/**
	 * set Order Product ID
	 * @param orderProductID
	 */
	public void setOrderProductID(int orderProductID) {
		this.orderProductID = orderProductID;
	}
	
	/**
	 * @return Order Product Name
	 */
	public String getOrderProductName() {
		return orderProductName;
	}


	/**
	 * set Order Product Name
	 * @param orderProductName
	 */
	public void setOrderProductName(String orderProductName) {
		this.orderProductName = orderProductName;
	}


	/**
	 * @return Order Product Quantity
	 */
	public long getOrderProductQuantity() {
		return orderProductQuantity;
	}

	/**
	 * set Order Product Quantity
	 * @param orderProductQuantity
	 */
	public void setOrderProductQuantity(long orderProductQuantity) {
		this.orderProductQuantity = orderProductQuantity;
	}
	
	/**
	 * @return
	 */
	public long getOrderProductUnitCost() {
		return orderProductUnitCost;
	}

	/**
	 * @param orderProductUnitCost
	 */
	public void setOrderProductUnitCost(long orderProductUnitCost) {
		this.orderProductUnitCost = orderProductUnitCost;
	}

	/**
	 * @return
	 */
	public long getOrderProductTotalCost() {
		return orderProductTotalCost;
	}

	/**
	 * @param orderProductTotalCost
	 */
	public void setOrderProductTotalCost(long orderProductTotalCost) {
		this.orderProductTotalCost = orderProductTotalCost;
	}

	/**
	 * @return respective order
	 */
	public Order getOrder() {
		return order;
	}

	/**
	 * set respective order
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}

	/**
	 * Returns Order Product with Order Product ID, Order Product Name ,Order Product Quantity, Order Product Unit Cost
	 * and Order Product Total Cost which user placed in order
	 */
	@Override
	public String toString() {
		return "OrderProduct [orderProductID=" + orderProductID + ", orderProductName=" + orderProductName
				+ ", orderProductQuantity=" + orderProductQuantity + ", orderProductUnitCost=" + orderProductUnitCost
				+ ", orderProductTotalCost=" + orderProductTotalCost + "]";
	}
	
}
