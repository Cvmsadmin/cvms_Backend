package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ss.vendorapi.entity.ServiceMasterEntity;

	public interface ServiceMasterRepository extends JpaRepository<ServiceMasterEntity, Long>{
		
	
	}