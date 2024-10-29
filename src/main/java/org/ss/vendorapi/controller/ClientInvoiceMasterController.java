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
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.service.ClientInvoiceMasterService;
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
public class ClientInvoiceMasterController {




	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Autowired 
	private Environment env;

	@Autowired
	private DataValidationService dataValidationService;

	@Autowired 
	private ClientInvoiceMasterService clientInvoiceService;	

	
	@EncryptResponse
	@PostMapping("/addClientInvoices")
	public ResponseEntity<?> addClientInvoices(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO, HttpServletRequest request) {

	    ResponseEntity<?> responseEntity = null;
	    String methodName = request.getRequestURI();
	    // logger.logMethodStart(methodName);

	    Map<String, Object> statusMap = new HashMap<>();

	    try {

	        if (UtilValidate.isEmpty(clientInvoiceDTO.getClientName()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getProjectName()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getDiscom()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDate()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceNo()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDescription()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDueDate()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getGstPer()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getGstAmount()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountExcluGst()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountIncluGst()) ||
	            UtilValidate.isEmpty(clientInvoiceDTO.getStatus())) {

	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        ClientInvoiceMasterEntity clientInvoice = new ClientInvoiceMasterEntity();

	        // Removed clientInvoice.setClientId(clientInvoiceDTO.getClientId());
	        clientInvoice.setClientName(clientInvoiceDTO.getClientName());
	        clientInvoice.setProjectName(clientInvoiceDTO.getProjectName());
	        clientInvoice.setDiscom(clientInvoiceDTO.getDiscom());
	        clientInvoice.setInvoiceDate(clientInvoiceDTO.getInvoiceDate());
	        clientInvoice.setInvoiceNo(clientInvoiceDTO.getInvoiceNo());
	        clientInvoice.setInvoiceDescription(clientInvoiceDTO.getInvoiceDescription());
	        clientInvoice.setInvoiceDueDate(clientInvoiceDTO.getInvoiceDueDate());
	        clientInvoice.setGstPer(clientInvoiceDTO.getGstPer());
	        clientInvoice.setGstAmount(clientInvoiceDTO.getGstAmount());
	        clientInvoice.setInvoiceAmountExcluGst(clientInvoiceDTO.getInvoiceAmountExcluGst());
	        clientInvoice.setInvoiceAmountIncluGst(clientInvoiceDTO.getInvoiceAmountIncluGst());
	        clientInvoice.setStatus(clientInvoiceDTO.getStatus());

	        if (clientInvoiceDTO.getStatus().equalsIgnoreCase("completed")) {

	            if (UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceBaseValue()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getGstBaseValue()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceInclusiveOfGst()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getTdsBaseValue()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getCgstOnTds()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getSgstOnTds()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalTdsDeducted()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getBalance()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getPenalty()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getPenaltyDeductionOnBase()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getGstOnPenalty()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalPenaltyDeduction()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalPaymentReceived())) {

	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	            }
	        }

	        clientInvoice.setInvoiceBaseValue(clientInvoiceDTO.getInvoiceBaseValue());
	        clientInvoice.setGstBaseValue(clientInvoiceDTO.getGstBaseValue());
	        clientInvoice.setInvoiceInclusiveOfGst(clientInvoiceDTO.getInvoiceInclusiveOfGst());
	        clientInvoice.setTdsBaseValue(clientInvoiceDTO.getTdsBaseValue());
	        clientInvoice.setCgstOnTds(clientInvoiceDTO.getCgstOnTds());
	        clientInvoice.setSgstOnTds(clientInvoiceDTO.getSgstOnTds());
	        clientInvoice.setTotalTdsDeducted(clientInvoiceDTO.getTotalTdsDeducted());
	        clientInvoice.setBalance(clientInvoiceDTO.getBalance());
	        clientInvoice.setPenalty(clientInvoiceDTO.getPenalty());
	        clientInvoice.setPenaltyDeductionOnBase(clientInvoiceDTO.getPenaltyDeductionOnBase());
	        clientInvoice.setGstOnPenalty(clientInvoiceDTO.getGstOnPenalty());
	        clientInvoice.setTotalPenaltyDeduction(clientInvoiceDTO.getTotalPenaltyDeduction());
	        clientInvoice.setTotalPaymentReceived(clientInvoiceDTO.getTotalPaymentReceived());

	        try {
	            clientInvoice = clientInvoiceService.save(clientInvoice);

	            if (clientInvoice != null) {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
	                statusMap.put(Parameters.status, Constants.SUCCESS);
	                statusMap.put(Parameters.statusCode, "RU_200");
	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
	            } else {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_NOT_GENERATED);
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

		
	
	

	//	*************************************************************************************************************************************************************************************
	//	*********************************************************************************get api****************************************************************************************************


	@EncryptResponse
	@GetMapping("/getAllClientInvoice")
	 public ResponseEntity<?> getAllClientInvoices() {
		    try {
		    	Map<String,Object> statusMap=new HashMap<>();
		        List<ClientInvoiceMasterEntity> clientInvoiceList = clientInvoiceService.findAll();
		        
		        statusMap.put("ClientInvoiceMasterEntity",clientInvoiceList);
		        statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
				statusMap.put(Parameters.status, Constants.SUCCESS);
				statusMap.put(Parameters.statusCode, "RU_200");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
		    } catch (Exception ex) {
		        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	
	@EncryptResponse
	@GetMapping("/byClientInvoiceNumber")
	public ResponseEntity<?> getClientInvoicesByNumber(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO ){
		Map<String,Object> statusMap=new HashMap<>();
		
		try {
			ClientInvoiceMasterEntity clientInvoiceEntity= clientInvoiceService.findByInvoiceNo(clientInvoiceDTO.getInvoiceNo());
			if(clientInvoiceEntity!=null) {
				statusMap.put("ClientInvoiceMasterEntity",clientInvoiceEntity);
				statusMap.put("Status","Success");
				statusMap.put("Status_Code","RU_200");
				statusMap.put("StatusMessage","SuccessFully Found");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
			}
			else {
				statusMap.put("Status","Fail");
				statusMap.put("Status_Code","RU_301");
				statusMap.put("StatusMessage","Data Not Found");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
			}
					
		}catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	
	@EncryptResponse
	@PutMapping("/updateClientInvoiceMaster")
	public ResponseEntity<?>updateClientMaster(@RequestBody ClientInvoiceMasterDTO clientInvoiceMasterDTO ){

		Map<String,Object> statusMap=new HashMap<String,Object>();
		try {
			ClientInvoiceMasterEntity clientInvoiceEntity=clientInvoiceService.findById(clientInvoiceMasterDTO.getId());

				
			clientInvoiceEntity.setClientName(clientInvoiceMasterDTO.getClientName()!=null?clientInvoiceMasterDTO.getClientName():clientInvoiceEntity.getClientName());
			clientInvoiceEntity.setProjectName(clientInvoiceMasterDTO.getProjectName()!=null?clientInvoiceMasterDTO.getProjectName():clientInvoiceEntity.getProjectName());
			clientInvoiceEntity.setDiscom(clientInvoiceMasterDTO.getDiscom() != null ? clientInvoiceMasterDTO.getDiscom() : clientInvoiceEntity.getDiscom());
		    clientInvoiceEntity.setInvoiceDate(clientInvoiceMasterDTO.getInvoiceDate() != null ? clientInvoiceMasterDTO.getInvoiceDate() : clientInvoiceEntity.getInvoiceDate());
		    clientInvoiceEntity.setInvoiceNo(clientInvoiceMasterDTO.getInvoiceNo() != null ? clientInvoiceMasterDTO.getInvoiceNo() : clientInvoiceEntity.getInvoiceNo());
		    clientInvoiceEntity.setInvoiceDescription(clientInvoiceMasterDTO.getInvoiceDescription() != null ? clientInvoiceMasterDTO.getInvoiceDescription() : clientInvoiceEntity.getInvoiceDescription());
		    clientInvoiceEntity.setInvoiceDueDate(clientInvoiceMasterDTO.getInvoiceDueDate() != null ? clientInvoiceMasterDTO.getInvoiceDueDate() : clientInvoiceEntity.getInvoiceDueDate());
		    clientInvoiceEntity.setGstPer(clientInvoiceMasterDTO.getGstPer() != null ? clientInvoiceMasterDTO.getGstPer() : clientInvoiceEntity.getGstPer());
		    clientInvoiceEntity.setGstAmount(clientInvoiceMasterDTO.getGstAmount() != null ? clientInvoiceMasterDTO.getGstAmount() : clientInvoiceEntity.getGstAmount());
		    clientInvoiceEntity.setInvoiceAmountExcluGst(clientInvoiceMasterDTO.getInvoiceAmountExcluGst() != null ? clientInvoiceMasterDTO.getInvoiceAmountExcluGst() : clientInvoiceEntity.getInvoiceAmountExcluGst());
		    clientInvoiceEntity.setInvoiceAmountIncluGst(clientInvoiceMasterDTO.getInvoiceAmountIncluGst() != null ? clientInvoiceMasterDTO.getInvoiceAmountIncluGst() : clientInvoiceEntity.getInvoiceAmountIncluGst());
		    clientInvoiceEntity.setStatus(clientInvoiceMasterDTO.getStatus() != null ? clientInvoiceMasterDTO.getStatus() : clientInvoiceEntity.getStatus());
		    clientInvoiceEntity.setInvoiceBaseValue(clientInvoiceMasterDTO.getInvoiceBaseValue() != null ? clientInvoiceMasterDTO.getInvoiceBaseValue() : clientInvoiceEntity.getInvoiceBaseValue());
		    clientInvoiceEntity.setGstBaseValue(clientInvoiceMasterDTO.getGstBaseValue() != null ? clientInvoiceMasterDTO.getGstBaseValue() : clientInvoiceEntity.getGstBaseValue());
		    clientInvoiceEntity.setInvoiceInclusiveOfGst(clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null ? clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() : clientInvoiceEntity.getInvoiceInclusiveOfGst());
		    clientInvoiceEntity.setTdsBaseValue(clientInvoiceMasterDTO.getTdsBaseValue() != null ? clientInvoiceMasterDTO.getTdsBaseValue() : clientInvoiceEntity.getTdsBaseValue());
		    clientInvoiceEntity.setCgstOnTds(clientInvoiceMasterDTO.getCgstOnTds() != null ? clientInvoiceMasterDTO.getCgstOnTds() : clientInvoiceEntity.getCgstOnTds());
		    clientInvoiceEntity.setSgstOnTds(clientInvoiceMasterDTO.getSgstOnTds() != null ? clientInvoiceMasterDTO.getSgstOnTds() : clientInvoiceEntity.getSgstOnTds());
		    clientInvoiceEntity.setTotalTdsDeducted(clientInvoiceMasterDTO.getTotalTdsDeducted() != null ? clientInvoiceMasterDTO.getTotalTdsDeducted() : clientInvoiceEntity.getTotalTdsDeducted());
		    clientInvoiceEntity.setBalance(clientInvoiceMasterDTO.getBalance() != null ? clientInvoiceMasterDTO.getBalance() : clientInvoiceEntity.getBalance());
		    clientInvoiceEntity.setPenalty(clientInvoiceMasterDTO.getPenalty() != null ? clientInvoiceMasterDTO.getPenalty() : clientInvoiceEntity.getPenalty());
		    clientInvoiceEntity.setPenaltyDeductionOnBase(clientInvoiceMasterDTO.getPenaltyDeductionOnBase() != null ? clientInvoiceMasterDTO.getPenaltyDeductionOnBase() : clientInvoiceEntity.getPenaltyDeductionOnBase());
		    clientInvoiceEntity.setGstOnPenalty(clientInvoiceMasterDTO.getGstOnPenalty() != null ? clientInvoiceMasterDTO.getGstOnPenalty() : clientInvoiceEntity.getGstOnPenalty());
		    clientInvoiceEntity.setTotalPenaltyDeduction(clientInvoiceMasterDTO.getTotalPenaltyDeduction() != null ? clientInvoiceMasterDTO.getTotalPenaltyDeduction() : clientInvoiceEntity.getTotalPenaltyDeduction());
		    clientInvoiceEntity.setTotalPaymentReceived(clientInvoiceMasterDTO.getTotalPaymentReceived() != null ? clientInvoiceMasterDTO.getTotalPaymentReceived() : clientInvoiceEntity.getTotalPaymentReceived());
		  

			clientInvoiceService.update(clientInvoiceEntity);
						
		statusMap.put("clientInvoiceMasterEntity",clientInvoiceEntity);
		statusMap.put("status", "SUCCESS");
		statusMap.put("statusCode", "RU_200");
		statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 

		return new ResponseEntity<>(statusMap,HttpStatus.OK);
	}catch(Exception e) {
		e.printStackTrace();

	}
	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
}
	
	
	@EncryptResponse
	@DeleteMapping("/deleteClientInvoice")
	public ResponseEntity<?> deleteClientInvoice(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			ClientInvoiceMasterEntity clientMaster = clientInvoiceService.findById(id);
			if(clientMaster!=null) {
				clientMaster.setActive(0);
				clientInvoiceService.update(clientMaster);

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