package com.eshop.eshopservice.communication;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.eshop.eshopmodel.customer.Customer;
import com.eshop.eshopmodel.logistics.Order;

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
 * Class to send different mails to Customer
 */

@Service
public class CustomerMailService {

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
		subject = mailSubjectHelper.setOrderMailSubject(orderObject.getOrderID(), orderObject.getOrderPlacedDate());
		setUpMIMEMessage(customerObject.getCustomerEmail(), subject);
		createMultipart();
		text = mailTextHelper.setOrderMailText(orderObject.getOrderID(), orderObject.getOrderPlacedDate(), orderObject.getOrderTotalAmount(), orderObject.getOrderProductList());
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
		addMessageBodyPart();
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
		addMessageBodyPart();
		addMessageBodyPartWithAttachment(fileFullPathname);
		mimeMultipart.addBodyPart(bodyPart);
		mimeMultipart.addBodyPart(bodyPartWithAttachment);
		sendMail();
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
