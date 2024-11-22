package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ProjectMasterEntity;

public interface ProjectMasterService {
	
	public ProjectMasterEntity save(ProjectMasterEntity projectMasterEntity);
	public List<ProjectMasterEntity> findAll();
	public ProjectMasterEntity update(ProjectMasterEntity projectMasterEntity);
	public ProjectMasterEntity findById(Long id);
	public List<ProjectMasterEntity> findByWhere(String where);
//	public List<ProjectMasterEntity> getProjectsByClientId(Long clientId);


}
