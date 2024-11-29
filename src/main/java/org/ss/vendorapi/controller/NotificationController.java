package org.ss.vendorapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.service.EmailService;
import org.ss.vendorapi.repository.UserMasterRepository;
import org.ss.vendorapi.entity.UserMasterEntity;


@RestController
public class NotificationController {
	
	 @Autowired
	    private EmailService emailService;

	    @Autowired
	    private UserMasterRepository userMasterRepository;

	    @GetMapping("/api/send-notifications")
	    public String sendNotifications() {
	        List<UserMasterEntity> users = userMasterRepository.findAll();

	        for (UserMasterEntity user : users) {
	            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
	                String to = user.getEmail();
	                String subject = "Upcoming Event Notification";
	                String body = generateEmailBody(user.getFirstName(), user.getLastName(), "Project Name", "2024-12-01");

	                try {
	                    emailService.sendEmail(to, subject, body);
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    return "Failed to send some emails.";
	                }
	            }
	        }
	        return "Notifications sent successfully.";
	    }

	    private String generateEmailBody(String firstName, String lastName, String projectName, String dueDate) {
	        return String.format(
	            "Dear %s %s,\n\n" +
	            "This is a reminder that an event for %s is scheduled to occur on %s.\n\n" +
	            "Thanks,\nCVMS Admin",
	            firstName, lastName, projectName, dueDate
	        );
	    }

}
