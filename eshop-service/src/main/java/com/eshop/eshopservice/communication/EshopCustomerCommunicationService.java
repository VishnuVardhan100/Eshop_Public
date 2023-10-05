package com.eshop.eshopservice.communication;

import java.sql.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.logistics.Order;
import com.eshop.eshopmodel.logistics.OrderProduct;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * Class to send mail to Customer about order
 */

@Service
public class EshopCustomerCommunicationService {

	private Session session = null;
	private Properties properties = null;
	
	private MimeMessage mimeMessage = null;
	private MimeMultipart mimeMultipart = null;
	private MimeBodyPart bodyPart = null;
	private MimeBodyPart bodyPartWithAttachment = null;
	private DataHandler dataHandler = null;
	private DataSource dataSource = null;
	
	@Value("${send.mail.from}")
	private String from;
	@Value("${send.mail.password}")
	private String password;
	private String subject = "";
	private String text = "";


	/**
	 * Add a message body part containing relevant text for mail
	 * @throws MessagingException
	 */
	private void addMessageBodyPart() throws MessagingException {
		bodyPart = new MimeBodyPart();
		bodyPart.setText(text);
	}
	
	/**
	 * Add a message body part containing relevant files to be sent as attachment
	 * @param fileFullPathname - file's full path
	 * @throws MessagingException
	 */
	private void addMessageBodyPartWithAttachment(String fileFullPathname) throws MessagingException {
		bodyPartWithAttachment = new MimeBodyPart();
		dataSource = new FileDataSource(fileFullPathname);
		dataHandler = new DataHandler(dataSource);
		bodyPartWithAttachment.setDataHandler(dataHandler);
		bodyPartWithAttachment.setFileName(fileFullPathname);
	}

	/**
	 * Initialize the MIME Multipart
	 */
	private void createMultipart() {
		mimeMultipart = new MimeMultipart();
	}
		
	/**
	 * Prepare the order mail's basic parts like session and body with relevant text
	 * @param Customer Object - for whom mail is being sent
	 * @param order Object - the intended order
	 * @throws AddressException 
	 * @throws MessagingException
	 */
	public void prepareOrderMail(Customer customerObject, Order orderObject) throws AddressException, MessagingException  {
		setUpSession();
		setOrderMailSubject(orderObject.getOrderID(), orderObject.getOrderDate());
		setUpMIMEMessage(customerObject.getCustomerEmail(), subject);
		createMultipart();
		setOrderMailText(orderObject.getOrderID(), orderObject.getOrderDate(), orderObject.getOrderTotalAmount(), orderObject.getOrderProductList());
		addMessageBodyPart();
	}

	/**
	 * Send the mail
	 * @throws MessagingException
	 */
	private void sendMail() throws MessagingException {
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
		addMessageBodyPartWithAttachment(fileFullPathname);
		mimeMultipart.addBodyPart(bodyPart);
		mimeMultipart.addBodyPart(bodyPartWithAttachment);
		sendMail();
	}	

	/**
	 * Set subject for order mail 
	 * @param order ID - respective order
	 * @param order Date - date when order was placed
	 */
	private void setOrderMailSubject(long orderID, Date orderDate) {
		subject = "Please find details for Order: " + orderID + ", placed on " + orderDate ;
	}

	/**
	 * Set text message part for order mail
	 * @param orderID - respective order
	 * @param orderDate - date when order was placed
	 * @param orderTotalAmount - total bill amount for the order
	 * @param listOfOrderProducts - all products in order in question
	 */
	private void setOrderMailText(long orderID, Date orderDate, long orderTotalAmount, List<OrderProduct> listOfOrderProducts) {
		text = "Order Summary for /n/nOrder ID: " + orderID + "/nOrder Date: " + orderDate + "/nOrder Amount: " + orderTotalAmount + "/n/n/n";
		
		text += "Order Product Details:/n/nProduct Name/tProduct Unit Cost/tProduct Quantity/tProduct Total Cost/n/n";
		
		for(OrderProduct product : listOfOrderProducts) {
			text += product.getOrderProductName() + "/t" + product.getOrderProductUnitCost() + "/t" + product.getOrderProductQuantity() + "/t" + product.getOrderProductTotalCost() + "/n";
		}
		
		text += "/n/n/nThank you and please visit again!!!"; 
	}	
	
	/**
	 * Set up the MIME message
	 * @param recepientToMail - recipient mail ID
	 * @param subject - subject for mail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	private void setUpMIMEMessage(String recipientToMail, String subject) throws AddressException, MessagingException{
		mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(from));  
		mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(recipientToMail));  
		mimeMessage.setSubject(subject);  
	}
	
	/**
	 * Set the session for sending mail 
	 */
	private void setUpSession() {
		properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.port", "465");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    
                  "jakarta.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    

        //get Session   
        session = Session.getDefaultInstance(properties,    
         new Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });
	}
	
}
