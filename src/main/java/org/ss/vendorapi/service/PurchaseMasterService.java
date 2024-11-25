package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.PurchaseMasterEntity;

public interface PurchaseMasterService {


	public PurchaseMasterEntity save(PurchaseMasterEntity purchaseMasterEntity);
	public PurchaseMasterEntity update(PurchaseMasterEntity purchaseMasterEntity);
	public List<PurchaseMasterEntity> findAll();
	public PurchaseMasterEntity findById(Long id);

	public PurchaseMasterEntity findByPrNo(String prNo);
	public PurchaseMasterEntity findByPoNo(String poNo);
	public void savePurchase(PurchaseMasterEntity purchaseMaster);
//	public String getPoNoByProjectName(String projectName);
	public List<PurchaseMasterEntity> findByProjectName(String projectName);
	
	
}
