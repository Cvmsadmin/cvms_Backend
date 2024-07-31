package org.ss.vendorapi.service;

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
		return clientMasterRepository.save(clientMasterEntity);
	}

}
