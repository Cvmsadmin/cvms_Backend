package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;

@Repository
public interface PurchaseBOMRepository extends JpaRepository<PurchaseBOMMasterEntity, Long>{

	public List<PurchaseBOMMasterEntity> findByPurchaseId(String purchaseId);
}
