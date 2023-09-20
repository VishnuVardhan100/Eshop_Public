package com.eshop.eshopmodel;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Order")
public class Order {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OrderID")
	private long id;
	
	@Column(name="OrderDate")
	private Date orderDate;
	
	@ManyToMany
	@JsonProperty(access=Access.WRITE_ONLY)
	private List<Product> productsList = new ArrayList<Product>();
	
	public Order() {
		
	}

	public Order(long id, Date orderDate, List<Product> productsList) {
		super();
		this.id = id;
		this.orderDate = orderDate;
		this.productsList = productsList;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<Product> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<Product> productsList) {
		this.productsList = productsList;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderDate=" + orderDate + ", productsList=" + productsList + "]";
	}
	
}
