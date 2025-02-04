package org.ss.vendorapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.Scheduler.EmailScheduler;
import org.ss.vendorapi.entity.EmailNotificationView;
import org.ss.vendorapi.repository.EmailNotificationViewRepository;

@RestController
@RequestMapping("v2/api")
public class EmailSchedulerTestController {
	
	 @Autowired
	    private EmailScheduler emailScheduler;
	 
	 @Autowired
	    private EmailNotificationViewRepository emailNotificationViewRepository;

	   @GetMapping("/sendEmailNotifications")
	    public ResponseEntity<String> testEmailScheduler() {
	        try {
	            emailScheduler.sendNotificationsForUpcomingEvents();
	            return ResponseEntity.ok("Email notifications triggered successfully!");
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body("Error triggering email notifications: " + e.getMessage());
	        }
	    }

	    @GetMapping("/emailNotifications")
	    public ResponseEntity<List<EmailNotificationView>> getEmailNotifications() {
	        List<EmailNotificationView> records = emailNotificationViewRepository.findAll();
	        return ResponseEntity.ok(records);
	    }
}
