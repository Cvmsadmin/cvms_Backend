package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.MilestoneMasterEntity;
//import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;

@Service
public interface MilestoneMasterService {

	public MilestoneMasterEntity save(MilestoneMasterEntity milestoneMasterEntity);
	public MilestoneMasterEntity update(MilestoneMasterEntity milestoneMasterEntity);
	public List<MilestoneMasterEntity> findAll();
	public MilestoneMasterEntity findById(Long id);
	public List<MilestoneMasterEntity> findByProjectId(String projectId);
	public List<MilestoneMasterEntity> getMilestonesByProjectId(Long projectId);
	public void deleteByProjectId(String string);
	

	
}
