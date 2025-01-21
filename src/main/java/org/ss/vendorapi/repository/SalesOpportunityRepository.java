package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;

@Repository
public interface SalesOpportunityRepository extends JpaRepository<SalesOpportunityMasterEntity,Long>{
	
//	SalesOpportunityMasterEntity save1(SalesOpportunityMasterEntity entity);

}
