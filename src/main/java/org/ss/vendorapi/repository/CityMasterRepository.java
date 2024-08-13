package org.ss.vendorapi.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.CityMasterEntity;


@Repository
public interface CityMasterRepository extends JpaRepository<CityMasterEntity, Long>{
	 List<CityMasterEntity> getByDistrictId(String districtId);

}
