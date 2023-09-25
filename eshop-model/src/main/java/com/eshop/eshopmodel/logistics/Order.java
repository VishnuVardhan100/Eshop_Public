package com.eshop.eshopmodel.logistics;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopmodel.consumer.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Base entity class for Order. Has list of products and tied to respectful user.
 */

@Data
@Entity
@Table(name="LogisticsOrder")
@Validated
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OrderID")
	private long orderID;

	@NotNull(message="Order Date is mandatory")
	@Column(name="OrderDate")
	private Date orderDate;

	@NotNull(message="Order cannot have zero order products")
	@OneToMany(mappedBy="order",fetch=FetchType.LAZY,cascade=CascadeType.ALL)
	@JsonProperty(access=Access.WRITE_ONLY)
	@Column(name="OrderProductID")
	private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

	@NotNull(message="Order cannot be placed without respective user")
	@ManyToOne(cascade= {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinColumn(name="UserID")
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
	 * @param user
	 */
	public Order(long orderID, @NotNull(message = "Order Date is mandatory") Date orderDate,
			@NotNull(message = "Order cannot have zero order products") List<OrderProduct> orderProductList,
			@NotNull(message = "Order cannot be placed without respective user") User user) {
		super();
		this.orderID = orderID;
		this.orderDate = orderDate;
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
		return "Order [orderID=" + orderID + ", orderDate=" + orderDate + "]";
	}
	
}
