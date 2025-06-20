package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ProftAndlLossView;

public interface ProftAndLossViewService {
	
	List<ProftAndlLossView> getAllProfitAndLossData();

	List<ProftAndlLossView> getProfitAndLossDataByMid(Long managerId);


}
