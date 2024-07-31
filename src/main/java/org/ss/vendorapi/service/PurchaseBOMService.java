package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;

public interface PurchaseBOMService {
	
	public PurchaseBOMMasterEntity save(PurchaseBOMMasterEntity purchaseBOMMasterEntity);
	public PurchaseBOMMasterEntity update(PurchaseBOMMasterEntity purchaseBOMMasterEntity);
	public List<PurchaseBOMMasterEntity> findAll();
	public PurchaseBOMMasterEntity findById(Long id);
	public List<PurchaseBOMMasterEntity> findByPurchaseId(String purchaseId);
}
