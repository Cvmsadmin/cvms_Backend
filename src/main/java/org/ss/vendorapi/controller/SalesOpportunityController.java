package org.ss.vendorapi.controller;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import org.ss.vendorapi.entity.SalesOpportunityMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.SalesOpportunityDTO;
import org.ss.vendorapi.service.EmailService;
import org.ss.vendorapi.service.SalesOpportunityService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;



@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class SalesOpportunityController {
	private static final Class<?> CLASS_NAME = UserMasterController.class;
	private static final DateTimeFormatter DATE_FORMATTER = null;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION, CLASS_NAME.toString());

	@Autowired 
	private Environment env;
	
//	@Autowired
//	private CommonUtils commonUtils;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private SalesOpportunityService salesOpportunityService;
	
//	@EncryptResponse
//	@PostMapping("/SalesCreated")
//	public ResponseEntity<?> createSales(@RequestBody SalesOpportunityDTO salesDTO, HttpServletRequest request) {
//	    String methodName = request.getRequestURI();
//	    Map<String, Object> statusMap = new HashMap<>();
//	    
//	    try {
//	        // Validate mandatory fields (you've already done this part)
//	        if (UtilValidate.isEmpty(salesDTO.getNameOfCustomer()) ||
//	            UtilValidate.isEmpty(salesDTO.getGeography()) ||
//	            UtilValidate.isEmpty(salesDTO.getRfpNumber()) ||
//	            UtilValidate.isEmpty(salesDTO.getEprocId()) ||
//	            UtilValidate.isEmpty(salesDTO.getRfpTitle()) ||
//	            UtilValidate.isEmpty(salesDTO.getModeOfSelection()) ||
//	            UtilValidate.isEmpty(salesDTO.getModeOfSubmission()) ||
//	            UtilValidate.isEmpty(salesDTO.getProjectDuration()) ||
//	            UtilValidate.isEmpty(salesDTO.getEstimatedProjectValue()) ||
//	            UtilValidate.isEmpty(salesDTO.getExpectedOEMs()) ||
//	            UtilValidate.isEmpty(salesDTO.getJvConsortiumSubContractors()) ||
//	            UtilValidate.isEmpty(salesDTO.getExpectedCompetitors()) ||
//	            UtilValidate.isEmpty(salesDTO.getConsultant()) ||
//	            UtilValidate.isEmpty(salesDTO.getSalesSPOC()) ||
//	            UtilValidate.isEmpty(salesDTO.getRemarksStatus()) ||
//	            salesDTO.getRfpPublished() == null ||
//	            salesDTO.getPreBidQueries() == null ||
//	            salesDTO.getPreBidMeeting() == null ||
//	            salesDTO.getSubmissionEndDate() == null ||
//	            salesDTO.getSubmissionOfBGHardCopy() == null ||
//	            salesDTO.getOpeningDate() == null ||
//	            salesDTO.getDateOfPresentation() == null ||
//	            UtilValidate.isEmpty(salesDTO.getEmdAmount()) ||
//	            UtilValidate.isEmpty(salesDTO.getModeOfEMD()) ||
//	            UtilValidate.isEmpty(salesDTO.getTenderDocFee()) ||
//	            UtilValidate.isEmpty(salesDTO.getTenderProcessingFee()) ||
//	            UtilValidate.isEmpty(salesDTO.getPortalCharges()) ||
//	            UtilValidate.isEmpty(salesDTO.getApprovalForBidParticipation())) {
//
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        // Map fields to entity (salesOpportunityMaster logic is the same)
//	        SalesOpportunityMasterEntity salesOpportunityMaster = new SalesOpportunityMasterEntity();
//	        salesOpportunityMaster.setNameOfCustomer(salesDTO.getNameOfCustomer());
//	        salesOpportunityMaster.setGeography(salesDTO.getGeography());
//	        salesOpportunityMaster.setRfpNumber(salesDTO.getRfpNumber());
//	        salesOpportunityMaster.setEProcId(salesDTO.getEprocId());
//	        salesOpportunityMaster.setRfpTitle(salesDTO.getRfpTitle());
//	        salesOpportunityMaster.setModeOfSelection(salesDTO.getModeOfSelection());
//	        salesOpportunityMaster.setModeOfSubmission(salesDTO.getModeOfSubmission());
//	        salesOpportunityMaster.setProjectDuration(salesDTO.getProjectDuration());
//	        salesOpportunityMaster.setEstimatedProjectValue(salesDTO.getEstimatedProjectValue());
//	        salesOpportunityMaster.setExpectedOEMs(salesDTO.getExpectedOEMs());
//	        salesOpportunityMaster.setJvConsortiumSubContractors(salesDTO.getJvConsortiumSubContractors());
//	        salesOpportunityMaster.setExpectedCompetitors(salesDTO.getExpectedCompetitors());
//	        salesOpportunityMaster.setConsultant(salesDTO.getConsultant());
//	        salesOpportunityMaster.setSalesSPOC(salesDTO.getSalesSPOC());
//	        salesOpportunityMaster.setRemarksStatus(salesDTO.getRemarksStatus());
//	        salesOpportunityMaster.setRfpPublished(salesDTO.getRfpPublished());
//	        salesOpportunityMaster.setPreBidQueries(salesDTO.getPreBidQueries());
//	        salesOpportunityMaster.setPreBidMeeting(salesDTO.getPreBidMeeting());
//	        salesOpportunityMaster.setSubmissionEndDate(salesDTO.getSubmissionEndDate());
//	        salesOpportunityMaster.setSubmissionOfBGHardCopy(salesDTO.getSubmissionOfBGHardCopy());
//	        salesOpportunityMaster.setOpeningDate(salesDTO.getOpeningDate());
//	        salesOpportunityMaster.setDateOfPresentation(salesDTO.getDateOfPresentation());
//	        salesOpportunityMaster.setEmdAmount(salesDTO.getEmdAmount());
//	        salesOpportunityMaster.setModeOfEMD(salesDTO.getModeOfEMD());
//	        salesOpportunityMaster.setTenderDocFee(salesDTO.getTenderDocFee());
//	        salesOpportunityMaster.setTenderProcessingFee(salesDTO.getTenderProcessingFee());
//	        salesOpportunityMaster.setPortalCharges(salesDTO.getPortalCharges());
//	        salesOpportunityMaster.setApprovalForBidParticipation(salesDTO.getApprovalForBidParticipation());
//
//	        // Dynamic currentStatus handling (same as before)
//	        if ("Approved".equalsIgnoreCase(salesDTO.getApprovalForBidParticipation())) {
//	            salesOpportunityMaster.setCurrentStatus(salesDTO.getCurrentStatus());
//	        } else {
//	            salesOpportunityMaster.setCurrentStatus("");
//	        }
//
//	        // Save the entity
//	        salesOpportunityMaster = salesOpportunityService.save(salesOpportunityMaster);
//	        
//	        EmailNotificationView notification;
//			// Handle pending payment for milestone
//	        String pendingPaymentStr = notification.getPending_payment_for_milestone();
//	        BigDecimal pendingPayment = null;
//	        try {
//	            pendingPayment = new BigDecimal(pendingPaymentStr);
//	        } catch (NumberFormatException e) {
//	            // Handle the error, set to default or log
//	            pendingPayment = BigDecimal.ZERO;
//	        }
//
//	        // Format the pending payment
//	        String formattedAmount = formatCurrency(pendingPayment);
//
//	        // Generate the overdue email body
//	        String formattedDueDate = "your formatted due date here"; // You'll need to format the due date properly
//	        String emailBody = generateEmailBodyForOverdue(notification, formattedDueDate);
//
//	        // Send the email
//	        emailService.sendEmail(notification.getRecipientEmail(), "Overdue Payment Notification", emailBody);
//
//	        // Prepare response
//	        if (salesOpportunityMaster != null) {
//	            statusMap.put("statusMsg", "Sales Created");
//	            statusMap.put("status", Constants.SUCCESS);
//	            statusMap.put("statusCode", "RU_200");
//	            statusMap.put("currentStatus", salesOpportunityMaster.getCurrentStatus());
//	            return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	        } else {
//	            statusMap.put(Parameters.statusMsg, "Sales Not Created");
//	            statusMap.put(Parameters.status, Constants.FAIL);
//	            statusMap.put(Parameters.statusCode, "RU_301");
//	            return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//	        }
//	    } catch (Exception ex) {
//	        // Log the exception
////	        logger.error("Error occurred while creating sales opportunity", ex);
//	        
//	        statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//	        statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//	        statusMap.put(Parameters.status, Constants.FAIL);
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//
//	// Helper method to format currency
//	private String formatCurrency(BigDecimal value) {
//	    if (value == null) {
//	        return "0.00"; // return default value if null
//	    }
//	    return NumberFormat.getCurrencyInstance().format(value);
//	}
//
//	// Method to generate the email body for overdue payments
//	@SuppressWarnings("unused")
//	private String generateEmailBodyForOverdue(EmailNotificationView notification, String formattedDueDate) {
//	    BigDecimal pendingPayment = null;
//	    try {
//	        pendingPayment = new BigDecimal(notification.getPending_payment_for_milestone());
//	    } catch (NumberFormatException e) {
//	        pendingPayment = BigDecimal.ZERO;
//	    }
//	    String formattedAmount = formatCurrency(pendingPayment);
//
//	    return String.format(
//	        "<html><body>" +
//	        "<p>Hi %s,</p>" +
//	        "<p>Please note that the following milestone payment is now <b>overdue</b> from <b>%s</b>:</p>" +
//	        "<ul>" +
//	        "<li>Project: <b>%s</b></li>" +
//	        "<li>Milestone: <b>%s</b></li>" +
//	        "<li>Amount (Incl. GST): <b>%s</b></li>" +
//	        "<li>Milestone Duration: <b>%s</b></li>" +
//	        "<li>Start Date: <b>%s</b></li>" +
//	        "<li>Due Date: <b>%s</b></li>" +
//	        "<li>Status: <b>%s</b></li>" +
//	        "</ul>" +
//	        "<p>Kindly proceed with the necessary steps to ensure payment is received as per the agreed terms.</p>" +
//	        "<p>Thanks,<br><b>CVMS Admin</b></p>" +
//	        "</body></html>",
//	        notification.getAccount_manager(),
//	        notification.getClient_name(),
//	        notification.getProject_name(),
//	        notification.getMilestone(),
//	        formattedAmount,
//	        notification.getMilestone_duration(),
//	        LocalDate.parse(notification.getStart_date(), DateTimeFormatter.ofPattern("dd-MM-yy")).format(DATE_FORMATTER),
//	        formattedDueDate,
//	        notification.getStatus()
//	    );
//	}


	@EncryptResponse
	@PostMapping("/SalesCreated")
	public ResponseEntity<?> createSales(@RequestBody SalesOpportunityDTO salesDTO, HttpServletRequest request) {
	    String methodName = request.getRequestURI();
	    Map<String, Object> statusMap = new HashMap<>();
	    
	    try {
	        // Validate mandatory fields
	    	if (UtilValidate.isEmpty(salesDTO.getNameOfCustomer()) ||
	    		    UtilValidate.isEmpty(salesDTO.getGeography()) ||
	    		    UtilValidate.isEmpty(salesDTO.getRfpNumber()) ||
	    		    UtilValidate.isEmpty(salesDTO.getEprocId()) ||
	    		    UtilValidate.isEmpty(salesDTO.getRfpTitle()) ||
	    		    UtilValidate.isEmpty(salesDTO.getModeOfSelection()) ||
	    		    UtilValidate.isEmpty(salesDTO.getModeOfSubmission()) ||
	    		    UtilValidate.isEmpty(salesDTO.getProjectDuration()) ||
	    		    UtilValidate.isEmpty(salesDTO.getEstimatedProjectValue()) ||
	    		    UtilValidate.isEmpty(salesDTO.getExpectedOEMs()) ||
	    		    UtilValidate.isEmpty(salesDTO.getJvConsortiumSubContractors()) ||
	    		    UtilValidate.isEmpty(salesDTO.getExpectedCompetitors()) ||
	    		    UtilValidate.isEmpty(salesDTO.getConsultant()) ||
	    		    UtilValidate.isEmpty(salesDTO.getSalesSPOC()) ||
	    		    UtilValidate.isEmpty(salesDTO.getRemarksStatus()) ||
	    		    salesDTO.getRfpPublished() == null ||
	    		    salesDTO.getPreBidQueries() == null ||
	    		    salesDTO.getPreBidMeeting() == null ||
	    		    salesDTO.getSubmissionEndDate() == null ||
	    		    salesDTO.getSubmissionOfBGHardCopy() == null ||
	    		    salesDTO.getOpeningDate() == null ||
	    		    salesDTO.getDateOfPresentation() == null ||
	    		    UtilValidate.isEmpty(salesDTO.getEmdAmount()) ||
	    		    UtilValidate.isEmpty(salesDTO.getModeOfEMD()) ||
	    		    UtilValidate.isEmpty(salesDTO.getTenderDocFee()) ||
	    		    UtilValidate.isEmpty(salesDTO.getTenderProcessingFee()) ||
	    		    UtilValidate.isEmpty(salesDTO.getPortalCharges()) ||
	    		    UtilValidate.isEmpty(salesDTO.getApprovalForBidParticipation())) {
	    		    return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	    		}

	        
	        // Format currency
	    	String formattedEstimatedProjectValue = formatAmount(salesDTO.getEstimatedProjectValue());
	        //String formattedProjectValue = formatAmount(salesDTO.getProjectValue());

	        // Map fields to entity
	        SalesOpportunityMasterEntity salesOpportunityMaster = new SalesOpportunityMasterEntity();
	        salesOpportunityMaster.setNameOfCustomer(salesDTO.getNameOfCustomer());
	        salesOpportunityMaster.setGeography(salesDTO.getGeography());
	        salesOpportunityMaster.setRfpNumber(salesDTO.getRfpNumber());
	        salesOpportunityMaster.setEProcId(salesDTO.getEprocId());
	        salesOpportunityMaster.setRfpTitle(salesDTO.getRfpTitle());
	        salesOpportunityMaster.setModeOfSelection(salesDTO.getModeOfSelection());
	        salesOpportunityMaster.setModeOfSubmission(salesDTO.getModeOfSubmission());
	        salesOpportunityMaster.setProjectDuration(salesDTO.getProjectDuration());
	        salesOpportunityMaster.setEstimatedProjectValue(salesDTO.getEstimatedProjectValue());
	        salesOpportunityMaster.setExpectedOEMs(salesDTO.getExpectedOEMs());
	        salesOpportunityMaster.setJvConsortiumSubContractors(salesDTO.getJvConsortiumSubContractors());
	        salesOpportunityMaster.setExpectedCompetitors(salesDTO.getExpectedCompetitors());
	        salesOpportunityMaster.setConsultant(salesDTO.getConsultant());
	        salesOpportunityMaster.setSalesSPOC(salesDTO.getSalesSPOC());
	        salesOpportunityMaster.setRemarksStatus(salesDTO.getRemarksStatus());
	        salesOpportunityMaster.setRfpPublished(salesDTO.getRfpPublished());
	        salesOpportunityMaster.setPreBidQueries(salesDTO.getPreBidQueries());
	        salesOpportunityMaster.setPreBidMeeting(salesDTO.getPreBidMeeting());
	        salesOpportunityMaster.setSubmissionEndDate(salesDTO.getSubmissionEndDate());
	        salesOpportunityMaster.setSubmissionOfBGHardCopy(salesDTO.getSubmissionOfBGHardCopy());
	        salesOpportunityMaster.setOpeningDate(salesDTO.getOpeningDate());
	        salesOpportunityMaster.setDateOfPresentation(salesDTO.getDateOfPresentation());
	        salesOpportunityMaster.setEmdAmount(salesDTO.getEmdAmount());
	        salesOpportunityMaster.setModeOfEMD(salesDTO.getModeOfEMD());
	        salesOpportunityMaster.setTenderDocFee(salesDTO.getTenderDocFee());
	        salesOpportunityMaster.setTenderProcessingFee(salesDTO.getTenderProcessingFee());
	        salesOpportunityMaster.setPortalCharges(salesDTO.getPortalCharges());
	        salesOpportunityMaster.setApprovalForBidParticipation(salesDTO.getApprovalForBidParticipation());

	        // Dynamic currentStatus handling
	        if ("Approved".equalsIgnoreCase(salesDTO.getApprovalForBidParticipation())) {
	            // Use the value provided in the DTO for Approved cases
	            salesOpportunityMaster.setCurrentStatus(salesDTO.getCurrentStatus());
	        } else {
	            // For Rejected or Pending, set to an empty string
	            salesOpportunityMaster.setCurrentStatus("");
	        }

	        // Save the entity
	        salesOpportunityMaster = salesOpportunityService.save(salesOpportunityMaster);
	        
//	        @SuppressWarnings("unused")
//			String formattedAmount = CommonUtils.formatAmountInIndianStyle(salesDTO.getEstimatedProjectValue());
	        
	        // Send email notification
	        String emailBody = String.format(
	        		 "<html><body>" +
	        				    "<p>Dear Sir,</p>" +
	        				    "<p>We have identified a new opportunity and are actively working towards it. Please find the key details below:</p>" +
	        				    "<table border='1' cellpadding='5' cellspacing='0'>" +
	        				    "<tr><th>Field</th><th>Details</th></tr>" +
	        				    "<tr><td>Prospect Client</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>RFP No</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Mode of Selection</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Geography</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>e-Proc ID</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>RFP Title</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Mode of Submission</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Project Duration</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Estimated Project Value</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Expected OEMs</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>JV/Consortium/Sub-Contractors</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Expected Competitors</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Consultant (If any)</td><td><b>%s</b></td></tr>" +
	        				    "<tr><td>Sales SPOC</td><td><b>%s</b></td></tr>" +
	        				    "</table>" +
	        				    "<p>We kindly seek your approval to proceed with this opportunity.</p>" +
	        				    "<p>Best regards,<br><b>CVMS Admin</b></p>" +
	        				    "</body></html>",
	                salesDTO.getNameOfCustomer(),
	                salesDTO.getRfpNumber(),
	                salesDTO.getModeOfSelection(),
	                salesDTO.getGeography(),
	                salesDTO.getEprocId(),
	                salesDTO.getRfpTitle(),
	                salesDTO.getModeOfSubmission(),
	                salesDTO.getProjectDuration(),
	                formattedEstimatedProjectValue,
	                salesDTO.getExpectedOEMs(),
	                salesDTO.getJvConsortiumSubContractors(),
	                salesDTO.getExpectedCompetitors(),
	                salesDTO.getConsultant(),
	                salesDTO.getSalesSPOC()
	            );

	            emailService.sendEmail("debidatta.das@infinite.com", "New Sales Opportunity Identified", emailBody);


	        // Prepare response
	        if (salesOpportunityMaster != null) {
	            statusMap.put("statusMsg", "Sales Created");
	            statusMap.put("status", Constants.SUCCESS);
	            statusMap.put("statusCode", "RU_200");
	            statusMap.put("currentStatus", salesOpportunityMaster.getCurrentStatus());
	            return new ResponseEntity<>(statusMap, HttpStatus.OK);
	        } else {
	            statusMap.put(Parameters.statusMsg, "Sales Not Created");
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_301");
	            return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
	        }
	    } catch (Exception ex) {
	        statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
	        statusMap.put(Parameters.statusCode, Constants.SVD_USR);
	        statusMap.put(Parameters.status, Constants.FAIL);
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}	
	
	private String formatAmount(String amount) {
	    try {
	        BigDecimal value = new BigDecimal(amount);
	        DecimalFormat formatter = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
	        formatter.applyPattern("#,##0.00");
	        return formatter.format(value);
	    } catch (NumberFormatException e) {
	        return "0.00"; // Fallback for invalid numbers
	    }
	}




	@EncryptResponse
	@GetMapping("/getAllSales")
	public ResponseEntity<?>getAllSales(){
		Map<String,Object> statusMap=new HashMap<>();
			try {
				List<SalesOpportunityMasterEntity> saleOptList=salesOpportunityService.findAll();
				statusMap.put("SalesOpportunityMasterEntity",saleOptList);
				statusMap.put("Status","Success");
				statusMap.put("Status_Code","RU_200");
				statusMap.put("StatusMessage","SuccessFully Found");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
			}
		catch(Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@EncryptResponse
	@PutMapping("/updateSales")
	public ResponseEntity<?>updateSales(@RequestBody SalesOpportunityDTO salesDTO ){
		Map<String,Object> statusMap=new HashMap<>();
		try {
			SalesOpportunityMasterEntity salesOpportunityMaster= salesOpportunityService.findById(salesDTO.getId());
			
			salesOpportunityMaster.setSrNumber(salesDTO.getSrNumber()!=null?salesDTO.getSrNumber():salesOpportunityMaster.getSrNumber());
			salesOpportunityMaster.setNameOfCustomer(salesDTO.getNameOfCustomer()!=null?salesDTO.getNameOfCustomer():salesOpportunityMaster.getNameOfCustomer());
			salesOpportunityMaster.setGeography(salesDTO.getGeography()!=null?salesDTO.getGeography():salesOpportunityMaster.getGeography());
			salesOpportunityMaster.setRfpNumber(salesDTO.getRfpNumber()!=null?salesDTO.getRfpNumber():salesOpportunityMaster.getRfpNumber());
			salesOpportunityMaster.setEProcId(salesDTO.getEprocId()!=null?salesDTO.getEprocId():salesOpportunityMaster.getEProcId());
			salesOpportunityMaster.setRfpTitle(salesDTO.getRfpTitle()!=null?salesDTO.getRfpTitle():salesOpportunityMaster.getRfpTitle());
			salesOpportunityMaster.setModeOfSelection(salesDTO.getModeOfSelection()!=null?salesDTO.getModeOfSelection():salesOpportunityMaster.getModeOfSelection());
			salesOpportunityMaster.setModeOfSubmission(salesDTO.getModeOfSubmission()!=null?salesDTO.getModeOfSubmission():salesOpportunityMaster.getModeOfSubmission());
			salesOpportunityMaster.setProjectDuration(salesDTO.getProjectDuration()!=null?salesDTO.getProjectDuration():salesOpportunityMaster.getProjectDuration());
			salesOpportunityMaster.setEstimatedProjectValue(salesDTO.getEstimatedProjectValue()!=null?salesDTO.getEstimatedProjectValue():salesOpportunityMaster.getEstimatedProjectValue());
			salesOpportunityMaster.setExpectedOEMs(salesDTO.getExpectedOEMs()!=null?salesDTO.getExpectedOEMs():salesOpportunityMaster.getExpectedOEMs());
			salesOpportunityMaster.setJvConsortiumSubContractors(salesDTO.getJvConsortiumSubContractors()!=null?salesDTO.getJvConsortiumSubContractors():salesOpportunityMaster.getJvConsortiumSubContractors());
			salesOpportunityMaster.setExpectedCompetitors(salesDTO.getExpectedCompetitors()!=null?salesDTO.getExpectedCompetitors():salesOpportunityMaster.getExpectedCompetitors());
			salesOpportunityMaster.setConsultant(salesDTO.getConsultant()!=null?salesDTO.getConsultant():salesOpportunityMaster.getConsultant());
			salesOpportunityMaster.setSalesSPOC(salesDTO.getSalesSPOC()!=null?salesDTO.getSalesSPOC():salesOpportunityMaster.getSalesSPOC());
			salesOpportunityMaster.setRemarksStatus(salesDTO.getRemarksStatus()!=null?salesDTO.getRemarksStatus():salesOpportunityMaster.getRemarksStatus());
			salesOpportunityMaster.setRfpPublished(salesDTO.getRfpPublished()!=null?salesDTO.getRfpPublished():salesOpportunityMaster.getRfpPublished());
			salesOpportunityMaster.setPreBidQueries(salesDTO.getPreBidQueries()!=null?salesDTO.getPreBidQueries():salesOpportunityMaster.getPreBidQueries());
			salesOpportunityMaster.setPreBidMeeting(salesDTO.getPreBidMeeting()!=null?salesDTO.getPreBidMeeting():salesOpportunityMaster.getPreBidMeeting());
			salesOpportunityMaster.setSubmissionEndDate(salesDTO.getSubmissionEndDate()!=null?salesDTO.getSubmissionEndDate():salesOpportunityMaster.getSubmissionEndDate());
			salesOpportunityMaster.setOpeningDate(salesDTO.getOpeningDate()!=null?salesDTO.getOpeningDate():salesOpportunityMaster.getOpeningDate());
			salesOpportunityMaster.setDateOfPresentation(salesDTO.getDateOfPresentation()!=null?salesDTO.getDateOfPresentation():salesOpportunityMaster.getDateOfPresentation());
			salesOpportunityMaster.setEmdAmount(salesDTO.getEmdAmount()!=null?salesDTO.getEmdAmount():salesOpportunityMaster.getEmdAmount());
			salesOpportunityMaster.setModeOfEMD(salesDTO.getModeOfEMD()!=null?salesDTO.getModeOfEMD():salesOpportunityMaster.getModeOfEMD());
			salesOpportunityMaster.setTenderDocFee(salesDTO.getTenderDocFee()!=null?salesDTO.getTenderDocFee():salesOpportunityMaster.getTenderDocFee());
			salesOpportunityMaster.setTenderProcessingFee(salesDTO.getTenderProcessingFee()!=null?salesDTO.getTenderProcessingFee():salesOpportunityMaster.getTenderProcessingFee());
			salesOpportunityMaster.setPortalCharges(salesDTO.getPortalCharges()!=null?salesDTO.getPortalCharges():salesOpportunityMaster.getPortalCharges());

			salesOpportunityMaster.setApprovalForBidParticipation(salesDTO.getApprovalForBidParticipation()!=null?salesDTO.getApprovalForBidParticipation():salesOpportunityMaster.getApprovalForBidParticipation());
			salesOpportunityMaster.setCurrentStatus(salesDTO.getCurrentStatus()!=null?salesDTO.getCurrentStatus():salesOpportunityMaster.getCurrentStatus());

			salesOpportunityService.update(salesOpportunityMaster);


			statusMap.put("purchaseMasterEntity",salesOpportunityMaster);
			statusMap.put("status", "SUCCESS");
			statusMap.put("statusCode", "RU_200");
			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 

			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	
	
	@EncryptResponse
	@DeleteMapping("/deleteSalesOpportunity")
	public ResponseEntity<?> deleteSalesOpportunityMaster(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			SalesOpportunityMasterEntity salesOpportunityMaster = salesOpportunityService.findById(id);
			if(salesOpportunityMaster!=null) {
				salesOpportunityMaster.setActive(0);
				salesOpportunityService.update(salesOpportunityMaster);

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