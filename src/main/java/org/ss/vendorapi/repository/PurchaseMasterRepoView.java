package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.ss.vendorapi.entity.PurchaseMasterEntity;
import org.ss.vendorapi.entity.PurchaseMasterView;

@Repository
public interface PurchaseMasterRepoView extends JpaRepository<PurchaseMasterView, Long>{

}
