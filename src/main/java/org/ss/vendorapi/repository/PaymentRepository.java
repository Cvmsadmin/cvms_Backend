package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ss.vendorapi.modal.PaymentDTO;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentDTO, String>{

	PaymentDTO findBytrackId(String trackId);
	List<PaymentDTO> findByAccountNumberAndDiscomName(String accountNumber,String discomName);
	@Modifying
    @Transactional
	@Query("update PaymentDTO v set v.paymentStatus=:status where v.trackId=:trackId")
	int updatePaymentStatus(String trackId, String status);
	
}
