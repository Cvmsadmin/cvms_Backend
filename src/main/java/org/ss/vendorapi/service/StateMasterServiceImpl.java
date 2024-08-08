package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.StateMasterEntity;
import org.ss.vendorapi.repository.StateMasterRepository;

@Service
public class StateMasterServiceImpl implements StateMasterService{

	@Autowired
	private StateMasterRepository stateMasterRepository;
	
	@Override
	public StateMasterEntity save(StateMasterEntity stateMasterEntity) {
		stateMasterEntity.setActive(1);
		stateMasterEntity.setCreateDate(new Date());
		return stateMasterRepository.save(stateMasterEntity);
	}

	@Override
	public StateMasterEntity update(StateMasterEntity stateMasterEntity) {
		stateMasterEntity.setUpdateDate(new Date());
		return stateMasterRepository.save(stateMasterEntity);
	}

	@Override
	public List<StateMasterEntity> findAll() {
		return stateMasterRepository.findAll();
	}

	@Override
	public StateMasterEntity findById(Long id) {
		return stateMasterRepository.findById(id).orElse(null);
	}

}
