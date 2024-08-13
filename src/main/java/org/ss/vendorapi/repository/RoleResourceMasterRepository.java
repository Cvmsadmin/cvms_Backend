package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.RoleResourceMasterEntity;

@Repository
public interface RoleResourceMasterRepository extends JpaRepository<RoleResourceMasterEntity, Long>{
	
	public List<RoleResourceMasterEntity> findByRoleId(String roleId);

}
