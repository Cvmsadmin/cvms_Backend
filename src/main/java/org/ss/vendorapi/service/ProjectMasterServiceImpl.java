package org.ss.vendorapi.service;

import java.util.Date;
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
		projectMasterEntity.setActive(1);
		projectMasterEntity.setCreateDate(new Date());
		return projectMasterRepository.save(projectMasterEntity);
	}



	@Override
	public ProjectMasterEntity update(ProjectMasterEntity projectMasterEntity) {
		projectMasterEntity.setUpdateDate(new Date());
		return projectMasterRepository.save(projectMasterEntity);
	}



	@Override
	public ProjectMasterEntity findById(Long id) {
		return projectMasterRepository.findById(id).orElse(null);
	}

}
