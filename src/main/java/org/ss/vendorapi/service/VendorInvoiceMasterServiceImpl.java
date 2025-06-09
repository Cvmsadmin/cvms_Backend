package org.ss.vendorapi.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.PayableInvoiceStatsDTO;
import org.ss.vendorapi.repository.ClientMasterRepository;
import org.ss.vendorapi.repository.VendorInvoiceMasterRepository;

@Service
public class VendorInvoiceMasterServiceImpl implements VendorInvoiceMasterService{

	@Autowired 
	private VendorInvoiceMasterRepository vendorinviceMasterRepository;
	
	@Autowired
    private ClientMasterRepository clientMasterRepository; 

	@Override
	public VendorInvoiceMasterEntity save(VendorInvoiceMasterEntity vendorInvoiceMasterEntity) {
		vendorInvoiceMasterEntity.setActive(1);
		vendorInvoiceMasterEntity.setCreateDate(new Date());
		return vendorinviceMasterRepository.save(vendorInvoiceMasterEntity);
	}

	@Override
	public List<VendorInvoiceMasterEntity> getAllVendorInvoices() {
		return vendorinviceMasterRepository.findAll();
	}

	@Override
	public VendorInvoiceMasterEntity update(VendorInvoiceMasterEntity vendorInvoiceMasterEntity) {
		vendorInvoiceMasterEntity.setUpdateDate(new Date());
		return vendorinviceMasterRepository.save(vendorInvoiceMasterEntity);
	}

	@Override
	public VendorInvoiceMasterEntity findById(Long id) {
		return vendorinviceMasterRepository.findById(id).orElse(null);
	}

	@Override
	public List<VendorInvoiceMasterEntity> findAll() {
	    return vendorinviceMasterRepository.findAll();
	}

	@Override
    public Optional<VendorInvoiceMasterEntity> findByInvoiceNo(String invoiceNo) {
        return vendorinviceMasterRepository.findByInvoiceNo(invoiceNo);
    }

	@Override
    public ClientMasterEntity findClientById(Long clientId) {
        return clientMasterRepository.findById(clientId).orElse(null); // Handle if not found
    }
	
	public PayableInvoiceStatsDTO getPayableInvoiceStats() {
//        return vendorinviceMasterRepository.getPayableInvoiceStats();
		return null;
    }
	
	
	@Override
	public Double getVendorAmountExcluGstByProjectName(String projectName) {
	    return vendorinviceMasterRepository.getVendorAmountExcluGstByProjectName(projectName);
	}

//	 @Override
//	    public Double getVendorAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate) {
//	        return vendorinviceMasterRepository.getSumByProjectNameAndDate(projectName, startDate, endDate);
//	    }


	 @Override
	    public Double getVendorAmountExcluGstByProjectNameAndDate(String projectName, LocalDate startDate, LocalDate endDate) {
	        try {
	            Double sum = vendorinviceMasterRepository.getVendorAmountExcluGstByProjectNameAndDate(projectName, startDate, endDate);
	            return (sum != null) ? sum : 0.0;
	        } catch (Exception e) {
	            e.printStackTrace();
	            return 0.0;
	        }
	    }

	 @Override
	    public List<VendorInvoiceMasterEntity> findByProjectName(String projectName) {
	        return vendorinviceMasterRepository.findByProjectName(projectName);
	    }


}
