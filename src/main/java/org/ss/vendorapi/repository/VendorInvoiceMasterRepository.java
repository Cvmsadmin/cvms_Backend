package org.ss.vendorapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;

@Repository
public interface VendorInvoiceMasterRepository extends JpaRepository<VendorInvoiceMasterEntity, Long>{
	
//	Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);
	
	Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);

	@Query("SELECT SUM(v.amountExcluGst) FROM VendorInvoiceMasterEntity v WHERE v.projectName = :projectName")
	Double getVendorAmountExcluGstByProjectName(@Param("projectName") String projectName);
	
//	@Query("SELECT SUM(v.amountExcluGst) FROM VendorInvoiceMasterEntity v " +
//	           "WHERE v.projectName = :projectName " +
//	           "AND (:startDate IS NULL OR v.invoiceDate >= :startDate) " +
//	           "AND (:endDate IS NULL OR v.invoiceDate <= :endDate)")
//	    Double getSumByProjectNameAndDate(@Param("projectName") String projectName,
//	                                      @Param("startDate") LocalDate startDate,
//	                                      @Param("endDate") LocalDate endDate);


//	@Query(value = "SELECT SUM(CAST(v.amount_exclu_gst AS DOUBLE PRECISION)) " +
//            "FROM vendor_invoice_master v " +
//            "WHERE v.active = 1 " +
//            "AND v.project_name = :projectName " +
//            "AND (:startDate IS NULL OR v.invoice_date >= :startDate) " +
//            "AND (:endDate IS NULL OR v.invoice_date <= :endDate)", 
//    nativeQuery = true)
//Double getVendorAmountExcluGstByProjectNameAndDate(
// @Param("projectName") String projectName,
// @Param("startDate") LocalDate startDate,
// @Param("endDate") LocalDate endDate);
	
	@Query(value = "SELECT SUM(CAST(v.amount_exclu_gst AS DOUBLE PRECISION)) " +
            "FROM vendor_invoice_master v " +
            "WHERE v.active = 1 " +
            "AND v.project_name = :projectName " +
            "AND (v.invoice_date >= COALESCE(:startDate, CAST(NULL AS DATE))) " +
            "AND (v.invoice_date <= COALESCE(:endDate, CAST(NULL AS DATE)))",
    nativeQuery = true)
Double getVendorAmountExcluGstByProjectNameAndDate(
 @Param("projectName") String projectName,
 @Param("startDate") LocalDate startDate,
 @Param("endDate") LocalDate endDate);

	List<VendorInvoiceMasterEntity> findByProjectName(String projectName);

//	List<Object[]> findVendorInvoicesByManagerId(Long managerId);

	@Query(value = "SELECT * FROM get_user_vendor_invoice_dd(:managerId)", nativeQuery = true)
	List<Object[]> findVendorInvoicesByManagerId(@Param("managerId") Long managerId);


//	Double getVendorAmountExcluGstByProjectId(Long projectId);


	


	
//	 @Query("SELECT new org.ss.vendorapi.dto.PayableInvoiceStatsDTO(" +
//	            "COUNT(vim) AS payableInvoiceCount, " +
//	            "SUM(vim.invoiceAmountIncluGst) AS payableInvoiceAmount) " +
//	            "FROM VendorInvoiceMasterEntity vim " +
//	            "WHERE vim.status = 'pending'")
//	    PayableInvoiceStatsDTO getPayableInvoiceStats();
}
