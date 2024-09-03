package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.VendorMasterEntity;
import org.ss.vendorapi.repository.VendorMasterRepository;

@Service
public class VendorMasterServiceImpl implements VendorMasterService{
	@Autowired 
	private VendorMasterRepository vendorMasterRepository;

	
	
	public List<VendorMasterEntity> getAllVendor(){
		return vendorMasterRepository.findAll();
	}



	@Override
	public VendorMasterEntity save(VendorMasterEntity vendorMasterEntity) {
		vendorMasterEntity.setActive(1);
		vendorMasterEntity.setCreateDate(new Date());
		return vendorMasterRepository.save(vendorMasterEntity);
	}



	@Override
	public VendorMasterEntity update(VendorMasterEntity vendorMasterEntity) {
		vendorMasterEntity.setUpdateDate(new Date());
		return vendorMasterRepository.save(vendorMasterEntity);
	}



	@Override
	public VendorMasterEntity findById(Long id) {
		return vendorMasterRepository.findById(id).orElse(null);
	}

}
