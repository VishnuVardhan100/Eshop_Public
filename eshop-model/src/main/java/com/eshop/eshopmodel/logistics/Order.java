package com.eshop.eshopmodel.logistics;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopmodel.consumer.User;

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
 * Base entity class for Order. Has list of products and tied to respectful user.
 */

@Data
@Entity
@Table(name="Logistics_Order")
@Validated
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Order_ID")
	private long orderID;

	@NotNull(message="Order Date is mandatory")
	@Column(name="Order_Date")
	private Date orderDate;

	@Min(value=1, message="Order total amount cannot be less than one")
	@Column(name="Order_Total_Amount")
	private long orderTotalAmount;

	@OneToMany(mappedBy="order",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	//@JsonProperty(access=Access.WRITE_ONLY)
	private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

	@NotNull(message="Order cannot be placed without respective user")
	@ManyToOne
	@JoinTable(	name="Consumer_User_Logistics_Order",
				joinColumns= {@JoinColumn(name="Order_ID" , referencedColumnName = "Order_ID")},
				inverseJoinColumns = {@JoinColumn(name="User_ID", referencedColumnName="User_ID")})
	private User user;	

	/**
	 * No argument constructor
	 */
	public Order() {
		
	}

	/**
	 * Parameterized Constructor
	 * @param orderID
	 * @param orderDate
	 * @param orderProductList
	 * @param orderTotalAmount
	 * @param user
	 */
	public Order(long orderID, @NotNull(message = "Order Date is mandatory") Date orderDate,
			@Min(value = 1, message = "Order total amount cannot be less than one") long orderTotalAmount,
			List<OrderProduct> orderProductList,
			@NotNull(message = "Order cannot be placed without respective user") User user) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
		this.orderTotalAmount = orderTotalAmount;
		this.orderProductList = orderProductList;
		this.user = user;
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
	public Date getOrderDate() {
		return orderDate;
	}

	/**
	 * set order placed date
	 * @param orderDate
	 */
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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
	 * @return respective user for the order
	 */
	public User getUser() {
		return user;
	}

	/**
	 * set respective user for the order
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	/**
	 * Return Order with order ID and order date
	 */
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", orderDate=" + orderDate + ", orderTotalAmount=" + orderTotalAmount + "]";
	}
	
}
