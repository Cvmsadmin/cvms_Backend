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
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.VendorInvioceMasterDTO;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.VendorInvoiceMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class VendorInvoiceMasterController {

	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Autowired 
	private Environment env;

	@Autowired
	private DataValidationService dataValidationService;

	@Autowired 
	private VendorInvoiceMasterService vendorInvoiceMasterService;


	//	************************************************************************************************************************************************************************************
	//	************************************************************************************************************************************************************************************


	@PostMapping("/addVendorInvoices")
	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO VendorInvoiceMEntity,HttpServletRequest request){


		ResponseEntity<?> responseEntity=null;
		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);	

		Map<String,Object> statusMap= new HashMap<String,Object>();

		VendorInvoiceMasterEntity vendorInvoiceCreationEntityObj=null;
		try{

			if(UtilValidate.isEmpty(VendorInvoiceMEntity.getVendorId()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getVendorName()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getClientName()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getProjectName()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceDate()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceNo()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getPoNo()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceDueDate()) || 
					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceDescription())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getGstPer())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceAmountExcluGst())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceAmountIncluGst())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getStatus())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getDate())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getTdsDeducted())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getAmount())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getPenalty())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getLaborCess())||
					UtilValidate.isEmpty(VendorInvoiceMEntity.getTotalAmount())
					){
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}	

			/** START ::: CHECK VALID MOBILE ::: THIS METHOD WILL CHECK VALID MOBILE NUMBER AND RETURN NULL IN CASE OF VALID MOBILE NUMBER  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID MOBILE NUMBER*/

			//			responseEntity=dataValidationService.checkValidMobileNumber(VendorInvoiceMEntity.getPhone(), methodName, UPPCLLogger.MODULE_REGISTRATION);
			//			if(responseEntity!=null)
			//				return responseEntity;

			/** END ::: CHECK VALID MOBILE */

			/** START ::: CHECK VALID EMAIL ::: THIS METHOD WILL CHECK VALID EMAIL AND RETURN NULL IN CASE OF VALID EMAIL ID  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID EMAIL ID */

			//			responseEntity=dataValidationService.checkValidEmailId(VendorInvoiceMEntity.getEmail(), methodName, UPPCLLogger.MODULE_REGISTRATION);
			//			if(responseEntity!=null)
			//				return responseEntity;

			/** END ::: CHECK VALID EMAIl */


			vendorInvoiceCreationEntityObj=new VendorInvoiceMasterEntity();
			vendorInvoiceCreationEntityObj.setVendorId(VendorInvoiceMEntity.getVendorId());
			vendorInvoiceCreationEntityObj.setVendorName(VendorInvoiceMEntity.getVendorName());
			vendorInvoiceCreationEntityObj.setClientName(VendorInvoiceMEntity.getClientName());
			vendorInvoiceCreationEntityObj.setProjectName(VendorInvoiceMEntity.getProjectName());
			vendorInvoiceCreationEntityObj.setInvoiceDate(VendorInvoiceMEntity.getInvoiceDate());
			vendorInvoiceCreationEntityObj.setInvoiceNo(VendorInvoiceMEntity.getInvoiceNo());
			vendorInvoiceCreationEntityObj.setPoNo(VendorInvoiceMEntity.getPoNo());
			vendorInvoiceCreationEntityObj.setInvoiceDueDate(VendorInvoiceMEntity.getInvoiceDueDate());
			vendorInvoiceCreationEntityObj.setInvoiceDescription(VendorInvoiceMEntity.getInvoiceDescription());
			vendorInvoiceCreationEntityObj.setGstPer(VendorInvoiceMEntity.getGstPer());
			vendorInvoiceCreationEntityObj.setInvoiceAmountExcluGst(VendorInvoiceMEntity.getInvoiceAmountExcluGst());
			vendorInvoiceCreationEntityObj.setInvoiceAmountIncluGst(VendorInvoiceMEntity.getInvoiceAmountIncluGst());
			vendorInvoiceCreationEntityObj.setStatus(VendorInvoiceMEntity.getStatus());
			vendorInvoiceCreationEntityObj.setDate(VendorInvoiceMEntity.getDate());
			vendorInvoiceCreationEntityObj.setTdsDeducted(VendorInvoiceMEntity.getTdsDeducted());
			vendorInvoiceCreationEntityObj.setAmount(VendorInvoiceMEntity.getAmount());
			vendorInvoiceCreationEntityObj.setPenalty(VendorInvoiceMEntity.getPenalty());
			vendorInvoiceCreationEntityObj.setLaborCess(VendorInvoiceMEntity.getLaborCess());
			vendorInvoiceCreationEntityObj.setTotalAmount(VendorInvoiceMEntity.getTotalAmount());
			try{
				/* SAVE THE USER TO THE DB ENTITY */
				vendorInvoiceCreationEntityObj=vendorInvoiceMasterService.save(vendorInvoiceCreationEntityObj);

				if(vendorInvoiceCreationEntityObj!=null) {

					statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}else {
					statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_NOT_GENERATED);
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

    //************************************************************************************************************************************************************************************
   //***********************************************************************************************get api **************************************************************************************
	
	
	@GetMapping("/getAllVendorInvoice")
	public ResponseEntity<?> getAllVendorInvoices() {
	    try {
	        List<VendorInvoiceMasterEntity> users = vendorInvoiceMasterService.getAllVendorInvoices();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	}
