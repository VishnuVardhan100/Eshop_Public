package com.eshop.eshopservice.communication;

import java.util.Properties;

import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Authenticator;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;

/**
 * Class to set up objects send different mails to Customer
 */

public class EshopMailService {

	/**
	 * Create a message body part for mail
	 * @return bodyPart object
	 * @throws MessagingException
	 */
	protected BodyPart createMessageBodyPart() throws MessagingException {
		BodyPart bodyPart = new MimeBodyPart();
		return bodyPart;
	}
	
	/**
	 * Create a message body part containing relevant files to be sent as attachment
	 * @param fileFullPathname - file's full path
	 * @return bodyPart object with attachment
	 * @throws MessagingException
	 */
	protected BodyPart createMessageBodyPartWithAttachment(String fileFullPathname) throws MessagingException {
		BodyPart bodyPartWithAttachment = new MimeBodyPart();
		DataSource dataSource = new FileDataSource(fileFullPathname);
		DataHandler dataHandler = new DataHandler(dataSource);
		bodyPartWithAttachment.setDataHandler(dataHandler);
		bodyPartWithAttachment.setFileName(fileFullPathname);
		return bodyPartWithAttachment;
	}

	/**
	 * Create the MIME message
	 * @param session - for session for which to set mime message to
	 * @param from - EShop sender mail ID
	 * @param recepientToMail - recipient mail ID
	 * @param subject - subject for mail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	protected MimeMessage createMIMEMessage(Session session, String from, String recipientToMail, String subject) throws AddressException, MessagingException{
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(from));  
		mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(recipientToMail));  
		mimeMessage.setSubject(subject);
		return mimeMessage;
	}

	/**
	 * Create the MIME message for many recipients
	 * @param session - for session for which to set mime message to
	 * @param from - EShop sender mail ID
	 * @param recepientMailList - list of recipient mail ID
	 * @param subject - subject for mail
	 * @throws AddressException
	 * @throws MessagingException
	 */
	protected MimeMessage createMIMEMessageMultiRecipient(Session session, String from, String[] recipientMailList, String subject) throws AddressException, MessagingException{
		MimeMessage mimeMessage = new MimeMessage(session);
		mimeMessage.setFrom(new InternetAddress(from));
		for(String recipient : recipientMailList) {
			mimeMessage.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
		}
		mimeMessage.setSubject(subject);
		return mimeMessage;
	}
	
	/**
	 * Get a MimeMultipart
	 * @return MimeMultipart object 
	 */
	protected MimeMultipart createMultipart() {
		MimeMultipart mimeMultipart = new MimeMultipart();
		return mimeMultipart;
	}	

	/**
	 * Create the session for sending mail
	 * @param Properties object for reading or setting properties
	 * @param from address for sending mail
	 * @param password for mail ID for authentication
	 * @return Session object
	 */
	protected Session createSession(Properties properties, String from, String password) {
		properties.put("mail.smtp.host", "smtp.gmail.com");    
        properties.put("mail.smtp.port", "465");    
        properties.put("mail.smtp.socketFactory.port", "465");    
        properties.put("mail.smtp.socketFactory.class",    
                  "jakarta.net.ssl.SSLSocketFactory");    
        properties.put("mail.smtp.auth", "true");    

        //get Session   
        Session session = Session.getDefaultInstance(properties,    
         new Authenticator() {    
         protected PasswordAuthentication getPasswordAuthentication() {    
         return new PasswordAuthentication(from,password);  
         }    
        });
        
        return session;
	}
	
}
