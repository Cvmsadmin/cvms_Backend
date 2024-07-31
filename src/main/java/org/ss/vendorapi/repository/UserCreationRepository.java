package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.UserMasterEntity;


@Repository
public interface UserCreationRepository extends JpaRepository<UserMasterEntity, Long>{

	
}
