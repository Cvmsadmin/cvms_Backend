package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.repository.ProfitLossMasterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class ProfitLossMasterServiceImpl implements ProfitLossMasterService{
	
	@Autowired 
	private ProfitLossMasterRepository profitLossMasterRepository;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public ProfitLossMasterEntity save(ProfitLossMasterEntity profitLossMasterEntity) {
		return profitLossMasterRepository.save(profitLossMasterEntity);
	}

	@Override
	public List<ProfitLossMasterEntity> findAll() {
				return profitLossMasterRepository.findAll();
	}

	@Override
	public ProfitLossMasterEntity update(ProfitLossMasterEntity profitLossMasterEntity) {
		return profitLossMasterRepository.save(profitLossMasterEntity);
	}

	@Override
	public ProfitLossMasterEntity findById(Long id) {
		return profitLossMasterRepository.findById(id).orElse(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProfitLossMasterEntity> findByWhere(String where) {
		List<ProfitLossMasterEntity> list=null;
		try{
			Query query=null;
		 	if(where!=null)
				query=entityManager.createQuery("FROM ProfitLossMasterEntity o WHERE "+where);
			list=query.getResultList();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

		public List<ProfitLossMasterEntity> findByProjectId(int projectId) {
		    return profitLossMasterRepository.findByProjectId(projectId);
		}

}
