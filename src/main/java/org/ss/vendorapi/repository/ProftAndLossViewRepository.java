package org.ss.vendorapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ss.vendorapi.entity.ProftAndlLossView;


@Repository
public interface ProftAndLossViewRepository extends JpaRepository<ProftAndlLossView, Long>{
	
	@Query(value = "SELECT * FROM get_user_profit_loss_view_dd(:managerId)", nativeQuery = true)
    List<ProftAndlLossView> getProfitAndLossDataByManagerId(@Param("managerId") Long managerId);
	
}