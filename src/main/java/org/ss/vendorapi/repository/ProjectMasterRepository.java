package org.ss.vendorapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ProjectMasterEntity;

@Repository
public interface ProjectMasterRepository extends JpaRepository<ProjectMasterEntity, Long>{

	List<ProjectMasterEntity> findByClientId(String clientId);
	ProjectMasterEntity findByProjectName(String projectName);
//	List<ProjectMasterEntity> findByClientId(Long clientId);

}
