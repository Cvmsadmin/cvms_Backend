package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ResourceMasterEntity;
import org.ss.vendorapi.entity.RoleResourceMasterEntity;

public interface RoleResourceMasterService {
	
	public RoleResourceMasterEntity saveResource(RoleResourceMasterEntity roleResourceMasterEntity);
	public RoleResourceMasterEntity updateResource(RoleResourceMasterEntity roleResourceMasterEntity);
	public Boolean deleteResourceById(Long id);
	public List<RoleResourceMasterEntity> findByRole(String roleId);
	



}
