//package org.ss.vendorapi.service;
//
//import java.math.BigDecimal;
//import java.text.NumberFormat;
//import java.util.List;
//import java.util.Locale;
//
//import javax.mail.MessagingException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
//import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
//import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
//import org.ss.vendorapi.repository.ClientInvoiceDetailsRepo;
//
//
//@Service
//public interface ClientInvoiceDetailsService {
//	
//	@Autowired
//	private ClientInvoiceDetailsRepo clientInvoiceDetailsRepo;
//	
//    @Autowired
//	private EmailService emailService;
//	
//	
//	public static void sendInvoiceEmailWithAttachment1(ClientInvoiceMasterDTO clientInvoiceDTO, String fileName, byte[] fileBytes) {
//        List<ClientInvoiceDetailsEntity> invoiceDetailsList = clientInvoiceDetailsRepo.getClientInvoiceDetails();
//
//        if (!invoiceDetailsList.isEmpty()) {
//            ClientInvoiceDetailsEntity invoiceDetails = invoiceDetailsList.get(0);
//
//            String formattedAmount = "N/A";
//            if (invoiceDetails.getInvoiceAmountIncluGst() != null) {
//                try {
//                    BigDecimal invoiceAmount = new BigDecimal(invoiceDetails.getInvoiceAmountIncluGst().toString());
//                    formattedAmount = formatCurrency(invoiceAmount);
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            String subject = "Invoice Details for " + invoiceDetails.getClientName();
//
//            String body = String.format(
//                "<html><body>" +
//                "<p>Dear All,</p>" +
//                "<p>Please find below the invoice details for <b>%s</b> related to the <b>%s</b>:</p>" +
//                "<table border='1' cellpadding='5' cellspacing='0'>" +
//                "<tr><th>Parameter</th><th>Details</th></tr>" +
//                "<tr><td><b>Client Name</b></td><td>%s</td></tr>" +
//                "<tr><td><b>Project Name</b></td><td>%s</td></tr>" +
//                "<tr><td><b>Invoice Date</b></td><td>%s</td></tr>" +
//                "<tr><td><b>Invoice No.</b></td><td>%s</td></tr>" +
//                "<tr><td><b>Invoice Due Date</b></td><td>%s</td></tr>" +
//                "<tr><td><b>Total Amount (Incl. GST)</b></td><td>%s</td></tr>" +
//                "</table>" +
//                "<p>Best regards,<br><b>CVMS Admin</b></p>" +
//                "</body></html>",
//                invoiceDetails.getClientName(),
//                invoiceDetails.getProjectName(),
//                invoiceDetails.getClientName(),
//                invoiceDetails.getProjectName(),
//                invoiceDetails.getInvoiceDate(),
//                invoiceDetails.getInvoiceNo(),
//                invoiceDetails.getInvoiceDueDate(),
//                formattedAmount
//            );
//
//            // Send the email with attachment
//            try {
//                emailService.sendEmailWithAttachment("debidatta.das@infinite.com", subject, body, fileName, fileBytes);
//            } catch (MessagingException | jakarta.mail.MessagingException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//        private static String formatCurrency(BigDecimal invoiceAmount) {
//      NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
//      return formatter.format(invoiceAmount);
//  }


//        public ClientInvoiceMasterDTO convertToDto(ClientInvoiceMasterEntity entity) {
//            ClientInvoiceMasterDTO dto = new ClientInvoiceMasterDTO();
//            dto.setClientName(entity.getClientName());
//            dto.setProjectName(entity.getProjectName());
//            dto.setInvoiceNo(entity.getInvoiceNo());
//            dto.setInvoiceDate(entity.getInvoiceDate());
//            dto.setInvoiceDueDate(entity.getInvoiceDueDate());
//            dto.setInvoiceAmountIncluGst(entity.getInvoiceAmountIncluGst());
//            return dto;
//        }

//}
