package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ResourceMasterEntity;
import org.ss.vendorapi.service.ResourceMasterService;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")

public class ResourceMasterController {


	@Autowired
	private ResourceMasterService resourceMasterService;
	
	@PostMapping("/saveRoleUrl")
	public ResponseEntity<?> roleCreated(@RequestBody ResourceMasterEntity resourceMaster){
		Map<String, Object> statusMap=new HashMap<String, Object>();
		try {
		
			if(UtilValidate.isNotEmptyAndNotNull(resourceMaster.getDescription()) &&
					UtilValidate.isNotEmptyAndNotNull(resourceMaster.getUrl()) &&
					UtilValidate.isNotEmptyAndNotNull(resourceMaster.getFeatureId())){

				resourceMaster.setDescription(resourceMaster.getDescription());
				resourceMaster.setUrl(resourceMaster.getUrl());
				resourceMaster.setFeatureId(resourceMaster.getFeatureId());

				resourceMaster=resourceMasterService.save(resourceMaster);
				
				statusMap.put("RoleMaster",resourceMaster);
				statusMap.put("status", "SUCCESS");
				statusMap.put("statusCode", "RME_200");
				statusMap.put("statusMessage", "SUCCESSFULLY SAVED"); 

				return new ResponseEntity<>(statusMap,HttpStatus.OK);
				
			}else {
				statusMap.put("status", "FAILED");
				statusMap.put("statusCode", "RME_301");
				statusMap.put("statusMessage", "Prameters Are Missing"); 
				
		     }
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		statusMap.put("status", "FAILED");
		statusMap.put("statusCode", "RME_500");
		statusMap.put("statusMessage", "NOT SAVED"); 

		return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
	} 
	@GetMapping("/getAllRolesUrl")
	public ResponseEntity<?> getAllRoleDetailsUrl(){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {
			List<ResourceMasterEntity> roleList=resourceMasterService.findAll();

			statusMap.put("ResourceMasterEntity",roleList);
			statusMap.put(Parameters.status, "Success");
			statusMap.put(Parameters.statusCode, "RME_200");
			statusMap.put(Parameters.statusMsg,"SuccessFully Found");

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
	}
}


