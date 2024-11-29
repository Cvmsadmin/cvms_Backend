package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;

@Repository
public interface ClientInvoiceMasterRepository extends JpaRepository<ClientInvoiceMasterEntity, Long>{

//	ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo);
	
	@Query(value = "SELECT * FROM client_invoice_master WHERE invoice_no = :invoiceNo", nativeQuery = true)
    ClientInvoiceMasterEntity findByInvoiceNoNative(@Param("invoiceNo") String invoiceNo);


}
