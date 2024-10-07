package org.ss.vendorapi.service;

import java.util.List;

//import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientMasterEntity;


public interface ClientMasterService {
	public ClientMasterEntity save(ClientMasterEntity clientMasterEntity);
	public List<ClientMasterEntity> findAll();
	public ClientMasterEntity update(ClientMasterEntity clientMasterEntity);
	public ClientMasterEntity findById(Long id);
	public List<ClientMasterEntity> findByAccountManager(String userId);
	public List<ClientMasterEntity> findByWhere(String where);
	//public List<ClientMasterEntity> findByProjectManager(String userId);
	public ClientMasterEntity findByEmail(String email);
	
}