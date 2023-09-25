package com.eshop.eshopmodel.logistics;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.eshop.eshopmodel.consumer.User;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO class for Order class 
 */

@Data
@Validated
public class OrderDTO {

	private long orderID;

	@NotNull(message="Order Date is mandatory")
	private Date orderDate;

	@NotNull(message="Order cannot have zero order products")
	private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

	@NotNull(message="Order cannot be placed without respective user")
	private User user;	

}
