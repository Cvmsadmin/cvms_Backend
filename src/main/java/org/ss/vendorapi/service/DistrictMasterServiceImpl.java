package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.DistrictMasterEntity;
import org.ss.vendorapi.repository.DistrictMasterRepository;


@Service
public class DistrictMasterServiceImpl implements DistrictMasterService {

	@Autowired
	private DistrictMasterRepository districtMasterRepository;
	
	@Override
	public DistrictMasterEntity save(DistrictMasterEntity districtMasterEntity) {
		districtMasterEntity.setActive(1);
		districtMasterEntity.setCreateDate(new Date());
		return districtMasterRepository.save(districtMasterEntity);
	}

	@Override
	public DistrictMasterEntity update(DistrictMasterEntity districtMasterEntity) {
		districtMasterEntity.setUpdateDate(new Date());
		return districtMasterRepository.save(districtMasterEntity);
	}

	@Override
	public List<DistrictMasterEntity> findAll() {
		return districtMasterRepository.findAll();
	}

	@Override
	public DistrictMasterEntity findById(Long id) {
		return districtMasterRepository.findById(id).orElse(null);

}
	public List<DistrictMasterEntity> getByStateId(String stateId) {
        return districtMasterRepository.getByStateId(stateId);
}
}