package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.RoleResourceMasterEntity;
import org.ss.vendorapi.repository.RoleResourceMasterRepository;

@Service
public class RoleResourceMasterServiceImpl implements RoleResourceMasterService{
	

	@Autowired
	private RoleResourceMasterRepository roleResourceMasterRepository;

	@Override
	public RoleResourceMasterEntity saveResource(RoleResourceMasterEntity roleResourceMasterEntity) {
		roleResourceMasterEntity.setActive(1);
		return roleResourceMasterRepository.save(roleResourceMasterEntity);
	}

	@Override
	public RoleResourceMasterEntity updateResource(RoleResourceMasterEntity roleResourceMasterEntity) {
		roleResourceMasterEntity.setActive(1);
		return roleResourceMasterRepository.save(roleResourceMasterEntity);
	}

	@Override
	public Boolean deleteResourceById(Long id) {
		try {
			RoleResourceMasterEntity roleResourceMasterEntity=roleResourceMasterRepository.findById(id).get();
			if(roleResourceMasterEntity!=null) {
				roleResourceMasterEntity.setActive(0);
				roleResourceMasterRepository.save(roleResourceMasterEntity);
				return true;
			}
		}catch(Exception ex) {
		}
		return false;
	}

	@Override
	public List<RoleResourceMasterEntity> findByRole(String roleId) {
		return roleResourceMasterRepository.findByRoleId(roleId);
	}




}