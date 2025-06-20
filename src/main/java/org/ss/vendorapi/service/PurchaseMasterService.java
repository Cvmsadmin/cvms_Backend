package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.ss.vendorapi.entity.PurchaseMasterEntity;
import org.ss.vendorapi.entity.PurchaseMasterView;

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
	List<PurchaseMasterView> findAll1();
	Optional<PurchaseMasterEntity> findOptionalByPoNo(String poNo);
	
//	public List<PurchaseMasterEntity> findByProjectId(Long projectId);
	
}
