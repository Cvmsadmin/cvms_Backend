package org.ss.vendorapi.service;

import java.util.List;

import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.entity.PurchaseMasterEntity;

public interface ProfitLossMasterService {

	public ProfitLossMasterEntity save(ProfitLossMasterEntity profitLossMasterEntity);
	public List<ProfitLossMasterEntity> findAll();
	public ProfitLossMasterEntity update(ProfitLossMasterEntity profitLossMasterEntity);
    public ProfitLossMasterEntity findById(Long id);
    public List<ProfitLossMasterEntity> findByWhere(String where);
	public List<ProfitLossMasterEntity> findByProjectId(int projectId);
}
