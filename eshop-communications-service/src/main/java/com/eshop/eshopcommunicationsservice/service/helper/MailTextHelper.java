package com.eshop.eshopcommunicationsservice.service.helper;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.eshop.eshopcommunicationsservice.model.logistics.OrderProduct;

/**
 * Set text for different mails
 */

@Service
public class MailTextHelper {

	private String text = null;
	
	/**
	 * @param CustomerID customerID
	 * @param FirstName firstName
	 * @param LastName lastName
	 * @param Email email
	 * @param MobileNumber mobileNumber
	 * @return text as string
	 */
	public String setCustomerCreationMailText(long customerID, String firstName, String lastName, String email, String mobileNumber, String subscription) {
		text = "Customer Account has been created successfully with below information" + "\n\n\n";
		
		text += "Customer ID: " + customerID + "\nCustomer First Name: " + firstName + "\nCustomer Last Name: " + lastName;

		text += "Customer Email: " + email + "\nCustomer Mobile Number: " + mobileNumber + "\nCustomer Subscription: " + subscription;
		
		return text;
	}
	
	
	/**
	 * Set text message part for order mail
	 * @param CustomerID customerID
	 * @param FirstName firstName
	 * @param LastName lastName
	 * @return mail text
	 */
	public String setCustomerDeletedMailText(long customerID, String firstName, String lastName) {
		text = "Customer Account has been deleted successfully for below customer" + "\n\n\n";
		
		text += "Customer ID: " + customerID + "\nCustomer First Name: " + firstName + "\nCustomer Last Name: " + lastName;
		
		return text;
	}
	
	/**
	 * Set text message part for order mail
	 * @param orderID - respective order
	 * @param orderDate - date when order was placed
	 * @param orderTotalAmount - total bill amount for the order
	 * @param listOfOrderProducts - all products in order in question
	 * @return mail text
	 */
	public String setOrderMailText(long orderID, Date orderDate, long orderTotalAmount, List<OrderProduct> listOfOrderProducts) {
		text = "Order Summary for /n/nOrder ID: " + orderID + "/nOrder Date: " + orderDate + "/nOrder Amount: " + orderTotalAmount + "/n/n/n";
		
		text += "Order Product Details:/n/nProduct Name/tProduct Unit Cost/tProduct Quantity/tProduct Total Cost/n/n";
		
		for(OrderProduct product : listOfOrderProducts) {
			text += product.getOrderProductName() + "/t" + product.getOrderProductUnitCost() + "/t" + product.getOrderProductQuantity() + "/t" + product.getOrderProductTotalCost() + "/n";
		}
		
		text += "/n/n/nThank you and please visit again!!!";
		return text;
	}

	/**
	 * Set text message part for order mail
	 * @param CustomerID customerID
	 * @param FirstName firstName
	 * @param LastName lastName
	 * @return mail text
	 */
	public String setCustomerPasswordResetMailText(long customerID, String firstName, String lastName) {
		text = "Password has been reset successfully for below customer" + "\n\n\n";
		
		text += "Customer ID: " + customerID + "\nCustomer First Name: " + firstName + "\nCustomer Last Name: " + lastName;
		
		return text;
	}
	
}
