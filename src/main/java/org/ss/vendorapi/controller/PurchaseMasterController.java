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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;
import org.ss.vendorapi.entity.PurchaseMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.PurchaseBOMMasterDTO;
import org.ss.vendorapi.modal.PurchaseRequestDTO;
import org.ss.vendorapi.service.PurchaseBOMService;
import org.ss.vendorapi.service.PurchaseMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
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

	@PostMapping("/addPurchase")
	public ResponseEntity<?> addPurchase(@RequestBody PurchaseRequestDTO purchaseRequestDTO, HttpServletRequest request) {
		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);

		Map<String, Object> statusMap = new HashMap<>();
		List<PurchaseBOMMasterEntity> savedEntities = new ArrayList<>();

		try {


			if (UtilValidate.isEmpty(UtilValidate.isEmpty(purchaseRequestDTO.getClientName()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getProjectName()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getVendor()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getRequestorName()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getDescription()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getPrNo()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getPrAmount()) ||
					UtilValidate.isEmpty(purchaseRequestDTO.getStatus()) ||
					purchaseRequestDTO.getPrDate()==null||
					purchaseRequestDTO.getApproveDate()==null||
					UtilValidate.isEmpty(purchaseRequestDTO.getPoNo())))  {

				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
			}

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
			purchaseMaster.setApproveDate(purchaseRequestDTO.getApproveDate());
			purchaseMaster.setPoNo(purchaseRequestDTO.getPoNo());

			purchaseMaster=purchaseMasterService.save(purchaseMaster);
			for (PurchaseBOMMasterEntity purchaseMasterDTO : purchaseRequestDTO.getBom()) {

				if (UtilValidate.isEmpty(purchaseMasterDTO.getBomDescription()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getService()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getTypeOfExpenditure()) ||
						purchaseMasterDTO.getStartDate() == null ||
						purchaseMasterDTO.getEndDate() == null ||
						UtilValidate.isEmpty(purchaseMasterDTO.getRenewable()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getRateUnit()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getGstRate()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getQuantity()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getGstRate()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getAmountExclGst()) ||
						UtilValidate.isEmpty(purchaseMasterDTO.getAmountInclGst())){
					return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
				}


				PurchaseBOMMasterEntity purchaseBOMMasterEntity= new PurchaseBOMMasterEntity ();
				
				purchaseBOMMasterEntity.setBomDescription(purchaseMasterDTO.getBomDescription());
				purchaseBOMMasterEntity.setPurchaseId(purchaseMaster.getId().toString());
				purchaseBOMMasterEntity.setService(purchaseMasterDTO.getService());
				purchaseBOMMasterEntity.setTypeOfExpenditure(purchaseMasterDTO.getTypeOfExpenditure());
				purchaseBOMMasterEntity.setStartDate(purchaseMasterDTO.getStartDate());
				purchaseBOMMasterEntity.setEndDate(purchaseMasterDTO.getEndDate());
				purchaseBOMMasterEntity.setRenewable(purchaseMasterDTO.getRenewable());
				purchaseBOMMasterEntity.setRateUnit(purchaseMasterDTO.getRateUnit());
				purchaseBOMMasterEntity.setQuantity(purchaseMasterDTO.getQuantity());
				purchaseBOMMasterEntity.setGstRate(purchaseMasterDTO.getGstRate());
				purchaseBOMMasterEntity.setAmountExclGst(purchaseMasterDTO.getAmountExclGst());
				purchaseBOMMasterEntity.setAmountInclGst(purchaseMasterDTO.getAmountInclGst());
				try {
										
					purchaseBOMMasterEntity = purchaseBOMService.save(purchaseBOMMasterEntity);
					savedEntities.add(purchaseBOMMasterEntity);
				}catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
			if(purchaseMaster!=null) {
				statusMap.put("statusMsg", "Purchase Created" );
				statusMap.put("status", Constants.SUCCESS);
				statusMap.put("statusCode", "RU_200");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
			}else {
				statusMap.put(Parameters.statusMsg,"purchase Not Created" );
				statusMap.put(Parameters.status, Constants.FAIL);
				statusMap.put(Parameters.statusCode, "RU_301");
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
		}catch(Exception ex) {

			statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
			statusMap.put(Parameters.statusCode, Constants.SVD_USR);
			statusMap.put(Parameters.status, Constants.FAIL);
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
		}
	}





	//	    ***********************************************************************************************************************************************************************
	//	    ******************************************************************************get api *********************************************************************************


	//	@GetMapping("/getPurchase")
	//	public ResponseEntity<?> getAllPurchase(@RequestBody PurchaseMasterDTO purchaseMasterDTO) {
	//		Map<String, Object> statusMap = new HashMap<>();
	//		try {
	//
	//			if(purchaseMasterDTO !=null && purchaseMasterDTO.getId()!=null ) {
	//				PurchaseMasterEntity purchaseMasterEntity=purchaseMasterService.findById(purchaseMasterDTO.getId());
	//
	//
	//				if(purchaseMasterEntity!=null) {
	//					return new ResponseEntity<>(purchaseMasterEntity, HttpStatus.OK);
	//
	//				}else {
	//
	//					statusMap.put("Status", "FAIL");
	//					statusMap.put("StatusCode", "RU_301");
	//					statusMap.put("StatusMessage", "NOT FOUND");
	//					return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
	//				}
	//			}else {
	//				List<PurchaseMasterEntity> purchaseMasterEntityList = purchaseMasterService.findAll();
	//				return new ResponseEntity<>(purchaseMasterEntityList, HttpStatus.OK);
	//			}
	//
	//		} catch (Exception ex) {
	//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	//		}
	//
	//	}
	@GetMapping("/getAllPurchase")
	public ResponseEntity<?> getAllPurchase() {
		Map<String, Object> statusMap = new HashMap<>();
		try {
			List<PurchaseMasterEntity> purchaseMasterEntityList = purchaseMasterService.findAll();
			statusMap.put("PurchaseMasterEntity",purchaseMasterEntityList);
			statusMap.put("Status","Success");
			statusMap.put("Status_Code","RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getAllBOMDetails")
	public ResponseEntity<?> getAllBOMPurchaseDetails(@RequestBody PurchaseBOMMasterDTO purchaseMasterDTO){
		Map<String, Object> statusMap = new HashMap<>();
		try {

			List<PurchaseBOMMasterEntity> purchaseBOMMasterEntity=purchaseBOMService.findByPurchaseId(purchaseMasterDTO.getPurchaseId());


			statusMap.put("PurchaseBOMMasterEntity",purchaseBOMMasterEntity);
			statusMap.put("Status","Success");
			statusMap.put("Status_Code","RU_200");

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}




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

	@PutMapping("/updatePurchaseBOM")
	public ResponseEntity<?>updatePurchaseBOM(@RequestBody PurchaseRequestDTO purchaseRequestDTO ){

		Map<String,Object> statusMap=new HashMap<String,Object>();
		try {
			List<PurchaseBOMMasterEntity> savedEntities = new ArrayList<>();

			for(PurchaseBOMMasterEntity purchaseBOM:purchaseRequestDTO.getBom()) {

				PurchaseBOMMasterEntity purchaseBOMMaster=purchaseBOMService.findById(purchaseBOM.getId());
				purchaseBOMMaster.setBomDescription(purchaseBOM.getBomDescription()!=null?purchaseBOM.getBomDescription():purchaseBOMMaster.getBomDescription());
				purchaseBOMMaster.setService(purchaseBOM.getService()!=null?purchaseBOM.getService():purchaseBOMMaster.getService());
				purchaseBOMMaster.setTypeOfExpenditure(purchaseBOM.getTypeOfExpenditure()!=null?purchaseBOM.getTypeOfExpenditure():purchaseBOMMaster.getTypeOfExpenditure());
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
}