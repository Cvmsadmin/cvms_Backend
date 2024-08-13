package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.CityMasterEntity;
import org.ss.vendorapi.repository.CityMasterRepository;

@Service
public class CityMasterServiceImpl implements CityMasterService{

	@Autowired
	private CityMasterRepository cityMasterRepository;
	
	@Override
	public CityMasterEntity save(CityMasterEntity cityMasterEntity) {
		cityMasterEntity.setActive(1);
		cityMasterEntity.setCreateDate(new Date());
		return cityMasterRepository.save(cityMasterEntity);
		
	}

	@Override
	public CityMasterEntity update(CityMasterEntity cityMasterEntity) {
		cityMasterEntity.setUpdateDate(new Date());
		return cityMasterRepository.save(cityMasterEntity);
	}

	@Override
	public CityMasterEntity findById(Long id) {
		return cityMasterRepository.findById(id).orElse(null);

	}

	@Override
	public List<CityMasterEntity> findAll() {
		return cityMasterRepository.findAll();
	}

	@Override
	public List<CityMasterEntity> getByDistrictId(String districtId) {
		return cityMasterRepository.getByDistrictId(districtId);
	}

	@Override
	public CityMasterEntity createNewCity(String cityName, String districtId) {
		CityMasterEntity cityMasterEntity= new CityMasterEntity ();
		cityMasterEntity.setCityName(cityName);
		cityMasterEntity.setDistrictId(districtId);
		cityMasterEntity.setActive(1);
		cityMasterEntity.setCreateDate(new Date());
		return cityMasterRepository.save(cityMasterEntity);
	}

}
