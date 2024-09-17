package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ResourceMasterEntity;

@Repository
public interface ResourceMasterRepository extends JpaRepository<ResourceMasterEntity, Long> {

	public List<ResourceMasterEntity> findByFeatureId(String featureId);

}
