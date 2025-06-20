package org.ss.vendorapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ProftAndlLossView;
import org.ss.vendorapi.repository.ProftAndLossViewRepository;

@Service
public class ProftAndLossViewServiceImpl implements ProftAndLossViewService {
	
	 @Autowired
	    private ProftAndLossViewRepository repository;

	    @Override
	    public List<ProftAndlLossView> getAllProfitAndLossData() {
	        return repository.findAll();
	    }


	    public List<ProftAndlLossView> getProfitAndLossDataByMid(Long managerId) {
	        return repository.getProfitAndLossDataByManagerId(managerId);
	    }	    
	    
}
