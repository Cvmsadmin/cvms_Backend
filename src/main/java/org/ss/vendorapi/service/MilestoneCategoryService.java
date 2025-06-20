package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.MilestoneCategory;

@Service
public interface MilestoneCategoryService {
	
	public void saveCategory(MilestoneCategory milestoneCategory);

	public List<MilestoneCategory> getPartsByProjectId(Long projectId);

	public MilestoneCategory getById(Long partsId);

	public MilestoneCategory getByProjectIdAndPartition(Long projectId, String partition);

	
	void deleteByProjectId(String projectId);

	
	void deleteByProjectId(Long projectId);


	
//	void saveCategory(MilestoneCategory milestoneCategory);

}
