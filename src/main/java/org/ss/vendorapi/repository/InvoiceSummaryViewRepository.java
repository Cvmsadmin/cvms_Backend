package org.ss.vendorapi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ss.vendorapi.entity.InvoiceSummaryView;

public interface InvoiceSummaryViewRepository extends JpaRepository<InvoiceSummaryView, Long> {

	 @Query("SELECT i FROM InvoiceSummaryView i")
	    List<InvoiceSummaryView> findAllInvoiceSummaries();

}
