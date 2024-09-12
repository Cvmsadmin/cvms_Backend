package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.RoleResourceMasterEntity;
import org.ss.vendorapi.modal.RoleResourceDTO;
import org.ss.vendorapi.service.RoleResourceMasterService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class RoleResourceController {

	@Autowired
	private RoleResourceMasterService roleResourceService;

	@PostMapping("/createRoleResources")
	public ResponseEntity<?> roleMapping(@RequestBody RoleResourceDTO roleResourceDTO){
		Map<String, Object> statusMap=new HashMap<String, Object>();
		try {
			
			RoleResourceMasterEntity roleResourceMasterEntity=null;
			for(String featureId : roleResourceDTO.getFeatures()) {
				
				roleResourceMasterEntity= new RoleResourceMasterEntity();
				
				roleResourceMasterEntity.setRoleId(roleResourceDTO.getRoleId());
				roleResourceMasterEntity.setFeatureId(featureId);
				roleResourceService.saveResource(roleResourceMasterEntity);
			}
			statusMap.put("RoleResourceMaster",roleResourceMasterEntity);
			statusMap.put("status", "SUCCESS");
			statusMap.put("statusCode", "RME_200");
			statusMap.put("statusMessage", "SUCCESSFULLY SAVED"); 

			return new ResponseEntity<>(statusMap,HttpStatus.OK);
				     
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		statusMap.put("status", "FAILED");
		statusMap.put("statusCode", "RME_500");
		statusMap.put("statusMessage", "Failled"); 

		return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
	}
	
	
}
