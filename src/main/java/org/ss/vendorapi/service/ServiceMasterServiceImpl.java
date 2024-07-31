package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ServiceMasterEntity;
import org.ss.vendorapi.repository.ServiceMasterRepository;

@Service
public class ServiceMasterServiceImpl implements ServiceMasterService{

	@Autowired 
	private ServiceMasterRepository serviceMasterRepository;

	
	@Override
	public List<ServiceMasterEntity> getAllService(){
		return serviceMasterRepository.findAll();
	}



	@Override
	public ServiceMasterEntity save(ServiceMasterEntity serviceMasterEntity) {
		return serviceMasterRepository.save(serviceMasterEntity);
	}


}
