package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;

public interface VendorInvoiceMasterService {
	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceMasterEntity);
	public List<VendorInvoiceMasterEntity> getAllVendorInvoices();
//	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceCreationEntityObj);

}
