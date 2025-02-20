package org.ss.vendorapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.repository.ClientInvoiceDetailsRepo;
//import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;
import org.ss.vendorapi.repository.ClientInvoiceMasterRepository;
import org.ss.vendorapi.repository.ClientMasterRepository;

@Service
public class ClientInvoiceMasterServiceImpl implements ClientInvoiceMasterService {
    
    @Autowired 
    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;
    
    @Autowired
    private ClientMasterRepository clientMasterRepository;
    
    @Autowired
    private ClientInvoiceDetailsRepo clientInvoiceDetailsRepo;
    
    @Autowired
    private EmailService emailService;

    @Override
    public ClientInvoiceMasterEntity save(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
        clientInvoiceMasterEntity.setActive(1);
        clientInvoiceMasterEntity.setCreateDate(new java.sql.Date(new Date().getTime()));
        try {
        	return clientInvoiceMasterRepository.save(clientInvoiceMasterEntity);
        }catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
        
    }

    @Override
    public ClientInvoiceMasterEntity update(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
        clientInvoiceMasterEntity.setUpdateDate(new java.sql.Date(new Date().getTime()));
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

//    @Override
//    public ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo) {
//        return clientInvoiceMasterRepository.findByInvoiceNo(invoiceNo);
//    }
    
    @Override
  public ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo) {
      return clientInvoiceMasterRepository.findByInvoiceNoNative(invoiceNo);
  }

    @Override
    public ClientMasterEntity findClientByClientName(String clientName) {
        return clientMasterRepository.findByClientName(clientName); 
    }

    
    public void sendInvoiceEmail(ClientInvoiceMasterDTO clientInvoiceDTO) {
        // Get the invoice details from the repository (assuming you want the most recent details)
        List<ClientInvoiceDetailsEntity> invoiceDetailsList = clientInvoiceDetailsRepo.getClientInvoiceDetails();

        if (!invoiceDetailsList.isEmpty()) {
            ClientInvoiceDetailsEntity invoiceDetails = invoiceDetailsList.get(0); // Assuming you want the most recent invoice details

            // Prepare the subject
            String subject = "Invoice Details for " + invoiceDetails.getClientName();

            // Prepare the body with the desired HTML format
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
                    "<p>The invoice is attached for your reference.</p>" +
                    "<p>Best regards,<br><b>CVMS Admin</b></p>" +
                    "</body></html>",
                    invoiceDetails.getClientName(),
                    invoiceDetails.getProjectName(),
                    invoiceDetails.getClientName(),
                    invoiceDetails.getProjectName(),
                    invoiceDetails.getInvoiceDate(),
                    invoiceDetails.getInvoiceNo(),
                    invoiceDetails.getInvoiceDueDate(),
                    invoiceDetails.getInvoiceAmountIncluGst()
            );

            // Send the email (assuming the emailService.sendEmail method exists and is properly implemented)
            try {
                emailService.sendEmail(invoiceDetails.getAccountManagerEmail1(), subject, body);
            } catch (MessagingException | jakarta.mail.MessagingException e) {
                e.printStackTrace();
            }

            try {
                emailService.sendEmail(invoiceDetails.getPrjectManagerEmail(), subject, body);
            } catch (MessagingException | jakarta.mail.MessagingException e) {
                e.printStackTrace();
            }
        }
    }


    
//    public void sendInvoiceEmail(ClientInvoiceMasterDTO clientInvoiceDTO) {
//        // Get the invoice details from the view
//        List<ClientInvoiceDetailsEntity> invoiceDetailsList = clientInvoiceDetailsRepo.getClientInvoiceDetails();
//        
//        if (!invoiceDetailsList.isEmpty()) {
//            ClientInvoiceDetailsEntity invoiceDetails = invoiceDetailsList.get(0); // Assuming you want to send the most recent invoice details
//
//            // Prepare the email content
//            String subject = "Invoice Details for " + invoiceDetails.getClientName();
//            String body = "Dear All,\n\n" +
//                    "Please find below the invoice details for " + invoiceDetails.getClientName() + " related to the " + invoiceDetails.getProjectName() + ":\n\n" +
//                    "Invoice Details:\n" +
//                    " Client Name: " + invoiceDetails.getClientName() + "\n" +
//                    " Project Name: " + invoiceDetails.getProjectName() + "\n" +
//                    " Invoice Date: " + invoiceDetails.getInvoiceDate() + "\n" +
//                    " Invoice No.: " + invoiceDetails.getInvoiceNo() + "\n" +
//                    " Invoice Due Date: " + invoiceDetails.getInvoiceDueDate() + "\n" +
//                    " Total Amount (Incl. GST): " + invoiceDetails.getInvoiceAmountIncluGst() + "\n\n" +
//                    "The invoice is attached for your reference.\n\n" +
//                    "Best regards,\n" +
//                    "CVMS Admin";
//
//            // Send the email (assuming the emailService.sendEmail method exists and is properly implemented)
//            try {
//				emailService.sendEmail(invoiceDetails.getAccountManagerEmail1(), subject, body);
//			} catch (MessagingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (jakarta.mail.MessagingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//            try {
//				emailService.sendEmail(invoiceDetails.getPrjectManagerEmail(), subject, body);
//			} catch (MessagingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (jakarta.mail.MessagingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//    }

    

    

    }