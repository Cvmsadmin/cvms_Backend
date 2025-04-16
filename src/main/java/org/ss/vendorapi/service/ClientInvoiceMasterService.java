package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
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
//	public void sendInvoiceEmail(ClientInvoiceMasterDTO clientInvoiceDTO);
//	public List<ClientInvoiceMasterEntity> findAllWithDescriptionValues();
//	public ClientInvoiceMasterEntity updateClientInvoice(ClientInvoiceMasterDTO clientInvoiceMasterDTO);
//	public void updateClientInvoice(Long clientId, ClientInvoiceMasterDTO dto);
//	public void sendInvoiceEmailWithAttachment(ClientInvoiceMasterDTO invoiceDTO, String fileName, byte[] fileBytes);
//	public ClientInvoiceMasterDTO convertToDto(ClientInvoiceMasterEntity invoice);
	public void sendInvoiceEmailWithAttachment(ClientInvoiceDetailsEntity invoiceDetails, String fileName,
			byte[] fileBytes);

	
//	List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName);

}
