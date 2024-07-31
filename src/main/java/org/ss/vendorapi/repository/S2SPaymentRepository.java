package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.modal.S2SPaymentDTO;

@Repository
public interface S2SPaymentRepository extends JpaRepository<S2SPaymentDTO, String>{

	S2SPaymentDTO findBytrackId(String trackId);

	
}
