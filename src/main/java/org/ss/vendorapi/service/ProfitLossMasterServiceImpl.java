package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.repository.ProfitLossMasterRepository;

@Service
public class ProfitLossMasterServiceImpl implements ProfitLossMasterService{
	
	@Autowired 
	private ProfitLossMasterRepository profitLossMasterRepository;

	
	
	@Override
	public ProfitLossMasterEntity save(ProfitLossMasterEntity profitLossMasterEntity) {
		return profitLossMasterRepository.save(profitLossMasterEntity);
	}



	@Override
	public List<ProfitLossMasterEntity> findAll() {
				return profitLossMasterRepository.findAll();
	}



	@Override
	public ProfitLossMasterEntity update(ProfitLossMasterEntity profitLossMasterEntity) {
		return profitLossMasterRepository.save(profitLossMasterEntity);
	}



	@Override
	public ProfitLossMasterEntity findById(Long id) {
		return profitLossMasterRepository.findById(id).orElse(null);
	}

}
