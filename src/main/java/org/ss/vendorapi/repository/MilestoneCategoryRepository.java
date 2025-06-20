package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ss.vendorapi.entity.MilestoneCategory;

public interface MilestoneCategoryRepository extends JpaRepository<MilestoneCategory, Long>{
	
	  List<MilestoneCategory> findByProjectId(Long projectId);

	MilestoneCategory findByProjectIdAndPartition(Long projectId, String partition);


	

}
