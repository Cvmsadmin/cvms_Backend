package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.BaseLocationEntity;
import org.ss.vendorapi.repository.BaseLocationRepository;

@Service
public class BaseLocationService {
	
	
	 @Autowired
	    private BaseLocationRepository baseLocationRepository;

	    public BaseLocationEntity save(BaseLocationEntity baseLocationEntity) {
	        return baseLocationRepository.save(baseLocationEntity);
	    }

	    public List<BaseLocationEntity> saveAll(List<BaseLocationEntity> baseLocationEntities) {
	        return baseLocationRepository.saveAll(baseLocationEntities);
	    }
	    
	    public List<BaseLocationEntity> getAllLocations() {
	        return baseLocationRepository.findAll();
	    }

	
	
	

}
