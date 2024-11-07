package org.ss.vendorapi.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.entity.RoleMasterEntity;
import org.ss.vendorapi.service.RoleMasterService;
import org.ss.vendorapi.service.RoleResourceMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class RoleMasterController {

	@Autowired
	private RoleMasterService roleMasterService;
	
	@Autowired
	private RoleResourceMasterService roleResourceMasterService;

	
	@EncryptResponse
	@GetMapping("/getAllRoles")
	public ResponseEntity<?> getAllRoleDetails(){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {
			List<RoleMasterEntity> roleList=roleMasterService.findAll();

			statusMap.put("RoleMasterEntity",roleList);
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

	
	@EncryptResponse
	@PostMapping("/createRole")
	public ResponseEntity<?> roleCreated(@RequestBody RoleMasterEntity role){
		Map<String, Object> statusMap=new HashMap<String, Object>();
		try {
		
			if(UtilValidate.isNotEmptyAndNotNull(role.getRoleName())) {
				
				role=roleMasterService.save(role);
				//roleResourceMasterService.saveList(role.getResourceMasterEntities(), role.getId().toString());
				
				statusMap.put("RoleMaster",role);
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


	@EncryptResponse
	@PutMapping("/updateRole")
	public ResponseEntity<?> updateRole(@RequestBody RoleMasterEntity role){
		Map<String, Object> statusMap=new HashMap<String, Object>();
		try {

			RoleMasterEntity roleMaster=roleMasterService.findById(role.getId());
			roleMaster.setRoleName(role.getRoleName()!=null?role.getRoleName():roleMaster.getRoleName());
			roleMaster.setUpdatedBy(role.getUpdatedBy()!=null?role.getUpdatedBy():roleMaster.getUpdatedBy());
			roleMaster.setCreatedBy(role.getCreatedBy()!=null?role.getCreatedBy():roleMaster.getCreatedBy());

			roleMasterService.update(roleMaster);

			statusMap.put("RoleMasterEntity",roleMaster);
			statusMap.put("status", "SUCCESS");
			statusMap.put("statusCode", "RME_200");
			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}catch(Exception e) {
			e.printStackTrace();

		}
		statusMap.put("status", "FAILED");
		statusMap.put("statusCode", "RME_500");
		statusMap.put("statusMessage", "NOT UPDATED"); 

		return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);


	}

	
	@EncryptResponse
	@DeleteMapping("/deleteRole")
	public ResponseEntity<?> deleteRole(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			RoleMasterEntity roleMaster = roleMasterService.findById(id);
			if(roleMaster!=null) {
				roleMaster.setActive(0);
				roleMasterService.update(roleMaster);

				statusMap.put("status", "SUCCESS");
				statusMap.put("statusCode", "RME_200");
				statusMap.put("statusMessage", "SUCCESSFULLY DELETED"); 
				return new ResponseEntity<>(statusMap,HttpStatus.OK);

			}else {
				statusMap.put("status", "FAIL");
				statusMap.put("statusCode", "RME_404");
				statusMap.put("statusMessage", "DATA NOT FOUND"); 
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}



	}
}
