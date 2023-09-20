package com.eshop.eshopmodel;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Product")
public class Product {

	@jakarta.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ProductID")
	private int id;
	
	@Column(name="ProductName")
	private String name;
	
	@Column(name="ProductInventoryQuantity")
	private long quantity;
	
	@ManyToMany(mappedBy="productsList")
	@JsonProperty(access=Access.WRITE_ONLY)
	private List<Order> ordersList = new ArrayList<Order>();	

	public Product() {
		
	}
	
	public Product(int id, String name, long quantity) {
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Product [ID=" + id + ", name=" + name + ", quantity=" + quantity + "]";
	}
	
}
