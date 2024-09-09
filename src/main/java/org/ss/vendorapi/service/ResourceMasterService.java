package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ResourceMasterEntity;

public interface ResourceMasterService {

	public ResourceMasterEntity save(ResourceMasterEntity resourceMasterEntity);
	public ResourceMasterEntity update(ResourceMasterEntity resourceMasterEntity);
	public List<ResourceMasterEntity> findAll();
	public ResourceMasterEntity findById(Long id);
	public List<ResourceMasterEntity> findByFeatureId(String featureId);

}
