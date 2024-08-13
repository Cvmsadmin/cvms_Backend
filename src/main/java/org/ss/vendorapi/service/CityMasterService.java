package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.CityMasterEntity;



public interface CityMasterService {
	public CityMasterEntity save(CityMasterEntity cityMasterEntity);
	public CityMasterEntity update(CityMasterEntity cityMasterEntity);
	public CityMasterEntity findById(Long id);
	public List<CityMasterEntity> findAll();
	public List<CityMasterEntity> getByDistrictId(String districtId);
	public CityMasterEntity createNewCity(String cityName, String districtId);
}
