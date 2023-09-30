package com.eshop.eshopmodel.logistics;

import java.sql.Date;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Min;
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

	@Min(value=1, message="Order total amount cannot be less than one")
	private long orderTotalAmount;

}
