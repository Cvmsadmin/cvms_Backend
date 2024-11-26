package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.InvoiceDescriptionValue;

@Repository
public interface ClientInvoiceDescriptionValueRepository extends JpaRepository<ClientInvoiceDescriptionValue, Long>{
	
	List<ClientInvoiceDescriptionValue> findByClientInvoice_Id(Long clientInvoiceId);	
	
	@Query("SELECT c FROM ClientInvoiceDescriptionValue c WHERE c.invoiceNo = :invoiceNo")
    List<ClientInvoiceDescriptionValue> findByInvoiceNo(@Param("invoiceNo") String invoiceNo);

//	List<ClientInvoiceDescriptionValue> findByfindByInvoiceNo(String invoiceNo);

}
