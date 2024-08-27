package org.ss.vendorapi.service;

import java.util.Collection;
import java.util.List;

import org.ss.vendorapi.entity.RoleMasterEntity;

public interface RoleMasterService {

	public RoleMasterEntity save(RoleMasterEntity roleMaster);
	public RoleMasterEntity update(RoleMasterEntity roleMaster);
	public List<RoleMasterEntity >findAll();
	public RoleMasterEntity findById(Long id);
	List<RoleMasterEntity> getAllRoles();
}
