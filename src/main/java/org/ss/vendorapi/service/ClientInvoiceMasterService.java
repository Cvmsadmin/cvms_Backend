package org.ss.vendorapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.modal.ClientInvoiceProjection;

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
	

	Double getClientAmountExcluGstByProjectName(String projectName);


	Double getClientAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate);
	public List<ClientInvoiceMasterEntity> getInvoicesByProjectName(String projectId);
	
	public List<ClientInvoiceProjection> getInvoicesByManagerId(Long managerId);

	
	
//	List<String> getAuthorizedEmailsForClientAndProject(String clientName, String projectName);
	


}
