package org.ss.vendorapi.controller;

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
import org.ss.vendorapi.entity.VendorMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.VendorMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

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
//				    UtilValidate.isEmpty(addVendorMEntity.getPincode()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getDistrict()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getContactPerson()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getContactNo()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getEmail()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getGst()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getPanNo()) || 
				    UtilValidate.isEmpty(addVendorMEntity.getTypeOfService())){
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}	

			/** START ::: CHECK VALID MOBILE ::: THIS METHOD WILL CHECK VALID MOBILE NUMBER AND RETURN NULL IN CASE OF VALID MOBILE NUMBER  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID MOBILE NUMBER*/

//			responseEntity=dataValidationService.checkValidMobileNumber(addVendorMEntity.getContactNo(), methodName, UPPCLLogger.MODULE_REGISTRATION);
			if(responseEntity!=null)
				return responseEntity;

			/** END ::: CHECK VALID MOBILE */

			/** START ::: CHECK VALID EMAIL ::: THIS METHOD WILL CHECK VALID EMAIL AND RETURN NULL IN CASE OF VALID EMAIL ID  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID EMAIL ID */

//			responseEntity=dataValidationService.checkValidEmailId(addVendorMEntity.getEmail(), methodName, UPPCLLogger.MODULE_REGISTRATION);
			if(responseEntity!=null)
				return responseEntity;

			/** END ::: CHECK VALID EMAIl */


			vendorCreationEntityObj=new VendorMasterEntity();
			vendorCreationEntityObj.setVendorName(addVendorMEntity.getVendorName());
			vendorCreationEntityObj.setAddress(addVendorMEntity.getAddress());
			vendorCreationEntityObj.setCity(addVendorMEntity.getCity());
			vendorCreationEntityObj.setState(addVendorMEntity.getState());
			vendorCreationEntityObj.setPinCode(addVendorMEntity.getPincode());
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
	
	@GetMapping("/getAllVendor")
	public ResponseEntity<?> getAllVendor() {
	    try {
	        List<VendorMasterEntity> users = vendorMasterService.getAllVendor();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	}

