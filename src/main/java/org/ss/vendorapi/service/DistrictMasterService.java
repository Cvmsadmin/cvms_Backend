package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.DistrictMasterEntity;
import org.ss.vendorapi.entity.StateMasterEntity;

public interface DistrictMasterService {
	public DistrictMasterEntity save(DistrictMasterEntity districtMasterEntity);
	public DistrictMasterEntity update(DistrictMasterEntity districtMasterEntity);
	public List<DistrictMasterEntity> findAll();
	public DistrictMasterEntity findById(Long id);
	public List<DistrictMasterEntity> getByStateId(String stateId);
}
