//package org.ss.vendorapi.service;
//
//import javax.mail.MessagingException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//@Service
//public class EmailService {
//	
//	   @Autowired
//	    private JavaMailSender mailSender; 
//
//	    public void sendEmail(String to, String subject, String text) throws MessagingException { 
//	        SimpleMailMessage message = new SimpleMailMessage();
//	        message.setFrom("CVMSADMIN@INFINITE.COM");
//	        message.setTo(to);
//	        message.setSubject(subject);
//	        message.setText(text);
//	        mailSender.send(message);
//	    }
//	
//}


package org.ss.vendorapi.service;
 
import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
 
@Service

public class EmailService {
 
    @Autowired

    private JavaMailSender mailSender;
 
    public void sendEmail(String to, String subject, String text) throws MessagingException, jakarta.mail.MessagingException {

        jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
 
        helper.setFrom("CVMSADMIN@INFINITE.COM");

        helper.setTo(to);

        helper.setSubject(subject);

        helper.setText(text, true); // ✅ 'true' ensures HTML formatting
 
        mailSender.send(message);

        System.out.println("HTML Email Sent Successfully!");

    }

}

 