package com.eshop.eshopmodel.logistics;

import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrapperOrderProduct {
	
	private OrderDTO orderDTOObject;
	private List<OrderProductDTO> listOfOrderProductDTOs;
	
	public WrapperOrderProduct( OrderDTO orderDTOObject, List<OrderProductDTO> listOfOrderProductDTOs) {
		this.orderDTOObject = orderDTOObject;
		this.listOfOrderProductDTOs = listOfOrderProductDTOs;
	}

	public OrderDTO getOrderDTOObject() {
		return orderDTOObject;
	}

	public void setOrderDTOObject(OrderDTO orderDTOObject) {
		this.orderDTOObject = orderDTOObject;
	}

	public List<OrderProductDTO> getListOfOrderProductDTOs() {
		return listOfOrderProductDTOs;
	}

	public void setListOfOrderProductDTOs(List<OrderProductDTO> listOfOrderProductDTOs) {
		this.listOfOrderProductDTOs = listOfOrderProductDTOs;
	}

}
