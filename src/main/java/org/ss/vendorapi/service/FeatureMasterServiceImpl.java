package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.FeatureMasterEntity;
import org.ss.vendorapi.repository.FeatureMasterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Service
public class FeatureMasterServiceImpl implements FeatureMasterService {

	@Autowired
	private FeatureMasterRepository roleFeatureRepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public FeatureMasterEntity save(FeatureMasterEntity feautureMasterEntity) {
		feautureMasterEntity.setActive(1);
		feautureMasterEntity.setCreateDate(new Date());
		return roleFeatureRepository.save(feautureMasterEntity);
	}

	@Override
	public FeatureMasterEntity update(FeatureMasterEntity feautureMasterEntity) {
		feautureMasterEntity.setUpdateDate(new Date());
		return roleFeatureRepository.save(feautureMasterEntity);
	}

	@Override
	public List<FeatureMasterEntity> findAll() {
		return roleFeatureRepository.findAll();
	}

	@Override
	public FeatureMasterEntity findById(Long id) {
		return roleFeatureRepository.findById(id).orElse(null);
	}

	@Override
	public List<FeatureMasterEntity> getMainFeatures() {
		return roleFeatureRepository.findBySubMenu("N");
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<FeatureMasterEntity> findByWhere(String where) {
		List<FeatureMasterEntity> list=null;
		try{
			Query query=null;
			if(where!=null)
				query=entityManager.createQuery("FROM FeatureMasterEntity o WHERE "+where);
			list=query.getResultList();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

}