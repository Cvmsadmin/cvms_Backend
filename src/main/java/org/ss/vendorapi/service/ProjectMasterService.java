package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ProjectMasterEntity;

public interface ProjectMasterService {
	
	public ProjectMasterEntity save(ProjectMasterEntity projectMasterEntity);
	public List<ProjectMasterEntity> getAllProject();
	public ProjectMasterEntity update(ProjectMasterEntity projectMasterEntity);
	public ProjectMasterEntity findById(Long id);

}
