package org.ss.vendorapi.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.repository.VendorInvoiceMasterRepository;

@Service
public class VendorInvoiceMasterServiceImpl implements VendorInvoiceMasterService{

	@Autowired 
	private VendorInvoiceMasterRepository vendorinviceMasterRepository;



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



}
