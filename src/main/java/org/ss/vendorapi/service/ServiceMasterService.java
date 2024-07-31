package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ServiceMasterEntity;	

public interface ServiceMasterService {
	

	public ServiceMasterEntity save(ServiceMasterEntity serviceMasterEntity);
	public List<ServiceMasterEntity> getAllService();

}
