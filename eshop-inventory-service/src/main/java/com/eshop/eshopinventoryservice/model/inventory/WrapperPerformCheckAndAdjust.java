package com.eshop.eshopinventoryservice.model.inventory;

import java.util.List;

import com.eshop.eshopinventoryservice.model.logistics.OrderProduct;

public class WrapperPerformCheckAndAdjust {

	private List<Long> inventoryProductIDList;
	private List<OrderProduct> orderProductList;

	public WrapperPerformCheckAndAdjust() {
		
	}
	
	public WrapperPerformCheckAndAdjust(List<Long> inventoryProductIDList, List<OrderProduct> orderProductList) {
		this.inventoryProductIDList = inventoryProductIDList;
		this.orderProductList = orderProductList;
	}

	public List<Long> getInventoryProductIDList() {
		return inventoryProductIDList;
	}
	public void setInventoryProductIDList(List<Long> inventoryProductIDList) {
		this.inventoryProductIDList = inventoryProductIDList;
	}
	public List<OrderProduct> getOrderProductList() {
		return orderProductList;
	}
	public void setOrderProductList(List<OrderProduct> orderProductList) {
		this.orderProductList = orderProductList;
	}
	
	
	
}
