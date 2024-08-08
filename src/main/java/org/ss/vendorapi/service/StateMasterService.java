package org.ss.vendorapi.service;

import java.util.List;


import org.ss.vendorapi.entity.StateMasterEntity;

public interface StateMasterService {

	public StateMasterEntity save(StateMasterEntity stateMasterEntity);
	public StateMasterEntity update(StateMasterEntity stateMasterEntity);
	public List<StateMasterEntity> findAll();
	public StateMasterEntity findById(Long id);
}
