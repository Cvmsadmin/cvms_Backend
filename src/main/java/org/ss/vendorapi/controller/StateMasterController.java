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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.ss.vendorapi.entity.StateMasterEntity;
import org.ss.vendorapi.service.StateMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class StateMasterController {

	@Autowired
	private StateMasterService stateMasterService;
	
	  @PostMapping("/createState")
	    public ResponseEntity<?> createState(@RequestBody StateMasterEntity stateMasterDTO){
		  Map<String,Object> statusMap= new HashMap<String,Object>();
		   
		  try {
			  if(UtilValidate.isEmpty(stateMasterDTO.getStateName())) {
				  return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			  }
			  StateMasterEntity stateMaster=new StateMasterEntity();
			  stateMaster.setStateName(stateMasterDTO.getStateName());
			  
			  stateMaster=stateMasterService.save(stateMaster);
			  
			  if(stateMaster!=null) {
					statusMap.put(Parameters.statusMsg,  StatusMessageConstants.STATE_CREATED_SUCCESSFULLY);
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}else {
					statusMap.put(Parameters.statusMsg,  StatusMessageConstants.STATE_NOT_CREATED);
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "RU_301");
					return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
		  }
		  }
			  catch(Exception ex) {
				  return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR); 
			  }
		  }
	  
	  @GetMapping("/getAllState")
		 public ResponseEntity<?> getAllState() {
			    try {
			    	Map<String,Object> statusMap=new HashMap<>();
			        List<StateMasterEntity> stateList = stateMasterService.findAll();
			        
			        statusMap.put("StateMasterEntity",stateList);
			        
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
			    } catch (Exception ex) {
			        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			    }
			}
	  @GetMapping("/getStateById")
		 public ResponseEntity<?> getAllState(@RequestParam Long id) {
			Map<String,Object> statusMap=new HashMap<>();
			  try {
				  StateMasterEntity stateMasterEntityList=stateMasterService.findById(id);
				  statusMap.put("stateMasterEntity",stateMasterEntityList);
			        
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
	  }catch(Exception ex) {
		  return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	  }
