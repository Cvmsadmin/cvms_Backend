package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.VendorMasterEntity;


@Repository
public interface VendorMasterRepository extends JpaRepository<VendorMasterEntity, Long> {

}
