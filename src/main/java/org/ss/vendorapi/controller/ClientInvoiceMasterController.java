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





	//	*****************************************************************************************************************************************************************************

	@PostMapping("/addClientInvoices")
	public ResponseEntity<?> addClientInvoices(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO,HttpServletRequest request){


		ResponseEntity<?> responseEntity=null;
		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);	

		Map<String,Object> statusMap= new HashMap<String,Object>();

		 
		try{

			if(UtilValidate.isEmpty(clientInvoiceDTO.getClientId()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getClientName()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getProjectName()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getDiscom()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDate()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceNo()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDescription()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDueDate()) || 
					UtilValidate.isEmpty(clientInvoiceDTO.getGstPer())||
					UtilValidate.isEmpty(clientInvoiceDTO.getGstAmount())||
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountExcluGst())||
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceAmountIncluGst())||
					UtilValidate.isEmpty(clientInvoiceDTO.getStatus())||
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceBaseValue())||
					UtilValidate.isEmpty(clientInvoiceDTO.getGstBaseValue())||
					UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceInclusiveOfGst())||
					UtilValidate.isEmpty(clientInvoiceDTO.getTdsBaseValue())||
					UtilValidate.isEmpty(clientInvoiceDTO.getCgstOnTds())||
					UtilValidate.isEmpty(clientInvoiceDTO.getSgstOnTds())||
					UtilValidate.isEmpty(clientInvoiceDTO.getTotalTdsDeducted())||
					UtilValidate.isEmpty(clientInvoiceDTO.getBalance())||
					UtilValidate.isEmpty(clientInvoiceDTO.getPenalty())||
					UtilValidate.isEmpty(clientInvoiceDTO.getPenaltyDeductionOnBase())||
					UtilValidate.isEmpty(clientInvoiceDTO.getGstOnPenalty())||
					UtilValidate.isEmpty(clientInvoiceDTO.getTotalPenaltyDeduction())||
					UtilValidate.isEmpty(clientInvoiceDTO.getTotalPenaltyReceived())||
					UtilValidate.isEmpty(clientInvoiceDTO.getTdsDeductionIncluGst())
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

			ClientInvoiceMasterEntity clientInvoice=new ClientInvoiceMasterEntity();
			
			clientInvoice.setClientId(clientInvoiceDTO.getClientId());
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
			clientInvoice.setInvoiceBaseValue(clientInvoiceDTO.getInvoiceBaseValue());
			clientInvoice.setGstBaseValue(clientInvoiceDTO.getGstBaseValue());
			clientInvoice.setInvoiceInclusiveOfGst(clientInvoiceDTO.getInvoiceInclusiveOfGst());
			clientInvoice.setTdsBaseValue(clientInvoiceDTO.getTdsBaseValue());
			clientInvoice.setCgstOnTds(clientInvoiceDTO.getCgstOnTds());
			clientInvoice.setSgstOnTds(clientInvoiceDTO.getSgstOnTds());
			clientInvoice.setTotalTdsDeducted(clientInvoiceDTO.getTotalTdsDeducted());
			clientInvoice.setBalance(clientInvoiceDTO.getBalance());
			clientInvoice.setPenalty(clientInvoiceDTO.getPenalty());
			clientInvoice.setPenaltyDctionOnBase(clientInvoiceDTO.getPenaltyDeductionOnBase());
			clientInvoice.setGstOnPenalty(clientInvoiceDTO.getGstOnPenalty());
			clientInvoice.setTotalPenaltyDeduction(clientInvoiceDTO.getTotalPenaltyDeduction());
			clientInvoice.setTotalPenaltyReceived(clientInvoiceDTO.getTotalPenaltyReceived());
			clientInvoice.setTdsDeductionIncluGst(clientInvoiceDTO.getTdsDeductionIncluGst());

			//			********************************************************************************************************************************

			// The following lines are commented out as the fields are commented out in the entity
			// clientInvoiceCreationEntityObj.setInvoiceUpload(ClientInvoiceMEntity.getInvoiceUpload());
			// clientInvoiceCreationEntityObj.setPo(ClientInvoiceMEntity.getPo());
			// clientInvoiceCreationEntityObj.setDeliveryAcceptance(ClientInvoiceMEntity.getDeliveryAcceptance());
			// clientInvoiceCreationEntityObj.setEWayBill(ClientInvoiceMEntity.getEWayBill());
			// clientInvoiceCreationEntityObj.setMiscellaneous(ClientInvoiceMEntity.getMiscellaneous());

			//			**********************************************************************************************************************************

			try{
				/* SAVE THE USER TO THE DB ENTITY */
				clientInvoice=clientInvoiceService.save(clientInvoice);

				if(clientInvoice!=null) {
					statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_INVOICE_GENERATED_SUCCESSFULLY);
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "RU_200");
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}else {
					statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_INVOICE_NOT_GENERATED);
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
		}
		catch(Exception ex)
		{
//			if (logger.isErrorLoggingEnabled()) {
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage() );
//			}
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	//	*************************************************************************************************************************************************************************************
	//	*********************************************************************************get api****************************************************************************************************


	@GetMapping("/getAllClientInvoice")
	 public ResponseEntity<?> getAllClientInvoices() {
		    try {
		        List<ClientInvoiceMasterEntity> clientInvoiceList = clientInvoiceService.findAll();
		        return new ResponseEntity<>(clientInvoiceList, HttpStatus.OK);
		    } catch (Exception ex) {
		        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}
	
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
}
