package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;

public interface ProfitLossMasterRepository extends JpaRepository<ProfitLossMasterEntity, Long>{

	 @Query("SELECT p FROM ProfitLossMasterEntity p WHERE p.projectId = :projectId AND p.active = 1")
	    List<ProfitLossMasterEntity> findByProjectId(@Param("projectId") int projectId);

}
