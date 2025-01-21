package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.repository.ClientMasterRepository;

public interface VendorInvoiceMasterService {
	
	
	
	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public List<VendorInvoiceMasterEntity> getAllVendorInvoices();
	public VendorInvoiceMasterEntity update(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public VendorInvoiceMasterEntity findById(Long id);
	public List<VendorInvoiceMasterEntity> findAll();
	public Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);
	
	ClientMasterEntity findClientById(Long clientId);
	
}
