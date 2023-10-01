package com.eshop.eshopmodel.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WrapperCustomerAddress {

	private long customerID;
	private CustomerAddress customerAddress;

}
