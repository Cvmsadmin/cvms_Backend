package org.ss.vendorapi.Scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.repository.UserCreationRepository;
import org.ss.vendorapi.service.EmailService;

@Component
public class EmailScheduler {
	
	@Autowired
    private EmailService emailService;
	
	@Autowired 
	private UserCreationRepository creationUserRepository;

	
	@Scheduled(fixedRate = 30 * 1000) // Schedule every 1 minute
    public void sendNotificationsForUpcomingEvents() {
        // Fetch users who need to be notified
        List<UserMasterEntity> users = creationUserRepository.findAll(); // Modify with criteria if needed

        // Prepare and send emails
        for (UserMasterEntity user : users) {
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                String to = user.getEmail();
                String subject = "Upcoming Event Notification";
                String body = generateEmailBody(user.getFirstName(), user.getLastName(), "Project Name", "2024-12-01");

                try {
                    emailService.sendEmail(to, subject, body);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	 // Schedule the task every 15 days (fixedRate is in milliseconds)
//    @Scheduled(fixedRate = 15 * 24 * 60 * 60 * 1000) // 15 days
//    public void sendScheduledEmails() {
//        // Fetch recipient data dynamically (e.g., from a database)
//        String to = "example@domain.com"; // Replace with dynamic data
//        String subject = "Vendor Invoice Due Notification";
//        String body = generateEmailBody("John Doe", "Jane Smith", "Vendor ABC", "Project X", "2024-12-15");
//
//        try {
//            emailService.sendEmail(to, subject, body);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    // Method to generate email body with dynamic data
//    private String generateEmailBody(String projectManager, String accountManager, 
//                                     String vendorName, String projectName, String dueDate) {
//        return String.format(
//            "Dear %s & %s,\n\n" +
//            "The invoice for %s toward %s is due on %s.\n\n" +
//            "Thanks,\nCVMS Admin",
//            projectManager, accountManager, vendorName, projectName, dueDate
//        );
//    }


	
		


