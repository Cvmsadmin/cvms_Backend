package org.ss.vendorapi.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
//import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;
import org.ss.vendorapi.repository.ClientInvoiceMasterRepository;
import org.ss.vendorapi.repository.ClientMasterRepository;

@Service
public class ClientInvoiceMasterServiceImpl implements ClientInvoiceMasterService {
    
    @Autowired 
    private ClientInvoiceMasterRepository clientInvoiceMasterRepository;
    
    @Autowired
    private ClientMasterRepository clientMasterRepository;

    @Override
    public ClientInvoiceMasterEntity save(ClientInvoiceMasterEntity clientInvoiceMasterEntity) {
        clientInvoiceMasterEntity.setActive(1);
        clientInvoiceMasterEntity.setCreateDate(new java.sql.Date(new Date().getTime()));
        return clientInvoiceMasterRepository.save(clientInvoiceMasterEntity);
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
    
    
//    @Override
//	public List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName) {
//	    // Initialize an empty list to hold the authorized email addresses
//	    List<String> authorizedEmails = new ArrayList<>();
//
//	    try {
//	        // 1. Query the database for users with roles of Account Manager, Project Manager, and Management
//	        // 2. Based on the given clientName and projectName
//	        
//	        // Assuming you have a repository or service to query the roles
//	        List<org.apache.catalina.User> users = clientInvoiceMasterRepository.findUsersByclientNameAndprojectName(clientName, projectName, 
//	              Arrays.asList("Account Manager", "Project Manager", "Management"));
//
//
//	        // 3. Extract the emails of these users and add them to the authorizedEmails list
//	        for (User user : users) {
//	            authorizedEmails.add(((UserMasterEntity) user).getEmail());
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace(); // Handle exceptions if necessary (e.g., logging)
//	    }
//
//	    // Return the list of authorized emails
//	    return authorizedEmails;
//	}
    
//    @Override
//    public ClientMasterEntity findClientByClientName(String clientName) {
//        Optional<ClientMasterEntity> clientOptional = clientMasterRepository.findByClientName(clientName);
//        return clientOptional.orElse(null);  // Return null if no client is found
//    }
    
//    public ReceivableInvoiceStatsDTO getReceivableInvoiceStats() {
//        return clientMasterRepository.getReceivableInvoiceStats();
//    }
    

}
