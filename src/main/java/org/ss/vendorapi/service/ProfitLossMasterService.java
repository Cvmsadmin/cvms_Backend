package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ProfitLossMasterEntity;

public interface ProfitLossMasterService {

	public ProfitLossMasterEntity save(ProfitLossMasterEntity profitLossMasterEntity);
	public List<ProfitLossMasterEntity> getAllProfitLoss();
}
