package org.ss.vendorapi.service;

import java.util.Date;
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
	public List<ServiceMasterEntity> findAll(){
		return serviceMasterRepository.findAll();
	}



	@Override
	public ServiceMasterEntity save(ServiceMasterEntity serviceMasterEntity) {
		serviceMasterEntity.setActive(1);
		serviceMasterEntity.setCreateDate(new Date());
		return serviceMasterRepository.save(serviceMasterEntity);
	}



	@Override
	public ServiceMasterEntity update(ServiceMasterEntity serviceMasterEntity) {
		serviceMasterEntity.setUpdateDate(new Date());
		return serviceMasterRepository.save(serviceMasterEntity);
		
	}



	@Override
	public ServiceMasterEntity findById(Long id) {
		return serviceMasterRepository.findById(id).orElse(null);
	}


}
