package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ss.vendorapi.entity.VendorInvoiceServiceTypeKey;
import org.ss.vendorapi.entity.VendorInvoiceServiceTypeMapping;

public interface VendorInvoiceServiceTypeMappingRepository extends JpaRepository<VendorInvoiceServiceTypeMapping, VendorInvoiceServiceTypeKey> {
	
	List<VendorInvoiceServiceTypeMapping> findByVendorInvoiceId(Long vendorInvoiceId);


}
