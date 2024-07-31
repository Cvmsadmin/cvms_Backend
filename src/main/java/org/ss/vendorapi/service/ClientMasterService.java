package org.ss.vendorapi.service;

import java.util.List;

//import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.entity.UserCreationEntity;


public interface ClientMasterService {
	public ClientMasterEntity save(ClientMasterEntity clientMasterEntity);
	public List<ClientMasterEntity> getAllClient();

}
