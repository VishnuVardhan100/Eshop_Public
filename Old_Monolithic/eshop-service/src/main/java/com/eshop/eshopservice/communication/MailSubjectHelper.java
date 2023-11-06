package com.eshop.eshopservice.communication;

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
	protected String setCustomerCreatedMailSubject() {
		mailSubject = "Customer account created successfully";
		return mailSubject;
	}
	
	/**
	 * Set subject for customer deletion mail
	 * @return mail subject
	 */
	protected String setCustomerDeletedMailSubject() {
		mailSubject = "Customer account deleted succesfully";
		return mailSubject;
	}
	
	/**
	 * Set subject for order mail 
	 * @param order ID - respective order
	 * @param order Date - date when order was placed
	 * @return mail subject
	 */
	protected String setOrderMailSubject(long orderID, Date orderDate) {
		 mailSubject = "Please find details for Order: " + orderID + ", placed on " + orderDate ;
		 return mailSubject;
	}

	/**Set subject for password reset confirmed mail
	 * @return mail subject
	 */
	protected String setPasswordResetMailSubject() {
		mailSubject = "Password reset successfully";
		return mailSubject;
	}
	
	
}
