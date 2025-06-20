package org.ss.vendorapi.service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.modal.ClientInvoiceProjection;
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
    private JdbcTemplate jdbcTemplate;
    
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
	        
	        @Override
	        public Double getClientAmountExcluGstByProjectName(String projectName) {
	            return clientInvoiceMasterRepository.getClientAmountExcluGstByProjectName(projectName);
	        }

	        @Override
	        public Double getClientAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate) {
	            try {
	                Double sum = clientInvoiceMasterRepository.getClientAmountExcluGstByProjectNameAndDate(projectName, startDate, endDate);
	                return (sum != null) ? sum : 0.0;
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                return 0.0;
	            }
	        }

	        public List<ClientInvoiceMasterEntity> getInvoicesByProjectName(String projectName) {
	            return clientInvoiceMasterRepository.findByProjectName(projectName);
	        }

	        
	        
	        
//	        public List<ClientInvoiceProjection> getInvoicesByManagerId(Long managerId) {
//	            return clientInvoiceMasterRepository.findInvoicesByManagerId(managerId);
//	        }
	        
//	        @Override
//	        public List<ClientInvoiceProjection> getInvoicesByManagerId(Long managerId) {
//	            List<Object[]> resultList = clientInvoiceMasterRepository.findInvoicesByManagerId(managerId);
//	            
//	            List<ClientInvoiceProjection> dtoList = new ArrayList<>();
//
//	            for (Object[] row : resultList) {
//	            	ClientInvoiceProjection dto = new ClientInvoiceProjection();
//	                dto.setId((row[0].toString())); // id
//	                dto.setClientName((row[1].toString())); // client_name
//	                dto.setProjectName((row[2].toString())); // project_name
//	                dto.setInvoiceNo((row[3].toString())); // invoice_no
//	                dto.setInvoiceDescription((row[4].toString())); // invoice_description
//	                dto.setStatus((row[5].toString())); // status
//	                dto.setInvoiceDate((row[6].toString())); // invoice_date
//	                dto.setInvoiceAmountIncluGst(row[7] != null ? Double.parseDouble(row[7].toString()) : null); // invoice_amount_inclu_gst
//	                dtoList.add(dto);
//	            }
//
//	            return dtoList;
//	        }

	        
	        @Override
	        public List<ClientInvoiceProjection> getInvoicesByManagerId(Long managerId) {
	            List<Object[]> resultList = clientInvoiceMasterRepository.findInvoicesByManagerId(managerId);
	            
	            List<ClientInvoiceProjection> dtoList = new ArrayList<>();

	            for (Object[] row : resultList) {
	                ClientInvoiceProjection dto = new ClientInvoiceProjection();
	                dto.setId(toString(row[0]));
	                dto.setBalance(toString(row[6]));          
	                dto.setCgstOnTds(toString(row[7]));       
	                dto.setClientName(toString(row[8]));       
	                dto.setDiscom(toString(row[9]));           
	                dto.setGstBaseValue(toString(row[10]));      
	                dto.setGstOnPenalty(toString(row[11]));
	                dto.setInvoiceAmountIncluGst(toDouble(row[12]));
	                dto.setInvoiceBaseValue(toString(row[13]));
	                dto.setInvoiceDate(toString(row[14]));
	                dto.setInvoiceDescription(toString(row[15]));
	                dto.setInvoiceDueDate(toString(row[16]));
	                dto.setInvoiceInclusiveOfGst(toString(row[17]));
	                dto.setInvoiceNo(toString(row[18]));
	                dto.setPenalty(toString(row[19]));
	                dto.setPenaltyDeductionOnBase(toString(row[20]));
	                dto.setProjectName(toString(row[21]));
	                dto.setSgstOnTds(toString(row[22]));
	                dto.setStatus(toString(row[23]));
	                dto.setTdsBaseValue(toString(row[24]));
	                dto.setTotalPaymentReceived(toString(row[25]));
	                dto.setTotalPenaltyDeduction(toString(row[26]));
	                dto.setTotalTdsDeducted(toString(row[27]));
	                dto.setClientId(toString(row[28]));
	                dto.setCreditNote(toString(row[29]));
	                dto.setTdsPer(toString(row[30]));
	                dto.setTdsOnGst(toString(row[31]));
	                dto.setTotalCgst(toString(row[32]));
	                dto.setTotalSgst(toString(row[33]));               
	                dto.setTotalIgst(toString(row[34]));
	                dto.setAmountExcluGst(toString(row[35]));
	                dto.setBillableState(toString(row[36]));
	                dto.setInvoiceAmtIncluGst(toString(row[37]));
	                dto.setPaymentDate(toString(row[38]));
	                
	                dtoList.add(dto);
	            }

	            return dtoList;
	        }

	        // Helper methods
	        private String toString(Object obj) {
	            return obj != null ? obj.toString() : null;
	        }

	        private Double toDouble(Object obj) {
	            try {
	                return obj != null ? Double.parseDouble(obj.toString()) : null;
	            } catch (NumberFormatException e) {
	                return null;
	            }
	        }

	        
	        
	}



    
    




//	public List<ClientInvoiceMasterEntity> findAllWithDescriptionValues() {
//        try {
//            return clientMasterRepository.findAllWithDescriptionValues(); // Call the custom query in repository
//        } catch (Exception ex) {
//            throw new RuntimeException("Error while fetching client invoices: " + ex.getMessage());
//        }
//    }

    