package org.ss.vendorapi.service;

import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.MilestoneCategory;

@Service
public interface MilestoneCategoryService {
	
	public void saveCategory(MilestoneCategory milestoneCategory);

}
