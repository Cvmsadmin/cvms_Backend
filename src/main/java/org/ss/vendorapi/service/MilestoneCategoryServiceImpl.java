package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.MilestoneCategory;
import org.ss.vendorapi.repository.MilestoneCategoryRepository;

@Service
public class MilestoneCategoryServiceImpl implements MilestoneCategoryService {
	
	@Autowired
    private MilestoneCategoryRepository milestoneCategoryRepository;


	@Override
	public void saveCategory(MilestoneCategory milestoneCategory) {
		 milestoneCategoryRepository.save(milestoneCategory);

	}
	
    public List<MilestoneCategory> getPartsByProjectId(Long projectId) {
	        return milestoneCategoryRepository.findByProjectId(projectId);
	    }


    public MilestoneCategory getById(Long id) {
        return milestoneCategoryRepository.findById(id).orElse(null);
    }
    
    @Override
    public MilestoneCategory getByProjectIdAndPartition(Long projectId, String partition) {
        return milestoneCategoryRepository.findByProjectIdAndPartition(projectId, partition);
    }


}
