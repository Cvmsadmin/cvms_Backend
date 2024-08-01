package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.MilestoneMasterEntity;
import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;

@Repository
public interface MilestoneMasterRepository extends JpaRepository<MilestoneMasterEntity, Long> {
	public List<MilestoneMasterEntity> findByProjectId(String projectId);
}
