package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;
import org.ss.vendorapi.repository.PurchaseBOMRepository;

import jakarta.transaction.Transactional;

@Service
public class PurchaseBOMServiceImpl implements PurchaseBOMService {
    
    @Autowired
    private PurchaseBOMRepository purchaseBOMRepository;

    @Override
    public PurchaseBOMMasterEntity save(PurchaseBOMMasterEntity purchaseBOMMasterEntity) {
        purchaseBOMMasterEntity.setActive(1);
        purchaseBOMMasterEntity.setCreateDate(new java.sql.Date(new Date().getTime()));
        return purchaseBOMRepository.save(purchaseBOMMasterEntity);
    }

    @Override
    public PurchaseBOMMasterEntity update(PurchaseBOMMasterEntity purchaseBOMMasterEntity) {
        purchaseBOMMasterEntity.setUpdateDate(new java.sql.Date(new Date().getTime()));
        return purchaseBOMRepository.save(purchaseBOMMasterEntity);
    }

    @Override
    public List<PurchaseBOMMasterEntity> findAll() {
        return purchaseBOMRepository.findAll();
    }

    @Override
    public PurchaseBOMMasterEntity findById(Long id) {
        return purchaseBOMRepository.findById(id).orElse(null);
    }

    @Override
    public List<PurchaseBOMMasterEntity> findByPurchaseId(String purchaseId) {
        return purchaseBOMRepository.findByPurchaseId(purchaseId);
    }


    @Override
    @Transactional
    public void deleteByPurchaseId(String purchaseId) {
        purchaseBOMRepository.deleteByPurchaseId(purchaseId);
    }

}
