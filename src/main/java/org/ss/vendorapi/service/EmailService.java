//package org.ss.vendorapi.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//public class EmailService {
//	
//	 @Autowired
//	    private JavaMailSender mailSender;
//
//	    public void sendEmail(String to, String subject, String text) {
//	        SimpleMailMessage message = new SimpleMailMessage();
//	        message.setFrom("CVMSADMIN@INFINITE.COM");
//	        message.setTo(to);
//	        message.setSubject(subject);
//	        message.setText(text);
//	        mailSender.send(message);
//	    }
//	
//	
//	