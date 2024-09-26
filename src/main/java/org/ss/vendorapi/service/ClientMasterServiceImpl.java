package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.entity.UserCreationEntity;
import org.ss.vendorapi.repository.ClientMasterRepository;
//import org.ss.vendorapi.repository.UserCreationRepository;

@Service
public class ClientMasterServiceImpl implements ClientMasterService{
	
	@Autowired 
	private ClientMasterRepository clientMasterRepository;

	
	
	public List<ClientMasterEntity> getAllClient(){
		return clientMasterRepository.findAll();
	}



	@Override
	public ClientMasterEntity save(ClientMasterEntity clientMasterEntity) {
		clientMasterEntity.setActive(1);
		clientMasterEntity.setCreateDate(new Date());
		return clientMasterRepository.save(clientMasterEntity);
	}



	@Override
	public ClientMasterEntity update(ClientMasterEntity clientMasterEntity) {
		clientMasterEntity.setUpdateDate(new Date());
		return clientMasterRepository.save(clientMasterEntity);
	}



	@Override
	public ClientMasterEntity findById(Long id) {
	
		return clientMasterRepository.findById(id).orElse(null);
	}


	@Override
	public List<ClientMasterEntity> findByAccountManager(String userId) {
		return clientMasterRepository.findByAccountManager(userId);
	}



//	@Override
//	public List<ClientMasterEntity> findByProjectManager(String userId) {
//		return clientMasterRepository.findByProjectManager(userId);
//	}

}
