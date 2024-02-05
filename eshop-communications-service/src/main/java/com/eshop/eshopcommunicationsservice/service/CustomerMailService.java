package com.eshop.eshopcommunicationsservice.service;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eshop.eshopcommunicationsservice.model.customer.Customer;
import com.eshop.eshopcommunicationsservice.service.helper.MailSubjectHelper;
import com.eshop.eshopcommunicationsservice.service.helper.MailTextHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * Class for sending customer mail for various customer actions
 */

@Service
public class CustomerMailService extends EshopMailService {

	private static int customerCreated = 0;
	private static int customerPasswordReset = 1;
	private static int customerDeleted = 2;
	
	
	@Autowired
	private MailSubjectHelper mailSubjectHelper;
	@Autowired
	private MailTextHelper mailTextHelper;
	
	private Session session = null;
	private Properties properties = null;
	
	private MimeMessage mimeMessage = null;
	private MimeMultipart mimeMultipart = null;
	private MimeBodyPart bodyPart = null;
	
	@Value("${send.mail.from}")
	private String from;
	@Value("${send.mail.password}")
	private String password;
	private String subject = "";
	private String text = "";

	/**
	 * Prepare the customer mail's basic parts like session and body with relevant text
	 * @param Customer Object - for whom mail is being sent
	 * @param typeOfCustomerMail - mail for customer creation / password reset /deleted
	 * @throws AddressException addressException
	 * @throws MessagingException messagingException
	 */
	protected void prepareCustomerMail(Customer customerObject, int typeOfCustomermail) throws AddressException, MessagingException  {
		session = createSession(properties, from , password);
		
		switch(typeOfCustomermail) {
			case 0: subject = mailSubjectHelper.setCustomerCreatedMailSubject();
					text = mailTextHelper.setCustomerCreationMailText(customerObject.getCustomerID(), customerObject.getCustomerFirstName(),
							customerObject.getCustomerLastName(), customerObject.getCustomerEmail(), customerObject.getCustomerMobileNumber(),
							customerObject.getCustomerSubscription().getSubscriptionType());

			case 1: subject = mailSubjectHelper.setCustomerDeletedMailSubject();
			text = mailTextHelper.setCustomerDeletedMailText(customerObject.getCustomerID(), customerObject.getCustomerFirstName(),
					customerObject.getCustomerLastName());

			case 2: subject = mailSubjectHelper.setPasswordResetMailSubject();
			text = mailTextHelper.setCustomerPasswordResetMailText(customerObject.getCustomerID(), customerObject.getCustomerFirstName(),
					customerObject.getCustomerLastName());
		}
		
		mimeMessage = createMIMEMessage(session, from, customerObject.getCustomerEmail(), subject);
		mimeMultipart = createMultipart();
	}
	
	/**
	 * Send the mail
	 * @throws MessagingException messagingException
	 */
	protected void sendMail() throws MessagingException {
		mimeMessage.setContent(mimeMultipart);
		Transport.send(mimeMessage);
	}
	
	/**
	 * Start the customer created mail sending process
	 * @param customerObject - for whom mail is being sent
	 * @throws AddressException addressException
	 * @throws MessagingException messagingException
	 */
	public void sendCustomerCreatedMail(Customer customerObject) throws AddressException, MessagingException  {
		prepareCustomerMail(customerObject, customerCreated);
		bodyPart = (MimeBodyPart) createMessageBodyPart();
		bodyPart.setText(text);	
		mimeMultipart.addBodyPart(bodyPart);
		sendMail();
	}

	/**
	 * Start the customer password reset confirmation mail sending process
	 * @param customerObject - for whom mail is being sent
	 * @throws AddressException addressException
	 * @throws MessagingException messagingException
	 */
	public void sendCustomerPasswordResetMail(Customer customerObject) throws AddressException, MessagingException  {
		prepareCustomerMail(customerObject, customerPasswordReset);
		bodyPart = (MimeBodyPart) createMessageBodyPart();
		bodyPart.setText(text);	
		mimeMultipart.addBodyPart(bodyPart);
		sendMail();
	}
	
	/**
	 * Start the customer deleted mail sending process
	 * @param customerObject - for whom mail is being sent
	 * @throws AddressException addressException
	 * @throws MessagingException messagingException
	 */
	public void sendCustomerDeletedMail(Customer customerObject) throws AddressException, MessagingException  {
		prepareCustomerMail(customerObject, customerDeleted);
		bodyPart = (MimeBodyPart) createMessageBodyPart();
		bodyPart.setText(text);	
		mimeMultipart.addBodyPart(bodyPart);
		sendMail();
	}

}
