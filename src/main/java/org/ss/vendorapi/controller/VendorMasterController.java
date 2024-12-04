
package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
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
import org.ss.vendorapi.entity.CityMasterEntity;
import org.ss.vendorapi.entity.VendorMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.service.CityMasterService;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.VendorMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;
import org.ss.vendorapi.util.ValueConstants;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class VendorMasterController {
	
	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Autowired 
	private Environment env;
	
	@Autowired 
	private VendorMasterService vendorMasterService;
	
	
	@Autowired
	private DataValidationService dataValidationService;
	
	@Autowired
	private CityMasterService cityMasterService;
	
	
	@EncryptResponse
	@PostMapping("/addVendor")
	public ResponseEntity<?> addVendor(@RequestBody CustomerDetailsDTO addVendorMEntity,HttpServletRequest request){
		
		ResponseEntity<?> responseEntity=null;
		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);	

		Map<String,Object> statusMap= new HashMap<String,Object>();
		
		VendorMasterEntity vendorCreationEntityObj=null;
		try{

			if(UtilValidate.isEmpty(addVendorMEntity.getVendorName()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getAddress()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getCity()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getState()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getPinCode()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getDistrict()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getContactPerson()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getContactNo()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getEmail()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getGst()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getPanNo()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getTypeOfService())){
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}	

			
			vendorCreationEntityObj=new VendorMasterEntity();
			vendorCreationEntityObj.setVendorName(addVendorMEntity.getVendorName());
			vendorCreationEntityObj.setAddress(addVendorMEntity.getAddress());
			
			/** @Author :: Lata Bisht */
			/** START :::   IF THE CITY IS OTHET THEN CREATE THE RESPECTIVE CITY IN CITY MASTER AND SAVE TO THE VENDO R*/
			if(addVendorMEntity.getCity()!=null && addVendorMEntity.getCity().equals(ValueConstants.OTHER_CITY)) {
				
				CityMasterEntity cityMasterEntity=cityMasterService.createNewCity(addVendorMEntity.getOtherCityName(), addVendorMEntity.getDistrict());
				addVendorMEntity.setCity(cityMasterEntity.getId().toString());
			}
			/** END :::   IF THE CITY IS OTHET THEN CREATE THE RESPECTIVE CITY IN CITY MASTER AND SAVE TO THE VENDO R*/
			
			
			vendorCreationEntityObj.setCity(addVendorMEntity.getCity());
			vendorCreationEntityObj.setState(addVendorMEntity.getState());
			vendorCreationEntityObj.setPinCode(addVendorMEntity.getPinCode());
			vendorCreationEntityObj.setDistrict(addVendorMEntity.getDistrict());
			vendorCreationEntityObj.setContactPerson(addVendorMEntity.getContactPerson());
			vendorCreationEntityObj.setContactNo(addVendorMEntity.getContactNo());
			vendorCreationEntityObj.setEmail(addVendorMEntity.getEmail());
			vendorCreationEntityObj.setTypeOfService(addVendorMEntity.getTypeOfService());
			vendorCreationEntityObj.setGst(addVendorMEntity.getGst());
			vendorCreationEntityObj.setPanNo(addVendorMEntity.getPanNo());
			try
			{
				/* SAVE THE USER TO THE DB ENTITY */
				vendorCreationEntityObj=vendorMasterService.save(vendorCreationEntityObj);

				if(vendorCreationEntityObj!=null) {

					statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_REGISTERED_SUCCESSFULLY);
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}else {
					statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_NOT_REGISTERED);
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "RU_301");
					return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
				}
			}
			catch(Exception ex)
			{
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 2. Failed to save user in DB response : "+ex.getMessage());
				statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
				statusMap.put(Parameters.statusCode, Constants.SVD_USR);
				statusMap.put(Parameters.status, Constants.FAIL);
				return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
			}
			//}
		}
		catch(Exception ex)
		{
//			if (logger.isErrorLoggingEnabled()) {
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage() );
//			}
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
//	******************************************************************************************************************************************************************************************
//	***************************************************************************************get api***************************************************************************************************
	
	
	@EncryptResponse
	@GetMapping("/getAllVendor")
	public ResponseEntity<?> getAllVendor() {
	    try {
	        List<VendorMasterEntity> users = vendorMasterService.getAllVendor();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	



	 @EncryptResponse
	 @PutMapping("/updateVendorMaster") 
	  public ResponseEntity<?>updateVendorMaster(@RequestBody CustomerDetailsDTO addVendorEntity ){
		
			Map<String,Object> statusMap=new HashMap<String,Object>(); 
			try {
				VendorMasterEntity vendorEntity=vendorMasterService.findById(addVendorEntity.getId());
			
				vendorEntity.setVendorName(addVendorEntity.getVendorName()!=null? addVendorEntity.getVendorName():vendorEntity.getVendorName());
				vendorEntity.setAddress(addVendorEntity.getAddress()!=null?addVendorEntity.getAddress():vendorEntity.getAddress());
				vendorEntity.setCity(addVendorEntity.getCity() != null ? addVendorEntity.getCity() : vendorEntity.getCity());
				vendorEntity.setState(addVendorEntity.getState() != null ? addVendorEntity.getState() : vendorEntity.getState());
				vendorEntity.setPinCode(addVendorEntity.getPinCode() != null ? addVendorEntity.getPinCode() : vendorEntity.getPinCode());
				vendorEntity.setDistrict(addVendorEntity.getDistrict() != null ?addVendorEntity.getDistrict() : vendorEntity.getDistrict());
				vendorEntity.setContactPerson(addVendorEntity.getContactPerson() != null ?addVendorEntity.getContactPerson() : vendorEntity.getContactPerson());
				vendorEntity.setContactNo(addVendorEntity.getContactNo() != null ?addVendorEntity.getContactNo() : vendorEntity.getContactNo());
				vendorEntity.setEmail(addVendorEntity.getEmail() != null ?addVendorEntity.getEmail() : vendorEntity.getEmail());
				vendorEntity.setTypeOfService(addVendorEntity.getTypeOfService() != null ?addVendorEntity.getTypeOfService() : vendorEntity.getTypeOfService());
				vendorEntity.setGst(addVendorEntity.getGst() != null ?addVendorEntity.getGst() : vendorEntity.getGst());
				vendorEntity.setPanNo(addVendorEntity.getPanNo() != null ?addVendorEntity.getPanNo() : vendorEntity.getPanNo());

				vendorMasterService.update(vendorEntity);

				      statusMap.put("vendorMasterEntity",vendorEntity);
				      statusMap.put("status","SUCCESS"); statusMap.put("statusCode", "RU_200");
				      statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");

					return new ResponseEntity<>(statusMap,HttpStatus.OK); 
					}catch(Exception e) {
						e.printStackTrace();
					} 
			       return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
			       
	 }
		
		
	 @EncryptResponse
	 @DeleteMapping("/deleteVendor")
		 public ResponseEntity<?> deleteVendorMaster(@RequestParam Long id){
		 Map<String, Object> statusMap=new HashMap<String, Object>();
		

		try {

			VendorMasterEntity vendorMaster = vendorMasterService.findById(id);
			if(vendorMaster!=null) { vendorMaster.setActive(0);
			vendorMasterService.update(vendorMaster);

			statusMap.put("status", "SUCCESS"); statusMap.put("statusCode", "RME_200");
			statusMap.put("statusMessage", "SUCCESSFULLY DELETED"); return new
					ResponseEntity<>(statusMap,HttpStatus.OK);

			}else { 
				statusMap.put("status", "FAIL"); 
				statusMap.put("statusCode","RME_404"); 
				statusMap.put("statusMessage", "DATA NOT FOUND"); return new
				ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED); } }
		catch(Exception ex) { 
			ex.printStackTrace(); 
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(),
						HttpStatus.INTERNAL_SERVER_ERROR); 
		} 
		} 
	
}


