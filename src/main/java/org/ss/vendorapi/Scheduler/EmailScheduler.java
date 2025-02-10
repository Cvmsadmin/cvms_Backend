package org.ss.vendorapi.Scheduler;
 
import java.util.List;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.EmailNotificationView;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;
//import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;
import org.ss.vendorapi.repository.ClientInvoiceMasterRepository;
import org.ss.vendorapi.repository.ClientMasterRepository;
import org.ss.vendorapi.repository.EmailNotificationViewRepository;
import org.ss.vendorapi.repository.VendorInvoiceMasterRepository;
import org.ss.vendorapi.service.EmailService;
 
@Component
public class EmailScheduler {
 
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private VendorInvoiceMasterRepository vendorInvoiceMasterRepository;

    @Autowired
    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;
 
    @Autowired
    private EmailNotificationViewRepository emailNotificationViewRepository;
 
    //@Scheduled(cron = "*/10 * * * * *") // Runs every 10 seconds for testing
    //@Scheduled(cron = "0 0 9 */3 * *")// Runs every 3 days at 9 AM
    public void sendNotificationsForUpcomingEvents() {
        System.out.println("Email Scheduler Running...");
 
        // Fetch pending email notifications from the view
        List<EmailNotificationView> pendingNotifications = emailNotificationViewRepository.findPendingEmailRecipients();
 
        if (pendingNotifications.isEmpty()) {
            System.out.println("No pending email notifications.");
            return;
        }
 
        for (EmailNotificationView notification : pendingNotifications) {
            String to = notification.getEmail();
            String subject;
            String body;
 
            // Check if the current date is before or after the due date
            LocalDate dueDate = LocalDate.parse(notification.getT_date(), DateTimeFormatter.ofPattern("dd-MM-yy"));
            LocalDate currentDate = LocalDate.now();
 
            if (!currentDate.isAfter(dueDate)) {
                // Send email for due milestone payment
                subject = "Milestone Payment Due – " + notification.getClient_name() + " – " + notification.getMilestone() ;
                body = generateEmailBodyForDue(notification);
            } else {
                // Send email for overdue milestone payment
                subject = "Overdue Milestone Payment – " + notification.getClient_name()+ " – " + notification.getProject_name();
                body = generateEmailBodyForOverdue(notification);
            }
 
            try {
                emailService.sendEmail(to, subject, body);
                System.out.println("Email sent to: " + to);
            } catch (Exception e) {
                System.err.println("Error sending email to: " + to + " - " + e.getMessage());
            }
        }
    }
 
    private String generateEmailBodyForDue(EmailNotificationView notification) {
        return String.format(
            "Hi %s,\n\n" +
            "Please note that the following milestone payment is now due from %s:\n\n" +
            "• Project: %s\n" +
            "• Milestone: %s\n" +
            "• Amount (Incl. GST): ₹%s\n" +
            "• Milestone Duration: %s\n" +
            "• Start Date: %s\n" +
            "• Due Date: %s\n\n" +
            "Kindly proceed with the necessary steps to ensure payment is received as per the agreed terms.\n\n" +
            "Thanks,\nCVMS Admin",
            notification.getAccount_manager(),
            notification.getClient_name(),
            notification.getProject_name(),
            notification.getMilestone(),
            notification.getPending_payment_for_milestone(),
            notification.getMilestone_duration(),
            notification.getStart_date(),
            notification.getT_date()

        );
    }
 
    private String generateEmailBodyForOverdue(EmailNotificationView notification) {
        return String.format(
            "Hi %s,\n\n" +
            "Please note that the following milestone payment is now overdue from %s:\n\n" +
            "• Project: %s\n" +
            "• Milestone: %s\n" +
            "• Amount: ₹%s\n" +
            "• Due Date: %s\n\n" +
            "Kindly proceed with the necessary steps to ensure payment is received as per the agreed terms.\n\n" +
            "Thanks,\nCVMS Admin",
            notification.getAccount_manager(),
            notification.getClient_name(),
            notification.getProject_name(),
            notification.getMilestone(),
            notification.getPending_payment_for_milestone(),
            notification.getT_date()
        );
    }
    
    
//  ***************************************************  Pending Invoice Summary **************************************************************
    
    
       
//    @Scheduled(cron = "*/10 * * * * *") // Runs every 10 seconds for testing
//    public void sendInvoiceSummaryEmail() {
//        System.out.println("Email Scheduler Running...");
//
//        // Fetch pending payable and receivable invoice statistics
////        PayableInvoiceStatsDTO payableStats = vendorInvoiceMasterRepository.getPayableInvoiceStats();
//        PayableInvoiceStatsDTO payableStats = null;
////        ClientInvoiceMasterEntity receivableStats1 = clientInvoiceMasterRepository.getReceivableInvoiceStats();
//        ReceivableInvoiceStatsDTO receivableStats = null; 
//        // Format the invoice data
//        String payableInvoiceCount = formatNumber(payableStats.getPayableInvoiceCount());
//        String payableInvoiceAmount = formatCurrency(payableStats.getPayableInvoiceAmount());
//        String receivableInvoiceCount = formatNumber(receivableStats.getReceivableInvoiceCount());
//        String receivableInvoiceAmount = formatCurrency(receivableStats.getReceivableInvoiceAmount());
//
//        // Prepare the email body
//        String emailBody = generateInvoiceSummaryEmailBody(payableInvoiceCount, payableInvoiceAmount, receivableInvoiceCount, receivableInvoiceAmount);
//
//        // Send the email (You can specify the recipient email address here)
//        String subject = "Pending Invoice Summary";
//        String recipientEmail = "amit.rawat2@infinite.com"; // Set the recipient email
//
//        try {
//            emailService.sendEmail(recipientEmail, subject, emailBody);
//            System.out.println("Invoice summary email sent to: " + recipientEmail);
//        } catch (Exception e) {
//            System.err.println("Error sending email to: " + recipientEmail + " - " + e.getMessage());
//        }
//    }
    

//    private String generateInvoiceSummaryEmailBody(String payableInvoiceCount, String payableInvoiceAmount, 
//                                                   String receivableInvoiceCount, String receivableInvoiceAmount) {
//        return String.format(
//            "Dear Sir,\n\n" +
//            "Please find below the summary of both pending payable and receivable invoices:\n\n" +
//            "Pending Payable Invoice Summary\n" +
//            "Total Payable Invoices: %s\n" +
//            "Total Payable Amount (Incl. GST): %s\n\n" +
//            "Pending Receivable Invoice Summary\n" +
//            "Total Receivable Invoices: %s\n" +
//            "Total Receivable Amount (Incl. GST): %s\n\n" +
//            "Best Regards,\n" +
//            "CVMS Admins",
//            payableInvoiceCount, payableInvoiceAmount, receivableInvoiceCount, receivableInvoiceAmount
//        );
//    }

    // Helper method to format numbers (for count)
    private String formatNumber(Long number) {
        return NumberFormat.getInstance().format(number);
    }

    // Helper method to format currency
    private String formatCurrency(BigDecimal bigDecimal) {
        return NumberFormat.getCurrencyInstance(new Locale("en", "IN")).format(bigDecimal);
    }
       
    
}