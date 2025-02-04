package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;
import org.ss.vendorapi.entity.PurchaseMasterEntity;
import org.ss.vendorapi.modal.PurchaseRequestDTO;
import org.ss.vendorapi.service.PurchaseBOMService;
import org.ss.vendorapi.service.PurchaseMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class PurchaseMasterController {

	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION, CLASS_NAME.toString());

	@Autowired
	private Environment env;

	@Autowired
	private PurchaseMasterService purchaseMasterService;


	@Autowired
	private PurchaseBOMService purchaseBOMService;
	
	@EncryptResponse
	@PostMapping("/addPurchase")
	public ResponseEntity<?> addPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO, HttpServletRequest request) {
	    String methodName = request.getRequestURI();

	    Map<String, Object> statusMap = new HashMap<>();
	    List<Map<String, Object>> bomList = new ArrayList<>();

	    try {
	        // Validate required parameters
	        if (UtilValidate.isEmpty(purchaseRequestDTO.getClientName()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getProjectName()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getVendor()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getRequestorName()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getDescription()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getPrNo()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getPrAmount()) ||
	                UtilValidate.isEmpty(purchaseRequestDTO.getStatus()) ||
	                purchaseRequestDTO.getPrDate() == null) {
	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        // Create and populate PurchaseMasterEntity
	        PurchaseMasterEntity purchaseMaster = new PurchaseMasterEntity();
	        purchaseMaster.setClientName(purchaseRequestDTO.getClientName());
	        purchaseMaster.setProjectName(purchaseRequestDTO.getProjectName());
	        purchaseMaster.setVendor(purchaseRequestDTO.getVendor());
	        purchaseMaster.setRequestorName(purchaseRequestDTO.getRequestorName());
	        purchaseMaster.setDescription(purchaseRequestDTO.getDescription());
	        purchaseMaster.setPrNo(purchaseRequestDTO.getPrNo());
	        purchaseMaster.setPrDate(purchaseRequestDTO.getPrDate());
	        purchaseMaster.setPrAmount(purchaseRequestDTO.getPrAmount());
	        purchaseMaster.setStatus(purchaseRequestDTO.getStatus());
	        purchaseMaster.setPrFor(purchaseRequestDTO.getPrFor());
	        purchaseMaster.setRejectionReason(purchaseRequestDTO.getRejectionReason());

	        // Handle status (Approved, Rejected, Pending, PoApproved)
	        String status = purchaseRequestDTO.getStatus();
	        if ("Approved".equalsIgnoreCase(status)) {
	            purchaseMaster.setStatus("Approved");
	            purchaseMaster.setApproveDate(new Date());
	            purchaseMaster.setPoNo(null); // Clear PO fields if status is "Approved"
	            purchaseMaster.setPoApproveDate(null);
	        } else if ("Rejected".equalsIgnoreCase(status)) {
	            purchaseMaster.setStatus("Rejected");
	            purchaseMaster.setApproveDate(null); // Clear approval fields if status is "Rejected"
	            purchaseMaster.setPoNo(null);
	            purchaseMaster.setPoApproveDate(null);
	        } else if ("PoApproved".equalsIgnoreCase(status)) {
	            purchaseMaster.setStatus("PoApproved");
	            purchaseMaster.setApproveDate(new Date());
	            purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo()); // Set PO fields if status is "PoApproved"
	            purchaseMaster.setPoApproveDate(purchaseRequestDTO.getPoApproveDate());
	        } else {
	            purchaseMaster.setStatus("Pending");
	            purchaseMaster.setApproveDate(null);
	            purchaseMaster.setPoNo(null);
	            purchaseMaster.setPoApproveDate(null);
	        }

	        // Save PurchaseMasterEntity
	        purchaseMaster = purchaseMasterService.save(purchaseMaster);

	        // Process and save BOM items
	        for (PurchaseBOMMasterEntity purchaseBOMDTO : purchaseRequestDTO.getBom()) {
	            if (UtilValidate.isEmpty(purchaseBOMDTO.getBomDescription()) ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getService()) ||
	                    purchaseBOMDTO.getStartDate() == null ||
	                    purchaseBOMDTO.getEndDate() == null ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getRenewable()) ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getRateUnit()) ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getGstRate()) ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getQuantity()) ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getAmountExclGst()) ||
	                    UtilValidate.isEmpty(purchaseBOMDTO.getAmountInclGst())) {
	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	            }

	            PurchaseBOMMasterEntity purchaseBOM = new PurchaseBOMMasterEntity();
	            purchaseBOM.setBomDescription(purchaseBOMDTO.getBomDescription());
	            purchaseBOM.setPurchaseId(purchaseMaster.getId().toString());
	            purchaseBOM.setService(purchaseBOMDTO.getService());
	            purchaseBOM.setStartDate(purchaseBOMDTO.getStartDate());
	            purchaseBOM.setEndDate(purchaseBOMDTO.getEndDate());
	            purchaseBOM.setRenewable(purchaseBOMDTO.getRenewable());
	            purchaseBOM.setRateUnit(purchaseBOMDTO.getRateUnit());
	            purchaseBOM.setQuantity(purchaseBOMDTO.getQuantity());
	            purchaseBOM.setGstRate(purchaseBOMDTO.getGstRate());
	            purchaseBOM.setAmountExclGst(purchaseBOMDTO.getAmountExclGst());
	            purchaseBOM.setAmountInclGst(purchaseBOMDTO.getAmountInclGst());
	            purchaseBOM.setUom(purchaseBOMDTO.getUom());

	            purchaseBOM = purchaseBOMService.save(purchaseBOM);

	            // Build BOM response map
	            Map<String, Object> bomResponseMap = new HashMap<>();
	            bomResponseMap.put("amountExclGst", purchaseBOM.getAmountExclGst());
	            bomResponseMap.put("amountInclGst", purchaseBOM.getAmountInclGst());
	            bomResponseMap.put("bomDescription", purchaseBOM.getBomDescription());
	            bomResponseMap.put("endDate", purchaseBOM.getEndDate());
	            bomResponseMap.put("gstRate", purchaseBOM.getGstRate());
	            bomResponseMap.put("quantity", purchaseBOM.getQuantity());
	            bomResponseMap.put("rateUnit", purchaseBOM.getRateUnit());
	            bomResponseMap.put("renewable", purchaseBOM.getRenewable());
	            bomResponseMap.put("service", purchaseBOM.getService());
	            bomResponseMap.put("startDate", purchaseBOM.getStartDate());
	            bomResponseMap.put("uom", purchaseBOM.getUom());

	            bomList.add(bomResponseMap);
	        }

	        // Prepare final response map
	        statusMap.put("clientName", purchaseMaster.getClientName());
	        statusMap.put("description", purchaseMaster.getDescription());
	        statusMap.put("prAmount", purchaseMaster.getPrAmount());
	        statusMap.put("prDate", purchaseMaster.getPrDate());
	        statusMap.put("prNo", purchaseMaster.getPrNo());
	        statusMap.put("projectName", purchaseMaster.getProjectName());
	        statusMap.put("requestorName", purchaseMaster.getRequestorName());
	        statusMap.put("status", purchaseMaster.getStatus());
	        statusMap.put("vendor", purchaseMaster.getVendor());
	        statusMap.put("bom", bomList);

	        // Handle status-specific fields for response
	        if ("Pending".equalsIgnoreCase(status)) {
	            // Do not include approveDate, poNo, rejectionReason, poApproveDate
	        } else if ("Rejected".equalsIgnoreCase(status)) {
	            statusMap.put("rejectionReason", purchaseMaster.getRejectionReason());
	        } else if ("Approved".equalsIgnoreCase(status)) {
	            statusMap.put("approveDate", purchaseMaster.getApproveDate());
	        } else if ("PoApproved".equalsIgnoreCase(status)) {
	            statusMap.put("approveDate", purchaseMaster.getApproveDate());
	            statusMap.put("poNo", purchaseMaster.getPoNo());
	            statusMap.put("poApproveDate", purchaseMaster.getPoApproveDate());
	        }

	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Purchase created successfully");
	        return new ResponseEntity<>(response, HttpStatus.OK);


	    } catch (Exception ex) {
	        statusMap.put("error", env.getProperty("common.api.error"));
	        statusMap.put("message", ex.getMessage());
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



	
//	@EncryptResponse
//	@PostMapping("/addPurchase")
//	public ResponseEntity<?> addPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO, HttpServletRequest request) {
//	    String methodName = request.getRequestURI();
//
//	    Map<String, Object> statusMap = new HashMap<>();
//	    List<Map<String, Object>> bomList = new ArrayList<>();
//
//	    try {
//	        // Validate required parameters
//	        if (UtilValidate.isEmpty(purchaseRequestDTO.getClientName()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getProjectName()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getVendor()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getRequestorName()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getDescription()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getPrNo()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getPrAmount()) ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getStatus()) ||
//	                purchaseRequestDTO.getPrDate() == null ||
//	                purchaseRequestDTO.getApproveDate() == null ||
//	                UtilValidate.isEmpty(purchaseRequestDTO.getPoNo()) ||
//	                purchaseRequestDTO.getPoApproveDate() == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        // Create and populate PurchaseMasterEntity
//	        PurchaseMasterEntity purchaseMaster = new PurchaseMasterEntity();
//	        purchaseMaster.setClientName(purchaseRequestDTO.getClientName());
//	        purchaseMaster.setProjectName(purchaseRequestDTO.getProjectName());
//	        purchaseMaster.setVendor(purchaseRequestDTO.getVendor());
//	        purchaseMaster.setRequestorName(purchaseRequestDTO.getRequestorName());
//	        purchaseMaster.setDescription(purchaseRequestDTO.getDescription());
//	        purchaseMaster.setPrNo(purchaseRequestDTO.getPrNo());
//	        purchaseMaster.setPrDate(purchaseRequestDTO.getPrDate());
//	        purchaseMaster.setPrAmount(purchaseRequestDTO.getPrAmount());
//	        purchaseMaster.setStatus(purchaseRequestDTO.getStatus());
//	        purchaseMaster.setApproveDate(purchaseRequestDTO.getApproveDate());
//	        purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo());
//	        purchaseMaster.setPrFor(purchaseRequestDTO.getPrFor());
//	        purchaseMaster.setRejectionReason(purchaseRequestDTO.getRejectionReason());
//	        purchaseMaster.setPoApproveDate(purchaseRequestDTO.getPoApproveDate());
//
//	        // Handle status (Approved or Rejected)
//	        String status = purchaseRequestDTO.getStatus();
//	        if ("Approved".equalsIgnoreCase(status)) {
//	            purchaseMaster.setStatus("Approved");
//	            purchaseMaster.setApproveDate(new Date());
//	        } else {
//	            purchaseMaster.setStatus("Pending");
//	        }
//
//	        // Save PurchaseMasterEntity
//	        purchaseMaster = purchaseMasterService.save(purchaseMaster);
//
//	        // Process and save BOM items
//	        for (PurchaseBOMMasterEntity purchaseBOMDTO : purchaseRequestDTO.getBom()) {
//	            if (UtilValidate.isEmpty(purchaseBOMDTO.getBomDescription()) ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getService()) ||
//	                    purchaseBOMDTO.getStartDate() == null ||
//	                    purchaseBOMDTO.getEndDate() == null ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getRenewable()) ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getRateUnit()) ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getGstRate()) ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getQuantity()) ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getAmountExclGst()) ||
//	                    UtilValidate.isEmpty(purchaseBOMDTO.getAmountInclGst())) {
//	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	            }
//
//	            PurchaseBOMMasterEntity purchaseBOM = new PurchaseBOMMasterEntity();
//	            purchaseBOM.setBomDescription(purchaseBOMDTO.getBomDescription());
//	            purchaseBOM.setPurchaseId(purchaseMaster.getId().toString());
//	            purchaseBOM.setService(purchaseBOMDTO.getService());
//	            purchaseBOM.setStartDate(purchaseBOMDTO.getStartDate());
//	            purchaseBOM.setEndDate(purchaseBOMDTO.getEndDate());
//	            purchaseBOM.setRenewable(purchaseBOMDTO.getRenewable());
//	            purchaseBOM.setRateUnit(purchaseBOMDTO.getRateUnit());
//	            purchaseBOM.setQuantity(purchaseBOMDTO.getQuantity());
//	            purchaseBOM.setGstRate(purchaseBOMDTO.getGstRate());
//	            purchaseBOM.setAmountExclGst(purchaseBOMDTO.getAmountExclGst());
//	            purchaseBOM.setAmountInclGst(purchaseBOMDTO.getAmountInclGst());
//	            purchaseBOM.setUom(purchaseBOMDTO.getUom());
//
//	            purchaseBOM = purchaseBOMService.save(purchaseBOM);
//
//	            // Build BOM response map
//	            Map<String, Object> bomResponseMap = new HashMap<>();
//	            bomResponseMap.put("amountExclGst", purchaseBOM.getAmountExclGst());
//	            bomResponseMap.put("amountInclGst", purchaseBOM.getAmountInclGst());
//	            bomResponseMap.put("bomDescription", purchaseBOM.getBomDescription());
//	            bomResponseMap.put("endDate", purchaseBOM.getEndDate());
//	            bomResponseMap.put("gstRate", purchaseBOM.getGstRate());
//	            bomResponseMap.put("quantity", purchaseBOM.getQuantity());
//	            bomResponseMap.put("rateUnit", purchaseBOM.getRateUnit());
//	            bomResponseMap.put("renewable", purchaseBOM.getRenewable());
//	            bomResponseMap.put("service", purchaseBOM.getService());
//	            bomResponseMap.put("startDate", purchaseBOM.getStartDate());
//	            bomResponseMap.put("uom", purchaseBOM.getUom());
//
//	            bomList.add(bomResponseMap);
//	        }
//
//	        // Prepare final response map
//	        statusMap.put("clientName", purchaseMaster.getClientName());
//	        statusMap.put("description", purchaseMaster.getDescription());
//	        statusMap.put("poNo", purchaseMaster.getPoNo());
//	        statusMap.put("prAmount", purchaseMaster.getPrAmount());
//	        statusMap.put("prDate", purchaseMaster.getPrDate());
//	        statusMap.put("prNo", purchaseMaster.getPrNo());
//	        statusMap.put("projectName", purchaseMaster.getProjectName());
//	        statusMap.put("requestorName", purchaseMaster.getRequestorName());
//	        statusMap.put("status", purchaseMaster.getStatus());
//	        statusMap.put("vendor", purchaseMaster.getVendor());
//	        statusMap.put("approveDate", purchaseMaster.getApproveDate());
//	        statusMap.put("poApproveDate", purchaseMaster.getPoApproveDate());
//	        statusMap.put("bom", bomList);
//
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        statusMap.put("error", env.getProperty("common.api.error"));
//	        statusMap.put("message", ex.getMessage());
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}





	
//	@EncryptResponse
//	@PostMapping("/addPurchase")
//	public ResponseEntity<Map<String, Object>> addPurchase(@RequestBody PurchaseMasterEntity purchaseRequestDTO) {
//	    Map<String, Object> response = new HashMap<>();
//
//	    try {
//	        // Check if a purchase with the same PR number already exists
//	        PurchaseMasterEntity existingPurchase = purchaseMasterService.findByPrNo(purchaseRequestDTO.getPrNo());
//	        
//	        if (existingPurchase != null) {
//	            // If the purchase already exists, return an error message
//	            response.put("message", "Purchase with the same PR number already exists.");
//	            response.put("status", HttpStatus.CONFLICT); // 409 Conflict status
//	            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//	        }
//
//	        // Create a new PurchaseMasterEntity and populate it
//	        PurchaseMasterEntity purchaseMaster = new PurchaseMasterEntity();
//	        purchaseMaster.setClientName(purchaseRequestDTO.getClientName());
//	        purchaseMaster.setProjectName(purchaseRequestDTO.getProjectName());
//	        purchaseMaster.setVendor(purchaseRequestDTO.getVendor());
//	        purchaseMaster.setRequestorName(purchaseRequestDTO.getRequestorName());
//	        purchaseMaster.setDescription(purchaseRequestDTO.getDescription());
//	        purchaseMaster.setPrNo(purchaseRequestDTO.getPrNo());
//	        purchaseMaster.setPrDate(purchaseRequestDTO.getPrDate());
//	        purchaseMaster.setPrAmount(purchaseRequestDTO.getPrAmount());
//
//	        // New fields added:
//	        purchaseMaster.setBillability(purchaseRequestDTO.getBillability());
//	        purchaseMaster.setTypeOfExpenditure(purchaseRequestDTO.getTypeOfExpenditure());
//	        purchaseMaster.setPrFor(purchaseRequestDTO.getPrFor());
//	        purchaseMaster.setRejectionReason(purchaseRequestDTO.getRejectionReason());
//
//	        // Handle status
//	        String status = purchaseRequestDTO.getStatus();
//	        if ("Approved".equalsIgnoreCase(status)) {
//	            purchaseMaster.setStatus("Approved");
//	            purchaseMaster.setApproveDate(new Date()); // Set current date for approval
//	            purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo()); // Set PO number
//	        } else if ("Reject".equalsIgnoreCase(status)) {
//	            purchaseMaster.setStatus("Pending"); // Set status to Pending if rejected
//	        } else {
//	            purchaseMaster.setStatus("Pending"); // Default to Pending
//	        }
//
//	        // Save the new purchase
//	        purchaseMasterService.savePurchase(purchaseMaster);
//	        response.put("message", "Purchase added successfully.");
//	        response.put("status", HttpStatus.OK);
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        response.put("message", "Error while adding purchase: " + e.getMessage());
//	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
//	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	
//	@EncryptResponse
//	@PostMapping("/addPurchase")
//	public ResponseEntity<Map<String, Object>> addPurchase(@RequestBody PurchaseMasterEntity purchaseRequestDTO) {
//	    Map<String, Object> response = new HashMap<>();
//
//	    try {
//	        // Check if a purchase with the same PR number already exists
//	        PurchaseMasterEntity existingPurchase = purchaseMasterService.findByPrNo(purchaseRequestDTO.getPrNo());
//	        
//	        if (existingPurchase != null) {
//	            // If the purchase already exists, return an error message
//	            response.put("message", "Purchase with the same PR number already exists.");
//	            response.put("status", HttpStatus.CONFLICT); // 409 Conflict status
//	            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
//	        }
//
//	        // Create a new PurchaseMasterEntity and populate it
//	        PurchaseMasterEntity purchaseMaster = new PurchaseMasterEntity();
//	        purchaseMaster.setClientName(purchaseRequestDTO.getClientName());
//	        purchaseMaster.setProjectName(purchaseRequestDTO.getProjectName());
//	        purchaseMaster.setVendor(purchaseRequestDTO.getVendor());
//	        purchaseMaster.setRequestorName(purchaseRequestDTO.getRequestorName());
//	        purchaseMaster.setDescription(purchaseRequestDTO.getDescription());
//	        purchaseMaster.setPrNo(purchaseRequestDTO.getPrNo());
//	        purchaseMaster.setPrDate(purchaseRequestDTO.getPrDate());
//	        purchaseMaster.setPrAmount(purchaseRequestDTO.getPrAmount());
//
//	        // Handle status
//	        String status = purchaseRequestDTO.getStatus();
//	        if ("Approved".equalsIgnoreCase(status)) {
//	            purchaseMaster.setStatus("Approved");
//	            purchaseMaster.setApproveDate(new Date()); // Set current date for approval
//	            purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo()); // Set PO number
//	        } else if ("Reject".equalsIgnoreCase(status)) {
//	            purchaseMaster.setStatus("Pending"); // Set status to Pending if rejected
//	        } else {
//	            purchaseMaster.setStatus("Pending"); // Default to Pending
//	        }
//
//	        // Save the new purchase
//	        purchaseMasterService.savePurchase(purchaseMaster);
//	        response.put("message", "Purchase added successfully.");
//	        response.put("status", HttpStatus.OK);
//	        return new ResponseEntity<>(response, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        response.put("message", "Error while adding purchase: " + e.getMessage());
//	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
//	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

//*************************************************************************************************************************************************************



//	@PostMapping("/addPurchase")
//	public ResponseEntity<?> addPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO, HttpServletRequest request) {
//		String methodName = request.getRequestURI();
////		logger.logMethodStart(methodName);
//
//		Map<String, Object> statusMap = new HashMap<>();
//		List<PurchaseBOMMasterEntity> savedEntities = new ArrayList<>();
//
//		try {
//
//
//			if (UtilValidate.isEmpty(UtilValidate.isEmpty(purchaseRequestDTO.getClientName()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getProjectName()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getVendor()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getRequestorName()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getDescription()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getPrNo()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getPrAmount()) ||
//					UtilValidate.isEmpty(purchaseRequestDTO.getStatus()) ||
//					purchaseRequestDTO.getPrDate()==null||
//					purchaseRequestDTO.getApproveDate()==null||
//					UtilValidate.isEmpty(purchaseRequestDTO.getPoNo())))  {
//
//				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//			}
//
//			PurchaseMasterEntity purchaseMaster = new PurchaseMasterEntity();
//
//			purchaseMaster.setClientName(purchaseRequestDTO.getClientName());
//			purchaseMaster.setProjectName(purchaseRequestDTO.getProjectName());
//			purchaseMaster.setVendor(purchaseRequestDTO.getVendor());
//			purchaseMaster.setRequestorName(purchaseRequestDTO.getRequestorName());
//			purchaseMaster.setDescription(purchaseRequestDTO.getDescription());
//			purchaseMaster.setPrNo(purchaseRequestDTO.getPrNo());
//			purchaseMaster.setPrDate(purchaseRequestDTO.getPrDate());
//			purchaseMaster.setPrAmount(purchaseRequestDTO.getPrAmount());
//			purchaseMaster.setStatus(purchaseRequestDTO.getStatus());
//			purchaseMaster.setApproveDate(purchaseRequestDTO.getApproveDate());
//			purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo());
//
//			purchaseMaster=purchaseMasterService.save(purchaseMaster);
//			for (PurchaseBOMMasterEntity purchaseMasterDTO : purchaseRequestDTO.getBom()) {
//
//				if (UtilValidate.isEmpty(purchaseMasterDTO.getBomDescription()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getService()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getTypeOfExpenditure()) ||
//						purchaseMasterDTO.getStartDate() == null ||
//						purchaseMasterDTO.getEndDate() == null ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getRenewable()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getRateUnit()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getGstRate()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getQuantity()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getGstRate()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getAmountExclGst()) ||
//						UtilValidate.isEmpty(purchaseMasterDTO.getAmountInclGst())){
//					return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//				}
//
//
//				PurchaseBOMMasterEntity purchaseBOMMasterEntity= new PurchaseBOMMasterEntity ();
//				
//				purchaseBOMMasterEntity.setBomDescription(purchaseMasterDTO.getBomDescription());
//				purchaseBOMMasterEntity.setPurchaseId(purchaseMaster.getId().toString());
//				purchaseBOMMasterEntity.setService(purchaseMasterDTO.getService());
//				purchaseBOMMasterEntity.setTypeOfExpenditure(purchaseMasterDTO.getTypeOfExpenditure());
//				purchaseBOMMasterEntity.setStartDate(purchaseMasterDTO.getStartDate());
//				purchaseBOMMasterEntity.setEndDate(purchaseMasterDTO.getEndDate());
//				purchaseBOMMasterEntity.setRenewable(purchaseMasterDTO.getRenewable());
//				purchaseBOMMasterEntity.setRateUnit(purchaseMasterDTO.getRateUnit());
//				purchaseBOMMasterEntity.setQuantity(purchaseMasterDTO.getQuantity());
//				purchaseBOMMasterEntity.setGstRate(purchaseMasterDTO.getGstRate());
//				purchaseBOMMasterEntity.setAmountExclGst(purchaseMasterDTO.getAmountExclGst());
//				purchaseBOMMasterEntity.setAmountInclGst(purchaseMasterDTO.getAmountInclGst());
//				try {
//										
//					purchaseBOMMasterEntity = purchaseBOMService.save(purchaseBOMMasterEntity);
//					savedEntities.add(purchaseBOMMasterEntity);
//				}catch(Exception ex) {
//					System.out.println(ex.getMessage());
//				}
//			}
//			if(purchaseMaster!=null) {
//				statusMap.put(Parameters.statusMsg, StatusMessageConstants.PURCHASE_CREATED_SUCCESSFULLY);
//                statusMap.put(Parameters.status, Constants.SUCCESS);
//                statusMap.put(Parameters.statusCode, "RU_200");
//				return new ResponseEntity<>(statusMap,HttpStatus.OK);
//			}else {
//				statusMap.put(Parameters.statusMsg, StatusMessageConstants.PURCHASE_NOT_CREATED );
//				statusMap.put(Parameters.status, Constants.FAIL);
//				statusMap.put(Parameters.statusCode, "RU_301");
//				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
//			}
//		} catch(Exception ex) {
//
//			statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//			statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//			statusMap.put(Parameters.status, Constants.FAIL);
//			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
//		}
//	 catch (ExceptionInInitializerError ex) {
//        // if (logger.isErrorLoggingEnabled()) {
//        // logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage());
//        // }
//        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//	}
	
	
	@EncryptResponse
	@GetMapping("/getAllPurchase")
	public ResponseEntity<?> getAllPurchase() {
	    Map<String, Object> statusMap = new HashMap<>();
	    try {
	        // Fetch all purchase records
	        List<PurchaseMasterEntity> purchaseMasterEntityList = purchaseMasterService.findAll();
	        
	        // Add data to the response
	        statusMap.put("PurchaseMasterEntity", purchaseMasterEntityList);
	        statusMap.put("Status", "Success");
	        statusMap.put("Status_Code", "RU_200");

	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	@EncryptResponse
	@GetMapping("/getAllBOMDetails")
	public ResponseEntity<?> getAllBOMPurchaseDetails(@RequestParam String purchaseId){
		Map<String, Object> statusMap = new HashMap<>();
		try {

			List<PurchaseBOMMasterEntity> purchaseBOMMasterEntity=purchaseBOMService.findByPurchaseId(purchaseId);




			statusMap.put("PurchaseBOMMasterEntity",purchaseBOMMasterEntity);
			statusMap.put("Status","Success");
			statusMap.put("Status_Code","RU_200");

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}




	@EncryptResponse
	@PutMapping("/updatePurchase")
	public ResponseEntity<?>updatePurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO ){
		Map<String,Object> statusMap=new HashMap<String,Object>();

		try{
			PurchaseMasterEntity purchaseMaster=purchaseMasterService.findById(purchaseRequestDTO.getId());

			purchaseMaster.setClientName(purchaseRequestDTO.getClientName()!=null?purchaseRequestDTO.getClientName():purchaseMaster.getClientName());
			purchaseMaster.setProjectName(purchaseRequestDTO.getProjectName()!=null?purchaseRequestDTO.getProjectName():purchaseMaster.getProjectName());
			purchaseMaster.setVendor(purchaseRequestDTO.getVendor()!=null?purchaseRequestDTO.getClientName():purchaseMaster.getVendor());
			purchaseMaster.setRequestorName(purchaseRequestDTO.getRequestorName()!=null?purchaseRequestDTO.getRequestorName():purchaseMaster.getRequestorName());
			purchaseMaster.setDescription(purchaseRequestDTO.getDescription()!=null?purchaseRequestDTO.getDescription():purchaseMaster.getDescription());
			purchaseMaster.setPrNo(purchaseRequestDTO.getPrNo()!=null?purchaseRequestDTO.getPrNo():purchaseMaster.getPrNo());
			purchaseMaster.setPrDate(purchaseRequestDTO.getPrDate()!=null?purchaseRequestDTO.getPrDate():purchaseMaster.getPrDate());
			purchaseMaster.setPrAmount(purchaseRequestDTO.getPrAmount()!=null?purchaseRequestDTO.getPrAmount():purchaseMaster.getPrAmount());
			purchaseMaster.setStatus(purchaseRequestDTO.getStatus()!=null?purchaseRequestDTO.getStatus():purchaseMaster.getStatus());
			purchaseMaster.setApproveDate(purchaseRequestDTO.getApproveDate()!=null?purchaseRequestDTO.getApproveDate():purchaseMaster.getApproveDate());
			purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo()!=null?purchaseRequestDTO.getPoNo():purchaseMaster.getPoNo());

			 purchaseMaster.setPrFor(purchaseRequestDTO.getPrFor() != null ? purchaseRequestDTO.getPrFor() : purchaseMaster.getPrFor());
		     purchaseMaster.setRejectionReason(purchaseRequestDTO.getRejectionReason() != null ? purchaseRequestDTO.getRejectionReason() : purchaseMaster.getRejectionReason());
		     purchaseMaster.setPoApproveDate(purchaseRequestDTO.getPoApproveDate() != null ? purchaseRequestDTO.getPoApproveDate() : purchaseMaster.getPoApproveDate());
		     
			purchaseMasterService.update(purchaseMaster);


			statusMap.put("purchaseMasterEntity",purchaseMaster);
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
	@PutMapping("/updatePurchaseBOM")
	public ResponseEntity<?>updatePurchaseBOM(@RequestBody PurchaseRequestDTO purchaseRequestDTO ){

		Map<String,Object> statusMap=new HashMap<String,Object>();
		try {
			List<PurchaseBOMMasterEntity> savedEntities = new ArrayList<>();

			for(PurchaseBOMMasterEntity purchaseBOM:purchaseRequestDTO.getBom()) {

				PurchaseBOMMasterEntity purchaseBOMMaster=purchaseBOMService.findById(purchaseBOM.getId());
				purchaseBOMMaster.setBomDescription(purchaseBOM.getBomDescription()!=null?purchaseBOM.getBomDescription():purchaseBOMMaster.getBomDescription());
				purchaseBOMMaster.setService(purchaseBOM.getService()!=null?purchaseBOM.getService():purchaseBOMMaster.getService());
				purchaseBOMMaster.setStartDate(purchaseBOM.getStartDate()!=null?purchaseBOM.getStartDate():purchaseBOMMaster.getStartDate());
				purchaseBOMMaster.setEndDate(purchaseBOM.getEndDate()!=null?purchaseBOM.getEndDate():purchaseBOMMaster.getEndDate());
				purchaseBOMMaster.setRenewable(purchaseBOM.getRenewable()!=null?purchaseBOM.getRenewable():purchaseBOMMaster.getRenewable());
				purchaseBOMMaster.setRateUnit(purchaseBOM.getRateUnit()!=null?purchaseBOM.getRateUnit():purchaseBOMMaster.getRateUnit());
				purchaseBOMMaster.setQuantity(purchaseBOM.getQuantity()!=null?purchaseBOM.getQuantity():purchaseBOMMaster.getQuantity());
				purchaseBOMMaster.setGstRate(purchaseBOM.getGstRate()!=null?purchaseBOM.getGstRate():purchaseBOMMaster.getGstRate());
				purchaseBOMMaster.setAmountExclGst(purchaseBOM.getAmountExclGst()!=null?purchaseBOM.getAmountExclGst():purchaseBOMMaster.getAmountExclGst());
				purchaseBOMMaster.setAmountInclGst(purchaseBOM.getAmountInclGst()!=null?purchaseBOM.getAmountInclGst():purchaseBOMMaster.getAmountInclGst());

				purchaseBOMMaster= purchaseBOMService.update(purchaseBOMMaster);
				savedEntities.add(purchaseBOMMaster);

			}
			statusMap.put("purchaseBOMMasterEntity",savedEntities);
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
	 @DeleteMapping("/deletePurchase")
		public ResponseEntity<?> deletePurchaseMaster(@RequestParam Long id){
			Map<String, Object> statusMap=new HashMap<String, Object>();

			try {

				PurchaseMasterEntity purchaseMaster = purchaseMasterService.findById(id);
				if(purchaseMaster!=null) {
					purchaseMaster.setActive(0);
					purchaseMasterService.update(purchaseMaster);

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
	
	
	@GetMapping("/getPurchaseByPoNo")
	public ResponseEntity<Map<String, Object>> getPurchaseByPoNo(@RequestParam String poNo) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // Fetch PurchaseMasterEntity based on poNo
	        PurchaseMasterEntity purchaseMaster = purchaseMasterService.findByPoNo(poNo);

	        if (purchaseMaster == null) {
	            // If no purchase found, return 404 Not Found
	            response.put("message", "Purchase with PO number " + poNo + " not found.");
	            response.put("status", HttpStatus.NOT_FOUND); // 404 Not Found
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }

	        // If purchase is found, return 200 OK with purchase details
	        response.put("message", "Purchase fetched successfully.");
	        response.put("data", purchaseMaster);
	        response.put("status", HttpStatus.OK); // 200 OK
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (Exception e) {
	        response.put("message", "Error while fetching purchase: " + e.getMessage());
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
		
	@EncryptResponse
	@GetMapping("/getPoNoByProjectName")
	public ResponseEntity<Map<String, Object>> getPoNoByProjectName(@RequestParam String projectName) {
	    Map<String, Object> response = new HashMap<>();

	    try {
	        // Fetch purchase records based on project name
	        List<PurchaseMasterEntity> purchases = purchaseMasterService.findByProjectName(projectName);

	        if (purchases == null || purchases.isEmpty()) {
	            // If no records found, return 404 Not Found
	            response.put("message", "No purchases found for project name: " + projectName);
	            response.put("status", HttpStatus.NOT_FOUND);
	            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	        }

	        // Extract PO numbers from the purchases
	        List<String> poNumbers = new ArrayList<>();
	        for (PurchaseMasterEntity purchase : purchases) {
	            poNumbers.add(purchase.getPoNo());
	        }

	        // Return PO numbers with 200 OK status
	        response.put("message", "PO numbers fetched successfully.");
	        response.put("poNumbers", poNumbers);
	        response.put("status", HttpStatus.OK);
	        return new ResponseEntity<>(response, HttpStatus.OK);

	    } catch (Exception e) {
	        response.put("message", "Error while fetching PO numbers: " + e.getMessage());
	        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
	        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}