package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;

public interface ClientInvoiceMasterService {

	
	public ClientInvoiceMasterEntity save(ClientInvoiceMasterEntity clientInvoiceMasterEntity);
	public ClientInvoiceMasterEntity update(ClientInvoiceMasterEntity clientInvoiceMasterEntity);
	public List<ClientInvoiceMasterEntity> findAll();
	public ClientInvoiceMasterEntity findById(Long id);
	public ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo);
//	public Optional findById(String clientName);	
	public ClientMasterEntity findClientByClientName(String clientName);
//	public List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName);
	public void sendInvoiceEmail(ClientInvoiceMasterDTO clientInvoiceDTO);
//	public List<ClientInvoiceMasterEntity> findAllWithDescriptionValues();
	
//	List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName);

}
