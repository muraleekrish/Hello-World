/*
 * Created on Feb 2, 2005
 *
 * ClassName	:  	Mail.java
 *
 * Copyright (c) 2005 Savant Technologies Pvt Ltd. India. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Savant Technologies Pvt Ltd. India.
 * (Confidential Information).  You shall not disclose or use Confidential
 * Information without the express written agreement of Savant Technologies Pvt Ltd. India 
 * 
 */
package com.savantit.prodacs.infra.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;


/**
 * @author spalanisamy
 *
 */
public class Mail implements TransportListener
{
    public static final int SUCCESSFULLYSENT = 1;
    public static final int NOTSENT = 2;
    public static final int PARTIALLYSENT = 3;
	private Message message;
	private Session session;
	private Multipart multipart;
	private BodyPart messageBodyPart;
	private Properties props;
	private int deliveryStatus=0;
	private Vector responseVec = new Vector(); 
	private InternetAddress addresses[] = new InternetAddress[0];
	private Vector addressVec = new Vector();
	private static Logger logger = Logger.getLogger(Mail.class);
	/**
	 * This method constructs an empty email (With no Email Ids, Subject, MessageBody etc.) 
	 * @throws IOException
	 */	
    public Mail() throws Exception
    {
        this.init();
    }
	/**
	 * This method constructs an email with the given parameters set
	 * @param from 
	 * @param to
	 * @param subject 
	 * @param bodyText - Body text of the mail 
	 * @throws Exception
	 */	
	public Mail(String from, String to, String subject, String bodyText) throws Exception
	{
		this.init();
		this.setFrom(from);
		this.addRecipient(to);
		this.setSubject(subject);
		this.setBodyText(bodyText);		
	}
	
	public void setFrom(String from) throws Exception
	{
		if(this.isValid(from))
			this.message.setFrom(new InternetAddress(from));	
	}
	
	public void addRecipient(String to) throws Exception
	{
		if(this.isValid(to))
		{
			if(BuildConfig.DMODE)
			    System.out.println("addRecipient called");
			this.message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		}
	}
	
	public void addRecipients(String[] emailIds, String recipientType) throws Exception
	{
	    if(emailIds!=null)
	    {
			for(int index=0, n=emailIds.length; index < n; index++)
			{
				String id = emailIds[index];
				if(this.isValid(id)) 
				{
					addressVec.add(new InternetAddress(id));
					if(recipientType.equalsIgnoreCase("to"))
					{				
						this.message.addRecipient(Message.RecipientType.TO, new InternetAddress(id));
					}
					else if(recipientType.equalsIgnoreCase("cc"))
					{
						this.message.addRecipient(Message.RecipientType.CC, new InternetAddress(id));
					}
					else if(recipientType.equalsIgnoreCase("bcc"))
					{
						this.message.addRecipient(Message.RecipientType.BCC, new InternetAddress(id));
					}
					else 
						throw new Exception("Attempt to resolve unknown RecipientType: " + recipientType);
				}				
			}		
	    }
	}
	
	public void setSubject(String subject) throws Exception
	{
		subject = subject.replaceAll("\n"," ");
		this.message.setSubject(subject);
	}
	
	public void setBodyText(String bodyText) throws Exception
	{
		this.messageBodyPart.setText(bodyText);
		this.multipart.addBodyPart(this.messageBodyPart);
	}
	
	public void setHtmlBodyText(String bodyText) throws Exception
	{
		this.messageBodyPart.setContent(bodyText, "text/html");		
		this.multipart.addBodyPart(this.messageBodyPart);
	}
	
	/**
	 * This method sets the Email's address fields (from & to) and sends it along with an attachment
	 * @param from
	 * @param to
	 * @param subject
	 * @param bodyText - Body text of the mail
	 * @param bytes - byte[] of the data to be transmitted
	 * @param filename - File name with Extension
	 * @return boolean status of the operation performed
	 */	
	public boolean sendAttachment(String from, String to, String subject, String bodyText,  byte[] bytes , String filename)
	{
		try
		{
			this.setFrom(from);
			this.addRecipient(to);			
			this.setSubject(subject);
			this.setBodyText(bodyText);			
			// Part two is attachment
			
//			this.addAttachment(bytes, filename);
			
			return this.send();
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}		
		return false;
	}
	
	/**
	 * This method sets the Email's address fields (from & to) and sends it along with multiple attachments
	 * @param from
	 * @param to
	 * @param copyTo
	 * @param blackCarbonCopyTo
	 * @param subject
	 * @param bodyText - Body text of the mail
	 * @param files - a collection of pair of key:Filename & value:Data - ByteArrayInputStream
	 * @return boolean status of the operation performed
	 */
	public boolean sendMultipleAttachments(String from, String to, String subject, String bodyText,  Hashtable files)
	{
		try
		{
			this.setFrom(from);
			this.addRecipient(to);
			this.setSubject(subject);
			
			this.setBodyText(bodyText);
			
			// Remaining Parts are attachments
			
			Enumeration enumer = files.keys();
			
			byte[] bytes;
			String filename; 
			
			while(enumer.hasMoreElements())
			{
				filename = (String)enumer.nextElement();
				ByteArrayInputStream bis = (ByteArrayInputStream)(files.get(filename));
				bytes = new byte[bis.available()];
				bis.read(bytes);
				
//				this.addAttachment(bytes, filename);
			}
			return this.send();			
		}
		catch(Exception e)
		{
			e.printStackTrace();			
		}
		
		return false;
	}
	/**
	 *  This method sets the cc and bcc fields of the Email
	 * @param cc
	 * @param bcc
	 * @return boolean status of the operation performed
	 */	
	public boolean setCarbonCopies(String[] cc, String[] bcc)
	{
		try
		{
			if(cc != null)				
				for(int index=0, n=cc.length; index < n; index++)
				{
					String id = cc[index];
					if(id.lastIndexOf(".") > id.indexOf("@")) 
					{
						message.addRecipient(Message.RecipientType.CC, new InternetAddress(id));					
					}				
				}		
			
			if(! bcc.equals(null))
				for(int index=0, n=bcc.length; index < n; index++)
				{
					String id = bcc[index];
					if(id.lastIndexOf(".") > id.indexOf("@")) 
					{
						message.addRecipient(Message.RecipientType.BCC, new InternetAddress(id));					
					}				
				}
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;	
	}
	/**
	 * This method attaches the given data to the Email
	 * @param bytes - byte[] of the data to be transmitted
	 * @param filename - File name with Extension
	 * @return boolean status of the operation performed
	 */	
	public boolean addAttachment(byte[] bytes,String filename)
	{
		try
		{
			// Create second body part
			BodyPart attachment = new MimeBodyPart();
			
			// Get the attachment
			DataSource source = new ByteDataSource(bytes);
			
			// Set the data handler to the attachment
			attachment.setDataHandler(new DataHandler(source));
			
			// Set the filename
			attachment.setFileName(filename);
			
			// Add part two
			this.multipart.addBodyPart(attachment);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * This method does the actual transmission of the Email
	 * @return boolean status of the operation performed
	 * @throws MessagingException
	 */	
	public boolean send() throws MessagingException
	{
		// Put parts in message
		this.message.setContent(this.multipart);
		
		// Send the message
		//Transport.send(this.message);
		//TODO - revisit the code for sending message
		//URLName urlName = new URLName("SMTP","smtp.mail.yahoo.co.uk",25,null,"senthilp007","savant123");
		//SMTPTransport transport = new SMTPTransport(session,urlName);
		
		Transport transport = null;
		try
		{
		    transport = this.session.getTransport("smtp");
		}
		catch(Exception e)
		{
		    if(BuildConfig.DMODE)
		    {
		        e.printStackTrace();
		    }
		    if(BuildConfig.USE_ERROR_LOGGER)
		        logger.error("Error in Mail.send()", e);
		}
		transport.addTransportListener(this);
		//transport.connect("smtp.mail.yahoo.co.uk","senthilp007","savant123");
		String smtpHostServer = props.getProperty("mail.host");
		String userId = props.getProperty("mail.user");
		String userPassword = props.getProperty("mail.password");
		transport.connect(smtpHostServer,userId,userPassword);
		addresses = new InternetAddress[addressVec.size()];
		addressVec.copyInto(addresses);
		message.setContent(multipart);
		this.message.saveChanges();
		transport.sendMessage(this.message,addresses);
		return true;
	}	
	
	/**
	 *  This method creates a new Email with no contents
	 * @throws IOException
	 */	
	private void init() throws Exception
	{
		this.props = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("ApplicationResources.properties");
		this.props.load(is);
		
		// Get session
		Session session = Session.getDefaultInstance(this.props, new SMTPAuthenticator());
		
		this.session = session;
		// Define message
		this.message = new MimeMessage(session);
		// Create a Multipart
		this.multipart = new MimeMultipart();
		// Create the message part 
		this.messageBodyPart = new MimeBodyPart();
		
		this.message.setFrom(new InternetAddress(this.props.getProperty("defaultSender")));
		this.message.setSubject(this.props.getProperty("defaultSubject"));
		if(BuildConfig.DMODE)
		    System.out.println("init() called");
	}
	
	private boolean isValid(String id)
	{
		return (id.indexOf("@")!=-1);		
	}
    /* (non-Javadoc)
     * @see javax.mail.event.TransportListener#messageDelivered(javax.mail.event.TransportEvent)
     */
    public void messageDelivered(TransportEvent transportEvent)
    {
        deliveryStatus = SUCCESSFULLYSENT;
        if(BuildConfig.DMODE)
            System.out.println("message Delivered");
    }
    /* (non-Javadoc)
     * @see javax.mail.event.TransportListener#messageNotDelivered(javax.mail.event.TransportEvent)
     */
    public void messageNotDelivered(TransportEvent transportEvent)
    {
        deliveryStatus = NOTSENT;
        Address validUnsentAddress[] = transportEvent.getValidUnsentAddresses();
        Address inValidAddress[] = transportEvent.getInvalidAddresses(); 
        // TODO Auto-generated method stub
        
    }
    /* (non-Javadoc)
     * @see javax.mail.event.TransportListener#messagePartiallyDelivered(javax.mail.event.TransportEvent)
     */
    public void messagePartiallyDelivered(TransportEvent transportEvent)
    {
        deliveryStatus = PARTIALLYSENT; 
        Address sentAddress[] = transportEvent.getValidSentAddresses();
        Address validUnsentAddress[] = transportEvent.getValidUnsentAddresses();
        Address invalidAddress[] = transportEvent.getInvalidAddresses(); 
        
        
    }

    /**
     * @return Returns the deliveryStatus.
     */
    public int getDeliveryStatus()
    {
        return deliveryStatus;
    }
    /**
     * @param deliveryStatus The deliveryStatus to set.
     */
    public void setDeliveryStatus(int deliveryStatus)
    {
        this.deliveryStatus = deliveryStatus;
    }
    /**
     * @param multiPart2
     */
    public void setMultiPartContent(MimeMultipart multiPart)
    {
        this.multipart = multiPart;
    }
    /**
     * @return Returns the response.
     */
    
    public static void main(String args[]) throws Exception
    {
        String str[] = {"vkrishnamoorthy@savant.in"};
        Mail mail = new Mail();
        mail.addRecipients(str,"to");
        mail.setFrom("vkrishnamoorthy@savant.in");
        mail.setSubject("Test Mail from yahoo");
        mail.setBodyText("Test");
        mail.send();
     }

    private class SMTPAuthenticator extends Authenticator 
    {
        protected PasswordAuthentication getPasswordAuthentication()
        {
            return new PasswordAuthentication ("savantdatrics", "savant123");
        }
    }

}


/*
*$Log: Mail.java,v $
*Revision 1.4  2005/12/28 05:30:57  kduraisamy
*setHtmlBodyText() added.
*
*Revision 1.3  2005/12/05 13:17:01  vkrishnamoorthy
*Modified for sending mails with authentication.
*
*Revision 1.11  2005/08/05 12:09:13  pkrishnan
*smtphost, user, and password are retrived from properties file
*
*Revision 1.10  2005/03/30 00:51:15  spalanisamy
*SMTP Authentication enabled and smtp.mail.yahoo.co.uk is used as a email gateway
*
*Revision 1.9  2005/03/29 20:22:52  spalanisamy
*included few changes for sending the email
*
*Revision 1.8  2005/03/19 08:47:52  spalanisamy
*called connect() method in transport
*
*Revision 1.7  2005/03/18 14:47:42  spalanisamy
**** empty log message ***
*
*Revision 1.6  2005/03/18 14:47:25  spalanisamy
*Commiting the Mail Handler with delivery status capabilities
*
*Revision 1.5  2005/03/18 11:20:42  spalanisamy
*Included EMail status storage routines
*
*Revision 1.4  2005/03/16 11:43:20  spalanisamy
*Included support for sending multipart contents
*
*Revision 1.3  2005/02/10 19:11:08  spalanisamy
*Object reference enum has been changed to enumer, hence this 'enum' has been introoduced as a keyword in java release 1.5
*
*Revision 1.2  2005/02/02 05:25:30  spalanisamy
*Mail Properties have been updated
*
*Revision 1.1  2005/02/02 01:27:29  spalanisamy
*EMail Handler included
*
*
*/