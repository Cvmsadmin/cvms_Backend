
package org.ss.vendorapi.service; 
import javax.mail.MessagingException;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.stereotype.Service;
import java.io.File;
import org.springframework.core.io.FileSystemResource;

 
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
        helper.setText(text, true);
        mailSender.send(message);
        System.out.println("HTML Email Sent Successfully!");

    }
    
    
//    public void sendEmailWithAttachment(String to, String subject, String body, String attachmentPath) throws MessagingException {
//        try {
//            jakarta.mail.internet.MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//            helper.setFrom("CVMSADMIN@INFINITE.COM");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(body, true);
//
//            if (attachmentPath != null && !attachmentPath.isEmpty()) {
//                File file = new File(attachmentPath);
//                if (file.exists()) {
//                    FileSystemResource fileResource = new FileSystemResource(file);
//                    helper.addAttachment(file.getName(), fileResource);
//                } else {
//                    System.err.println("Attachment file not found: " + attachmentPath);
//                }
//            }
//
//            mailSender.send(message); // ✅ Now using Jakarta Mail
//            System.out.println("Email with attachment sent successfully!");
//        } catch (jakarta.mail.MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }



}

 