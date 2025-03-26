package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.InvoiceDescriptionValue;

import org.springframework.transaction.annotation.Transactional;

@Repository
public interface InvoiceDescriptionValueRepository extends JpaRepository<InvoiceDescriptionValue, Long>{

	
	 @Modifying
	    @Transactional
	    @Query("DELETE FROM InvoiceDescriptionValue iv WHERE iv.vendorInvoice.id = :id")
	    void deleteByVendorInvoiceId(@Param("id") Long id);
	
}
