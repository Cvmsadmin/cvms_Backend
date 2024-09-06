package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.FeatureMasterEntity;

@Repository
public interface FeatureMasterRepository  extends JpaRepository<FeatureMasterEntity, Long> {
	
	public List<FeatureMasterEntity> findBySubMenu(String subMenu);
	

}
