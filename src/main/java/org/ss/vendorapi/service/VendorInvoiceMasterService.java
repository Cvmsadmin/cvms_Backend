package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;

public interface VendorInvoiceMasterService {
	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public List<VendorInvoiceMasterEntity> getAllVendorInvoices();
	public VendorInvoiceMasterEntity update(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public VendorInvoiceMasterEntity findById(Long id);
	public List<VendorInvoiceMasterEntity> findAll();
	public Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);
	
}
