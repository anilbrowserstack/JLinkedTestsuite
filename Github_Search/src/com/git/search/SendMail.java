package com.git.search;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	static void sendemail() throws IOException {
		final String username = "automationbs@gmail.com";
		final String password = "bsautomation";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("automationbs@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse("anil@browserstack.com"));
			message.setSubject("Github User stats:");
			BufferedReader br = new BufferedReader(new FileReader("github_userlinks.txt"));
			String s=null;
			String msg = null;
			while((s=br.readLine()) != null){
				msg+=s;
				msg+="\n";
			}
			message.setText(msg);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
