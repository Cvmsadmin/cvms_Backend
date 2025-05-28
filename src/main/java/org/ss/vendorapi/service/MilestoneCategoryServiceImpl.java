package org.ss.vendorapi.service;

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

}
