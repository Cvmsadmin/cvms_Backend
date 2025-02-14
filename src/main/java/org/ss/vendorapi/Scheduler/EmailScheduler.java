package org.ss.vendorapi.Scheduler;
 
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ss.vendorapi.entity.EmailNotificationView;
import org.ss.vendorapi.entity.InvoiceSummaryView;
import org.ss.vendorapi.entity.RecivableInvoicesView;
import org.ss.vendorapi.repository.EmailNotificationViewRepository;
import org.ss.vendorapi.repository.InvoiceSummaryViewRepository;
import org.ss.vendorapi.repository.RecivableInvoicesViewRepository;
import org.ss.vendorapi.service.EmailService;
     
    @Component
    public class EmailScheduler {
        @Autowired
        private EmailService emailService;
        
        @Autowired
        private EmailNotificationViewRepository emailNotificationViewRepository;
        
        @Autowired
        private InvoiceSummaryViewRepository invoiceSummaryViewRepository;
        
        @Autowired
        private RecivableInvoicesViewRepository recivableInvoicesViewRepository;
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
     
//        @Scheduled(cron = "*/10 * * * * *") // Runs every 10 seconds for testing
        public void sendNotificationsForUpcomingEvents() {
            System.out.println("Email Scheduler Running...");
            List<EmailNotificationView> pendingNotifications = emailNotificationViewRepository.findPendingEmailRecipients();
            if (pendingNotifications.isEmpty()) {
                System.out.println("No pending email notifications.");
                return;
            }
            for (EmailNotificationView notification : pendingNotifications) {
                String to = notification.getEmail();
                String subject;
                String body;
     
                LocalDate dueDate = LocalDate.parse(notification.getT_date(), DateTimeFormatter.ofPattern("dd-MM-yy"));
                String formattedDueDate = dueDate.format(DATE_FORMATTER);
                LocalDate currentDate = LocalDate.now();
     
                if (!currentDate.isAfter(dueDate)) {
                    subject = "Milestone Payment Due – " + notification.getClient_name() + " – " + notification.getMilestone();
                    body = generateEmailBodyForDue(notification, formattedDueDate);
                } else {
                    subject = "Overdue Milestone Payment – " + notification.getClient_name() + " – " + notification.getMilestone();
                    body = generateEmailBodyForOverdue(notification, formattedDueDate);
                }
     
                try {
                    emailService.sendEmail(to, subject, body);
                    System.out.println("Email sent to: " + to);
                } catch (Exception e) {
                    System.err.println("Error sending email to: " + to + " - " + e.getMessage());
                }
            }
        }
     
        private String generateEmailBodyForDue(EmailNotificationView notification, String formattedDueDate) {
            String formattedAmount = formatCurrency(new BigDecimal(notification.getPending_payment_for_milestone()));
     
            return String.format(
                "<html><body>" +
                "<p>Hi %s,</p>" +
                "<p>Please note that the following milestone payment is now due from <b>%s</b>:</p>" +
                "<ul>" +
                "<li>Project: <b>%s</b></li>" +
                "<li>Milestone: <b>%s</b></li>" +
                "<li>Amount (Incl. GST): <b>%s</b></li>" +
                "<li>Milestone Duration: <b>%s</b></li>" +
                "<li>Start Date: <b>%s</b></li>" +
                "<li>Due Date: <b>%s</b></li>" +
                "</ul>" +
                "<p>Kindly proceed with the necessary steps to ensure payment is received as per the agreed terms.</p>" +
                "<p>Thanks,<br><b>CVMS Admin</b></p>" +
                "</body></html>",
                notification.getAccount_manager(),
                notification.getClient_name(),
                notification.getProject_name(),
                notification.getMilestone(),
                formattedAmount,
                notification.getMilestone_duration(),
                LocalDate.parse(notification.getStart_date(), DateTimeFormatter.ofPattern("dd-MM-yy")).format(DATE_FORMATTER),
                formattedDueDate
            );
        }
     
        private String generateEmailBodyForOverdue(EmailNotificationView notification, String formattedDueDate) {
            String formattedAmount = formatCurrency(new BigDecimal(notification.getPending_payment_for_milestone()));
     
            return String.format(
                "<html><body>" +
                "<p>Hi %s,</p>" +
                "<p>Please note that the following milestone payment is now <b>overdue</b> from <b>%s</b>:</p>" +
                "<ul>" +
                "<li>Project: <b>%s</b></li>" +
                "<li>Milestone: <b>%s</b></li>" +
                "<li>Amount (Incl. GST): <b>%s</b></li>" +
                "<li>Milestone Duration: <b>%s</b></li>" +
                "<li>Start Date: <b>%s</b></li>" +
                "<li>Due Date: <b>%s</b></li>" +
                "<li>Status: <b>%s</b></li>" +
                "</ul>" +
                "<p>Kindly proceed with the necessary steps to ensure payment is received as per the agreed terms.</p>" +
                "<p>Thanks,<br><b>CVMS Admin</b></p>" +
                "</body></html>",
                notification.getAccount_manager(),
                notification.getClient_name(),
                notification.getProject_name(),
                notification.getMilestone(),
                formattedAmount,
                notification.getMilestone_duration(),
                LocalDate.parse(notification.getStart_date(), DateTimeFormatter.ofPattern("dd-MM-yy")).format(DATE_FORMATTER),
                formattedDueDate,
                notification.getStatus()
            );
        }
        
    // **************************************************************************************************************************************************************
        
//        @Scheduled(cron = "*/10 * * * * *") // Runs every 10 seconds for testing 
        public void sendInvoiceSummaryEmail() {
            System.out.println("Invoice Summary Scheduler Running...");

            // Fetch invoice summary data based on aging_bucket (directly from the entity)
            List<InvoiceSummaryView> invoiceSummaries = invoiceSummaryViewRepository.findAll(); 
            if (invoiceSummaries.isEmpty()) {
                System.out.println("No invoice summary data found.");
                return;
            }

            // Hardcoded email recipients
            String[] recipients = {"debidatta.das@infinite.com", "amit.rawat2@infinite.com"};

            // Build the email body with all the summaries
            String subject = "Pending Invoice Summary – Payable";
            String body = generateInvoiceSummaryBody(invoiceSummaries);

            // Send the email to each recipient
            for (String to : recipients) {
                try {
                    emailService.sendEmail(to, subject, body);
                    System.out.println("Invoice summary email sent to: " + to);
                } catch (Exception e) {
                    System.err.println("Error sending email to: " + to + " - " + e.getMessage());
                }
            }
        }

        private String generateInvoiceSummaryBody(List<InvoiceSummaryView> invoiceSummaries) {
            // Start the HTML content
            StringBuilder tableContent = new StringBuilder();
            tableContent.append("<html><body style='text-align: left;'>");
            tableContent.append("<p>Dear Sir,</p>");
            tableContent.append("<p>Please find below the summary of pending payable invoices:</p>");
            tableContent.append("<table border='1' cellpadding='10' cellspacing='0' style='margin-left: 0; margin-right: auto; border-collapse: collapse;'>");
            tableContent.append("<tr>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Aging</th>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Total Payable Invoices</th>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Total Payable Amount (Incl. GST)</th>");
            tableContent.append("</tr>");
            
            // Iterate over each invoice summary and append it to the table
            for (InvoiceSummaryView summary : invoiceSummaries) {
                String agingBucket = summary.getAging_bucket();
                
                if (agingBucket != null && !agingBucket.isEmpty()) {
                    tableContent.append("<tr>");
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(agingBucket).append("</td>");
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(summary.getReceivable_invoice_count()).append("</td>");
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(formatCurrency(summary.getReceivable_invoice_amount())).append("</td>");
                    tableContent.append("</tr>");
                } else {
                    System.err.println("Missing aging_bucket for invoice summary ID: " + summary.getId());
                }
            }
            
            // End the table and the email body
            tableContent.append("</table>");
            tableContent.append("<p>Best regards,<br><b>CVMS Admin</b></p>");
            tableContent.append("</body></html>");
            
            return tableContent.toString();
        }
        
//  ****************************************************************************************************************************************************************************************      
        
//        @Scheduled(cron = "*/10 * * * * *") // Runs every 10 seconds for testing
        public void sendrecivableSummaryEmail() {
            System.out.println("Invoice Summary Scheduler Running...");

            // Fetch invoice data from RecivableInvoicesView
            List<RecivableInvoicesView> receivables = recivableInvoicesViewRepository.findAll();
            if (receivables.isEmpty()) {
                System.out.println("No receivable invoices found.");
                return;
            }

            // Hardcoded email recipients
            String[] recipients = {"debidatta.das@infinite.com", "amit.rawat2@infinite.com"};

            // Build the email body with all the receivables
            String subject = "Pending Invoice Summary – Receivables";
            String body = generaterecivableSummaryBody(receivables);

            // Send the email to each recipient
            for (String to : recipients) {
                try {
                    emailService.sendEmail(to, subject, body);
                    System.out.println("Invoice summary email sent to: " + to);
                } catch (Exception e) {
                    System.err.println("Error sending email to: " + to + " - " + e.getMessage());
                }
            }
        }

        private String generaterecivableSummaryBody(List<RecivableInvoicesView> receivables) {
            // Start the HTML content
            StringBuilder tableContent = new StringBuilder();
            tableContent.append("<html><body style='text-align: left;'>");
            tableContent.append("<p>Dear Sir,</p>");
            tableContent.append("<p>Please find below the summary of pending receivable invoices:</p>");
            tableContent.append("<table border='1' cellpadding='10' cellspacing='0' style='margin-left: 0; margin-right: auto; border-collapse: collapse;'>");
            tableContent.append("<tr>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Client Name</th>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Project Name</th>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Milestone</th>");
            tableContent.append("<th style='text-align: center; padding: 10px;'>Total Receivable Amount (Incl. GST)</th>");
            tableContent.append("</tr>");
            
            // Iterate over each receivable and append it to the table
            for (RecivableInvoicesView receivable : receivables) {
                String clientName = receivable.getClient_name();
                String projectName = receivable.getProject_name();
                String milestone = receivable.getMilestone();
                int invoiceInclusiveOfGst = receivable.getInvoice_inclusive_of_gst();

                if (clientName != null && !clientName.isEmpty() && projectName != null && !projectName.isEmpty()) {
                    tableContent.append("<tr>");
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(clientName).append("</td>");
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(projectName).append("</td>");
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(milestone).append("</td>");
                    // Format the currency for the amount
                    tableContent.append("<td style='text-align: center; padding: 10px;'>").append(formatCurrency(new BigDecimal(invoiceInclusiveOfGst))).append("</td>");
                    tableContent.append("</tr>");
                } else {
                    System.err.println("Missing client or project name for receivable ID: " + receivable.getId());
                }
            }
            
            // End the table and the email body
            tableContent.append("</table>");
            tableContent.append("<p>Best regards,<br><b>CVMS Admin</b></p>");
            tableContent.append("</body></html>");
            
            return tableContent.toString();
        }

        private String formatCurrency(BigDecimal amount) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            return formatter.format(amount);
        }
}