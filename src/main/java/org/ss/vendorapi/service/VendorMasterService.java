package org.ss.vendorapi.service;

import java.util.List;

//import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.VendorMasterEntity;

public interface VendorMasterService {
	public VendorMasterEntity save(VendorMasterEntity vendorMasterEntity);
	public List<VendorMasterEntity> getAllVendor();
	public VendorMasterEntity update(VendorMasterEntity vendorMasterEntity);
	public VendorMasterEntity findById(Long id);
}
