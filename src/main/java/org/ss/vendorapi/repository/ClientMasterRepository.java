package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.entity.UserCreationEntity;


@Repository
public interface ClientMasterRepository extends JpaRepository<ClientMasterEntity, Long>{
	
	
	public ClientMasterEntity findByEmail(String email);
	public List<ClientMasterEntity> findByAccountManager(String accountManager);


}
