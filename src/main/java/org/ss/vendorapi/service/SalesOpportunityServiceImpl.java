package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;
import org.ss.vendorapi.repository.SalesOpportunityRepository;

@Service
public class SalesOpportunityServiceImpl implements SalesOpportunityService {

    @Autowired
    private SalesOpportunityRepository salesOpportunityRepository;
    
    @Override
    public SalesOpportunityMasterEntity save(SalesOpportunityMasterEntity salesOpportunityMaster) {
        salesOpportunityMaster.setActive(1);
        salesOpportunityMaster.setCreateDate(new java.sql.Date(new Date().getTime()));
        return salesOpportunityRepository.save(salesOpportunityMaster);
    }

    @Override
    public SalesOpportunityMasterEntity update(SalesOpportunityMasterEntity salesOpportunityMaster) {
        salesOpportunityMaster.setUpdateDate(new java.sql.Date(new Date().getTime()));
        return salesOpportunityRepository.save(salesOpportunityMaster);
    }

    @Override
    public List<SalesOpportunityMasterEntity> findAll() {
        return salesOpportunityRepository.findAll();
    }

    @Override
    public SalesOpportunityMasterEntity findById(Long id) {
        return salesOpportunityRepository.findById(id).orElse(null);
    }

}
