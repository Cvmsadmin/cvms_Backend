package org.ss.vendorapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;

@Repository
public interface VendorInvoiceMasterRepository extends JpaRepository<VendorInvoiceMasterEntity, Long>{

	
	
	Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);
}
