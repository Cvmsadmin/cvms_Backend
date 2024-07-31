package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;

public interface SalesOpportunityService {

	public SalesOpportunityMasterEntity save(SalesOpportunityMasterEntity salesOpportunityMaster);
	public SalesOpportunityMasterEntity update(SalesOpportunityMasterEntity salesOpportunityMaster);
	public List<SalesOpportunityMasterEntity> findAll();
	public SalesOpportunityMasterEntity findById(Long id);
	
}
