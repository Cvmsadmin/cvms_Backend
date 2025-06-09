package org.ss.vendorapi.repository;

import java.time.LocalDate;
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

	@Query("SELECT SUM(CAST(c.amountExcluGst AS double)) FROM ClientInvoiceMasterEntity c WHERE c.projectName = :projectName")
	Double getClientAmountExcluGstByProjectName(@Param("projectName") String projectName);
	

	
	@Query(value = "SELECT SUM(CAST(c.amount_exclu_gst AS DOUBLE PRECISION)) " +
            "FROM client_invoice_master c " +
            "WHERE c.project_name = :projectName " +
            "AND (c.invoice_date >= COALESCE(:startDate, CAST(NULL AS DATE))) " +
            "AND (c.invoice_date <= COALESCE(:endDate, CAST(NULL AS DATE)))",
    nativeQuery = true)
Double getClientAmountExcluGstByProjectNameAndDate(
 @Param("projectName") String projectName,
 @Param("startDate") LocalDate startDate,
 @Param("endDate") LocalDate endDate);

	
	 List<ClientInvoiceMasterEntity> findByProjectName(String projectName);


//	Double getClientAmountExcluGstByProjectId(Long projectId);



	
//	List<User> findUsersByclientNameAndprojectName(String clientName, String projectName, List<String> asList);


}
