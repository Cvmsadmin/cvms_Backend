package org.ss.vendorapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;
import org.ss.vendorapi.modal.ReceivableInvoiceStatsDTO;
//import org.ss.vendorapi.entity.UserCreationEntity;
//import org.ss.vendorapi.service.Client;

@Repository
public interface ClientMasterRepository extends JpaRepository<ClientMasterEntity, Long>{
	
	
	public ClientMasterEntity findByEmail(String email);
	public List<ClientMasterEntity> findByAccountManagerId(String accountManagerId);
	
//	Optional<ClientMasterEntity> findById(Long id);	
	
//    Optional<ClientInvoiceMasterEntity> findByClientId(String clientId);
	
//	Optional<ClientMasterEntity> findByClientName(String clientName);
	
	ClientMasterEntity findByClientName(String clientName);	
//	ClientMasterEntity findById(Long id);
//	public ReceivableInvoiceStatsDTO getReceivableInvoiceStats();
//	public List<ClientInvoiceMasterEntity> findAllWithDescriptionValues();
	public Optional<ClientMasterEntity> findById(long clientName);
	
//	public Optional<ClientMasterEntity> findById(Long clientId);
	
}