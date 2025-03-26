package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.VendorMasterEntity;


@Repository
public interface VendorMasterRepository extends JpaRepository<VendorMasterEntity, Long> {

    @Query("SELECT v FROM VendorMasterEntity v")
	List<VendorMasterEntity> findAllVendor();


}
