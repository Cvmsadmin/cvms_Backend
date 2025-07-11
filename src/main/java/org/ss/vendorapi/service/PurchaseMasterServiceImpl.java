package org.ss.vendorapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.PurchaseMasterEntity;
import org.ss.vendorapi.entity.PurchaseMasterView;
import org.ss.vendorapi.repository.PurchaseMasterRepoView;
import org.ss.vendorapi.repository.PurchaseMasterRepository;

@Service
public class PurchaseMasterServiceImpl implements PurchaseMasterService{
	
	@Autowired 
	private PurchaseMasterRepository purchaseMasterRepository;
	
	@Autowired 
	private PurchaseMasterRepoView purchaseMasterRepoView;


	@Override
	public List<PurchaseMasterEntity> findAll(){
		return purchaseMasterRepository.findAll();
	}

	@Override
	public PurchaseMasterEntity save(PurchaseMasterEntity purchaseMasterEntity) {
		return purchaseMasterRepository.save(purchaseMasterEntity);
	}

	@Override
	public PurchaseMasterEntity findById(Long id) {
		return purchaseMasterRepository.findById(id).orElse(null);
	}

	@Override
	public PurchaseMasterEntity update(PurchaseMasterEntity purchaseMasterEntity) {
		return purchaseMasterRepository.save(purchaseMasterEntity);
	}

	@Override
	public void savePurchase(PurchaseMasterEntity purchaseMaster) {
		purchaseMasterRepository.save(purchaseMaster);
		
	}

	@Override
    public PurchaseMasterEntity findByPrNo(String prNo) {
        return purchaseMasterRepository.findByPrNo(prNo);
    }

	@Override
	public PurchaseMasterEntity findByPoNo(String poNo) {	
		return purchaseMasterRepository.findByPoNo(poNo);
	}


	public List<PurchaseMasterEntity> findByProjectName(String projectName) {
	    return purchaseMasterRepository.findByProjectName(projectName);
	}

	@Override
	public List<PurchaseMasterView> findAll1() {
		return purchaseMasterRepoView.findAll();
	}
	
	@Override
	public Optional<PurchaseMasterEntity> findOptionalByPoNo(String poNo) {
	    return Optional.ofNullable(purchaseMasterRepository.findByPoNo(poNo));
	}

//	@Override
//	public List<PurchaseMasterEntity> findByProjectId(Long projectId) {
//	    return purchaseMasterRepository.findByProjectId(projectId);
//	}




//	@Override
//	public String getPoNoByProjectName(String projectName) {
//        return purchaseMasterRepository.findPoNoByProjectName(projectName);
//    }
}