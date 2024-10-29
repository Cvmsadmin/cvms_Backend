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
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
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


//	@PostMapping("/addVendorInvoices")
//	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO VendorInvoiceMEntity,HttpServletRequest request){
//
//
//		ResponseEntity<?> responseEntity=null;
//		String methodName = request.getRequestURI();
////		logger.logMethodStart(methodName);	
//
//		Map<String,Object> statusMap= new HashMap<String,Object>();
//
//		VendorInvoiceMasterEntity vendorInvoiceCreationEntityObj=null;
//		try{
//
//			if(UtilValidate.isEmpty(VendorInvoiceMEntity.getVendorId()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getVendorName()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getClientName()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getProjectName()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceDate()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceNo()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getPoNo()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceDueDate()) || 
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceDescription())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getGstPer())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceAmountExcluGst())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getInvoiceAmountIncluGst())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getStatus())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getDate())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getTdsDeducted())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getAmount())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getPenalty())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getLaborCess())||
//					UtilValidate.isEmpty(VendorInvoiceMEntity.getTotalAmount())
//					){
//				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//			}	
//
//
//			vendorInvoiceCreationEntityObj=new VendorInvoiceMasterEntity();
//			vendorInvoiceCreationEntityObj.setVendorId(VendorInvoiceMEntity.getVendorId());
//			vendorInvoiceCreationEntityObj.setVendorName(VendorInvoiceMEntity.getVendorName());
//			vendorInvoiceCreationEntityObj.setClientName(VendorInvoiceMEntity.getClientName());
//			vendorInvoiceCreationEntityObj.setProjectName(VendorInvoiceMEntity.getProjectName());
//			vendorInvoiceCreationEntityObj.setInvoiceDate(VendorInvoiceMEntity.getInvoiceDate());
//			vendorInvoiceCreationEntityObj.setInvoiceNo(VendorInvoiceMEntity.getInvoiceNo());
//			vendorInvoiceCreationEntityObj.setPoNo(VendorInvoiceMEntity.getPoNo());
//			vendorInvoiceCreationEntityObj.setInvoiceDueDate(VendorInvoiceMEntity.getInvoiceDueDate());
//			vendorInvoiceCreationEntityObj.setInvoiceDescription(VendorInvoiceMEntity.getInvoiceDescription());
//			vendorInvoiceCreationEntityObj.setGstPer(VendorInvoiceMEntity.getGstPer());
//			vendorInvoiceCreationEntityObj.setInvoiceAmountExcluGst(VendorInvoiceMEntity.getInvoiceAmountExcluGst());
//			vendorInvoiceCreationEntityObj.setInvoiceAmountIncluGst(VendorInvoiceMEntity.getInvoiceAmountIncluGst());
//			vendorInvoiceCreationEntityObj.setStatus(VendorInvoiceMEntity.getStatus());
//			vendorInvoiceCreationEntityObj.setDate(VendorInvoiceMEntity.getDate());
//			vendorInvoiceCreationEntityObj.setTdsDeducted(VendorInvoiceMEntity.getTdsDeducted());
//			vendorInvoiceCreationEntityObj.setAmount(VendorInvoiceMEntity.getAmount());
//			vendorInvoiceCreationEntityObj.setPenalty(VendorInvoiceMEntity.getPenalty());
//			vendorInvoiceCreationEntityObj.setLaborCess(VendorInvoiceMEntity.getLaborCess());
//			vendorInvoiceCreationEntityObj.setTotalAmount(VendorInvoiceMEntity.getTotalAmount());
//			try{
//				/* SAVE THE USER TO THE DB ENTITY */
//				vendorInvoiceCreationEntityObj=vendorInvoiceMasterService.save(vendorInvoiceCreationEntityObj);
//
//				if(vendorInvoiceCreationEntityObj!=null) {
//
//					statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
//					statusMap.put(Parameters.status, Constants.SUCCESS);
//					statusMap.put(Parameters.statusCode, "RU_200");
//					return new ResponseEntity<>(statusMap,HttpStatus.OK);
//				}else {
//					statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_NOT_GENERATED);
//					statusMap.put(Parameters.status, Constants.FAIL);
//					statusMap.put(Parameters.statusCode, "RU_301");
//					return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
//				}
//			}
//			catch(Exception ex)
//			{
////				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 2. Failed to save user in DB response : "+ex.getMessage());
//				statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//				statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//				statusMap.put(Parameters.status, Constants.FAIL);
//				return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
//			}
//			//}
//		}
//		catch(Exception ex)
//		{
////			if (logger.isErrorLoggingEnabled()) {
////				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage() );
////			}
//			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
	
	@EncryptResponse
	@PostMapping("/addVendorInvoices")
	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO vendorInvoiceDTO, HttpServletRequest request) {

	    ResponseEntity<?> responseEntity = null;
	    String methodName = request.getRequestURI();
	    // logger.logMethodStart(methodName);

	    Map<String, Object> statusMap = new HashMap<>();

	   
	    try {

	        if (UtilValidate.isEmpty(vendorInvoiceDTO.getVendorName()) || 
	            UtilValidate.isEmpty(vendorInvoiceDTO.getClientName()) || 
	            UtilValidate.isEmpty(vendorInvoiceDTO.getProjectName()) ||
	            vendorInvoiceDTO.getInvoiceDate()==null||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceNo()) || 
	            UtilValidate.isEmpty(vendorInvoiceDTO.getPoNo()) || 
	            vendorInvoiceDTO.getInvoiceDueDate()==null || 
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceDescription()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstPer()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountExcluGst()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountIncluGst()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getStatus()) ||
	            vendorInvoiceDTO.getDate()==null ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getTdsDeducted()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getAmount()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getPenalty()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getLaborCess()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalAmount())){

	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        VendorInvoiceMasterEntity vendorInvoiceMaster = new VendorInvoiceMasterEntity();
	        
	        // Removed vendorInvoiceCreationEntityObj.setVendorId(VendorInvoiceMEntity.getVendorId());
	        vendorInvoiceMaster.setVendorName(vendorInvoiceDTO.getVendorName());
	        vendorInvoiceMaster.setClientName(vendorInvoiceDTO.getClientName());
	        vendorInvoiceMaster.setProjectName(vendorInvoiceDTO.getProjectName());
	        vendorInvoiceMaster.setInvoiceDate(vendorInvoiceDTO.getInvoiceDate());
	        vendorInvoiceMaster.setInvoiceNo(vendorInvoiceDTO.getInvoiceNo());
	        vendorInvoiceMaster.setPoNo(vendorInvoiceDTO.getPoNo());
	        vendorInvoiceMaster.setInvoiceDueDate(vendorInvoiceDTO.getInvoiceDueDate());
	        vendorInvoiceMaster.setInvoiceDescription(vendorInvoiceDTO.getInvoiceDescription());
	        vendorInvoiceMaster.setGstPer(vendorInvoiceDTO.getGstPer());
	        vendorInvoiceMaster.setInvoiceAmountExcluGst(vendorInvoiceDTO.getInvoiceAmountExcluGst());
	        vendorInvoiceMaster.setInvoiceAmountIncluGst(vendorInvoiceDTO.getInvoiceAmountIncluGst());
	        vendorInvoiceMaster.setStatus(vendorInvoiceDTO.getStatus());
	        vendorInvoiceMaster.setDate(vendorInvoiceDTO.getDate());
	        vendorInvoiceMaster.setTdsDeducted(vendorInvoiceDTO.getTdsDeducted());
	        vendorInvoiceMaster.setAmount(vendorInvoiceDTO.getAmount());
	        vendorInvoiceMaster.setPenalty(vendorInvoiceDTO.getPenalty());
	        vendorInvoiceMaster.setLaborCess(vendorInvoiceDTO.getLaborCess());
	        vendorInvoiceMaster.setTotalAmount(vendorInvoiceDTO.getTotalAmount());

	        try {
	            /* SAVE THE USER TO THE DB ENTITY */
	        	vendorInvoiceMaster = vendorInvoiceMasterService.save(vendorInvoiceMaster);

	            if (vendorInvoiceMaster != null) {

	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
	                statusMap.put(Parameters.status, Constants.SUCCESS);
	                statusMap.put(Parameters.statusCode, "RU_200");
	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
	            } else {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_NOT_GENERATED);
	                statusMap.put(Parameters.status, Constants.FAIL);
	                statusMap.put(Parameters.statusCode, "RU_301");
	                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
	            }
	        } catch (Exception ex) {
	            // logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 2. Failed to save user in DB response : " + ex.getMessage());
	            statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
	            statusMap.put(Parameters.statusCode, Constants.SVD_USR);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    } catch (Exception ex) {
	        // if (logger.isErrorLoggingEnabled()) {
	        // logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage());
	        // }
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	

    //************************************************************************************************************************************************************************************
   //***********************************************************************************************get api **************************************************************************************
	
	@EncryptResponse
	@GetMapping("/getAllVendorInvoice")
	public ResponseEntity<?> getAllVendorInvoices() {
	    try {
	        List<VendorInvoiceMasterEntity> users = vendorInvoiceMasterService.getAllVendorInvoices();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	@EncryptResponse
	@PutMapping("/updateVendorInvoiceMaster")
	public ResponseEntity<?>updateVendorMaster(@RequestBody VendorInvioceMasterDTO vendorInvioceMasterDTO ){

		Map<String,Object> statusMap=new HashMap<String,Object>();
		try {
			VendorInvoiceMasterEntity vendorInvoiceEntity=vendorInvoiceMasterService.findById(vendorInvioceMasterDTO.getId());
			
			
			vendorInvoiceEntity.setVendorName(vendorInvioceMasterDTO.getVendorName() != null ? vendorInvioceMasterDTO.getVendorName() : vendorInvoiceEntity.getVendorName());
			vendorInvoiceEntity.setClientName(vendorInvioceMasterDTO.getClientName() != null ? vendorInvioceMasterDTO.getClientName() : vendorInvoiceEntity.getClientName());
			vendorInvoiceEntity.setProjectName(vendorInvioceMasterDTO.getProjectName() != null ? vendorInvioceMasterDTO.getProjectName() : vendorInvoiceEntity.getProjectName());
			vendorInvoiceEntity.setInvoiceDate(vendorInvioceMasterDTO.getInvoiceDate() != null ? vendorInvioceMasterDTO.getInvoiceDate() : vendorInvoiceEntity.getInvoiceDate());
			vendorInvoiceEntity.setInvoiceNo(vendorInvioceMasterDTO.getInvoiceNo() != null ? vendorInvioceMasterDTO.getInvoiceNo() : vendorInvoiceEntity.getInvoiceNo());
			vendorInvoiceEntity.setPoNo(vendorInvioceMasterDTO.getPoNo() != null ? vendorInvioceMasterDTO.getPoNo() : vendorInvoiceEntity.getPoNo());
			vendorInvoiceEntity.setInvoiceDueDate(vendorInvioceMasterDTO.getInvoiceDueDate() != null ? vendorInvioceMasterDTO.getInvoiceDueDate() : vendorInvoiceEntity.getInvoiceDueDate());
			vendorInvoiceEntity.setInvoiceDescription(vendorInvioceMasterDTO.getInvoiceDescription() != null ? vendorInvioceMasterDTO.getInvoiceDescription() : vendorInvoiceEntity.getInvoiceDescription());
			vendorInvoiceEntity.setGstPer(vendorInvioceMasterDTO.getGstPer() != null ? vendorInvioceMasterDTO.getGstPer() : vendorInvoiceEntity.getGstPer());
			vendorInvoiceEntity.setInvoiceAmountExcluGst(vendorInvioceMasterDTO.getInvoiceAmountExcluGst() != null ? vendorInvioceMasterDTO.getInvoiceAmountExcluGst() : vendorInvoiceEntity.getInvoiceAmountExcluGst());
			vendorInvoiceEntity.setInvoiceAmountIncluGst(vendorInvioceMasterDTO.getInvoiceAmountIncluGst() != null ? vendorInvioceMasterDTO.getInvoiceAmountIncluGst() : vendorInvoiceEntity.getInvoiceAmountIncluGst());
			vendorInvoiceEntity.setStatus(vendorInvioceMasterDTO.getStatus() != null ? vendorInvioceMasterDTO.getStatus() : vendorInvoiceEntity.getStatus());
			vendorInvoiceEntity.setDate(vendorInvioceMasterDTO.getDate() != null ? vendorInvioceMasterDTO.getDate() : vendorInvoiceEntity.getDate());
			vendorInvoiceEntity.setTdsDeducted(vendorInvioceMasterDTO.getTdsDeducted() != null ? vendorInvioceMasterDTO.getTdsDeducted() : vendorInvoiceEntity.getTdsDeducted());
			vendorInvoiceEntity.setAmount(vendorInvioceMasterDTO.getAmount() != null ? vendorInvioceMasterDTO.getAmount() : vendorInvoiceEntity.getAmount());
			vendorInvoiceEntity.setPenalty(vendorInvioceMasterDTO.getPenalty() != null ? vendorInvioceMasterDTO.getPenalty() : vendorInvoiceEntity.getPenalty());
			vendorInvoiceEntity.setLaborCess(vendorInvioceMasterDTO.getLaborCess() != null ? vendorInvioceMasterDTO.getLaborCess() : vendorInvoiceEntity.getLaborCess());
			vendorInvoiceEntity.setTotalAmount(vendorInvioceMasterDTO.getTotalAmount() != null ? vendorInvioceMasterDTO.getTotalAmount() : vendorInvoiceEntity.getTotalAmount());
			
			vendorInvoiceMasterService.update(vendorInvoiceEntity);


			statusMap.put("purchaseMasterEntity",vendorInvoiceEntity);
			statusMap.put("status", "SUCCESS");
			statusMap.put("statusCode", "RU_200");
			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}catch(Exception e){
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		}
	
	
	@EncryptResponse
	@DeleteMapping("/deleteVendorInvoice")
	public ResponseEntity<?> deleteVendorInvoiceMaster(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			VendorInvoiceMasterEntity vendorInvoiceMaster = vendorInvoiceMasterService.findById(id);
			if(vendorInvoiceMaster!=null) {
				vendorInvoiceMaster.setActive(0);
				vendorInvoiceMasterService.update(vendorInvoiceMaster);

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
