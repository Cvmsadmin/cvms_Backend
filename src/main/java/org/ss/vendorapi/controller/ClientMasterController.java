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
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.service.ClientMasterService;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class ClientMasterController {
	
	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Autowired 
	private Environment env;
	
	@Autowired private ClientMasterService clientMasterService;
	
	
	
	@Autowired
	private DataValidationService dataValidationService;
	
	
	@PostMapping("/addClient")
	public ResponseEntity<?> addClient(@RequestBody CustomerDetailsDTO addClientMEntity,HttpServletRequest request){


		ResponseEntity<?> responseEntity=null;
		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);	

		Map<String,Object> statusMap= new HashMap<String,Object>();

		ClientMasterEntity clientCreationEntityObj=null;
		try{

			if(UtilValidate.isEmpty(addClientMEntity.getClientName()) || 
				    UtilValidate.isEmpty(addClientMEntity.getAddress()) || 
				    UtilValidate.isEmpty(addClientMEntity.getCity()) || 
				    UtilValidate.isEmpty(addClientMEntity.getState()) || 
				    UtilValidate.isEmpty(addClientMEntity.getPincode()) || 
				    UtilValidate.isEmpty(addClientMEntity.getDistrict()) || 
				    UtilValidate.isEmpty(addClientMEntity.getContactPerson()) || 
				    UtilValidate.isEmpty(addClientMEntity.getContactNo()) || 
				    UtilValidate.isEmpty(addClientMEntity.getEmail()) || 
				    UtilValidate.isEmpty(addClientMEntity.getGst()) || 
				    UtilValidate.isEmpty(addClientMEntity.getPan()) || 
				    UtilValidate.isEmpty(addClientMEntity.getTypeOfService()) || 
				    UtilValidate.isEmpty(addClientMEntity.getACcountManager())){
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}	

			/** START ::: CHECK VALID MOBILE ::: THIS METHOD WILL CHECK VALID MOBILE NUMBER AND RETURN NULL IN CASE OF VALID MOBILE NUMBER  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID MOBILE NUMBER*/

//			responseEntity=dataValidationService.checkValidMobileNumber(addClientMEntity.getContactNo(), methodName, UPPCLLogger.MODULE_REGISTRATION);
			if(responseEntity!=null)
				return responseEntity;

			
//			responseEntity=dataValidationService.checkValidEmailId(addClientMEntity.getEmail(), methodName, UPPCLLogger.MODULE_REGISTRATION);
			if(responseEntity!=null)
				return responseEntity;

			

			clientCreationEntityObj=new ClientMasterEntity();
			clientCreationEntityObj.setClientName(addClientMEntity.getClientName());
			clientCreationEntityObj.setAddress(addClientMEntity.getAddress());
			clientCreationEntityObj.setCity(addClientMEntity.getCity());
			clientCreationEntityObj.setState(addClientMEntity.getState());
			clientCreationEntityObj.setPincode(addClientMEntity.getPincode());
			clientCreationEntityObj.setDistrict(addClientMEntity.getDistrict());
			clientCreationEntityObj.setContactPerson(addClientMEntity.getContactPerson());
			clientCreationEntityObj.setContactNo(addClientMEntity.getContactNo());
			clientCreationEntityObj.setEmail(addClientMEntity.getEmail());
			clientCreationEntityObj.setGst(addClientMEntity.getGst());
			clientCreationEntityObj.setPan(addClientMEntity.getPan());
			clientCreationEntityObj.setTypeOfService(addClientMEntity.getTypeOfService());
			clientCreationEntityObj.setAccountManager(addClientMEntity.getACcountManager());
			try
			{
				/* SAVE THE USER TO THE DB ENTITY */
				clientCreationEntityObj=clientMasterService.save(clientCreationEntityObj);

				if(clientCreationEntityObj!=null) {

					statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_REGISTERED_SUCCESSFULLY);
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}else {
					statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_NOT_REGISTERED);
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
//	******************************************************************************************************************************************************************************************
	
	@GetMapping("/getAllClient")	
    public ResponseEntity<?> getAllClient() {
	    try {
	        List<ClientMasterEntity> users = clientMasterService.getAllClient();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	

}
