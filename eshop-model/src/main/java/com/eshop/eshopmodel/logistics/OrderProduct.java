package com.eshop.eshopmodel.logistics;

import org.springframework.validation.annotation.Validated;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="OrderProduct")
@Validated
public class OrderProduct{

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OrderProductID")
	private int orderProductID;

	@NotBlank(message="Order Product Name cannot be empty or blank")
	@Column(name="OrderProductName")
	private String orderProductName;	
	
	@Column(name="OrderProductQuantity")
	@Min(value=0, message="Inventory Product Quantity cannot be less than zero")
	private long orderProductQuantity;

	@NotNull(message="Respective Order cannot be null")
	@ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
	@Column(name="OrderID")
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
	 * @param order
	 */
	public OrderProduct(int orderProductID,
			@NotBlank(message = "Order Product Name cannot be empty or blank") String orderProductName,
			@Min(value = 0, message = "Inventory Product Quantity cannot be less than zero") long orderProductQuantity,
			@NotNull(message = "Respective Order cannot be null") Order order) {
		super();
		this.orderProductID = orderProductID;
		this.orderProductName = orderProductName;
		this.orderProductQuantity = orderProductQuantity;
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
	 * Returns Order Product with Order Product ID, Order Product Name and Order Product Quantity which user placed in order
	 */
	@Override
	public String toString() {
		return "Product [orderProductID=" + orderProductID + ", orderProductName=" + getOrderProductName() + ", orderProductQuantity=" + orderProductQuantity + "]";
	}
	
}
