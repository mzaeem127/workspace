package com.webmoli;

import java.util.Date;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

public class SendMail {

	public static void main(String[] args) throws Exception {
		Email email = new SimpleEmail();
		email.setHostName("smtp.csdcsystems.com");
		email.setFrom("venkat.sadasivam@aspiresys.com");
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-) \n" + new Date());
		email.addTo("venkatsalem@gmail.com");
		email.send();
	}

}
