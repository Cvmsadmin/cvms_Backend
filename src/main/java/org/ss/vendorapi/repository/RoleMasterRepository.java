package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.RoleMasterEntity;

@Repository
public interface RoleMasterRepository extends JpaRepository<RoleMasterEntity, Long> {
	
	

}
