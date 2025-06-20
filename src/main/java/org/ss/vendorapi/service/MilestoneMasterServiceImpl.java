package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.MilestoneCategory;
import org.ss.vendorapi.entity.MilestoneMasterEntity;
import org.ss.vendorapi.repository.MilestoneMasterRepository;

@Service
public class MilestoneMasterServiceImpl implements MilestoneMasterService{

	
	
	@Autowired
	private MilestoneMasterRepository milestoneMasterRepository;
	
	@Override
	public MilestoneMasterEntity save(MilestoneMasterEntity milestoneMasterEntity) {
		milestoneMasterEntity.setActive(1);
		milestoneMasterEntity.setCreateDate(new Date());
		return milestoneMasterRepository.save(milestoneMasterEntity);
	}

	@Override
	public MilestoneMasterEntity update(MilestoneMasterEntity milestoneMasterEntity) {
		milestoneMasterEntity.setUpdateDate(new Date());
		return milestoneMasterRepository.saveAndFlush(milestoneMasterEntity);
	}

	@Override
	public List<MilestoneMasterEntity> findAll() {
		return milestoneMasterRepository.findAll();
	}

	@Override
	public MilestoneMasterEntity findById(Long id) {
		return milestoneMasterRepository.findById(id).orElse(null) ;
	}

	@Override
	public List<MilestoneMasterEntity> findByProjectId(String projectId) {
		return milestoneMasterRepository.findByProjectId(projectId);
	}
	
	public List<MilestoneMasterEntity> getMilestonesByProjectId(Long projectId) {
        return milestoneMasterRepository.findByProjectId(String.valueOf(projectId));
    }

	
	public void deleteByProjectId(String projectId) {
	    milestoneMasterRepository.deleteByProjectId(projectId);
	}


}


