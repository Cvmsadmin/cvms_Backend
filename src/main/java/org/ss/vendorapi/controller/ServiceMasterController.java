package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.entity.ServiceMasterEntity;
import org.ss.vendorapi.modal.ServiceMasterDTO;
import org.ss.vendorapi.service.ServiceMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class ServiceMasterController {
	
	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Autowired 
	private Environment env;

	
	@Autowired 
	private ServiceMasterService serviceMasterService;
	
	

	
	
//	************************************************************************
	
	
	@EncryptResponse
	@PostMapping("/addService")
	public ResponseEntity<?> addService(@RequestBody List<ServiceMasterDTO> serviceList, HttpServletRequest request) {
	    String methodName = request.getRequestURI();
//	    logger.logMethodStart(methodName);

	    Map<String, Object> statusMap = new HashMap<>();
	    List<ServiceMasterEntity> savedEntities = new ArrayList<>();

	    try {
	        for (ServiceMasterDTO serviceMasterDTO : serviceList) {
	            if (UtilValidate.isEmpty(serviceMasterDTO.getSrNo()) ||
	            		UtilValidate.isEmpty(serviceMasterDTO.getServiceName())) {
	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	            }

	            ServiceMasterEntity serviceCreationEntityObj = new ServiceMasterEntity();
	            serviceCreationEntityObj.setSrNo(serviceMasterDTO.getSrNo());
	            serviceCreationEntityObj.setServiceName(serviceMasterDTO.getServiceName());
	           

	            try {
	                serviceCreationEntityObj = serviceMasterService.save(serviceCreationEntityObj);
	                savedEntities.add(serviceCreationEntityObj);
	            } catch (Exception ex) {
//	                logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Failed to save user in DB: " + ex.getMessage());
	                statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
	                statusMap.put(Parameters.statusCode, Constants.SVD_USR);
	                statusMap.put(Parameters.status, Constants.FAIL);
	                return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	            }
	        }

	        if (!savedEntities.isEmpty()) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.SERVICE_CREATED_SUCCESSFULLY);
	            statusMap.put(Parameters.status, Constants.SUCCESS);
	            statusMap.put(Parameters.statusCode, "RU_200");
	            return new ResponseEntity<>(statusMap, HttpStatus.OK);
	        } else {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.SERVICE_NOT_CREATED);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_301");
	            return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
	        }
	    } catch (Exception ex) {
//	        if (logger.isErrorLoggingEnabled()) {
//	            logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Exception when processing service list: " + ex.getMessage());
//	        }
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
//	********************************************************
//	**********************get api ******************************
	
	
	@EncryptResponse
	@GetMapping("/getAllService")	
	public ResponseEntity<?> getAllService() {
	    try {
	        List<ServiceMasterEntity> users = serviceMasterService.findAll();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
}