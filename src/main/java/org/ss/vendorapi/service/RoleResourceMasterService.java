package org.ss.vendorapi.service;

import org.ss.vendorapi.entity.RoleResourceMasterEntity;

public interface RoleResourceMasterService {
	
	public RoleResourceMasterEntity saveResource(RoleResourceMasterEntity roleResourceMasterEntity);
	public RoleResourceMasterEntity updateResource(RoleResourceMasterEntity roleResourceMasterEntity);
	public Boolean deleteResourceById(Long id);

}
