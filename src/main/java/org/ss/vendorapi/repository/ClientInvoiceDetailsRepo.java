package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;

@Repository
public interface ClientInvoiceDetailsRepo extends JpaRepository<ClientInvoiceDetailsEntity, Long> {
	
	@Query("SELECT c FROM ClientInvoiceDetailsEntity c")
    List<ClientInvoiceDetailsEntity> getClientInvoiceDetails();

}
