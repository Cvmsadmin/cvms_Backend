package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.entity.UserCreationEntity;


@Repository
public interface ClientMasterRepository extends JpaRepository<ClientMasterEntity, Long>{

}
