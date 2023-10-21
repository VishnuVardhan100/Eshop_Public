package com.eshop.eshopinventoryservice.model.customer;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class WrapperCustomerAddress {

	private CustomerDTO customerDTOObject;
	private CustomerAddressDTO customerAddressDTOObject;

	public WrapperCustomerAddress(CustomerDTO customerDTOObject, CustomerAddressDTO customerAddressDTOObject) {
		this.customerDTOObject = customerDTOObject;
		this.customerAddressDTOObject = customerAddressDTOObject;
	}
	
	public CustomerDTO getCustomerDTOObject() {
		return customerDTOObject;
	}

	public void setCustomerDTOObject(CustomerDTO customerDTOObject) {
		this.customerDTOObject = customerDTOObject;
	}

	public CustomerAddressDTO getCustomerAddressDTOObject() {
		return customerAddressDTOObject;
	}

	public void setCustomerAddressDTOObject(CustomerAddressDTO customerAddressDTOObject) {
		this.customerAddressDTOObject = customerAddressDTOObject;
	}

}
