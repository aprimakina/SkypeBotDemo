package de.wwu.skype;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendHTMLEmail 
{

	public static void main(String subject, String chatId, String mailContent)
	{
		// Recipient's email ID needs to be mentioned.
		

		// Sender's email ID needs to be mentioned
		String from 			= "tech.suppot.skype.bot@gmail.com"; //set here email-addy from
		//String subject 			= "Chat history with customer " + customer; // here the subject of the email
		String content 			= mailContent + "<br><br>Best reagrds, <br> Your Skype Bot"; // here the content for the email
		
		final String to 		= Constants.EMAIL_TO_ADDRESS; //"iot@didatademo.local"
		final String username 	= "tech.suppot.skype.bot@gmail.com"; //"iot@didatademo.local"
		final String password 	= "TestTest"; //"iot";

		String host 			= "smtp.gmail.com"; //"10.132.140.221"

		Properties props 		= new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", "587"); //"25"

		// Get the Session object.
		Session session 		= Session.getInstance(props, new javax.mail.Authenticator() 
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(username, password);
			}
		});

		try 
		{
			// Create a default MimeMessage object.
			Message message 	= new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// Set Subject: header field
			message.setSubject(subject);

			// Send the actual HTML message, as big as you like
			message.setContent( content, "text/html");

			// Send message
			Transport.send(message);

			//System.out.println("Sent message successfully....");

		} 
		catch (MessagingException e) 
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
