package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ProjectMasterEntity;

@Repository
public interface ProjectMasterRepository extends JpaRepository<ProjectMasterEntity, Long>{
	
//	List<ProjectMasterEntity> findByClientId(Long clientId);

}
