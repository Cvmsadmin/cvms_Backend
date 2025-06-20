package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.PurchaseMasterEntity;

@Repository
public interface PurchaseMasterRepository extends JpaRepository<PurchaseMasterEntity, Long>{

	
		PurchaseMasterEntity findByPrNo(String prNo);

		PurchaseMasterEntity findByPoNo(String poNo);

		List<PurchaseMasterEntity> findByProjectName(String projectName);

//		List<PurchaseMasterEntity> findByProjectId(Long projectId);
		
//		String findPoNoByProjectName(String projectName);
		
}
