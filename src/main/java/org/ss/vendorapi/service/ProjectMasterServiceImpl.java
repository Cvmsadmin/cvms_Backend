package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.FeatureMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.repository.ProjectMasterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ProjectMasterServiceImpl implements ProjectMasterService{

	@Autowired 
	private ProjectMasterRepository projectMasterRepository;

	@PersistenceContext
	private EntityManager entityManager;


	@Override
	public List<ProjectMasterEntity> findAll(){
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectMasterEntity> findByWhere(String where) {
		List<ProjectMasterEntity> list=null;
		try{
			Query query=null;
			if(where!=null)
				query=entityManager.createQuery("FROM ProjectMasterEntity o WHERE "+where);
			list=query.getResultList();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}
	
//	public List<ProjectMasterEntity> getProjectsByClientId(Long clientId) {
//        return projectMasterRepository.findByClientId(clientId);
//    }


}
