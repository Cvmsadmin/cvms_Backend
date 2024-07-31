package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.RoleMasterEntity;
import org.ss.vendorapi.repository.RoleMasterRepository;

@Service
public class RoleMasterServiceImpl implements RoleMasterService {

	@Autowired
	private RoleMasterRepository roleMasterRepository;

	@Override
	public RoleMasterEntity save(RoleMasterEntity roleMaster) {
		roleMaster.setActive(1);
		roleMaster.setCreateDate(new Date());
		return roleMasterRepository.save(roleMaster);
	}

	@Override
	public RoleMasterEntity update(RoleMasterEntity roleMaster) {
		roleMaster.setUpdateDate(new Date());
		return roleMasterRepository.save(roleMaster);
	}

	@Override
	public List<RoleMasterEntity> findAll() {
		return roleMasterRepository.findAll();
	}

	
	@Override
	public RoleMasterEntity findById(Long id) {
		return roleMasterRepository.findById(id).orElse(null);
	}


}





