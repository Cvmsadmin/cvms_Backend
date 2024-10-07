package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;
import org.ss.vendorapi.entity.ServiceMasterEntity;	

public interface ServiceMasterService {


	public ServiceMasterEntity save(ServiceMasterEntity serviceMasterEntity);
	public List<ServiceMasterEntity> findAll();
	public ServiceMasterEntity update(ServiceMasterEntity serviceMasterEntity);
	public ServiceMasterEntity findById(Long id);

}
