package org.ss.vendorapi.repository;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;
//import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;

@Repository
public interface ClientInvoiceMasterRepository extends JpaRepository<ClientInvoiceMasterEntity, Long>{

//	ClientInvoiceMasterEntity findByInvoiceNo(String invoiceNo);
	
	@Query(value = "SELECT * FROM client_invoice_master WHERE invoice_no = :invoiceNo", nativeQuery = true)
    ClientInvoiceMasterEntity findByInvoiceNoNative(@Param("invoiceNo") String invoiceNo);
	
	ClientInvoiceMasterEntity findByClientId(String clientId);
	
//	List<User> findUsersByclientNameAndprojectName(String clientName, String projectName, List<String> asList);


}
