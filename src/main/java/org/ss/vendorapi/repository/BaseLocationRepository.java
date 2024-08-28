package org.ss.vendorapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.ss.vendorapi.entity.BaseLocationEntity;

public interface BaseLocationRepository extends JpaRepository<BaseLocationEntity, Long>{

}
