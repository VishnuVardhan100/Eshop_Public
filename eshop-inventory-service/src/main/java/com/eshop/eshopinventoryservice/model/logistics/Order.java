package com.eshop.eshopinventoryservice.model.logistics;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopinventoryservice.model.customer.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Base entity class for Order. Has list of products and tied to respectful Customer.
 */

@Data
@Entity
@Table(name="Logistics_Order")
@Validated
public class Order implements Serializable {

	/**
	 * Default Version
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Order_ID")
	private long orderID;

	@NotNull(message="Order Date is mandatory")
	@Column(name="Order_Placed_Date")
	private Date orderPlacedDate;

	@Column(name="Order_Delivery_Date")
	private Date orderDeliveryDate;
	
	@Min(value=1, message="Order total amount cannot be less than one")
	@Column(name="Order_Total_Amount")
	private long orderTotalAmount;

	@Column(name="Order_Delivery_Status")
	boolean orderDeliveryStatus;
	
	@OneToMany(mappedBy="order",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

	@NotNull(message="Order cannot be placed without respective customer")
	@ManyToOne
	@JoinTable(	name="Customer_Logistics_Order",
				joinColumns= {@JoinColumn(name="Order_ID" , referencedColumnName = "Order_ID")},
				inverseJoinColumns = {@JoinColumn(name="Customer_ID", referencedColumnName="Customer_ID")})
	private Customer customer;	

	/**
	 * No argument constructor
	 */
	public Order() {
		
	}

	/**
	 * Parameterized Constructor
	 * @param orderID
	 * @param orderPlacedDate
	 * @param orderDeliveryDate
	 * @param orderDeliveryStatus
	 * @param orderProductList
	 * @param orderTotalAmount
	 * @param customer
	 */
	public Order(long orderID, 
			@NotNull(message = "Order Date is mandatory") Date orderPlacedDate,
			Date orderDeliveryDate,
			@Min(value = 1, message = "Order total amount cannot be less than one") long orderTotalAmount,
			boolean orderDeliveryStatus,
			List<OrderProduct> orderProductList,
			@NotNull(message = "Order cannot be placed without respective customer") Customer customer) {
		super();
		this.orderID = orderID;
		this.orderPlacedDate = orderPlacedDate;
		this.orderDeliveryDate = orderDeliveryDate;
		this.orderTotalAmount = orderTotalAmount;
		this.orderDeliveryStatus = false;
		this.orderProductList = orderProductList;
		this.customer = customer;
	}
		
	/**
	 * @return order id
	 */
	public long getOrderID() {
		return orderID;
	}

	/**
	 * set order id
	 * @param id
	 */
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}

	/**
	 * @return order placed date
	 */
	public Date getOrderPlacedDate() {
		return orderPlacedDate;
	}

	/**
	 * set order placed date
	 * @param orderPlacedDate
	 */
	public void setOrderPlacedDate(Date orderPlacedDate) {
		this.orderPlacedDate = orderPlacedDate;
	}

	/**
	 * @return order delivery date
	 */
	public Date getOrderDeliveryDate() {
		return orderDeliveryDate;
	}

	/**
	 * set order delivery date
	 * @param orderDeliveryDate
	 */
	public void setOrderDeliveryDate(Date orderDeliveryDate) {
		this.orderDeliveryDate = orderDeliveryDate;
	}
	
	/**
	 * @return Order Total Amount
	 */
	public long getOrderTotalAmount() {
		return orderTotalAmount;
	}

	/**
	 * Set Order Total Amount
	 * @param orderTotalAmount
	 */
	public void setOrderTotalAmount(long orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}

	/**
	 * @return Order Delivery Status
	 */
	public boolean getOrderDeliveryStatus() {
		return orderDeliveryStatus;
	}

	/**
	 * Set Order Delivery Status
	 * @param orderDelivery Status
	 */
	public void setOrderDeliveryStatus(boolean orderDeliveryStatus) {
		this.orderDeliveryStatus = orderDeliveryStatus;
	}
	
	/**
	 * @return list of products in the order
	 */
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}

	/**
	 * set list of products in the order
	 * @param productsList
	 */
	public void setProductsList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}

	/**
	 * @return respective customer for the order
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * set respective customer for the order
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	/**
	 * Return Order with order ID and order date
	 */
	@Override
	public String toString() {
		return "Order [ID=" + orderID + ", Order Placed Date=" + orderPlacedDate + ", Order Delivery Date=" + orderDeliveryDate + ", Order Total Amount=" + orderTotalAmount 
				+ ", Order Delivery Status" + orderDeliveryStatus + "]";
	}
	
}
