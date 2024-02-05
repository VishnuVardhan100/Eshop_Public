package com.eshop.eshoporderservice.model.logistics;

import java.util.List;

public class WrapperOrderProduct {
	
	private OrderDTO orderDTOObject;
	private List<Long> listOfInventoryProductIDs;
	private List<OrderProductDTO> listOfOrderProductDTOs;
	
	public WrapperOrderProduct() {
		
	}
	
	public WrapperOrderProduct( OrderDTO orderDTOObject, List<Long> listOfInventoryProductIDs,
			List<OrderProductDTO> listOfOrderProductDTOs) {
		this.orderDTOObject = orderDTOObject;
		this.listOfInventoryProductIDs = listOfInventoryProductIDs;
		this.listOfOrderProductDTOs = listOfOrderProductDTOs;
	}

	public OrderDTO getOrderDTOObject() {
		return orderDTOObject;
	}

	public void setOrderDTOObject(OrderDTO orderDTOObject) {
		this.orderDTOObject = orderDTOObject;
	}

	public List<Long> getListOfInventoryProductIDs() {
		return listOfInventoryProductIDs;
	}

	public void setListOfInventoryProductIDs(List<Long> listOfInventoryProductIDs) {
		this.listOfInventoryProductIDs = listOfInventoryProductIDs;
	}
	
	public List<OrderProductDTO> getListOfOrderProductDTOs() {
		return listOfOrderProductDTOs;
	}

	public void setListOfOrderProductDTOs(List<OrderProductDTO> listOfOrderProductDTOs) {
		this.listOfOrderProductDTOs = listOfOrderProductDTOs;
	}
	
}
