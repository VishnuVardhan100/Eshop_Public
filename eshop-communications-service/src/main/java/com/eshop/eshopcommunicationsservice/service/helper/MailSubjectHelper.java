package com.eshop.eshopcommunicationsservice.service.helper;

import java.sql.Date;

import org.springframework.stereotype.Service;

/**
 * Set subject for different mails
 */

@Service
public class MailSubjectHelper {

	private String mailSubject = null;

	/**
	 * Set subject for customer creation mail
	 * @return mail subject
	 */
	public String setCustomerCreatedMailSubject() {
		mailSubject = "Customer account created successfully";
		return mailSubject;
	}
	
	/**
	 * Set subject for customer deletion mail
	 * @return mail subject
	 */
	public String setCustomerDeletedMailSubject() {
		mailSubject = "Customer account deleted successfully";
		return mailSubject;
	}
	
	/**
	 * Set subject for order mail 
	 * @param OrderID - respective order
	 * @param OrderDate - date when order was placed
	 * @return mail subject
	 */
	public String setOrderMailSubject(long orderID, Date orderDate) {
		 mailSubject = "Please find details for Order: " + orderID + ", placed on " + orderDate ;
		 return mailSubject;
	}

	/**Set subject for password reset confirmed mail
	 * @return mail subject
	 */
	public String setPasswordResetMailSubject() {
		mailSubject = "Password reset successfully";
		return mailSubject;
	}
	
	
}
