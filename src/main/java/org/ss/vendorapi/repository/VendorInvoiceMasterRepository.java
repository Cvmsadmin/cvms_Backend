package org.ss.vendorapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;

@Repository
public interface VendorInvoiceMasterRepository extends JpaRepository<VendorInvoiceMasterEntity, Long>{
	
	Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo);
	
//	 @Query("SELECT new org.ss.vendorapi.dto.PayableInvoiceStatsDTO(" +
//	            "COUNT(vim) AS payableInvoiceCount, " +
//	            "SUM(vim.invoiceAmountIncluGst) AS payableInvoiceAmount) " +
//	            "FROM VendorInvoiceMasterEntity vim " +
//	            "WHERE vim.status = 'pending'")
//	    PayableInvoiceStatsDTO getPayableInvoiceStats();
}
