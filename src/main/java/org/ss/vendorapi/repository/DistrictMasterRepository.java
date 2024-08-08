package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.DistrictMasterEntity;

@Repository
public interface DistrictMasterRepository extends JpaRepository<DistrictMasterEntity, Long>{
 
	 List<DistrictMasterEntity> getByStateId(String stateId);
}
