package org.ss.vendorapi.controller;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
//import org.ss.vendorapi.entity.DescriptionAndBaseValue;
import org.ss.vendorapi.entity.InvoiceDescriptionValue;
import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;
import org.ss.vendorapi.entity.VendorInvoiceMasterEntity;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.VendorInvioceMasterDTO;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.InvoiceDescriptionValueService;
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
	private InvoiceDescriptionValueService invoiceDescriptionValueService;

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
	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO vendorInvoiceDTO) {
	    Map<String, Object> statusMap = new HashMap<>();
	    try {
	        // Validate mandatory fields
	        if (UtilValidate.isEmpty(vendorInvoiceDTO.getVendorName()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getClientName()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getProjectName()) ||
	            vendorInvoiceDTO.getInvoiceDate() == null ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceNo()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getPoNo()) ||
	            vendorInvoiceDTO.getInvoiceDueDate() == null ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceDescription()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountIncluGst()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmtIncluGst()) ||
	            UtilValidate.isEmpty(vendorInvoiceDTO.getStatus())) {
	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }
	        
	     // Check if invoice with the same invoiceNo already exists
	        VendorInvoiceMasterEntity existingInvoice = vendorInvoiceMasterService.findByInvoiceNo(vendorInvoiceDTO.getInvoiceNo()).orElse(null);
	        if (existingInvoice != null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Invoice with this Invoice No. already exists.", HttpStatus.CONFLICT);
	        }
	        // Map DTO to Entity
	        VendorInvoiceMasterEntity vendorInvoiceMaster = new VendorInvoiceMasterEntity();
	        vendorInvoiceMaster.setVendorName(vendorInvoiceDTO.getVendorName());
	        vendorInvoiceMaster.setClientName(vendorInvoiceDTO.getClientName());
	        vendorInvoiceMaster.setProjectName(vendorInvoiceDTO.getProjectName());
	        vendorInvoiceMaster.setInvoiceDate(vendorInvoiceDTO.getInvoiceDate());
	        vendorInvoiceMaster.setInvoiceNo(vendorInvoiceDTO.getInvoiceNo());
	        vendorInvoiceMaster.setPoNo(vendorInvoiceDTO.getPoNo());
	        vendorInvoiceMaster.setInvoiceDueDate(vendorInvoiceDTO.getInvoiceDueDate());
	        vendorInvoiceMaster.setInvoiceDescription(vendorInvoiceDTO.getInvoiceDescription());
	        vendorInvoiceMaster.setInvoiceAmountIncluGst(vendorInvoiceDTO.getInvoiceAmountIncluGst());
	        vendorInvoiceMaster.setInvoiceAmtIncluGst(Double.valueOf(vendorInvoiceDTO.getInvoiceAmtIncluGst()));
	        vendorInvoiceMaster.setStatus(vendorInvoiceDTO.getStatus());
	        
	        vendorInvoiceMaster.setStartDate(vendorInvoiceDTO.getStartDate());
	        vendorInvoiceMaster.setEndDate(vendorInvoiceDTO.getEndDate());
	        vendorInvoiceMaster.setModeOfPayment(vendorInvoiceDTO.getModeOfPayment());

	        // Optional fields
	        vendorInvoiceMaster.setInvoiceBaseValue(vendorInvoiceDTO.getInvoiceBaseValue());
	        vendorInvoiceMaster.setGstBaseValue(vendorInvoiceDTO.getGstBaseValue());
	        vendorInvoiceMaster.setInvoiceInclusiveOfGst(vendorInvoiceDTO.getInvoiceInclusiveOfGst());
	        vendorInvoiceMaster.setTdsBaseValue(vendorInvoiceDTO.getTdsBaseValue());
	        vendorInvoiceMaster.setTdsPer(vendorInvoiceDTO.getTdsPer());
	        vendorInvoiceMaster.setTdsOnGst(vendorInvoiceDTO.getTdsOnGst());
	        vendorInvoiceMaster.setIgstOnTds(vendorInvoiceDTO.getIgstOnTds());
	        vendorInvoiceMaster.setCgstOnTds(vendorInvoiceDTO.getCgstOnTds());
	        vendorInvoiceMaster.setSgstOnTds(vendorInvoiceDTO.getSgstOnTds());
	        vendorInvoiceMaster.setTotalTdsDeducted(vendorInvoiceDTO.getTotalTdsDeducted());
	        vendorInvoiceMaster.setBalance(vendorInvoiceDTO.getBalance());
	        vendorInvoiceMaster.setPenalty(vendorInvoiceDTO.getPenalty());
	        vendorInvoiceMaster.setPenaltyDeductionOnBase(vendorInvoiceDTO.getPenaltyDeductionOnBase());
	        vendorInvoiceMaster.setGstOnPenalty(vendorInvoiceDTO.getGstOnPenalty());
	        vendorInvoiceMaster.setTotalPenaltyDeduction(vendorInvoiceDTO.getTotalPenaltyDeduction());
	        vendorInvoiceMaster.setCreditNote(vendorInvoiceDTO.getCreditNote());
	        vendorInvoiceMaster.setTotalPaymentReceived(vendorInvoiceDTO.getTotalPaymentReceived());
//	        vendorInvoiceMaster.setpaymentDate(vendorInvoiceDTO.getpaymentDate());

	        // Additional Fields (like CGST, SGST, IGST)
	        vendorInvoiceMaster.setTotalCgst(vendorInvoiceDTO.getTotalCgst());
	        vendorInvoiceMaster.setTotalSgst(vendorInvoiceDTO.getTotalSgst());
	        vendorInvoiceMaster.setTotalIgst(vendorInvoiceDTO.getTotalIgst());
	        vendorInvoiceMaster.setAmountExcluGst(vendorInvoiceDTO.getAmountExcluGst());

	        // Save vendor invoice
	        vendorInvoiceMaster = vendorInvoiceMasterService.save(vendorInvoiceMaster);

	        // Handle descriptions and base values
	        if (vendorInvoiceDTO.getInvoiceDescriptionValue() != null) {
	            for (InvoiceDescriptionValue descAndBase : vendorInvoiceDTO.getInvoiceDescriptionValue()) {
	                if (!UtilValidate.isEmpty(descAndBase.getBaseValue())) {
	                    InvoiceDescriptionValue descriptionValue = new InvoiceDescriptionValue();
	                    descriptionValue.setVendorInvoice(vendorInvoiceMaster);
	                    descriptionValue.setItemDescription(descAndBase.getItemDescription());
	                    descriptionValue.setBaseValue(descAndBase.getBaseValue());
	                    descriptionValue.setGstPer(descAndBase.getGstPer());
	                    descriptionValue.setCgst(descAndBase.getCgst());
	                    descriptionValue.setSgst(descAndBase.getSgst());
	                    descriptionValue.setIgst(descAndBase.getIgst());
	                    descriptionValue.setAmtInclGst(descAndBase.getAmtInclGst());
	                    invoiceDescriptionValueService.save(descriptionValue);
	                }
	            }
	        }

	        statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
	        statusMap.put(Parameters.status, Constants.SUCCESS);
	        statusMap.put(Parameters.statusCode, "RU_200");
	        return new ResponseEntity<>(statusMap, HttpStatus.OK);

	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
//	**************************************************************************new ************************************
//	@EncryptResponse
//	@PostMapping("/addVendorInvoices")
//	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO vendorInvoiceDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    try {
//	        // Validate mandatory fields
//	        if (UtilValidate.isEmpty(vendorInvoiceDTO.getVendorName()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getClientName()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getProjectName()) ||
//	            vendorInvoiceDTO.getInvoiceDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceNo()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getPoNo()) ||
//	            vendorInvoiceDTO.getInvoiceDueDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceDescription()) ||
////	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstPer()) ||
////	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountExcluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getStatus())) {
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        // Map DTO to Entity
//	        VendorInvoiceMasterEntity vendorInvoiceMaster = new VendorInvoiceMasterEntity();
//	        vendorInvoiceMaster.setVendorName(vendorInvoiceDTO.getVendorName());
//	        vendorInvoiceMaster.setClientName(vendorInvoiceDTO.getClientName());
//	        vendorInvoiceMaster.setProjectName(vendorInvoiceDTO.getProjectName());
//	        vendorInvoiceMaster.setInvoiceDate(vendorInvoiceDTO.getInvoiceDate());
//	        vendorInvoiceMaster.setInvoiceNo(vendorInvoiceDTO.getInvoiceNo());
//	        vendorInvoiceMaster.setPoNo(vendorInvoiceDTO.getPoNo());
//	        vendorInvoiceMaster.setInvoiceDueDate(vendorInvoiceDTO.getInvoiceDueDate());
//	        vendorInvoiceMaster.setInvoiceDescription(vendorInvoiceDTO.getInvoiceDescription());
////	        vendorInvoiceMaster.setGstPer(vendorInvoiceDTO.getGstPer());
////	        vendorInvoiceMaster.setInvoiceAmountExcluGst(vendorInvoiceDTO.getInvoiceAmountExcluGst());
//	        vendorInvoiceMaster.setInvoiceAmountIncluGst(vendorInvoiceDTO.getInvoiceAmountIncluGst());
//	        vendorInvoiceMaster.setStatus(vendorInvoiceDTO.getStatus());
//	        
//	        vendorInvoiceMaster.setStartDate(vendorInvoiceDTO.getStartDate());
//	        vendorInvoiceMaster.setEndDate(vendorInvoiceDTO.getEndDate());
//	        vendorInvoiceMaster.setModeOfPayment(vendorInvoiceDTO.getModeOfPayment());
//
//	        // Optional fields
//	        vendorInvoiceMaster.setInvoiceBaseValue(vendorInvoiceDTO.getInvoiceBaseValue());
//	        vendorInvoiceMaster.setGstBaseValue(vendorInvoiceDTO.getGstBaseValue());
//	        vendorInvoiceMaster.setInvoiceInclusiveOfGst(vendorInvoiceDTO.getInvoiceInclusiveOfGst());
//	        vendorInvoiceMaster.setTdsBaseValue(vendorInvoiceDTO.getTdsBaseValue());
//	        vendorInvoiceMaster.setTdsPer(vendorInvoiceDTO.getTdsPer());
//	        vendorInvoiceMaster.setTdsOnGst(vendorInvoiceDTO.getTdsOnGst());
//	        vendorInvoiceMaster.setIgstOnTds(vendorInvoiceDTO.getIgstOnTds());
//	        vendorInvoiceMaster.setCgstOnTds(vendorInvoiceDTO.getCgstOnTds());
//	        vendorInvoiceMaster.setSgstOnTds(vendorInvoiceDTO.getSgstOnTds());
//	        vendorInvoiceMaster.setTotalTdsDeducted(vendorInvoiceDTO.getTotalTdsDeducted());
//	        vendorInvoiceMaster.setBalance(vendorInvoiceDTO.getBalance());
//	        vendorInvoiceMaster.setPenalty(vendorInvoiceDTO.getPenalty());
//	        vendorInvoiceMaster.setPenaltyDeductionOnBase(vendorInvoiceDTO.getPenaltyDeductionOnBase());
//	        vendorInvoiceMaster.setGstOnPenalty(vendorInvoiceDTO.getGstOnPenalty());
//	        vendorInvoiceMaster.setTotalPenaltyDeduction(vendorInvoiceDTO.getTotalPenaltyDeduction());
//	        vendorInvoiceMaster.setCreditNote(vendorInvoiceDTO.getCreditNote());
//	        vendorInvoiceMaster.setTotalPaymentReceived(vendorInvoiceDTO.getTotalPaymentReceived());
//
//	        // Save vendor invoice
//	        vendorInvoiceMaster = vendorInvoiceMasterService.save(vendorInvoiceMaster);
//
//	        // Handle descriptions and base values
//	        if (vendorInvoiceDTO.getInvoiceDescriptionValue() != null) {
//	            for (InvoiceDescriptionValue descAndBase : vendorInvoiceDTO.getInvoiceDescriptionValue()) {
//	                if (!UtilValidate.isEmpty(descAndBase.getBaseValue())) {
//	                    InvoiceDescriptionValue descriptionValue = new InvoiceDescriptionValue();
//	                    descriptionValue.setVendorInvoice(vendorInvoiceMaster);
//	                    descriptionValue.setItemDescription(descAndBase.getItemDescription());
//	                    descriptionValue.setBaseValue(descAndBase.getBaseValue());
//	                    invoiceDescriptionValueService.save(descriptionValue);
//	                }
//	            }
//	        }
//
//	        statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
//	        statusMap.put(Parameters.status, Constants.SUCCESS);
//	        statusMap.put(Parameters.statusCode, "RU_200");
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}



	
	
//	*************************************************************************************************************************************
	
//	@EncryptResponse
//	@PostMapping("/addVendorInvoices")
//	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO vendorInvoiceDTO, HttpServletRequest request) {
//
//	    ResponseEntity<?> responseEntity = null;
//	    String methodName = request.getRequestURI();
//	    Map<String, Object> statusMap = new HashMap<>();
//
//	    try {
//	        // Validate all fields, including the missing ones
//	        if (UtilValidate.isEmpty(vendorInvoiceDTO.getVendorName()) || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getClientName()) || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getProjectName()) ||
//	            vendorInvoiceDTO.getInvoiceDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceNo()) || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getPoNo()) || 
//	            vendorInvoiceDTO.getInvoiceDueDate() == null || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceDescription()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstPer()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountExcluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getStatus()) ||
//	            
//	            // Additional validation for new fields
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceBaseValue()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstBaseValue()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceInclusiveOfGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTdsBaseValue()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTdsPer()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTdsOnGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getIgstOnTds()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getCgstOnTds()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getSgstOnTds()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalTdsDeducted()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getBalance()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getPenalty()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getPenaltyDeductionOnBase()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstOnPenalty()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalPenaltyDeduction()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getCreditNote()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalPaymentReceived())) {
//	                
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        VendorInvoiceMasterEntity vendorInvoiceMaster = new VendorInvoiceMasterEntity();
//
//	        // Populate fields from DTO to entity, including new fields
//	        vendorInvoiceMaster.setVendorName(vendorInvoiceDTO.getVendorName());
//	        vendorInvoiceMaster.setClientName(vendorInvoiceDTO.getClientName());
//	        vendorInvoiceMaster.setProjectName(vendorInvoiceDTO.getProjectName());
//	        vendorInvoiceMaster.setInvoiceDate(vendorInvoiceDTO.getInvoiceDate());
//	        vendorInvoiceMaster.setInvoiceNo(vendorInvoiceDTO.getInvoiceNo());
//	        vendorInvoiceMaster.setPoNo(vendorInvoiceDTO.getPoNo());
//	        vendorInvoiceMaster.setInvoiceDueDate(vendorInvoiceDTO.getInvoiceDueDate());
//	        vendorInvoiceMaster.setInvoiceDescription(vendorInvoiceDTO.getInvoiceDescription());
//	        vendorInvoiceMaster.setGstPer(vendorInvoiceDTO.getGstPer());
//	        vendorInvoiceMaster.setInvoiceAmountExcluGst(vendorInvoiceDTO.getInvoiceAmountExcluGst());
//	        vendorInvoiceMaster.setInvoiceAmountIncluGst(vendorInvoiceDTO.getInvoiceAmountIncluGst());
//	        vendorInvoiceMaster.setStatus(vendorInvoiceDTO.getStatus());
//
//	        // Setting new fields
//	        vendorInvoiceMaster.setInvoiceBaseValue(vendorInvoiceDTO.getInvoiceBaseValue());
//	        vendorInvoiceMaster.setGstBaseValue(vendorInvoiceDTO.getGstBaseValue());
//	        vendorInvoiceMaster.setInvoiceInclusiveOfGst(vendorInvoiceDTO.getInvoiceInclusiveOfGst());
//	        vendorInvoiceMaster.setTdsBaseValue(vendorInvoiceDTO.getTdsBaseValue());
//	        vendorInvoiceMaster.setTdsPer(vendorInvoiceDTO.getTdsPer());
//	        vendorInvoiceMaster.setTdsOnGstPer(vendorInvoiceDTO.getTdsOnGst());
//	        vendorInvoiceMaster.setIgstOnTds(vendorInvoiceDTO.getIgstOnTds());
//	        vendorInvoiceMaster.setCgstOnTds(vendorInvoiceDTO.getCgstOnTds());
//	        vendorInvoiceMaster.setSgstOnTds(vendorInvoiceDTO.getSgstOnTds());
//	        vendorInvoiceMaster.setTotalTdsDeducted(vendorInvoiceDTO.getTotalTdsDeducted());
//	        vendorInvoiceMaster.setBalance(vendorInvoiceDTO.getBalance());
//	        vendorInvoiceMaster.setPenalty(vendorInvoiceDTO.getPenalty());
//	        vendorInvoiceMaster.setPenaltyDeductionOnBase(vendorInvoiceDTO.getPenaltyDeductionOnBase());
//	        vendorInvoiceMaster.setGstOnPenalty(vendorInvoiceDTO.getGstOnPenalty());
//	        vendorInvoiceMaster.setTotalPenaltyDeduction(vendorInvoiceDTO.getTotalPenaltyDeduction());
//	        vendorInvoiceMaster.setCreditNote(vendorInvoiceDTO.getCreditNote());
//	        vendorInvoiceMaster.setTotalPaymentReceived(vendorInvoiceDTO.getTotalPaymentReceived());
//
//	        try {
//	            // Save the invoice to the database
//	            vendorInvoiceMaster = vendorInvoiceMasterService.save(vendorInvoiceMaster);
//
//	            if (vendorInvoiceMaster != null) {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
//	                statusMap.put(Parameters.status, Constants.SUCCESS);
//	                statusMap.put(Parameters.statusCode, "RU_200");
//	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	            } else {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_NOT_GENERATED);
//	                statusMap.put(Parameters.status, Constants.FAIL);
//	                statusMap.put(Parameters.statusCode, "RU_301");
//	                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//	            }
//	        } catch (Exception ex) {
//	            statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//	            statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//	            statusMap.put(Parameters.status, Constants.FAIL);
//	            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

//	****************************************************************************************************************************
	
	
	
	
//	@EncryptResponse
//	@PostMapping("/addVendorInvoices")
//	public ResponseEntity<?> addVendorInvoices(@RequestBody VendorInvioceMasterDTO vendorInvoiceDTO, HttpServletRequest request) {
//
//	    ResponseEntity<?> responseEntity = null;
//	    String methodName = request.getRequestURI();
//	    // logger.logMethodStart(methodName);
//
//	    Map<String, Object> statusMap = new HashMap<>();
//
//	    try {
//	        // Validate the fields, including the new ones
//	        if (UtilValidate.isEmpty(vendorInvoiceDTO.getVendorName()) || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getClientName()) || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getProjectName()) ||
//	            vendorInvoiceDTO.getInvoiceDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceNo()) || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getPoNo()) || 
//	            vendorInvoiceDTO.getInvoiceDueDate() == null || 
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceDescription()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstPer()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountExcluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getStatus()) ||
//	            
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceBaseValue()) ||  // Validation for new fields
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstBaseValue()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getInvoiceInclusiveOfGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTdsBaseValue()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getCgstOnTds()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getSgstOnTds()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalTdsDeducted()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getBalance()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getPenaltyDeductionOnBase()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getGstOnPenalty()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalPenaltyDeduction()) ||
//	            UtilValidate.isEmpty(vendorInvoiceDTO.getTotalPaymentReceived())) { 
//	            
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        VendorInvoiceMasterEntity vendorInvoiceMaster = new VendorInvoiceMasterEntity();
//
//	        // Set the fields from DTO to entity, including new fields
//	        vendorInvoiceMaster.setVendorName(vendorInvoiceDTO.getVendorName());
//	        vendorInvoiceMaster.setClientName(vendorInvoiceDTO.getClientName());
//	        vendorInvoiceMaster.setProjectName(vendorInvoiceDTO.getProjectName());
//	        vendorInvoiceMaster.setInvoiceDate(vendorInvoiceDTO.getInvoiceDate());
//	        vendorInvoiceMaster.setInvoiceNo(vendorInvoiceDTO.getInvoiceNo());
//	        vendorInvoiceMaster.setPoNo(vendorInvoiceDTO.getPoNo());
//	        vendorInvoiceMaster.setInvoiceDueDate(vendorInvoiceDTO.getInvoiceDueDate());
//	        vendorInvoiceMaster.setInvoiceDescription(vendorInvoiceDTO.getInvoiceDescription());
//	        vendorInvoiceMaster.setGstPer(vendorInvoiceDTO.getGstPer());
//	        vendorInvoiceMaster.setInvoiceAmountExcluGst(vendorInvoiceDTO.getInvoiceAmountExcluGst());
//	        vendorInvoiceMaster.setInvoiceAmountIncluGst(vendorInvoiceDTO.getInvoiceAmountIncluGst());
//	        vendorInvoiceMaster.setStatus(vendorInvoiceDTO.getStatus());
//	        
//
//	        // Set the new fields
//	        vendorInvoiceMaster.setInvoiceBaseValue(vendorInvoiceDTO.getInvoiceBaseValue());
//	        vendorInvoiceMaster.setGstBaseValue(vendorInvoiceDTO.getGstBaseValue());
//	        vendorInvoiceMaster.setInvoiceInclusiveOfGst(vendorInvoiceDTO.getInvoiceInclusiveOfGst());
//	        vendorInvoiceMaster.setTdsBaseValue(vendorInvoiceDTO.getTdsBaseValue());
//	        vendorInvoiceMaster.setCgstOnTds(vendorInvoiceDTO.getCgstOnTds());
//	        vendorInvoiceMaster.setSgstOnTds(vendorInvoiceDTO.getSgstOnTds());
//	        vendorInvoiceMaster.setTotalTdsDeducted(vendorInvoiceDTO.getTotalTdsDeducted());
//	        vendorInvoiceMaster.setBalance(vendorInvoiceDTO.getBalance());
//	        vendorInvoiceMaster.setPenaltyDeductionOnBase(vendorInvoiceDTO.getPenaltyDeductionOnBase());
//	        vendorInvoiceMaster.setGstOnPenalty(vendorInvoiceDTO.getGstOnPenalty());
//	        vendorInvoiceMaster.setTotalPenaltyDeduction(vendorInvoiceDTO.getTotalPenaltyDeduction());
//	        vendorInvoiceMaster.setTotalPaymentReceived(vendorInvoiceDTO.getTotalPaymentReceived());
//	       
//
//	        try {
//	            /* SAVE THE USER TO THE DB ENTITY */
//	            vendorInvoiceMaster = vendorInvoiceMasterService.save(vendorInvoiceMaster);
//
//	            if (vendorInvoiceMaster != null) {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_GENERATED_SUCCESSFULLY);
//	                statusMap.put(Parameters.status, Constants.SUCCESS);
//	                statusMap.put(Parameters.statusCode, "RU_200");
//	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	            } else {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.VENDOR_INVOICE_NOT_GENERATED);
//	                statusMap.put(Parameters.status, Constants.FAIL);
//	                statusMap.put(Parameters.statusCode, "RU_301");
//	                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//	            }
//	        } catch (Exception ex) {
//	            // logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 2. Failed to save user in DB response : " + ex.getMessage());
//	            statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//	            statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//	            statusMap.put(Parameters.status, Constants.FAIL);
//	            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    } catch (Exception ex) {
//	        // if (logger.isErrorLoggingEnabled()) {
//	        // logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage());
//	        // }
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}


	

    //************************************************************************************************************************************************************************************
   //***********************************************************************************************get api**************************************************************************************
	
	@EncryptResponse
	@GetMapping("/getAllVendorInvoice")
	public ResponseEntity<?> getAllVendorInvoices() {
	    try {
	        // Fetch all vendor invoices from the database
	        List<VendorInvoiceMasterEntity> vendorInvoices = vendorInvoiceMasterService.findAll();

	        if (vendorInvoices.isEmpty()) {
	            return CommonUtils.createResponse(Constants.FAIL, "No Vendor Invoices Found", HttpStatus.NOT_FOUND);
	        }

	        // Sort the vendor invoices by ID in descending order
	        if (vendorInvoices != null) {
	            vendorInvoices.sort(Comparator.comparing(VendorInvoiceMasterEntity::getId).reversed());
	        }

	        // Map entities to DTOs and handle nested objects explicitly
	        List<VendorInvioceMasterDTO> vendorInvoiceDTOs = vendorInvoices.stream()
	            .map(invoice -> {
	                VendorInvioceMasterDTO dto = new VendorInvioceMasterDTO();
	                BeanUtils.copyProperties(invoice, dto);

	                // Get client info
	                if (invoice.getClientName() != null) {
	                    ClientMasterEntity client = vendorInvoiceMasterService.findClientById(Long.parseLong(invoice.getClientName()));
	                    if (client != null) {
	                        dto.setClientId(client.getId());
	                        dto.setClientName(client.getClientName());
	                    } else {
	                        dto.setClientId(null); // Default to null if client is not found
	                        dto.setClientName("Unknown");
	                    }
	                }

	                // Map nested descriptionsAndBaseValues from descriptionValues
	                if (invoice.getInvoiceDescriptionValue() != null && !invoice.getInvoiceDescriptionValue().isEmpty()) {
	                    List<InvoiceDescriptionValue> descriptionAndBaseValues = invoice.getInvoiceDescriptionValue().stream()
	                        .map(desc -> {
	                            InvoiceDescriptionValue value = new InvoiceDescriptionValue();
	                            value.setItemDescription(desc.getItemDescription() != null ? desc.getItemDescription() : ""); // Fallback to empty string
	                            value.setBaseValue(desc.getBaseValue() != null ? desc.getBaseValue() : "0"); // Fallback to "0" if null
	                            value.setGstPer(desc.getGstPer());
	                            value.setCgst(desc.getCgst());
	                            value.setSgst(desc.getSgst());
	                            value.setIgst(desc.getIgst());
	                            value.setAmtInclGst(desc.getAmtInclGst());
	                            return value;
	                        })
	                        .collect(Collectors.toList());
	                    dto.setInvoiceDescriptionValue(descriptionAndBaseValues);
	                } else {
	                    // If descriptionValues is null or empty, set default values
	                    List<InvoiceDescriptionValue> defaultDescriptionAndBaseValues = new ArrayList<>();
	                    InvoiceDescriptionValue defaultValue = new InvoiceDescriptionValue();
	                    defaultValue.setItemDescription(""); // Default empty description
	                    defaultValue.setBaseValue("1000"); // Default base value
	                    defaultDescriptionAndBaseValues.add(defaultValue);
	                    dto.setInvoiceDescriptionValue(defaultDescriptionAndBaseValues);
	                }

	                // Mapping all the other fields explicitly like POST method
	                dto.setInvoiceBaseValue(invoice.getInvoiceBaseValue());
	                dto.setGstBaseValue(invoice.getGstBaseValue());
	                dto.setInvoiceInclusiveOfGst(invoice.getInvoiceInclusiveOfGst());
	                dto.setTdsBaseValue(invoice.getTdsBaseValue());
	                dto.setTdsPer(invoice.getTdsPer());
	                dto.setTdsOnGst(invoice.getTdsOnGst());
	                dto.setIgstOnTds(invoice.getIgstOnTds());
	                dto.setCgstOnTds(invoice.getCgstOnTds());
	                dto.setSgstOnTds(invoice.getSgstOnTds());
	                dto.setTotalTdsDeducted(invoice.getTotalTdsDeducted());
	                dto.setBalance(invoice.getBalance());
	                dto.setPenalty(invoice.getPenalty());
	                dto.setPenaltyDeductionOnBase(invoice.getPenaltyDeductionOnBase());
	                dto.setGstOnPenalty(invoice.getGstOnPenalty());
	                dto.setTotalPenaltyDeduction(invoice.getTotalPenaltyDeduction());
	                dto.setCreditNote(invoice.getCreditNote());
	                dto.setTotalPaymentReceived(invoice.getTotalPaymentReceived());
	                dto.setTotalCgst(invoice.getTotalCgst());
	                dto.setTotalSgst(invoice.getTotalSgst());
	                dto.setTotalIgst(invoice.getTotalIgst());
	                dto.setAmountExcluGst(invoice.getAmountExcluGst());

	                return dto;
	            })
	            .collect(Collectors.toList());

	        return new ResponseEntity<>(vendorInvoiceDTOs, HttpStatus.OK);

	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
//	@EncryptResponse
//	@GetMapping("/getAllVendorInvoice")
//	public ResponseEntity<?> getAllVendorInvoices() {
//	    try {
//	        // Fetch all vendor invoices from the database
//	        List<VendorInvoiceMasterEntity> vendorInvoices = vendorInvoiceMasterService.findAll();
//
//	        if (vendorInvoices.isEmpty()) {
//	            return CommonUtils.createResponse(Constants.FAIL, "No Vendor Invoices Found", HttpStatus.NOT_FOUND);
//	        }
//	        
//	        // Sort the vendor invoices by ID in descending order
//	        if (vendorInvoices != null) {
//	            vendorInvoices.sort(Comparator.comparing(VendorInvoiceMasterEntity::getId).reversed());
//	        }
//
//	        // Map entities to DTOs and handle nested objects explicitly
//	        List<VendorInvioceMasterDTO> vendorInvoiceDTOs = vendorInvoices.stream()
//	            .map(invoice -> {
//	                VendorInvioceMasterDTO dto = new VendorInvioceMasterDTO();
//	                BeanUtils.copyProperties(invoice, dto);
//
//	                // Get client info
//	                if (invoice.getClientName() != null) {
//	                    ClientMasterEntity client = vendorInvoiceMasterService.findClientById(Long.parseLong(invoice.getClientName()));
//	                    if (client != null) {
//	                        dto.setClientId(client.getId());
//	                        dto.setClientName(client.getClientName());
//	                    } else {
//	                        dto.setClientId(null); // Default to null if client is not found
//	                        dto.setClientName("Unknown");
//	                    }
//	                }
//
//	                // Map nested descriptionsAndBaseValues from descriptionValues
//	                if (invoice.getInvoiceDescriptionValue() != null && !invoice.getInvoiceDescriptionValue().isEmpty()) {
//	                    List<InvoiceDescriptionValue> descriptionAndBaseValues = invoice.getInvoiceDescriptionValue().stream()
//	                        .map(desc -> {
//	                        	InvoiceDescriptionValue value = new InvoiceDescriptionValue();
//	                            value.setItemDescription(desc.getItemDescription() != null ? desc.getItemDescription() : ""); // Fallback to empty string
//	                            value.setBaseValue(desc.getBaseValue() != null ? desc.getBaseValue() : "0"); // Fallback to "0" if null
//	                            return value;
//	                        })
//	                        .collect(Collectors.toList());
//	                    dto.setInvoiceDescriptionValue(descriptionAndBaseValues);
//	                } else {
//	                    // If descriptionValues is null or empty, set default values
//	                    List<InvoiceDescriptionValue> defaultDescriptionAndBaseValues = new ArrayList<>();
//	                    InvoiceDescriptionValue defaultValue = new InvoiceDescriptionValue();
//	                    defaultValue.setItemDescription(""); // Default empty description
//	                    defaultValue.setBaseValue("1000"); // Default base value
//	                    defaultDescriptionAndBaseValues.add(defaultValue);
//	                    dto.setInvoiceDescriptionValue(defaultDescriptionAndBaseValues);
//	                }
//
//	                return dto;
//	            })
//	            .collect(Collectors.toList());
//
//	        return new ResponseEntity<>(vendorInvoiceDTOs, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

//*********************************************************************************************************************	
	
	
	
//	@EncryptResponse
//	@GetMapping("/getAllVendorInvoice")
//	public ResponseEntity<?> getAllVendorInvoices() {
//	    try {
//	        // Fetch all vendor invoices from the database
//	        List<VendorInvoiceMasterEntity> vendorInvoices = vendorInvoiceMasterService.findAll();
//
//	        if (vendorInvoices.isEmpty()) {
//	            return CommonUtils.createResponse(Constants.FAIL, "No Vendor Invoices Found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Map entities to DTOs and handle nested objects explicitly
//	        List<VendorInvioceMasterDTO> vendorInvoiceDTOs = vendorInvoices.stream()
//	            .map(invoice -> {
//	                VendorInvioceMasterDTO dto = new VendorInvioceMasterDTO();
//	                BeanUtils.copyProperties(invoice, dto);
//
//	                // Get client info
//	                if (invoice.getClientName() != null) {
//	                    ClientMasterEntity client = vendorInvoiceMasterService.findClientById(Long.parseLong(invoice.getClientName()));
//	                    if (client != null) {
//	                        dto.setClientId(client.getId());
//	                        dto.setClientName(client.getClientName());
//	                    } else {
//	                        dto.setClientId(null); // Default to null if client is not found
//	                        dto.setClientName("Unknown");
//	                    }
//	                }
//
//	                // Map nested descriptionsAndBaseValues from descriptionValues
//	                if (invoice.getDescriptionValues() != null && !invoice.getDescriptionValues().isEmpty()) {
//	                    List<DescriptionAndBaseValue> descriptionAndBaseValues = invoice.getDescriptionValues().stream()
//	                        .map(desc -> {
//	                            DescriptionAndBaseValue value = new DescriptionAndBaseValue();
//	                            value.setItemDescription(desc.getItemDescription() != null ? desc.getItemDescription() : ""); // Fallback to empty string
//	                            value.setBaseValue(desc.getBaseValue() != null ? desc.getBaseValue() : "0"); // Fallback to "0" if null
//	                            return value;
//	                        })
//	                        .collect(Collectors.toList());
//	                    dto.setDescriptionsAndBaseValues(descriptionAndBaseValues);
//	                } else {
//	                    // If descriptionValues is null or empty, you can set default values
//	                    List<DescriptionAndBaseValue> defaultDescriptionAndBaseValues = new ArrayList<>();
//	                    DescriptionAndBaseValue defaultValue = new DescriptionAndBaseValue();
//	                    defaultValue.setItemDescription(""); // Default empty description
//	                    defaultValue.setBaseValue("1000"); // Default base value
//	                    defaultDescriptionAndBaseValues.add(defaultValue);
//	                    dto.setDescriptionsAndBaseValues(defaultDescriptionAndBaseValues);
//	                }
//
//	                return dto;
//	            })
//	            .collect(Collectors.toList());
//
//	        return new ResponseEntity<>(vendorInvoiceDTOs, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	

	
//	@EncryptResponse
//	@PutMapping("/updateVendorInvoiceMaster")
//	public ResponseEntity<?> updateVendorInvoiceMaster(@RequestBody VendorInvioceMasterDTO vendorInvoiceMasterDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    try {
//	        // Validate mandatory fields
//	        if (UtilValidate.isEmpty(vendorInvoiceMasterDTO.getVendorName()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getClientName()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getProjectName()) ||
//	            vendorInvoiceMasterDTO.getInvoiceDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceNo()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getPoNo()) ||
////	            vendorInvoiceMasterDTO.getInvoiceDueDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceDescription()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getGstPer()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceAmountExcluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getStatus())) {
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        // Find the existing VendorInvoice by ID
//	        VendorInvoiceMasterEntity vendorInvoiceEntity = vendorInvoiceMasterService.findById(vendorInvoiceMasterDTO.getId());
//	        if (vendorInvoiceEntity == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Vendor Invoice not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Map DTO to Entity (Mandatory fields)
//	        vendorInvoiceEntity.setVendorName(vendorInvoiceMasterDTO.getVendorName());
//	        vendorInvoiceEntity.setClientName(vendorInvoiceMasterDTO.getClientName());
//	        vendorInvoiceEntity.setProjectName(vendorInvoiceMasterDTO.getProjectName());
//	        vendorInvoiceEntity.setInvoiceDate(vendorInvoiceMasterDTO.getInvoiceDate());
//	        vendorInvoiceEntity.setInvoiceNo(vendorInvoiceMasterDTO.getInvoiceNo());
//	        vendorInvoiceEntity.setPoNo(vendorInvoiceMasterDTO.getPoNo());
//	        vendorInvoiceEntity.setInvoiceDueDate(vendorInvoiceMasterDTO.getInvoiceDueDate());
//	        vendorInvoiceEntity.setInvoiceDescription(vendorInvoiceMasterDTO.getInvoiceDescription());
////	        vendorInvoiceEntity.setGstPer(vendorInvoiceMasterDTO.getGstPer());
////	        vendorInvoiceEntity.setInvoiceAmountExcluGst(vendorInvoiceMasterDTO.getInvoiceAmountExcluGst());
//	        vendorInvoiceEntity.setInvoiceAmountIncluGst(vendorInvoiceMasterDTO.getInvoiceAmountIncluGst());
//	        vendorInvoiceEntity.setStatus(vendorInvoiceMasterDTO.getStatus());
//	        
//	        // Set the new fields
//	        vendorInvoiceEntity.setStartDate(vendorInvoiceMasterDTO.getStartDate());
//	        vendorInvoiceEntity.setEndDate(vendorInvoiceMasterDTO.getEndDate());
//	        vendorInvoiceEntity.setModeOfPayment(vendorInvoiceMasterDTO.getModeOfPayment());
//
//	        // Optional fields (only set if provided in the DTO)
//	        vendorInvoiceEntity.setInvoiceBaseValue(vendorInvoiceMasterDTO.getInvoiceBaseValue() != null ? vendorInvoiceMasterDTO.getInvoiceBaseValue() : vendorInvoiceEntity.getInvoiceBaseValue());
//	        vendorInvoiceEntity.setGstBaseValue(vendorInvoiceMasterDTO.getGstBaseValue() != null ? vendorInvoiceMasterDTO.getGstBaseValue() : vendorInvoiceEntity.getGstBaseValue());
//	        vendorInvoiceEntity.setInvoiceInclusiveOfGst(vendorInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null ? vendorInvoiceMasterDTO.getInvoiceInclusiveOfGst() : vendorInvoiceEntity.getInvoiceInclusiveOfGst());
//	        vendorInvoiceEntity.setTdsBaseValue(vendorInvoiceMasterDTO.getTdsBaseValue() != null ? vendorInvoiceMasterDTO.getTdsBaseValue() : vendorInvoiceEntity.getTdsBaseValue());
//	        vendorInvoiceEntity.setTdsPer(vendorInvoiceMasterDTO.getTdsPer() != null ? vendorInvoiceMasterDTO.getTdsPer() : vendorInvoiceEntity.getTdsPer());
//	        vendorInvoiceEntity.setTdsOnGst(vendorInvoiceMasterDTO.getTdsOnGst() != null ? vendorInvoiceMasterDTO.getTdsOnGst() : vendorInvoiceEntity.getTdsOnGst());
//	        vendorInvoiceEntity.setIgstOnTds(vendorInvoiceMasterDTO.getIgstOnTds() != null ? vendorInvoiceMasterDTO.getIgstOnTds() : vendorInvoiceEntity.getIgstOnTds());
//	        vendorInvoiceEntity.setCgstOnTds(vendorInvoiceMasterDTO.getCgstOnTds() != null ? vendorInvoiceMasterDTO.getCgstOnTds() : vendorInvoiceEntity.getCgstOnTds());
//	        vendorInvoiceEntity.setSgstOnTds(vendorInvoiceMasterDTO.getSgstOnTds() != null ? vendorInvoiceMasterDTO.getSgstOnTds() : vendorInvoiceEntity.getSgstOnTds());
//	        vendorInvoiceEntity.setTotalTdsDeducted(vendorInvoiceMasterDTO.getTotalTdsDeducted() != null ? vendorInvoiceMasterDTO.getTotalTdsDeducted() : vendorInvoiceEntity.getTotalTdsDeducted());
//	        vendorInvoiceEntity.setBalance(vendorInvoiceMasterDTO.getBalance() != null ? vendorInvoiceMasterDTO.getBalance() : vendorInvoiceEntity.getBalance());
//	        vendorInvoiceEntity.setPenalty(vendorInvoiceMasterDTO.getPenalty() != null ? vendorInvoiceMasterDTO.getPenalty() : vendorInvoiceEntity.getPenalty());
//	        vendorInvoiceEntity.setPenaltyDeductionOnBase(vendorInvoiceMasterDTO.getPenaltyDeductionOnBase() != null ? vendorInvoiceMasterDTO.getPenaltyDeductionOnBase() : vendorInvoiceEntity.getPenaltyDeductionOnBase());
//	        vendorInvoiceEntity.setGstOnPenalty(vendorInvoiceMasterDTO.getGstOnPenalty() != null ? vendorInvoiceMasterDTO.getGstOnPenalty() : vendorInvoiceEntity.getGstOnPenalty());
//	        vendorInvoiceEntity.setTotalPenaltyDeduction(vendorInvoiceMasterDTO.getTotalPenaltyDeduction() != null ? vendorInvoiceMasterDTO.getTotalPenaltyDeduction() : vendorInvoiceEntity.getTotalPenaltyDeduction());
//	        vendorInvoiceEntity.setCreditNote(vendorInvoiceMasterDTO.getCreditNote() != null ? vendorInvoiceMasterDTO.getCreditNote() : vendorInvoiceEntity.getCreditNote());
//	        vendorInvoiceEntity.setTotalPaymentReceived(vendorInvoiceMasterDTO.getTotalPaymentReceived() != null ? vendorInvoiceMasterDTO.getTotalPaymentReceived() : vendorInvoiceEntity.getTotalPaymentReceived());
//
//	        // Save the updated vendor invoice
//	        vendorInvoiceMasterService.update(vendorInvoiceEntity);
//
//	        // Handle descriptions and base values (similar to the addVendorInvoices method)
//	        if (vendorInvoiceMasterDTO.getInvoiceDescriptionValue() != null) {
//	            for (InvoiceDescriptionValue descAndBase : vendorInvoiceMasterDTO.getInvoiceDescriptionValue()) {
//	                if (!UtilValidate.isEmpty(descAndBase.getBaseValue())) {
//	                    InvoiceDescriptionValue descriptionValue = new InvoiceDescriptionValue();
//	                    descriptionValue.setVendorInvoice(vendorInvoiceEntity);
//	                    descriptionValue.setItemDescription(descAndBase.getItemDescription());
//	                    descriptionValue.setBaseValue(descAndBase.getBaseValue());
//	                    invoiceDescriptionValueService.save(descriptionValue);
//	                }
//	            }
//	        }
//
//	        // Prepare response data
//	        statusMap.put("purchaseMasterEntity", vendorInvoiceEntity);
//	        statusMap.put("status", "SUCCESS");
//	        statusMap.put("statusCode", "RU_200");
//	        statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");
//
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	@EncryptResponse
	@PutMapping("/updateVendorInvoiceMaster")
	public ResponseEntity<?> updateVendorInvoiceMaster(@RequestBody VendorInvioceMasterDTO vendorInvoiceMasterDTO) {
	    Map<String, Object> statusMap = new HashMap<>();
	    try {
	        // Validate mandatory fields (similar to addVendorInvoices API)
	        if (UtilValidate.isEmpty(vendorInvoiceMasterDTO.getVendorName()) ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getClientName()) ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getProjectName()) ||
	            vendorInvoiceMasterDTO.getInvoiceDate() == null ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceNo()) ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getPoNo()) ||
	            vendorInvoiceMasterDTO.getInvoiceDueDate() == null ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceDescription()) ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceAmountIncluGst()) ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceAmtIncluGst()) ||
	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getStatus())) {
	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        // Find the existing VendorInvoice by ID
	        VendorInvoiceMasterEntity vendorInvoiceEntity = vendorInvoiceMasterService.findById(vendorInvoiceMasterDTO.getId());
	        if (vendorInvoiceEntity == null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Vendor Invoice not found", HttpStatus.NOT_FOUND);
	        }

	        // Map DTO to Entity (Mandatory fields)
	        vendorInvoiceEntity.setVendorName(vendorInvoiceMasterDTO.getVendorName());
	        vendorInvoiceEntity.setClientName(vendorInvoiceMasterDTO.getClientName());
	        vendorInvoiceEntity.setProjectName(vendorInvoiceMasterDTO.getProjectName());
	        vendorInvoiceEntity.setInvoiceDate(vendorInvoiceMasterDTO.getInvoiceDate());
	        vendorInvoiceEntity.setInvoiceNo(vendorInvoiceMasterDTO.getInvoiceNo());
	        vendorInvoiceEntity.setPoNo(vendorInvoiceMasterDTO.getPoNo());
	        vendorInvoiceEntity.setInvoiceDueDate(vendorInvoiceMasterDTO.getInvoiceDueDate());
	        vendorInvoiceEntity.setInvoiceDescription(vendorInvoiceMasterDTO.getInvoiceDescription());
	        vendorInvoiceEntity.setInvoiceAmountIncluGst(vendorInvoiceMasterDTO.getInvoiceAmountIncluGst());
	        vendorInvoiceEntity.setInvoiceAmtIncluGst(Double.valueOf(vendorInvoiceMasterDTO.getInvoiceAmtIncluGst()));
	        vendorInvoiceEntity.setStatus(vendorInvoiceMasterDTO.getStatus());

	        // Optional fields: Set only if provided in the DTO
	        vendorInvoiceEntity.setStartDate(vendorInvoiceMasterDTO.getStartDate());
	        vendorInvoiceEntity.setEndDate(vendorInvoiceMasterDTO.getEndDate());
	        vendorInvoiceEntity.setModeOfPayment(vendorInvoiceMasterDTO.getModeOfPayment());

	        // New fields: Set only if provided
	        vendorInvoiceEntity.setInvoiceBaseValue(vendorInvoiceMasterDTO.getInvoiceBaseValue() != null ? vendorInvoiceMasterDTO.getInvoiceBaseValue() : vendorInvoiceEntity.getInvoiceBaseValue());
	        vendorInvoiceEntity.setGstBaseValue(vendorInvoiceMasterDTO.getGstBaseValue() != null ? vendorInvoiceMasterDTO.getGstBaseValue() : vendorInvoiceEntity.getGstBaseValue());
	        vendorInvoiceEntity.setInvoiceInclusiveOfGst(vendorInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null ? vendorInvoiceMasterDTO.getInvoiceInclusiveOfGst() : vendorInvoiceEntity.getInvoiceInclusiveOfGst());
	        vendorInvoiceEntity.setTdsBaseValue(vendorInvoiceMasterDTO.getTdsBaseValue() != null ? vendorInvoiceMasterDTO.getTdsBaseValue() : vendorInvoiceEntity.getTdsBaseValue());
	        vendorInvoiceEntity.setTdsPer(vendorInvoiceMasterDTO.getTdsPer() != null ? vendorInvoiceMasterDTO.getTdsPer() : vendorInvoiceEntity.getTdsPer());
	        vendorInvoiceEntity.setTdsOnGst(vendorInvoiceMasterDTO.getTdsOnGst() != null ? vendorInvoiceMasterDTO.getTdsOnGst() : vendorInvoiceEntity.getTdsOnGst());
	        vendorInvoiceEntity.setIgstOnTds(vendorInvoiceMasterDTO.getIgstOnTds() != null ? vendorInvoiceMasterDTO.getIgstOnTds() : vendorInvoiceEntity.getIgstOnTds());
	        vendorInvoiceEntity.setCgstOnTds(vendorInvoiceMasterDTO.getCgstOnTds() != null ? vendorInvoiceMasterDTO.getCgstOnTds() : vendorInvoiceEntity.getCgstOnTds());
	        vendorInvoiceEntity.setSgstOnTds(vendorInvoiceMasterDTO.getSgstOnTds() != null ? vendorInvoiceMasterDTO.getSgstOnTds() : vendorInvoiceEntity.getSgstOnTds());
	        vendorInvoiceEntity.setTotalTdsDeducted(vendorInvoiceMasterDTO.getTotalTdsDeducted() != null ? vendorInvoiceMasterDTO.getTotalTdsDeducted() : vendorInvoiceEntity.getTotalTdsDeducted());
	        vendorInvoiceEntity.setBalance(vendorInvoiceMasterDTO.getBalance() != null ? vendorInvoiceMasterDTO.getBalance() : vendorInvoiceEntity.getBalance());
	        vendorInvoiceEntity.setPenalty(vendorInvoiceMasterDTO.getPenalty() != null ? vendorInvoiceMasterDTO.getPenalty() : vendorInvoiceEntity.getPenalty());
	        vendorInvoiceEntity.setPenaltyDeductionOnBase(vendorInvoiceMasterDTO.getPenaltyDeductionOnBase() != null ? vendorInvoiceMasterDTO.getPenaltyDeductionOnBase() : vendorInvoiceEntity.getPenaltyDeductionOnBase());
	        vendorInvoiceEntity.setGstOnPenalty(vendorInvoiceMasterDTO.getGstOnPenalty() != null ? vendorInvoiceMasterDTO.getGstOnPenalty() : vendorInvoiceEntity.getGstOnPenalty());
	        vendorInvoiceEntity.setTotalPenaltyDeduction(vendorInvoiceMasterDTO.getTotalPenaltyDeduction() != null ? vendorInvoiceMasterDTO.getTotalPenaltyDeduction() : vendorInvoiceEntity.getTotalPenaltyDeduction());
	        vendorInvoiceEntity.setCreditNote(vendorInvoiceMasterDTO.getCreditNote() != null ? vendorInvoiceMasterDTO.getCreditNote() : vendorInvoiceEntity.getCreditNote());
	        vendorInvoiceEntity.setTotalPaymentReceived(vendorInvoiceMasterDTO.getTotalPaymentReceived() != null ? vendorInvoiceMasterDTO.getTotalPaymentReceived() : vendorInvoiceEntity.getTotalPaymentReceived());

	        // Set total tax values if present
	        vendorInvoiceEntity.setTotalCgst(vendorInvoiceMasterDTO.getTotalCgst() != null ? vendorInvoiceMasterDTO.getTotalCgst() : vendorInvoiceEntity.getTotalCgst());
	        vendorInvoiceEntity.setTotalSgst(vendorInvoiceMasterDTO.getTotalSgst() != null ? vendorInvoiceMasterDTO.getTotalSgst() : vendorInvoiceEntity.getTotalSgst());
	        vendorInvoiceEntity.setTotalIgst(vendorInvoiceMasterDTO.getTotalIgst() != null ? vendorInvoiceMasterDTO.getTotalIgst() : vendorInvoiceEntity.getTotalIgst());
	        vendorInvoiceEntity.setAmountExcluGst(vendorInvoiceMasterDTO.getAmountExcluGst() != null ? vendorInvoiceMasterDTO.getAmountExcluGst() : vendorInvoiceEntity.getAmountExcluGst());

	        // Save the updated vendor invoice
	        vendorInvoiceMasterService.update(vendorInvoiceEntity);

	        // Handle descriptions and base values (only set if provided)
	        if (vendorInvoiceMasterDTO.getInvoiceDescriptionValue() != null) {
	            // Clear existing descriptions before adding new ones
	            invoiceDescriptionValueService.deleteByVendorInvoiceId(vendorInvoiceEntity.getId());

	            for (InvoiceDescriptionValue descAndBase : vendorInvoiceMasterDTO.getInvoiceDescriptionValue()) {
	                if (!UtilValidate.isEmpty(descAndBase.getBaseValue())) {
	                    InvoiceDescriptionValue descriptionValue = new InvoiceDescriptionValue();
	                    descriptionValue.setVendorInvoice(vendorInvoiceEntity);
	                    descriptionValue.setItemDescription(descAndBase.getItemDescription());
	                    descriptionValue.setBaseValue(descAndBase.getBaseValue());
	                    descriptionValue.setGstPer(descAndBase.getGstPer());
	                    descriptionValue.setCgst(descAndBase.getCgst());
	                    descriptionValue.setSgst(descAndBase.getSgst());
	                    descriptionValue.setIgst(descAndBase.getIgst());
	                    descriptionValue.setAmtInclGst(descAndBase.getAmtInclGst());
	                    invoiceDescriptionValueService.save(descriptionValue);
	                }
	            }
	        }

	        // Prepare response data
	        statusMap.put("purchaseMasterEntity", vendorInvoiceEntity);
	        statusMap.put("status", "SUCCESS");
	        statusMap.put("statusCode", "RU_200");
	        statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");

	        return new ResponseEntity<>(statusMap, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	
	
//	@EncryptResponse
//	@PutMapping("/updateVendorInvoiceMaster")
//	public ResponseEntity<?> updateVendorInvoiceMaster(@RequestBody VendorInvioceMasterDTO vendorInvoiceMasterDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    try {
//	        // Validate mandatory fields
//	        if (UtilValidate.isEmpty(vendorInvoiceMasterDTO.getVendorName()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getClientName()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getProjectName()) ||
//	            vendorInvoiceMasterDTO.getInvoiceDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceNo()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getPoNo()) ||
//	            vendorInvoiceMasterDTO.getInvoiceDueDate() == null ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceDescription()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getInvoiceAmtIncluGst()) ||
//	            UtilValidate.isEmpty(vendorInvoiceMasterDTO.getStatus())) {
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        // Check if the vendor invoice exists
//	        VendorInvoiceMasterEntity vendorInvoiceEntity = vendorInvoiceMasterService.findById(vendorInvoiceMasterDTO.getId());
//	        if (vendorInvoiceEntity == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Vendor Invoice not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Update the existing invoice entity with new values from the DTO
//	        updateVendorInvoiceFields(vendorInvoiceEntity, vendorInvoiceMasterDTO);
//
//	        // Save the updated vendor invoice
//	        vendorInvoiceMasterService.update(vendorInvoiceEntity);
//
//	        // Handle descriptions and base values (only set if provided)
//	        if (vendorInvoiceMasterDTO.getInvoiceDescriptionValue() != null) {
//	            updateInvoiceDescriptionValues(vendorInvoiceEntity, vendorInvoiceMasterDTO.getInvoiceDescriptionValue());
//	        }
//
//	        // Prepare and return success response
//	        statusMap.put("purchaseMasterEntity", vendorInvoiceEntity);
//	        statusMap.put("status", "SUCCESS");
//	        statusMap.put("statusCode", "RU_200");
//	        statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");
//
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        // Enhanced error handling
//	        e.printStackTrace();
//	        statusMap.put("status", "FAIL");
//	        statusMap.put("statusCode", "RU_500");
//	        statusMap.put("statusMessage", "An error occurred while updating the vendor invoice.");
//	        return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//
//	private void updateVendorInvoiceFields(VendorInvoiceMasterEntity vendorInvoiceEntity, VendorInvioceMasterDTO vendorInvoiceMasterDTO) {
//	    vendorInvoiceEntity.setVendorName(vendorInvoiceMasterDTO.getVendorName());
//	    vendorInvoiceEntity.setClientName(vendorInvoiceMasterDTO.getClientName());
//	    vendorInvoiceEntity.setProjectName(vendorInvoiceMasterDTO.getProjectName());
//	    vendorInvoiceEntity.setInvoiceDate(vendorInvoiceMasterDTO.getInvoiceDate());
//	    vendorInvoiceEntity.setInvoiceNo(vendorInvoiceMasterDTO.getInvoiceNo());
//	    vendorInvoiceEntity.setPoNo(vendorInvoiceMasterDTO.getPoNo());
//	    vendorInvoiceEntity.setInvoiceDueDate(vendorInvoiceMasterDTO.getInvoiceDueDate());
//	    vendorInvoiceEntity.setInvoiceDescription(vendorInvoiceMasterDTO.getInvoiceDescription());
//	    vendorInvoiceEntity.setInvoiceAmountIncluGst(vendorInvoiceMasterDTO.getInvoiceAmountIncluGst());
//	    vendorInvoiceEntity.setInvoiceAmtIncluGst(Double.valueOf(vendorInvoiceMasterDTO.getInvoiceAmtIncluGst()));
//	    vendorInvoiceEntity.setStatus(vendorInvoiceMasterDTO.getStatus());
//
//	    // Optional fields
//	    vendorInvoiceEntity.setStartDate(vendorInvoiceMasterDTO.getStartDate());
//	    vendorInvoiceEntity.setEndDate(vendorInvoiceMasterDTO.getEndDate());
//	    vendorInvoiceEntity.setModeOfPayment(vendorInvoiceMasterDTO.getModeOfPayment());
//
//	    // Tax fields
//	    vendorInvoiceEntity.setInvoiceBaseValue(vendorInvoiceMasterDTO.getInvoiceBaseValue() != null ? vendorInvoiceMasterDTO.getInvoiceBaseValue() : vendorInvoiceEntity.getInvoiceBaseValue());
//	    vendorInvoiceEntity.setGstBaseValue(vendorInvoiceMasterDTO.getGstBaseValue() != null ? vendorInvoiceMasterDTO.getGstBaseValue() : vendorInvoiceEntity.getGstBaseValue());
//	    vendorInvoiceEntity.setInvoiceInclusiveOfGst(vendorInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null ? vendorInvoiceMasterDTO.getInvoiceInclusiveOfGst() : vendorInvoiceEntity.getInvoiceInclusiveOfGst());
//	    vendorInvoiceEntity.setTdsBaseValue(vendorInvoiceMasterDTO.getTdsBaseValue() != null ? vendorInvoiceMasterDTO.getTdsBaseValue() : vendorInvoiceEntity.getTdsBaseValue());
//	    vendorInvoiceEntity.setTdsPer(vendorInvoiceMasterDTO.getTdsPer() != null ? vendorInvoiceMasterDTO.getTdsPer() : vendorInvoiceEntity.getTdsPer());
//	    vendorInvoiceEntity.setTdsOnGst(vendorInvoiceMasterDTO.getTdsOnGst() != null ? vendorInvoiceMasterDTO.getTdsOnGst() : vendorInvoiceEntity.getTdsOnGst());
//	    vendorInvoiceEntity.setIgstOnTds(vendorInvoiceMasterDTO.getIgstOnTds() != null ? vendorInvoiceMasterDTO.getIgstOnTds() : vendorInvoiceEntity.getIgstOnTds());
//	    vendorInvoiceEntity.setCgstOnTds(vendorInvoiceMasterDTO.getCgstOnTds() != null ? vendorInvoiceMasterDTO.getCgstOnTds() : vendorInvoiceEntity.getCgstOnTds());
//	    vendorInvoiceEntity.setSgstOnTds(vendorInvoiceMasterDTO.getSgstOnTds() != null ? vendorInvoiceMasterDTO.getSgstOnTds() : vendorInvoiceEntity.getSgstOnTds());
//	    vendorInvoiceEntity.setTotalTdsDeducted(vendorInvoiceMasterDTO.getTotalTdsDeducted() != null ? vendorInvoiceMasterDTO.getTotalTdsDeducted() : vendorInvoiceEntity.getTotalTdsDeducted());
//	    vendorInvoiceEntity.setBalance(vendorInvoiceMasterDTO.getBalance() != null ? vendorInvoiceMasterDTO.getBalance() : vendorInvoiceEntity.getBalance());
//	    vendorInvoiceEntity.setPenalty(vendorInvoiceMasterDTO.getPenalty() != null ? vendorInvoiceMasterDTO.getPenalty() : vendorInvoiceEntity.getPenalty());
//	    vendorInvoiceEntity.setPenaltyDeductionOnBase(vendorInvoiceMasterDTO.getPenaltyDeductionOnBase() != null ? vendorInvoiceMasterDTO.getPenaltyDeductionOnBase() : vendorInvoiceEntity.getPenaltyDeductionOnBase());
//	    vendorInvoiceEntity.setGstOnPenalty(vendorInvoiceMasterDTO.getGstOnPenalty() != null ? vendorInvoiceMasterDTO.getGstOnPenalty() : vendorInvoiceEntity.getGstOnPenalty());
//	    vendorInvoiceEntity.setTotalPenaltyDeduction(vendorInvoiceMasterDTO.getTotalPenaltyDeduction() != null ? vendorInvoiceMasterDTO.getTotalPenaltyDeduction() : vendorInvoiceEntity.getTotalPenaltyDeduction());
//	    vendorInvoiceEntity.setCreditNote(vendorInvoiceMasterDTO.getCreditNote() != null ? vendorInvoiceMasterDTO.getCreditNote() : vendorInvoiceEntity.getCreditNote());
//	    vendorInvoiceEntity.setTotalPaymentReceived(vendorInvoiceMasterDTO.getTotalPaymentReceived() != null ? vendorInvoiceMasterDTO.getTotalPaymentReceived() : vendorInvoiceEntity.getTotalPaymentReceived());
//
//	    vendorInvoiceEntity.setTotalCgst(vendorInvoiceMasterDTO.getTotalCgst() != null ? vendorInvoiceMasterDTO.getTotalCgst() : vendorInvoiceEntity.getTotalCgst());
//	    vendorInvoiceEntity.setTotalSgst(vendorInvoiceMasterDTO.getTotalSgst() != null ? vendorInvoiceMasterDTO.getTotalSgst() : vendorInvoiceEntity.getTotalSgst());
//	    vendorInvoiceEntity.setTotalIgst(vendorInvoiceMasterDTO.getTotalIgst() != null ? vendorInvoiceMasterDTO.getTotalIgst() : vendorInvoiceEntity.getTotalIgst());
//	    vendorInvoiceEntity.setAmountExcluGst(vendorInvoiceMasterDTO.getAmountExcluGst() != null ? vendorInvoiceMasterDTO.getAmountExcluGst() : vendorInvoiceEntity.getAmountExcluGst());
//	}
//
//	private void updateInvoiceDescriptionValues(VendorInvoiceMasterEntity vendorInvoiceEntity, List<InvoiceDescriptionValue> descriptionValues) {
//	    // Clear existing descriptions before adding updated ones
////	    invoiceDescriptionValueService.deleteAllByVendorInvoice(vendorInvoiceEntity);
//
//	    // Add the new descriptions
//	    for (InvoiceDescriptionValue descAndBase : descriptionValues) {
//	        if (!UtilValidate.isEmpty(descAndBase.getBaseValue())) {
//	            InvoiceDescriptionValue descriptionValue = new InvoiceDescriptionValue();
//	            descriptionValue.setVendorInvoice(vendorInvoiceEntity);
//	            descriptionValue.setItemDescription(descAndBase.getItemDescription());
//	            descriptionValue.setBaseValue(descAndBase.getBaseValue());
//	            descriptionValue.setGstPer(descAndBase.getGstPer());
//	            descriptionValue.setCgst(descAndBase.getCgst());
//	            descriptionValue.setSgst(descAndBase.getSgst());
//	            descriptionValue.setIgst(descAndBase.getIgst());
//	            descriptionValue.setAmtInclGst(descAndBase.getAmtInclGst());
//	            invoiceDescriptionValueService.save(descriptionValue);
//	        }
//	    }
//	}



	
	
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
	
	@EncryptResponse
	@GetMapping("/getVendorInvoice/{invoiceNo}")
	public ResponseEntity<?> getVendorInvoiceByInvoiceNo(@PathVariable("invoiceNo") String invoiceNo) {
	    try {
	        // Decode the invoice number to handle URL encoding
	        String decodedInvoiceNo = URLDecoder.decode(invoiceNo, StandardCharsets.UTF_8);
	        
	        // Fetch the invoice by invoiceNo
	        Optional<VendorInvoiceMasterEntity> invoiceOptional = vendorInvoiceMasterService.findByInvoiceNo(decodedInvoiceNo);

	        if (invoiceOptional.isEmpty()) {
	            return CommonUtils.createResponse(Constants.FAIL, "Vendor Invoice Not Found", HttpStatus.NOT_FOUND);
	        }

	        VendorInvoiceMasterEntity invoice = invoiceOptional.get();
	        
	        // Map entity to DTO
	        VendorInvioceMasterDTO dto = new VendorInvioceMasterDTO();
	        BeanUtils.copyProperties(invoice, dto);

	        // Get client info correctly
	        if (invoice.getClientName() != null) {
	            try {
	                Long clientId = Long.parseLong(invoice.getClientName());
	                ClientMasterEntity client = vendorInvoiceMasterService.findClientById(clientId);
	                if (client != null) {
	                    dto.setClientId(client.getId());
	                    dto.setClientName(client.getClientName());
	                } else {
	                    dto.setClientId(null);
	                    dto.setClientName("Unknown");
	                }
	            } catch (NumberFormatException e) {
	                dto.setClientId(null);
	                dto.setClientName("Invalid Client ID");
	            }
	        }

	        // Handle nested invoiceDescriptionValue mapping properly
	        if (invoice.getInvoiceDescriptionValue() != null && !invoice.getInvoiceDescriptionValue().isEmpty()) {
	            List<InvoiceDescriptionValue> descriptionAndBaseValues = invoice.getInvoiceDescriptionValue().stream()
	                .map(desc -> {
	                    InvoiceDescriptionValue value = new InvoiceDescriptionValue();
	                    value.setItemDescription(desc.getItemDescription() != null ? desc.getItemDescription() : "");
	                    value.setBaseValue(desc.getBaseValue() != null ? desc.getBaseValue() : "0");
	                    value.setGstPer(desc.getGstPer() != null ? desc.getGstPer() : 0.0);
	                    value.setCgst(desc.getCgst() != null ? desc.getCgst() : 0.0);
	                    value.setSgst(desc.getSgst() != null ? desc.getSgst() : 0.0);
	                    value.setIgst(desc.getIgst() != null ? desc.getIgst() : 0.0);
	                    value.setAmtInclGst(desc.getAmtInclGst() != null ? desc.getAmtInclGst() : 0.0);
	                    return value;
	                })
	                .collect(Collectors.toList());
	            dto.setInvoiceDescriptionValue(descriptionAndBaseValues);
	        }

	        // Return response as a list (to match the expected JSON format)
	        return new ResponseEntity<>(Collections.singletonList(dto), HttpStatus.OK);

	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
//	@EncryptResponse
//	@GetMapping("/getVendorInvoice/{invoiceNo}")
//	public ResponseEntity<?> getVendorInvoiceByInvoiceNo(@PathVariable("invoiceNo") String invoiceNo) {
//	    try {
//	    	// Decode the invoice number to handle URL encoding
//	        String decodedInvoiceNo = URLDecoder.decode(invoiceNo, StandardCharsets.UTF_8);
//	        // Fetch the invoice by invoiceNo
//	        Optional<VendorInvoiceMasterEntity> invoiceOptional = vendorInvoiceMasterService.findByInvoiceNo(invoiceNo);
//
//	        if (invoiceOptional.isEmpty()) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Vendor Invoice Not Found", HttpStatus.NOT_FOUND);
//	        }
//
//	        VendorInvoiceMasterEntity invoice = invoiceOptional.get();
//	        
//	        // Map entity to DTO
//	        VendorInvioceMasterDTO dto = new VendorInvioceMasterDTO();
//	        BeanUtils.copyProperties(invoice, dto);
//
//	        // Handle nested descriptionsAndBaseValues mapping
//	        if (invoice.getInvoiceDescriptionValue() != null && !invoice.getInvoiceDescriptionValue().isEmpty()) {
//	            List<InvoiceDescriptionValue> descriptionAndBaseValues = invoice.getInvoiceDescriptionValue().stream()
//	                .map(desc -> {
//	                	InvoiceDescriptionValue value = new InvoiceDescriptionValue();
//	                    value.setItemDescription(desc.getItemDescription() != null ? desc.getItemDescription() : "");
//	                    value.setBaseValue(desc.getBaseValue() != null ? desc.getBaseValue() : "0");
//	                    return value;
//	                })
//	                .collect(Collectors.toList());
//	            dto.setInvoiceDescriptionValue(descriptionAndBaseValues);
//	        }
//
//	        return new ResponseEntity<>(dto, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
	}
