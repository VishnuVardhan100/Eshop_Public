package com.eshop.eshopservice.communication;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.logistics.Order;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * Class for sending order related mail to specific customer
 */

@Service
public class OrderMailService extends EshopMailService {

	@Autowired
	private MailSubjectHelper mailSubjectHelper;
	
	@Autowired
	private MailTextHelper mailTextHelper;
	
	private Session session = null;
	private Properties properties = null;
	
	private MimeMessage mimeMessage = null;
	private MimeMultipart mimeMultipart = null;
	private MimeBodyPart bodyPart = null;
	private MimeBodyPart bodyPartWithAttachment = null;
	
	@Value("${send.mail.from}")
	private String from;
	@Value("${send.mail.password}")
	private String password;
	private String subject = "";
	private String text = "";
	
	/**
	 * Prepare the order mail's basic parts like session and body with relevant text
	 * @param Customer Object - for whom mail is being sent
	 * @param order Object - the intended order
	 * @throws AddressException 
	 * @throws MessagingException
	 */
	protected void prepareOrderMail(Customer customerObject, Order orderObject) throws AddressException, MessagingException  {
		session = createSession(properties, from , password);
		subject = mailSubjectHelper.setOrderMailSubject(orderObject.getOrderID(), orderObject.getOrderPlacedDate());
		mimeMessage = createMIMEMessage(session, from, customerObject.getCustomerEmail(), subject);
		mimeMultipart = createMultipart();
		text = mailTextHelper.setOrderMailText(orderObject.getOrderID(), orderObject.getOrderPlacedDate(), orderObject.getOrderTotalAmount(), orderObject.getOrderProductList());
	}

	/**
	 * Send the mail
	 * @throws MessagingException
	 */
	protected void sendMail() throws MessagingException {
		mimeMessage.setContent(mimeMultipart);
		Transport.send(mimeMessage);
	}
	
	/**
	 * Start the order mail sending process
	 * @param customerObject - for whom mail is being sent
	 * @param orderObject - the intended order
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendOrderSummaryViaMail(Customer customerObject, Order orderObject) throws AddressException, MessagingException  {
		prepareOrderMail(customerObject, orderObject);
		bodyPart = (MimeBodyPart) createMessageBodyPart();
		bodyPart.setText(text);	
		mimeMultipart.addBodyPart(bodyPart);
		sendMail();
	}

	/**
	 * Start the order mail sending process with an attachment
	 * @param customerObject - for whom mail is being sent
	 * @param orderObject - the intended order
	 * @param fileFullPathname
	 * @throws AddressException
	 * @throws MessagingException
	 */
	public void sendOrderSummaryViaMailWithAttachement(Customer customerObject, Order orderObject, String fileFullPathname) throws AddressException, MessagingException  {
		prepareOrderMail(customerObject, orderObject);
		bodyPart = (MimeBodyPart) createMessageBodyPart();
		bodyPart.setText(text);
		bodyPartWithAttachment = (MimeBodyPart) createMessageBodyPartWithAttachment(fileFullPathname);
		mimeMultipart.addBodyPart(bodyPart);
		mimeMultipart.addBodyPart(bodyPartWithAttachment);
		sendMail();
	}	

	
	
}
