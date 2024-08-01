package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.repository.ProjectMasterRepository;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService{
	
	@Autowired 
	private ProjectMasterRepository projectMasterRepository;

	
	@Override
	public List<ProjectMasterEntity> getAllProject(){
		return projectMasterRepository.findAll();
	}



	@Override
	public ProjectMasterEntity save(ProjectMasterEntity projectMasterEntity) {
		return projectMasterRepository.save(projectMasterEntity);
	}

}
