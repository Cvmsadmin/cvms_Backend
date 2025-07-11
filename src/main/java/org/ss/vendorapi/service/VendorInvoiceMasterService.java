package org.ss.vendorapi.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.VendorInvoiceProjection;
import org.ss.vendorapi.repository.ClientMasterRepository;

public interface VendorInvoiceMasterService {
	
	
	
	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public List<VendorInvoiceMasterEntity> getAllVendorInvoices();
	public VendorInvoiceMasterEntity update(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public VendorInvoiceMasterEntity findById(Long id);
	public List<VendorInvoiceMasterEntity> findAll();
	
	Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);
	ClientMasterEntity findClientById(Long clientId);
	
	Double getVendorAmountExcluGstByProjectName(String projectName);
	
	
	public Double getVendorAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate);
	public List<VendorInvoiceMasterEntity> findByProjectName(String projectName);
	public List<VendorInvoiceProjection> getVendorInvoicesByManagerId(Long managerId);

	
}
