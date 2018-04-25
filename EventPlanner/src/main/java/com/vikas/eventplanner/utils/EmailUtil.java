package com.vikas.eventplanner.utils;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailUtil {

	public static boolean sendEmail(String from, String to, String msgBody, String title) {
		try {
			Email email = new SimpleEmail();
			email.setHostName("smtp.googlemail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("contactapplication2018@gmail.com", "springmvc"));
			email.setSSLOnConnect(true);
			email.setFrom("event-planner@msis.neu.edu"); // This user email does not
			// exist
			email.setSubject(title);
			email.setMsg(msgBody); // Retrieve email from the DAO and send this
			email.addTo(to);
			email.send();
			return true;
		} catch (EmailException e) {
			System.out.println("Email cannot be sent");
		}
		return false;
	}

}
