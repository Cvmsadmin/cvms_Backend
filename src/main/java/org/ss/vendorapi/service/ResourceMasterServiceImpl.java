package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ResourceMasterEntity;
import org.ss.vendorapi.repository.ResourceMasterRepository;

@Service
public class ResourceMasterServiceImpl implements ResourceMasterService{
	
	@Autowired
	private ResourceMasterRepository resourceMasterRepository;

	@Override
	public ResourceMasterEntity save(ResourceMasterEntity resourceMasterEntity) {
		resourceMasterEntity.setActive(1);
		resourceMasterEntity.setCreateDate(new Date());
		return resourceMasterRepository.save(resourceMasterEntity);
	}

	@Override
	public ResourceMasterEntity update(ResourceMasterEntity resourceMasterEntity) {
		resourceMasterEntity.setUpdateDate(new Date());
		return resourceMasterRepository.save(resourceMasterEntity);
	}

	@Override
	public List<ResourceMasterEntity> findAll() {
		return resourceMasterRepository.findAll();
	}

	@Override
	public ResourceMasterEntity findById(Long id) {
		return resourceMasterRepository.findById(id).orElse(null);
	}

	

}



