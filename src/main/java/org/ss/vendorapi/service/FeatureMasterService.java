package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.FeatureMasterEntity;


public interface FeatureMasterService {

	public FeatureMasterEntity save(FeatureMasterEntity roleMasterEntity);
	public FeatureMasterEntity update(FeatureMasterEntity roleMasterEntity);
	public List<FeatureMasterEntity> findAll();
	public FeatureMasterEntity findById(Long id);
	public List<FeatureMasterEntity> getMainFeatures();
	public List<FeatureMasterEntity> findByWhere(String where);
}