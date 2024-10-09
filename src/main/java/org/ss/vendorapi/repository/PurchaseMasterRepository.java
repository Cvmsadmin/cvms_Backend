package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.PurchaseMasterEntity;

@Repository
public interface PurchaseMasterRepository extends JpaRepository<PurchaseMasterEntity, Long>{

	
		PurchaseMasterEntity findByPrNo(String prNo);
}
