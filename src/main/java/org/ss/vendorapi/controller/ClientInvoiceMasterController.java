package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import org.ss.vendorapi.entity.ClientDescriptionAndBaseValue;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.service.ClientInvoiceDescriptionValueService;
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
	
//	@Autowired
//	private ClientInvoiceDescriptionValue clientInvoiceDescriptionValue;
	
	@Autowired
	private ClientInvoiceDescriptionValueService clientInvoiceDescriptionValueService;

	@EncryptResponse
	@PostMapping("/addClientInvoices")
	public ResponseEntity<?> addClientInvoices(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO, HttpServletRequest request) {
	    Map<String, Object> responseMap = new HashMap<>();
	    
	    try {
	        // Validate mandatory fields if status is not "completed"
	        if (!"completed".equalsIgnoreCase(clientInvoiceDTO.getStatus()) && (
	                UtilValidate.isEmpty(clientInvoiceDTO.getClientName()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getProjectName()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getDiscom()) ||
	                clientInvoiceDTO.getInvoiceDate() == null ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceNo()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDescription()) ||
	                clientInvoiceDTO.getInvoiceDueDate() == null ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getGstPer()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountExcluGst()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountIncluGst()))) {

	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        // Map fields to ClientInvoiceMasterEntity
	        ClientInvoiceMasterEntity clientInvoice = new ClientInvoiceMasterEntity();
	        clientInvoice.setClientName(clientInvoiceDTO.getClientName());
	        clientInvoice.setProjectName(clientInvoiceDTO.getProjectName());
	        clientInvoice.setDiscom(clientInvoiceDTO.getDiscom());
	        clientInvoice.setInvoiceDate(clientInvoiceDTO.getInvoiceDate());
	        clientInvoice.setInvoiceNo(clientInvoiceDTO.getInvoiceNo());
	        clientInvoice.setInvoiceDescription(clientInvoiceDTO.getInvoiceDescription());
	        clientInvoice.setInvoiceDueDate(clientInvoiceDTO.getInvoiceDueDate());
	        clientInvoice.setGstPer(clientInvoiceDTO.getGstPer());
	        clientInvoice.setInvoiceAmountExcluGst(clientInvoiceDTO.getInvoiceAmountExcluGst());
	        clientInvoice.setInvoiceAmountIncluGst(clientInvoiceDTO.getInvoiceAmountIncluGst());
	        clientInvoice.setStatus(clientInvoiceDTO.getStatus());

	        // Set optional fields
	        Optional.ofNullable(clientInvoiceDTO.getInvoiceBaseValue()).ifPresent(clientInvoice::setInvoiceBaseValue);
	        Optional.ofNullable(clientInvoiceDTO.getGstBaseValue()).ifPresent(clientInvoice::setGstBaseValue);
	        Optional.ofNullable(clientInvoiceDTO.getInvoiceInclusiveOfGst()).ifPresent(clientInvoice::setInvoiceInclusiveOfGst);
	        Optional.ofNullable(clientInvoiceDTO.getBalance()).ifPresent(clientInvoice::setBalance);
	        Optional.ofNullable(clientInvoiceDTO.getCreditNote()).ifPresent(clientInvoice::setCreditNote);
	        Optional.ofNullable(clientInvoiceDTO.getTotalPaymentReceived()).ifPresent(clientInvoice::setTotalPaymentReceived);

	        // Save ClientInvoiceMasterEntity
	        clientInvoice = clientInvoiceService.save(clientInvoice);

	        // Save descriptions and base values
	        if (clientInvoiceDTO.getDescriptionsAndBaseValues() != null) {
	            for (ClientDescriptionAndBaseValue description : clientInvoiceDTO.getClientDescriptionAndBaseValue()) {
	                ClientInvoiceDescriptionValue descriptionValue = new ClientInvoiceDescriptionValue();
	                descriptionValue.setClientInvoice(clientInvoice);
	                descriptionValue.setItemDescription(description.getItemDescription());
	                descriptionValue.setBaseValue(description.getBaseValue());
	                clientInvoiceDescriptionValueService.save(descriptionValue);
	            }
	        }

	        // Prepare success response
	        responseMap.put("clientName", clientInvoice.getClientName());
	        responseMap.put("projectName", clientInvoice.getProjectName());
	        responseMap.put("discom", clientInvoice.getDiscom());
	        responseMap.put("invoiceDate", clientInvoice.getInvoiceDate());
	        responseMap.put("invoiceNo", clientInvoice.getInvoiceNo());
	        responseMap.put("invoiceDescription", clientInvoice.getInvoiceDescription());
	        responseMap.put("invoiceDueDate", clientInvoice.getInvoiceDueDate());
	        responseMap.put("invoiceAmountExcluGst", clientInvoice.getInvoiceAmountExcluGst());
	        responseMap.put("gstPer", clientInvoice.getGstPer());
	        responseMap.put("gstAmount", clientInvoiceDTO.getGstAmount());
	        responseMap.put("invoiceAmountIncluGst", clientInvoice.getInvoiceAmountIncluGst());
	        responseMap.put("status", clientInvoice.getStatus());
	        responseMap.put("clientDescriptionAndBaseValue", clientInvoiceDTO.getClientDescriptionAndBaseValue());
	        responseMap.put("invoiceBaseValue", clientInvoiceDTO.getInvoiceBaseValue());
	        responseMap.put("gstBaseValue", clientInvoiceDTO.getGstBaseValue());
	        responseMap.put("invoiceInclusiveOfGst", clientInvoiceDTO.getInvoiceInclusiveOfGst());
	        responseMap.put("tdsper", clientInvoiceDTO.getTdsPer());
	        responseMap.put("tdsBaseValue", clientInvoiceDTO.getTdsBaseValue());
	        responseMap.put("tdsOnGst", clientInvoiceDTO.getTdsOnGst());
	        responseMap.put("billableState", clientInvoiceDTO.getBillableState());
	        responseMap.put("cgstOnTds", clientInvoiceDTO.getCgstOnTds());
	        responseMap.put("sgstOnTds", clientInvoiceDTO.getSgstOnTds());
	        responseMap.put("igstOnTds", clientInvoiceDTO.getIgstOnTds());
	        responseMap.put("totalTdsDeducted", clientInvoiceDTO.getTotalTdsDeducted());
	        responseMap.put("balance", clientInvoiceDTO.getBalance());
	        responseMap.put("penalty", clientInvoiceDTO.getPenalty());
	        responseMap.put("penaltyDeductionOnBase", clientInvoiceDTO.getPenaltyDeductionOnBase());
	        responseMap.put("gstOnPenalty", clientInvoiceDTO.getGstOnPenalty());
	        responseMap.put("totalPenaltyDeduction", clientInvoiceDTO.getTotalPenaltyDeduction());
	        responseMap.put("creditNote", clientInvoiceDTO.getCreditNote());
	        responseMap.put("totalPaymentReceived", clientInvoiceDTO.getTotalPaymentReceived());

	        return new ResponseEntity<>(responseMap, HttpStatus.OK);

	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}




		
	
	
	
//	@EncryptResponse
//	@PostMapping("/addClientInvoices")
//	public ResponseEntity<?> addClientInvoices(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO, HttpServletRequest request) {
//
//	    ResponseEntity<?> responseEntity = null;
//	    String methodName = request.getRequestURI();
//	    Map<String, Object> statusMap = new HashMap<>();
//
//	    try {
//	        if (UtilValidate.isEmpty(clientInvoiceDTO.getClientName()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getProjectName()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getDiscom()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDate()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceNo()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDescription()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDueDate()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getGstPer()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getGstAmount()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountExcluGst()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getStatus())) {
//
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        ClientInvoiceMasterEntity clientInvoice = new ClientInvoiceMasterEntity();
//
//	        // Set initial fields
//	        clientInvoice.setClientName(clientInvoiceDTO.getClientName());
//	        clientInvoice.setProjectName(clientInvoiceDTO.getProjectName());
//	        clientInvoice.setDiscom(clientInvoiceDTO.getDiscom());
//	        clientInvoice.setInvoiceDate(clientInvoiceDTO.getInvoiceDate());
//	        clientInvoice.setInvoiceNo(clientInvoiceDTO.getInvoiceNo());
//	        clientInvoice.setInvoiceDescription(clientInvoiceDTO.getInvoiceDescription());
//	        clientInvoice.setInvoiceDueDate(clientInvoiceDTO.getInvoiceDueDate());
//	        clientInvoice.setGstPer(clientInvoiceDTO.getGstPer());
//	        clientInvoice.setGstAmount(clientInvoiceDTO.getGstAmount());
//	        clientInvoice.setInvoiceAmountExcluGst(clientInvoiceDTO.getInvoiceAmountExcluGst());
//	        clientInvoice.setInvoiceAmountIncluGst(clientInvoiceDTO.getInvoiceAmountIncluGst());
//	        clientInvoice.setStatus(clientInvoiceDTO.getStatus());
//
//	        // Set additional fields when status is "completed"
//	        if (clientInvoiceDTO.getStatus().equalsIgnoreCase("completed")) {
//	            if (UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceBaseValue()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getGstBaseValue()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceInclusiveOfGst()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTdsPer()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTdsOnGstPer()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTdsBaseValue()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getIgstOnTds()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getCgstOnTds()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getSgstOnTds()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalTdsDeducted()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getBalance()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getPenalty()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getPenaltyDeductionOnBase()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getGstOnPenalty()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalPenaltyDeduction()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getCreditNote()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalPaymentReceived())) {
//
//	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	            }
//
//	            // Set fields that are specifically required for the "completed" status
//	            clientInvoice.setInvoiceBaseValue(clientInvoiceDTO.getInvoiceBaseValue());
//	            clientInvoice.setGstBaseValue(clientInvoiceDTO.getGstBaseValue());
//	            clientInvoice.setInvoiceInclusiveOfGst(clientInvoiceDTO.getInvoiceInclusiveOfGst());
//	            clientInvoice.setTdsPer(clientInvoiceDTO.getTdsPer());
//	            clientInvoice.setTdsOnGstPer(clientInvoiceDTO.getTdsOnGstPer());
//	            clientInvoice.setTdsBaseValue(clientInvoiceDTO.getTdsBaseValue());
//	            clientInvoice.setIgstOnTds(clientInvoiceDTO.getIgstOnTds());
//	            clientInvoice.setCgstOnTds(clientInvoiceDTO.getCgstOnTds());
//	            clientInvoice.setSgstOnTds(clientInvoiceDTO.getSgstOnTds());
//	            clientInvoice.setTotalTdsDeducted(clientInvoiceDTO.getTotalTdsDeducted());
//	            clientInvoice.setBalance(clientInvoiceDTO.getBalance());
//	            clientInvoice.setPenalty(clientInvoiceDTO.getPenalty());
//	            clientInvoice.setPenaltyDeductionOnBase(clientInvoiceDTO.getPenaltyDeductionOnBase());
//	            clientInvoice.setGstOnPenalty(clientInvoiceDTO.getGstOnPenalty());
//	            clientInvoice.setTotalPenaltyDeduction(clientInvoiceDTO.getTotalPenaltyDeduction());
//	            clientInvoice.setCreditNote(clientInvoiceDTO.getCreditNote());
//	            clientInvoice.setTotalPaymentReceived(clientInvoiceDTO.getTotalPaymentReceived());
//	        }
//
//	        try {
//	            clientInvoice = clientInvoiceService.save(clientInvoice);
//
//	            if (clientInvoice != null) {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
//	                statusMap.put(Parameters.status, Constants.SUCCESS);
//	                statusMap.put(Parameters.statusCode, "RU_200");
//	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	            } else {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_NOT_GENERATED);
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

	

//	@PostMapping("/addClientInvoices")
//	public ResponseEntity<?> addClientInvoices(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO, HttpServletRequest request) {
//
//	    ResponseEntity<?> responseEntity = null;
//	    String methodName = request.getRequestURI();
//	    // logger.logMethodStart(methodName);
//
//	    Map<String, Object> statusMap = new HashMap<>();
//
//	    try {
//
//	        if (UtilValidate.isEmpty(clientInvoiceDTO.getClientName()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getProjectName()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getDiscom()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDate()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceNo()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDescription()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDueDate()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getGstPer()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getGstAmount()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountExcluGst()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountIncluGst()) ||
//	            UtilValidate.isEmpty(clientInvoiceDTO.getStatus())) {
//
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        ClientInvoiceMasterEntity clientInvoice = new ClientInvoiceMasterEntity();
//
//	        // Removed clientInvoice.setClientId(clientInvoiceDTO.getClientId());
//	        clientInvoice.setClientName(clientInvoiceDTO.getClientName());
//	        clientInvoice.setProjectName(clientInvoiceDTO.getProjectName());
//	        clientInvoice.setDiscom(clientInvoiceDTO.getDiscom());
//	        clientInvoice.setInvoiceDate(clientInvoiceDTO.getInvoiceDate());
//	        clientInvoice.setInvoiceNo(clientInvoiceDTO.getInvoiceNo());
//	        clientInvoice.setInvoiceDescription(clientInvoiceDTO.getInvoiceDescription());
//	        clientInvoice.setInvoiceDueDate(clientInvoiceDTO.getInvoiceDueDate());
//	        clientInvoice.setGstPer(clientInvoiceDTO.getGstPer());
//	        clientInvoice.setGstAmount(clientInvoiceDTO.getGstAmount());
//	        clientInvoice.setInvoiceAmountExcluGst(clientInvoiceDTO.getInvoiceAmountExcluGst());
//	        clientInvoice.setInvoiceAmountIncluGst(clientInvoiceDTO.getInvoiceAmountIncluGst());
//	        clientInvoice.setStatus(clientInvoiceDTO.getStatus());
//
//	        if (clientInvoiceDTO.getStatus().equalsIgnoreCase("completed")) {
//
//	            if (UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceBaseValue()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getGstBaseValue()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceInclusiveOfGst()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTdsBaseValue()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getCgstOnTds()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getSgstOnTds()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalTdsDeducted()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getBalance()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getPenalty()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getPenaltyDeductionOnBase()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getGstOnPenalty()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalPenaltyDeduction()) ||
//	                UtilValidate.isEmpty(clientInvoiceDTO.getTotalPaymentReceived())) {
//
//	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	            }
//	        }
//
//	        clientInvoice.setInvoiceBaseValue(clientInvoiceDTO.getInvoiceBaseValue());
//	        clientInvoice.setGstBaseValue(clientInvoiceDTO.getGstBaseValue());
//	        clientInvoice.setInvoiceInclusiveOfGst(clientInvoiceDTO.getInvoiceInclusiveOfGst());
//	        clientInvoice.setTdsBaseValue(clientInvoiceDTO.getTdsBaseValue());
//	        clientInvoice.setCgstOnTds(clientInvoiceDTO.getCgstOnTds());
//	        clientInvoice.setSgstOnTds(clientInvoiceDTO.getSgstOnTds());
//	        clientInvoice.setTotalTdsDeducted(clientInvoiceDTO.getTotalTdsDeducted());
//	        clientInvoice.setBalance(clientInvoiceDTO.getBalance());
//	        clientInvoice.setPenalty(clientInvoiceDTO.getPenalty());
//	        clientInvoice.setPenaltyDeductionOnBase(clientInvoiceDTO.getPenaltyDeductionOnBase());
//	        clientInvoice.setGstOnPenalty(clientInvoiceDTO.getGstOnPenalty());
//	        clientInvoice.setTotalPenaltyDeduction(clientInvoiceDTO.getTotalPenaltyDeduction());
//	        clientInvoice.setTotalPaymentReceived(clientInvoiceDTO.getTotalPaymentReceived());
//
//	        try {
//	            clientInvoice = clientInvoiceService.save(clientInvoice);
//
//	            if (clientInvoice != null) {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
//	                statusMap.put(Parameters.status, Constants.SUCCESS);
//	                statusMap.put(Parameters.statusCode, "RU_200");
//	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	            } else {
//	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_NOT_GENERATED);
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

		
	
	

	//	*************************************************************************************************************************************************************************************
	//	*********************************************************************************get api****************************************************************************************************


	@EncryptResponse
	@GetMapping("/getAllClientInvoices")
	public ResponseEntity<?> getAllClientInvoices() {
	    try {
	        // Fetch all client invoices
	        List<ClientInvoiceMasterEntity> clientInvoices = clientInvoiceService.findAll();

	        // Map entities to response structure
	        List<Map<String, Object>> responseList = clientInvoices.stream().map(invoice -> {
	            Map<String, Object> invoiceMap = new HashMap<>();
	            invoiceMap.put("clientName", invoice.getClientName());
	            invoiceMap.put("projectName", invoice.getProjectName());
	            invoiceMap.put("discom", invoice.getDiscom());
	            invoiceMap.put("invoiceDate", invoice.getInvoiceDate());
	            invoiceMap.put("invoiceNo", invoice.getInvoiceNo());
	            invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription());
	            invoiceMap.put("invoiceDueDate", invoice.getInvoiceDueDate());
	            invoiceMap.put("invoiceAmountExcluGst", invoice.getInvoiceAmountExcluGst());
	            invoiceMap.put("gstPer", invoice.getGstPer());
	            invoiceMap.put("gstAmount", invoice.getGstAmount());
	            invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst());
	            invoiceMap.put("status", invoice.getStatus());
	            invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue());
	            invoiceMap.put("gstBaseValue", invoice.getGstBaseValue());
	            invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst());
	            invoiceMap.put("tdsper", invoice.getTdsPer());
	            invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue());
	            invoiceMap.put("tdsOnGst", invoice.getTdsOnGstPer());
	            invoiceMap.put("billableState", invoice.getBillableState());
	            invoiceMap.put("cgstOnTds", invoice.getCgstOnTds());
	            invoiceMap.put("sgstOnTds", invoice.getSgstOnTds());
	            invoiceMap.put("igstOnTds", invoice.getIgstOnTds());
	            invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted());
	            invoiceMap.put("balance", invoice.getBalance());
	            invoiceMap.put("penalty", invoice.getPenalty());
	            invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase());
	            invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty());
	            invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction());
	            invoiceMap.put("creditNote", invoice.getCreditNote());
	            invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived());

	            // Map nested descriptions
	            List<ClientInvoiceDescriptionValue> descriptions = clientInvoiceDescriptionValueService.findByClientInvoice(invoice);
	            List<Map<String, Object>> descriptionList = descriptions.stream().map(desc -> {
	                Map<String, Object> descMap = new HashMap<>();
	                descMap.put("itemDescription", desc.getItemDescription());
	                descMap.put("baseValue", desc.getBaseValue());
	                return descMap;
	            }).toList();

	            invoiceMap.put("descriptionsAndBaseValues", descriptionList);
	            return invoiceMap;
	        }).toList();

	        return new ResponseEntity<>(responseList, HttpStatus.OK);
	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
//	@EncryptResponse
//	@GetMapping("/getAllClientInvoice")
//	public ResponseEntity<?> getAllClientInvoices() {
//	    try {
//	        // Fetch all client invoices
//	        List<ClientInvoiceMasterEntity> clientInvoiceList = clientInvoiceService.findAll();
//
//	        // Prepare the response
//	        Map<String, Object> responseMap = new HashMap<>();
//	        responseMap.put("ClientInvoiceMasterEntity", clientInvoiceList);
//	        responseMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
//	        responseMap.put(Parameters.status, Constants.SUCCESS);
//	        responseMap.put(Parameters.statusCode, "RU_200");
//
//	        return new ResponseEntity<>(responseMap, HttpStatus.OK);
//	    } catch (Exception ex) {
//	        // Return error response in case of exception
//	        Map<String, Object> errorResponse = new HashMap<>();
//	        errorResponse.put("status", Constants.FAIL);
//	        errorResponse.put("statusMsg", "An error occurred while fetching client invoices.");
//	        errorResponse.put("errorDetails", ex.getMessage());
//	        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

//	@EncryptResponse
//	@GetMapping("/getAllClientInvoice")
//	 public ResponseEntity<?> getAllClientInvoices() {
//		    try {
//		    	Map<String,Object> statusMap=new HashMap<>();
//		        List<ClientInvoiceMasterEntity> clientInvoiceList = clientInvoiceService.findAll();
//		        
//		        statusMap.put("ClientInvoiceMasterEntity",clientInvoiceList);
//		        statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
//				statusMap.put(Parameters.status, Constants.SUCCESS);
//				statusMap.put(Parameters.statusCode, "RU_200");
//				return new ResponseEntity<>(statusMap,HttpStatus.OK);
//		    } catch (Exception ex) {
//		        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		    }
//		}
	
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
	
	@GetMapping("/getClientInvoice/{invoiceNo}")
	public ResponseEntity<?> getClientInvoice(@PathVariable String invoiceNo) {
	    try {
	        // Fetch invoice by invoiceNo
	        ClientInvoiceMasterEntity clientInvoice = clientInvoiceService.findByInvoiceNo(invoiceNo);

	        // Check if the invoice exists
	        if (clientInvoice == null) {
	            return new ResponseEntity<>(Map.of("message", "Invoice not found"), HttpStatus.NOT_FOUND);
	        }

	        // Prepare response data
	        Map<String, Object> responseMap = new HashMap<>();
	        responseMap.put("clientName", clientInvoice.getClientName());
	        responseMap.put("projectName", clientInvoice.getProjectName());
	        responseMap.put("discom", clientInvoice.getDiscom());
	        responseMap.put("invoiceDate", clientInvoice.getInvoiceDate());
	        responseMap.put("invoiceNo", clientInvoice.getInvoiceNo());
	        responseMap.put("invoiceDescription", clientInvoice.getInvoiceDescription());
	        responseMap.put("invoiceDueDate", clientInvoice.getInvoiceDueDate());
	        responseMap.put("gstPer", clientInvoice.getGstPer());
	        responseMap.put("gstAmount", clientInvoice.getGstAmount());
	        responseMap.put("invoiceAmountExcluGst", clientInvoice.getInvoiceAmountExcluGst());
	        responseMap.put("invoiceAmountIncluGst", clientInvoice.getInvoiceAmountIncluGst());
	        responseMap.put("status", clientInvoice.getStatus());
	        responseMap.put("invoiceBaseValue", clientInvoice.getInvoiceBaseValue());
	        responseMap.put("gstBaseValue", clientInvoice.getGstBaseValue());
	        responseMap.put("invoiceInclusiveOfGst", clientInvoice.getInvoiceInclusiveOfGst());
	        responseMap.put("tdsPer", clientInvoice.getTdsPer());
	        responseMap.put("tdsBaseValue", clientInvoice.getTdsBaseValue());
	        responseMap.put("igstOnTds", clientInvoice.getIgstOnTds());
	        responseMap.put("cgstOnTds", clientInvoice.getCgstOnTds());
	        responseMap.put("sgstOnTds", clientInvoice.getSgstOnTds());
	        responseMap.put("totalTdsDeducted", clientInvoice.getTotalTdsDeducted());
	        responseMap.put("balance", clientInvoice.getBalance());
	        responseMap.put("penalty", clientInvoice.getPenalty());
	        responseMap.put("penaltyDeductionOnBase", clientInvoice.getPenaltyDeductionOnBase());
	        responseMap.put("gstOnPenalty", clientInvoice.getGstOnPenalty());
	        responseMap.put("totalPenaltyDeduction", clientInvoice.getTotalPenaltyDeduction());
	        responseMap.put("creditNote", clientInvoice.getCreditNote());
	        responseMap.put("totalPaymentReceived", clientInvoice.getTotalPaymentReceived());
	        responseMap.put("billableState", clientInvoice.getBillableState());

	        // Fetch and map DescriptionAndBaseValue
	        List<ClientInvoiceDescriptionValue> descriptions = clientInvoiceDescriptionValueService.findByInvoiceNo(invoiceNo);
	        List<Map<String, Object>> descriptionList = descriptions.stream().map(desc -> {
	            Map<String, Object> descMap = new HashMap<>();
	            descMap.put("itemDescription", desc.getItemDescription());
	            descMap.put("baseValue", desc.getBaseValue());
	            return descMap;
	        }).toList();

	        responseMap.put("descriptionsAndBaseValues", descriptionList);

	        return new ResponseEntity<>(responseMap, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	

}