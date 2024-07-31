package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.PurchaseMasterEntity;
import org.ss.vendorapi.repository.PurchaseMasterRepository;

@Service
public class PurchaseMasterServiceImpl implements PurchaseMasterService{
	
	@Autowired 
	private PurchaseMasterRepository purchaseMasterRepository;

	
	@Override
	public List<PurchaseMasterEntity> findAll(){
		return purchaseMasterRepository.findAll();
	}



	@Override
	public PurchaseMasterEntity save(PurchaseMasterEntity purchaseMasterEntity) {
		return purchaseMasterRepository.save(purchaseMasterEntity);
	}



	@Override
	public PurchaseMasterEntity findById(Long id) {
		return purchaseMasterRepository.findById(id).orElse(null);
	}



	@Override
	public PurchaseMasterEntity update(PurchaseMasterEntity purchaseMasterEntity) {
		return purchaseMasterRepository.save(purchaseMasterEntity);
	}

}
