package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.entity.UserCreationEntity;
import org.ss.vendorapi.repository.ClientMasterRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Service
public class ClientMasterServiceImpl implements ClientMasterService{
	
	@Autowired 
	private ClientMasterRepository clientMasterRepository;

	@Autowired
	private EntityManager entityManager;
	
	
	public List<ClientMasterEntity> findAll(){
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
	public List<ClientMasterEntity> findByAccountManagerId(String userId) {
		return clientMasterRepository.findByAccountManagerId(userId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientMasterEntity> findByWhere(String where) {
		List<ClientMasterEntity> list=null;
		try{
			Query query=null;
			if(where!=null)
				query=entityManager.createQuery("FROM ClientMasterEntity o WHERE "+where);
			list=query.getResultList();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}


	@Override
	public ClientMasterEntity findByEmail(String email) {
		return clientMasterRepository.findByEmail(email);
	}


	@Override
	public ClientMasterEntity findByClientName(String clientName) {		
		return clientMasterRepository.findByClientName(clientName);
	}
	
	
//	public String getClientNameById(String string) {
//	    // Fetch the client by ID
//	    Optional<ClientMasterEntity> client = clientMasterRepository.findById(string);
//
//	    // Return client name if present, else "Unknown"
//	    return client.map(ClientMasterEntity::getClientName).orElse("Unknown");
//	}


	
	
		
	
	}
	


