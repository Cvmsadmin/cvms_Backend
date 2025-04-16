package org.ss.vendorapi.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.repository.ClientInvoiceDescriptionValueRepository;
import org.ss.vendorapi.repository.ClientInvoiceDetailsRepo;
//import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;
import org.ss.vendorapi.repository.ClientInvoiceMasterRepository;
import org.ss.vendorapi.repository.ClientMasterRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClientInvoiceMasterServiceImpl implements ClientInvoiceMasterService {
    
    @Autowired 
    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;
    
    @Autowired
    private ClientMasterRepository clientMasterRepository;
    
    @Autowired
    private ClientInvoiceDescriptionValueRepository clientInvoiceDescriptionValueRepository;
    
    @Autowired
    private ClientInvoiceDetailsRepo clientInvoiceDetailsRepo;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired 
    private SftpUploaderService sftpUploaderService;

    @Override
    public ClientInvoiceMasterEntity save(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
//        clientInvoiceMasterEntity.setActive(1);
//        clientInvoiceMasterEntity.setCreateDate(new java.sql.Date(new Date().getTime()));
        try {
//        	clientInvoiceMasterEntity.setId(Long.parseLong("1"));
//        	clientInvoiceMasterEntity.setClientId("12");
        	return clientInvoiceMasterRepository.save(clientInvoiceMasterEntity);
        }catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
        
    }

    @Override
    public ClientInvoiceMasterEntity update(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
//        clientInvoiceMasterEntity.setUpdateDate(new java.sql.Date(new Date().getTime()));
        return clientInvoiceMasterRepository.save(clientInvoiceMasterEntity);
    }

    @Override
    public List<ClientInvoiceMasterEntity> findAll() {
        return clientInvoiceMasterRepository.findAll();
    }

    @Override
    public ClientInvoiceMasterEntity findById(Long id) {
        return clientInvoiceMasterRepository.findById(id).orElse(null);
    }

    
    @Override
  public ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo) {
      return clientInvoiceMasterRepository.findByInvoiceNoNative(invoiceNo);
  }

    @Override
    public ClientMasterEntity findClientByClientName(String clientName) {
        return clientMasterRepository.findByClientName(clientName); 
    }

//    public void sendInvoiceEmail(ClientInvoiceMasterDTO clientInvoiceDTO) {
//        // Get the invoice details from the repository (assuming you want the most recent details)
//        List<ClientInvoiceDetailsEntity> invoiceDetailsList = clientInvoiceDetailsRepo.getClientInvoiceDetails();
//
//        if (!invoiceDetailsList.isEmpty()) {
//            ClientInvoiceDetailsEntity invoiceDetails = invoiceDetailsList.get(0); // Get the most recent invoice details
//
//            // Convert invoice amount to BigDecimal safely and format it properly
//            BigDecimal invoiceAmount = BigDecimal.ZERO;
//            String formattedAmount = "N/A"; // Default in case of errors
//
//            if (invoiceDetails.getInvoiceAmountIncluGst() != null) {
//                try {
//                    invoiceAmount = new BigDecimal(invoiceDetails.getInvoiceAmountIncluGst().toString());
//                    formattedAmount = formatCurrency(invoiceAmount); // Format the currency properly
//                } catch (NumberFormatException e) {
//                    e.printStackTrace(); // Log the error
//                }
//            }
//
//            // Prepare the email subject
//            String subject = "Invoice Details for " + invoiceDetails.getClientName();
//
//            // Prepare the HTML email body
//            String body = String.format(
//                    "<html><body>" +
//                    "<p>Dear All,</p>" +
//                    "<p>Please find below the invoice details for <b>%s</b> related to the <b>%s</b>:</p>" +
//                    "<table border='1' cellpadding='5' cellspacing='0'>" +
//                    "<tr><th>Parameter</th><th>Details</th></tr>" +
//                    "<tr><td><b>Client Name</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Project Name</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Invoice Date</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Invoice No.</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Invoice Due Date</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Total Amount (Incl. GST)</b></td><td>%s</td></tr>" +
//                    "</table>" +
////                    "<p>The invoice is attached for your reference.</p>" +
//                    "<p>Best regards,<br><b>CVMS Admin</b></p>" +
//                    "</body></html>",
//                    invoiceDetails.getClientName(),
//                    invoiceDetails.getProjectName(),
//                    invoiceDetails.getClientName(),
//                    invoiceDetails.getProjectName(),
//                    invoiceDetails.getInvoiceDate(),
//                    invoiceDetails.getInvoiceNo(),
//                    invoiceDetails.getInvoiceDueDate(),
//                    formattedAmount // Now properly formatted with ₹ and commas
//            );
//            
//            // Hardcoded email address for account manager
//            String hardcodedEmail = "debidatta.das@infinite.com";
//
//            // Send the email to account manager
//            
//            try {
//                emailService.sendEmail(hardcodedEmail, subject, body);
//            } catch (MessagingException | jakarta.mail.MessagingException e) {
//                e.printStackTrace();
//            }
//
//            // Also send the email to the same hardcoded email address instead of project manager email
////            try {
////                emailService.sendEmail(hardcodedEmail, subject, body);
////            } catch (MessagingException | jakarta.mail.MessagingException e) {
////                e.printStackTrace();
////            }
////            try {
////                emailService.sendEmail(invoiceDetails.getAccountManagerEmail1(), subject, body);
////            } catch (MessagingException | jakarta.mail.MessagingException e) {
////                e.printStackTrace();
////            }
////
////            // Send the email to project manager
////            try {
////                emailService.sendEmail(invoiceDetails.getPrjectManagerEmail(), subject, body);
////            } catch (MessagingException | jakarta.mail.MessagingException e) {
////                e.printStackTrace();
////            }
//        }
//    }
////
//	private String formatCurrency(BigDecimal invoiceAmount) {
//		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
//        return formatter.format(invoiceAmount);
//	}
    
    
//********************************************************************************************************************************************************
//        public void sendInvoiceEmail(ClientInvoiceMasterDTO clientInvoiceDTO) {
//            List<ClientInvoiceDetailsEntity> invoiceDetailsList = clientInvoiceDetailsRepo.getClientInvoiceDetails();
//
//            if (!invoiceDetailsList.isEmpty()) {
//                ClientInvoiceDetailsEntity invoiceDetails = invoiceDetailsList.get(0);
//
//                BigDecimal invoiceAmount = BigDecimal.ZERO;
//                String formattedAmount = "N/A";
//
//                if (invoiceDetails.getInvoiceAmountIncluGst() != null) {
//                    try {
//                        invoiceAmount = new BigDecimal(invoiceDetails.getInvoiceAmountIncluGst().toString());
//                        formattedAmount = formatCurrency(invoiceAmount);
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                String subject = "Invoice Details for " + invoiceDetails.getClientName();
//
//                String body = String.format(
//                    "<html><body>" +
//                    "<p>Dear All,</p>" +
//                    "<p>Please find below the invoice details for <b>%s</b> related to the <b>%s</b>:</p>" +
//                    "<table border='1' cellpadding='5' cellspacing='0'>" +
//                    "<tr><th>Parameter</th><th>Details</th></tr>" +
//                    "<tr><td><b>Client Name</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Project Name</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Invoice Date</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Invoice No.</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Invoice Due Date</b></td><td>%s</td></tr>" +
//                    "<tr><td><b>Total Amount (Incl. GST)</b></td><td>%s</td></tr>" +
//                    "</table>" +
//                    "<p>Best regards,<br><b>CVMS Admin</b></p>" +
//                    "</body></html>",
//                    invoiceDetails.getClientName(),
//                    invoiceDetails.getProjectName(),
//                    invoiceDetails.getClientName(),
//                    invoiceDetails.getProjectName(),
//                    invoiceDetails.getInvoiceDate(),
//                    invoiceDetails.getInvoiceNo(),
//                    invoiceDetails.getInvoiceDueDate(),
//                    formattedAmount
//                );
//
//                // Define the fileName (make sure it's initialized correctly)
//                String fileName = "Invoice_" + invoiceDetails.getInvoiceNo() + ".pdf";
//
//                // Build SFTP path
//                String sftpPath = "/opt/cvmsdocuments/client/" +
//                        invoiceDetails.getClientName() + "/" +
//                        invoiceDetails.getProjectName() + "/" + fileName;
//
//                try {
//                    byte[] fileBytes = sftpUploaderService.downloadFileFromServer(sftpPath);
//                    emailService.sendEmailWithAttachment("debidatta.das@infinite.com", subject, body, fileName, fileBytes);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        // Move this method outside sendInvoiceEmail
//        private String formatCurrency(BigDecimal invoiceAmount) {
//            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
//            return formatter.format(invoiceAmount);
//        }
//
    
//	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	
//    public void sendInvoiceEmailWithAttachment(ClientInvoiceMasterDTO clientInvoiceDTO, String fileName, byte[] fileBytes) {
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
//        private String formatCurrency(BigDecimal invoiceAmount) {
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
        
    public ClientInvoiceMasterEntity saveInvoice(ClientInvoiceMasterEntity invoice) {
        return clientInvoiceMasterRepository.save(invoice);
    }

    
    
    @Override
    public void sendInvoiceEmailWithAttachment(ClientInvoiceDetailsEntity invoiceDetails, String fileName, byte[] fileBytes) {
        String formattedAmount = "N/A";
        if (invoiceDetails.getInvoiceAmountIncluGst() != null) {
            try {
                BigDecimal invoiceAmount = new BigDecimal(invoiceDetails.getInvoiceAmountIncluGst().toString());
                formattedAmount = formatCurrency(invoiceAmount);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        String subject = "Invoice Details for " + invoiceDetails.getClientName();

        String body = String.format(
            "<html><body>" +
            "<p>Dear All,</p>" +
            "<p>Please find below the invoice details for <b>%s</b> related to the <b>%s</b>:</p>" +
            "<table border='1' cellpadding='5' cellspacing='0'>" +
            "<tr><th>Parameter</th><th>Details</th></tr>" +
            "<tr><td><b>Client Name</b></td><td>%s</td></tr>" +
            "<tr><td><b>Project Name</b></td><td>%s</td></tr>" +
            "<tr><td><b>Invoice Date</b></td><td>%s</td></tr>" +
            "<tr><td><b>Invoice No.</b></td><td>%s</td></tr>" +
            "<tr><td><b>Invoice Due Date</b></td><td>%s</td></tr>" +
            "<tr><td><b>Total Amount (Incl. GST)</b></td><td>%s</td></tr>" +
            "</table>" +
            "<p>Best regards,<br><b>CVMS Admin</b></p>" +
            "</body></html>",
            invoiceDetails.getClientName(),
            invoiceDetails.getProjectName(),
            invoiceDetails.getClientName(),
            invoiceDetails.getProjectName(),
            invoiceDetails.getInvoiceDate(),
            invoiceDetails.getInvoiceNo(),
            invoiceDetails.getInvoiceDueDate(),
            formattedAmount
        );

        try {
            emailService.sendEmailWithAttachment("Amit.Rawat2@infinite.com", subject, body, fileName, fileBytes);
        } catch (MessagingException | jakarta.mail.MessagingException e) {
            e.printStackTrace();
        }
    }

	        private String formatCurrency(BigDecimal invoiceAmount) {
	      NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
	      return formatter.format(invoiceAmount);
	  }

		
	}



    
    




//	public List<ClientInvoiceMasterEntity> findAllWithDescriptionValues() {
//        try {
//            return clientMasterRepository.findAllWithDescriptionValues(); // Call the custom query in repository
//        } catch (Exception ex) {
//            throw new RuntimeException("Error while fetching client invoices: " + ex.getMessage());
//        }
//    }

    