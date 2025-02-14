package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.ss.vendorapi.entity.RecivableInvoicesView;

public interface RecivableInvoicesViewRepository extends JpaRepository<RecivableInvoicesView, Long> {
	
	 @Query("SELECT i FROM RecivableInvoicesView i")
	    List<RecivableInvoicesView> findAllInvoiceSummaries();
	

}
