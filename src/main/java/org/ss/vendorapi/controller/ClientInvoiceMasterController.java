package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
//import org.ss.vendorapi.entity.ClientDescriptionAndBaseValue;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;
import org.ss.vendorapi.entity.ClientInvoiceDetailsEntity;
import org.ss.vendorapi.entity.ClientInvoiceMasterEntity;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.FileUploadRequestModal;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.ClientInvoiceMasterDTO;
import org.ss.vendorapi.repository.ClientInvoiceDetailsRepo;
import org.ss.vendorapi.repository.ClientMasterRepository;
import org.ss.vendorapi.repository.DocumentUploadRepository;
import org.ss.vendorapi.repository.ProjectMasterRepository;
import org.ss.vendorapi.service.ClientInvoiceDescriptionValueService;
import org.ss.vendorapi.service.ClientInvoiceMasterService;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.EmailService;
import org.ss.vendorapi.service.SftpUploaderService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
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
	
	@Autowired
	private ClientMasterRepository clientMasterRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private SftpUploaderService sftpUploaderService;
	
	@Autowired
	private ClientInvoiceDetailsRepo clientInvoiceDetailsRepo;
		
//	@Autowired
//	private UserMasterService userMasterService;
//	@Autowired
//	private ClientInvoiceDescriptionValue clientInvoiceDescriptionValue;
	
	@Autowired
	private ClientInvoiceDescriptionValueService clientInvoiceDescriptionValueService;
	
	@Autowired
	private DocumentUploadRepository documentUploadRepository;
	
	@Autowired
	private ProjectMasterRepository projectMasterRepository;
	
	@EncryptResponse 	
	@PostMapping("/addClientInvoices")
	public ResponseEntity<?> addClientInvoices(@RequestBody ClientInvoiceMasterDTO clientInvoiceDTO, HttpServletRequest request) {
	    Map<String, Object> responseMap = new HashMap<>();
	    try {
	        // Check if the invoice already exists
	        if (clientInvoiceDTO.getInvoiceNo() == null || clientInvoiceDTO.getInvoiceNo().trim().isEmpty()) {
	            return CommonUtils.createResponse(Constants.FAIL, "Invoice No cannot be null or empty.", HttpStatus.BAD_REQUEST);
	        }
	        
	        ClientInvoiceMasterEntity existingInvoice = clientInvoiceService.findByInvoiceNo(clientInvoiceDTO.getInvoiceNo());
	        if (existingInvoice != null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Invoice with this Invoice No. already exists.", HttpStatus.CONFLICT);
	        }

	        // Validate mandatory fields if status is not "completed"
	        if (!"completed".equalsIgnoreCase(clientInvoiceDTO.getStatus()) && (
	                UtilValidate.isEmpty(clientInvoiceDTO.getClientName()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getProjectName()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getDiscom()) ||
	                clientInvoiceDTO.getInvoiceDate() == null ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceNo()) ||
	                UtilValidate.isEmpty(clientInvoiceDTO.getInvoiceDescription()) ||
	                clientInvoiceDTO.getInvoiceDueDate() == null ||
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
	        
	        // Ensure correct numeric type for fields
	        if (clientInvoiceDTO.getInvoiceAmountIncluGst() != null) {
	            clientInvoice.setInvoiceAmountIncluGst(clientInvoiceDTO.getInvoiceAmountIncluGst());
	        }
	        if (clientInvoiceDTO.getInvoiceAmtIncluGst() != null) {
	            clientInvoice.setInvoiceAmtIncluGst(Double.parseDouble(clientInvoiceDTO.getInvoiceAmtIncluGst()));
	        }
	        clientInvoice.setStatus(clientInvoiceDTO.getStatus());
	        clientInvoice.setBillableState(clientInvoiceDTO.getBillableState());
	        
	        if (clientInvoiceDTO.getTotalCgst() != null) {
	            clientInvoice.setTotalCgst(clientInvoiceDTO.getTotalCgst());
	        }
	        if (clientInvoiceDTO.getTotalSgst() != null) {
	            clientInvoice.setTotalSgst(clientInvoiceDTO.getTotalSgst());
	        }
	        if (clientInvoiceDTO.getTotalIgst() != null) {
	            clientInvoice.setTotalIgst(clientInvoiceDTO.getTotalIgst());
	        }
	        if (clientInvoiceDTO.getTdsPer() != null) {
	            clientInvoice.setTdsPer(clientInvoiceDTO.getTdsPer());
	        }
	        if (clientInvoiceDTO.getTdsBaseValue() != null) {
	            clientInvoice.setTdsBaseValue(clientInvoiceDTO.getTdsBaseValue());
	        }
	        if (clientInvoiceDTO.getTdsOnGst() != null) {
	            clientInvoice.setTdsOnGst(clientInvoiceDTO.getTdsOnGst());
	        }
	        if (clientInvoiceDTO.getCgstOnTds() != null) {
	            clientInvoice.setCgstOnTds(clientInvoiceDTO.getCgstOnTds());
	        }
	        if (clientInvoiceDTO.getSgstOnTds() != null) {
	            clientInvoice.setSgstOnTds(clientInvoiceDTO.getSgstOnTds());
	        }
	        if (clientInvoiceDTO.getTotalTdsDeducted() != null) {
	            clientInvoice.setTotalTdsDeducted(clientInvoiceDTO.getTotalTdsDeducted());
	        }
	        if (clientInvoiceDTO.getPenalty() != null) {
	            clientInvoice.setPenalty(clientInvoiceDTO.getPenalty());
	        }
	        if (clientInvoiceDTO.getPenaltyDeductionOnBase() != null) {
	            clientInvoice.setPenaltyDeductionOnBase(clientInvoiceDTO.getPenaltyDeductionOnBase());
	        }
	        if (clientInvoiceDTO.getGstOnPenalty() != null) {
	            clientInvoice.setGstOnPenalty(clientInvoiceDTO.getGstOnPenalty());
	        }
	        if (clientInvoiceDTO.getTotalPenaltyDeduction() != null) {
	            clientInvoice.setTotalPenaltyDeduction(clientInvoiceDTO.getTotalPenaltyDeduction());
	        }

	        // Map newly added fields
	        if (clientInvoiceDTO.getBalance() != null) {
	            clientInvoice.setBalance(clientInvoiceDTO.getBalance());
	        }
	        
	        if (clientInvoiceDTO.getPaymentDate() != null) {  
	            clientInvoice.setPaymentDate(clientInvoiceDTO.getPaymentDate());
	        } else {
	            clientInvoice.setPaymentDate(null); // Ensures it's stored as null if not provided
	        }
	        
	        if (clientInvoiceDTO.getGstBaseValue() != null) {
	            clientInvoice.setGstBaseValue(clientInvoiceDTO.getGstBaseValue());
	        }
	        if (clientInvoiceDTO.getInvoiceBaseValue() != null) {
	            clientInvoice.setInvoiceBaseValue(clientInvoiceDTO.getInvoiceBaseValue());
	        }
	        if (clientInvoiceDTO.getInvoiceInclusiveOfGst() != null) {
	            clientInvoice.setInvoiceInclusiveOfGst(clientInvoiceDTO.getInvoiceInclusiveOfGst());
	        }
	        if (clientInvoiceDTO.getTotalPaymentReceived() != null) {
	            clientInvoice.setTotalPaymentReceived(Double.valueOf(clientInvoiceDTO.getTotalPaymentReceived()));
	        }
	        if (clientInvoiceDTO.getCreditNote() != null) {
	            clientInvoice.setCreditNote(String.valueOf(clientInvoiceDTO.getCreditNote()));
	        }
	        if (clientInvoiceDTO.getAmountExcluGst() != null) {
	            clientInvoice.setAmountExcluGst(String.valueOf(clientInvoiceDTO.getAmountExcluGst()));
	        }
	       

//	        clientInvoice.setAmountExcluGst(clientInvoiceDTO.getAmountExcluGst());
//	        clientInvoice.setMilestone(String.valueOf(clientInvoiceDTO.getMilestone()));

	        // Save ClientInvoiceMasterEntity first
	        clientInvoice = clientInvoiceService.save(clientInvoice);

	        // Save descriptions and base values AFTER clientInvoice is saved
	     // Save descriptions and base values AFTER clientInvoice is saved
	        if (clientInvoiceDTO.getClientInvoiceDescriptionValue() != null) {
	            for (ClientInvoiceDescriptionValue description : clientInvoiceDTO.getClientInvoiceDescriptionValue()) {
	                ClientInvoiceDescriptionValue descriptionValue = new ClientInvoiceDescriptionValue();
	                
	                // Set the correct clientInvoice reference
	                descriptionValue.setClientInvoice(clientInvoice); // Ensure the invoice_id is set
	                
	                // Ensure correct numeric type for fields
	                if (description.getItemDescription() != null) {
	                    descriptionValue.setItemDescription(String.valueOf(description.getItemDescription()));
	                }
	                
	                if (description.getBaseValue() != null) {
	                    descriptionValue.setBaseValue(String.valueOf(description.getBaseValue()));
	                }
	                if (description.getGstPer() != null) {
	                    descriptionValue.setGstPer(Double.valueOf(description.getGstPer()));
	                }
	                if (description.getCgst() != null) {
	                    descriptionValue.setCgst(Double.valueOf(description.getCgst()));
	                }
	                if (description.getSgst() != null) {
	                    descriptionValue.setSgst(Double.valueOf(description.getSgst()));
	                }
	                if (description.getIgst() != null) {
	                    descriptionValue.setIgst(Double.valueOf(description.getIgst()));
	                }
	                if (description.getAmtInclGst() != null) {
	                    descriptionValue.setAmtInclGst(Double.valueOf(description.getAmtInclGst()));
	                }
	                
	                // Newly added fields
	                if (description.getMilestone() != null) {
	                    descriptionValue.setMilestone(String.valueOf(description.getMilestone()));
	                }

	                if (description.getSubMilestone() != null) {
	                    descriptionValue.setSubMilestone(description.getSubMilestone());
	                }

	                // Save the description value
	                clientInvoiceDescriptionValueService.save(descriptionValue);
	            }
	        }          
	        // Send email notification
//	        clientInvoiceService.sendInvoiceEmail(clientInvoiceDTO);

	        // Return success response
	        Map<String, String> response = new HashMap<>();
	        response.put("message", "Client Invoice added successfully");
	        return ResponseEntity.ok(response);

	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
//	@EncryptResponse
//	@PostMapping("/sendInvoiceEmailWithAttachment")
//	public ResponseEntity<?> sendInvoiceEmailWithAttachment(@RequestBody Map<String, String> requestMap) {
//	    String invoiceNo = requestMap.get("invoiceNo");
//
//	    if (invoiceNo == null || invoiceNo.trim().isEmpty()) {
//	        return CommonUtils.createResponse(Constants.FAIL, "Invoice No is required", HttpStatus.BAD_REQUEST);
//	    }
//
//	    try {
//	        // Fetch invoice entity
//	        ClientInvoiceMasterEntity invoice = clientInvoiceService.findByInvoiceNo(invoiceNo);
//	        if (invoice == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Invoice not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Convert to DTO
//	        ClientInvoiceMasterDTO invoiceDTO = clientInvoiceService.convertToDto(invoice);
//
//	        // Fetch uploaded file info from FileUploadRequestModal (assuming applicantId = invoiceNo or similar)
//	        FileUploadRequestModal uploadedInvoiceDoc = documentUploadRepository.findByApplicantIdAndDocumentType(
//	            invoiceNo, FileUploadRequestModal.DocumentType.INVOICE_UPLOAD
//	        );
//
//	        if (uploadedInvoiceDoc == null || uploadedInvoiceDoc.getDocumentPath() == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Invoice document not uploaded or missing", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Get the file name from the path
//	        String documentPath = uploadedInvoiceDoc.getDocumentPath();
//	        String fileName = Paths.get(documentPath).getFileName().toString();  // Extract file name from path
//
//	        // Download the file from the server
//	        byte[] fileBytes = sftpUploaderService.downloadFileFromServer(documentPath);
//
//	        if (fileBytes == null || fileBytes.length == 0) {
//	            return CommonUtils.createResponse(Constants.FAIL, "File not found or empty", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Send the invoice email with attachment
//	        clientInvoiceService.sendInvoiceEmailWithAttachment(invoiceDTO, fileName, fileBytes);
//
//	        return ResponseEntity.ok(Collections.singletonMap("message", "Email sent with attachment."));
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return CommonUtils.createResponse(Constants.FAIL, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

//***************************************************************************************************************************************************	
//	@EncryptResponse
//	@PostMapping("/sendInvoiceEmailWithAttachment")
//	public ResponseEntity<?> sendInvoiceEmailWithAttachment(@RequestBody Map<String, String> requestMap) {
//	    String invoiceNo = requestMap.get("invoiceNo");
//
//	    if (invoiceNo == null || invoiceNo.trim().isEmpty()) {
//	        return CommonUtils.createResponse(Constants.FAIL, "Invoice No is required", HttpStatus.BAD_REQUEST);
//	    }
//
//	    try {
//	        // Fetch invoice from DB using invoiceNo
//	        ClientInvoiceMasterEntity invoice = clientInvoiceService.findByInvoiceNo(invoiceNo);
//	        if (invoice == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Invoice not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Convert to DTO
//	        ClientInvoiceMasterDTO invoiceDTO = clientInvoiceService.convertToDto(invoice);
//
//	        // Build dynamic filename
//	        String clientName = invoice.getClientName();
//	        String projectName = invoice.getProjectName();
//	        String fileName = "Invoice_" + invoiceNo + ".pdf";  // You define the pattern here
//
//	        // Build remote path
//	        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;
//
//	        // Download file
//	        byte[] fileBytes = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//
//	        if (fileBytes == null || fileBytes.length == 0) {
//	            return CommonUtils.createResponse(Constants.FAIL, "File not found or empty", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Send email
//	        clientInvoiceService.sendInvoiceEmailWithAttachment(invoiceDTO, fileName, fileBytes);
//
//	        return ResponseEntity.ok(Collections.singletonMap("message", "Email sent with attachment."));
//
//	    } catch (Exception e) {
//	        return CommonUtils.createResponse(Constants.FAIL, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//************************************************************************************************************************************************
	
//	&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&   Real one
//	@EncryptResponse
//	@PostMapping("/sendInvoiceEmailWithAttachment")
//	public ResponseEntity<?> sendInvoiceEmailWithAttachment(@RequestBody Map<String, String> requestMap) {
//	    String invoiceNo = requestMap.get("invoiceNo");
//
//	    if (invoiceNo == null || invoiceNo.trim().isEmpty()) {
//	        return CommonUtils.createResponse(Constants.FAIL, "Invoice No is required", HttpStatus.BAD_REQUEST);
//	    }
//
//	    try {
//	        // Fetch invoice from DB using invoiceNo
//	        ClientInvoiceMasterEntity invoice = clientInvoiceService.findByInvoiceNo(invoiceNo);
//	        if (invoice == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Invoice not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Convert to DTO
//	        ClientInvoiceMasterDTO invoiceDTO = clientInvoiceService.convertToDto(invoice);
//
//	        // Replace '/' with '-' in invoiceNo for filename
//	        String correctedInvoiceNo = invoiceNo.replaceAll("/", "-").trim();
//	        
//	        // Build dynamic filename
//	        String clientName = invoice.getClientName();
//	        String projectName = invoice.getProjectName();
//	        String fileName = "Invoice_" + correctedInvoiceNo + ".pdf";  // Invoice file naming pattern
//
//	        // Build remote path
//	        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;
//	        
//	        System.out.println(remoteFilePath);
//
//	        // Download file
//	        byte[] fileBytes = sftpUploaderService.downloadFileFromServer(remoteFilePath);
//
//	        if (fileBytes == null || fileBytes.length == 0) {
//	            return CommonUtils.createResponse(Constants.FAIL, "File not found or empty", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Send email with attachment
//	        clientInvoiceService.sendInvoiceEmailWithAttachment(invoiceDTO, fileName, fileBytes);
//
//	        return ResponseEntity.ok(Collections.singletonMap("message", "Email sent with attachment."));
//
//	    } catch (Exception e) {
//	        return CommonUtils.createResponse(Constants.FAIL, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
//&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
	
	@EncryptResponse
	@PostMapping("/sendInvoiceEmailWithAttachment")
	public ResponseEntity<?> sendInvoiceEmailWithAttachment(@RequestBody Map<String, String> requestMap) {
	    String invoiceNo = requestMap.get("invoiceNo");

	    if (invoiceNo == null || invoiceNo.trim().isEmpty()) {
	        return CommonUtils.createResponse(Constants.FAIL, "Invoice No is required", HttpStatus.BAD_REQUEST);
	    }

	    try {
	        // Replace '/' with '-' for file name construction
	        String correctedInvoiceNo = invoiceNo.replaceAll("/", "-").trim();

	        // ✅ Fetch invoice details from ClientInvoiceDetailsEntity table
	        List<ClientInvoiceDetailsEntity> invoiceDetailsList = clientInvoiceDetailsRepo.getClientInvoiceDetails();

	        if (invoiceDetailsList.isEmpty()) {
	            return CommonUtils.createResponse(Constants.FAIL, "Invoice details not found", HttpStatus.NOT_FOUND);
	        }

	        // ✅ Optionally filter by invoiceNo (you can add a query method in the repo for this)
	        ClientInvoiceDetailsEntity invoiceDetails = invoiceDetailsList.stream()
	            .filter(i -> correctedInvoiceNo.equals(i.getInvoiceNo().replaceAll("/", "-")))
	            .findFirst()
	            .orElse(null);

	        if (invoiceDetails == null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Invoice not found in invoice details table", HttpStatus.NOT_FOUND);
	        }

	        String clientName = invoiceDetails.getClientName();
	        String projectName = invoiceDetails.getProjectName();
	        String fileName = "Invoice_" + correctedInvoiceNo + ".pdf";

	        String remoteFilePath = "/opt/cvmsdocuments/client/" + clientName + "/" + projectName + "/" + fileName;

	        // ✅ Download file
	        byte[] fileBytes = sftpUploaderService.downloadFileFromServer(remoteFilePath);
	        if (fileBytes == null || fileBytes.length == 0) {
	            return CommonUtils.createResponse(Constants.FAIL, "File not found or empty", HttpStatus.NOT_FOUND);
	        }

	        // ✅ Delegate to service method already present in ClientInvoiceMasterServiceImpl
	        clientInvoiceService.sendInvoiceEmailWithAttachment(invoiceDetails, fileName, fileBytes);

	        return ResponseEntity.ok(Collections.singletonMap("message", "Email sent with attachment."));

	    } catch (Exception e) {
	        e.printStackTrace();
	        return CommonUtils.createResponse(Constants.FAIL, "Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



//**************************************************************************&

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

		
	
	

	
	//	*********************************************************************************get api****************************************************************************************************


//	@GetMapping("/getAllClientInvoices")
//	public ResponseEntity<?> getAllClientInvoices() {
//	    try {
//	        // Fetch all client invoices with their descriptions (using a custom query)
//	        List<ClientInvoiceMasterEntity> clientInvoices = clientInvoiceService.findAllWithDescriptionValues(); // Ensure this query fetches descriptions too
//
//	        // Sort client invoices by id in descending order
//	        if (clientInvoices != null) {
//	            clientInvoices.sort(Comparator.comparing(ClientInvoiceMasterEntity::getId).reversed());
//	        }
//
//	        // Create a response list by processing each client invoice
//	        List<Map<String, Object>> responseList = clientInvoices.stream().map(invoice -> {
//
//	            Map<String, Object> invoiceMap = new HashMap<>();
//
//	            // Handle invoice ID
//	            invoiceMap.put("id", invoice.getId());
//
//	            // Fetch client details from ClientMasterEntity using clientId
//	            String clientName = invoice.getClientName();
//	            if (clientName != null && !clientName.trim().isEmpty()) {
//	                Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(clientName));
//	                if (clientMasterEntity.isPresent()) {
//	                    invoiceMap.put("clientName", clientMasterEntity.get().getClientName());
//	                    invoiceMap.put("clientId", clientMasterEntity.get().getId());
//	                } else {
//	                    invoiceMap.put("clientName", "Not found");
//	                    invoiceMap.put("clientId", "Not found");
//	                }
//	            } else {
//	                invoiceMap.put("clientName", "Not provided");
//	                invoiceMap.put("clientId", "Not provided");
//	            }
//
//	            // Handle missing or null values for other fields
//	            invoiceMap.put("projectName", invoice.getProjectName() != null ? invoice.getProjectName() : "Not provided");
//	            invoiceMap.put("discom", invoice.getDiscom() != null ? invoice.getDiscom() : "Not provided");
//	            invoiceMap.put("invoiceNo", invoice.getInvoiceNo() != null ? invoice.getInvoiceNo() : "Not provided");
//	            invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription() != null ? invoice.getInvoiceDescription() : "Not provided");
//	            invoiceMap.put("invoiceDate", invoice.getInvoiceDate() != null ? invoice.getInvoiceDate() : "Not provided");
//
//	            // Include the new fields in the response format as per your requirements
//	            invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst() != null ? invoice.getInvoiceAmountIncluGst() : "Not provided");
//	            invoiceMap.put("milestone", invoice.getMilestone() != null ? invoice.getMilestone() : "Not provided");
//	            invoiceMap.put("billableState", invoice.getBillableState() != null ? invoice.getBillableState() : "Not provided");
//	            invoiceMap.put("status", invoice.getStatus() != null ? invoice.getStatus() : "Not provided");
//	            invoiceMap.put("amountExcluGst", invoice.getAmountExcluGst() != null ? invoice.getAmountExcluGst() : "Not provided");
//	            invoiceMap.put("totalCgst", invoice.getTotalCgst() != null ? invoice.getTotalCgst() : "Not provided");
//	            invoiceMap.put("totalSgst", invoice.getTotalSgst() != null ? invoice.getTotalSgst() : "Not provided");
//	            invoiceMap.put("totalIgst", invoice.getTotalIgst() != null ? invoice.getTotalIgst() : "Not provided");
//	            invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue() != null ? invoice.getInvoiceBaseValue() : "Not provided");
//	            invoiceMap.put("gstBaseValue", invoice.getGstBaseValue() != null ? invoice.getGstBaseValue() : "Not provided");
//	            invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst() != null ? invoice.getInvoiceInclusiveOfGst() : "Not provided");
//	            invoiceMap.put("tdsPer", invoice.getTdsPer() != null ? invoice.getTdsPer() : "Not provided");
//	            invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue() != null ? invoice.getTdsBaseValue() : "Not provided");
//	            invoiceMap.put("tdsOnGst", invoice.getTdsOnGst() != null ? invoice.getTdsOnGst() : "Not provided");
//	            invoiceMap.put("cgstOnTds", invoice.getCgstOnTds() != null ? invoice.getCgstOnTds() : "Not provided");
//	            invoiceMap.put("sgstOnTds", invoice.getSgstOnTds() != null ? invoice.getSgstOnTds() : "Not provided");
//	            invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted() != null ? invoice.getTotalTdsDeducted() : "Not provided");
//	            invoiceMap.put("balance", invoice.getBalance() != null ? invoice.getBalance() : "Not provided");
//	            invoiceMap.put("penalty", invoice.getPenalty() != null ? invoice.getPenalty() : "Not provided");
//	            invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase() != null ? invoice.getPenaltyDeductionOnBase() : "Not provided");
//	            invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty() != null ? invoice.getGstOnPenalty() : "Not provided");
//	            invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction() != null ? invoice.getTotalPenaltyDeduction() : "Not provided");
//	            invoiceMap.put("creditNote", invoice.getCreditNote() != null ? invoice.getCreditNote() : "Not provided");
//	            invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived() != null ? invoice.getTotalPaymentReceived() : "Not provided");
//
//	            // Include client invoice descriptions if present
//	            if (invoice.getClientInvoiceDescriptionValue() != null && !invoice.getClientInvoiceDescriptionValue().isEmpty()) {
//	                List<Map<String, Object>> descriptionValues = new ArrayList<>();
//	                for (ClientInvoiceDescriptionValue description : invoice.getClientInvoiceDescriptionValue()) {
//	                    Map<String, Object> descriptionMap = new HashMap<>();
//	                    descriptionMap.put("itemDescription", description.getItemDescription() != null ? description.getItemDescription() : "Not provided");
//	                    descriptionMap.put("baseValue", description.getBaseValue() != null ? description.getBaseValue() : "Not provided");
//	                    descriptionMap.put("gstPer", description.getGstPer() != null ? description.getGstPer() : "Not provided");
//	                    descriptionMap.put("cgst", description.getCgst() != null ? description.getCgst() : "Not provided");
//	                    descriptionMap.put("sgst", description.getSgst() != null ? description.getSgst() : "Not provided");
//	                    descriptionMap.put("igst", description.getIgst() != null ? description.getIgst() : "Not provided");
//	                    descriptionMap.put("amtInclGst", description.getAmtInclGst() != null ? description.getAmtInclGst() : "Not provided");
//	                    descriptionValues.add(descriptionMap);
//	                }
//	                invoiceMap.put("clientInvoiceDescriptionValue", descriptionValues);
//	            } else {
//	                // If no descriptions, add a placeholder or message
//	                invoiceMap.put("clientInvoiceDescriptionValue", "No descriptions available");
//	            }
//
//	            return invoiceMap;
//
//	        }).collect(Collectors.toList()); // Ensure it's a list
//
//	        // Return the final response
//	        return new ResponseEntity<>(responseList, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        // Handle any exceptions that occur
//	        return CommonUtils.createResponse(Constants.FAIL, "An error occurred while fetching invoices: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}


	@GetMapping("/getAllClientInvoices")
	public ResponseEntity<?> getAllClientInvoices() {
	    try {
	        // Fetch all client invoices
	        List<ClientInvoiceMasterEntity> clientInvoices = clientInvoiceService.findAll();

	        // Sort client invoices by id in descending order
	        if (clientInvoices != null) {
	            clientInvoices.sort(Comparator.comparing(ClientInvoiceMasterEntity::getId).reversed());
	        }

	        // Create a response list by processing each client invoice
	        List<Map<String, Object>> responseList = clientInvoices.stream().map(invoice -> {

	            Map<String, Object> invoiceMap = new HashMap<>();

	            // Handle invoice ID
	            invoiceMap.put("id", invoice.getId());

	            // Fetch client details from ClientMasterEntity using clientId
	            String clientName = invoice.getClientName();
	            if (clientName != null && !clientName.trim().isEmpty()) {
	                Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(clientName));
	                if (clientMasterEntity.isPresent()) {
	                    invoiceMap.put("clientName", clientMasterEntity.get().getClientName());
	                    invoiceMap.put("clientId", clientMasterEntity.get().getId());
	                } else {
	                    invoiceMap.put("clientName", "Not found");
	                    invoiceMap.put("clientId", "Not found");
	                }
	            } else {
	                invoiceMap.put("clientName", "Not provided");
	                invoiceMap.put("clientId", "Not provided");
	            }

	            // Handle missing or null values for other fields
	            invoiceMap.put("projectName", invoice.getProjectName() != null ? invoice.getProjectName() : "Not provided");
	            invoiceMap.put("discom", invoice.getDiscom() != null ? invoice.getDiscom() : "Not provided");
	            invoiceMap.put("invoiceNo", invoice.getInvoiceNo() != null ? invoice.getInvoiceNo() : "Not provided");
	            invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription() != null ? invoice.getInvoiceDescription() : "Not provided");
	            invoiceMap.put("invoiceDate", invoice.getInvoiceDate() != null ? invoice.getInvoiceDate() : "Not provided");
	            invoiceMap.put("invoiceDueDate", invoice.getInvoiceDueDate() != null ? invoice.getInvoiceDueDate() : "Not provided");

	            // Include the new fields in the response format as per your requirements
	            invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst() != null ? invoice.getInvoiceAmountIncluGst() : "Not provided");
//	            invoiceMap.put("milestone", invoice.getMilestone() != null ? invoice.getMilestone() : "Not provided");
	            invoiceMap.put("billableState", invoice.getBillableState() != null ? invoice.getBillableState() : "Not provided");
	            invoiceMap.put("status", invoice.getStatus() != null ? invoice.getStatus() : "Not provided");
	            invoiceMap.put("amountExcluGst", invoice.getAmountExcluGst() != null ? invoice.getAmountExcluGst() : "Not provided");
	            invoiceMap.put("totalCgst", invoice.getTotalCgst() != null ? invoice.getTotalCgst() : "Not provided");
	            invoiceMap.put("totalSgst", invoice.getTotalSgst() != null ? invoice.getTotalSgst() : "Not provided");
	            invoiceMap.put("totalIgst", invoice.getTotalIgst() != null ? invoice.getTotalIgst() : "Not provided");
	            invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue() != null ? invoice.getInvoiceBaseValue() : "Not provided");
	            invoiceMap.put("gstBaseValue", invoice.getGstBaseValue() != null ? invoice.getGstBaseValue() : "Not provided");
	            invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst() != null ? invoice.getInvoiceInclusiveOfGst() : "Not provided");
	            invoiceMap.put("tdsPer", invoice.getTdsPer() != null ? invoice.getTdsPer() : "Not provided");
	            invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue() != null ? invoice.getTdsBaseValue() : "Not provided");
	            invoiceMap.put("tdsOnGst", invoice.getTdsOnGst() != null ? invoice.getTdsOnGst() : "Not provided");
	            invoiceMap.put("cgstOnTds", invoice.getCgstOnTds() != null ? invoice.getCgstOnTds() : "Not provided");
	            invoiceMap.put("sgstOnTds", invoice.getSgstOnTds() != null ? invoice.getSgstOnTds() : "Not provided");
	            invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted() != null ? invoice.getTotalTdsDeducted() : "Not provided");
	            invoiceMap.put("balance", invoice.getBalance() != null ? invoice.getBalance() : "Not provided");
	            invoiceMap.put("penalty", invoice.getPenalty() != null ? invoice.getPenalty() : "Not provided");
	            invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase() != null ? invoice.getPenaltyDeductionOnBase() : "Not provided");
	            invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty() != null ? invoice.getGstOnPenalty() : "Not provided");
	            invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction() != null ? invoice.getTotalPenaltyDeduction() : "Not provided");
	            invoiceMap.put("creditNote", invoice.getCreditNote() != null ? invoice.getCreditNote() : "Not provided");
	            invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived() != null ? invoice.getTotalPaymentReceived() : "Not provided");
	         // Include paymentDate field
	            invoiceMap.put("paymentDate", invoice.getPaymentDate() != null ? invoice.getPaymentDate() : "Not provided");
	            
//	             Include client invoice descriptions if present
	            if (invoice.getClientInvoiceDescriptionValue() != null) {
	                List<Map<String, Object>> descriptionValues = new ArrayList<>();
	                for (ClientInvoiceDescriptionValue description : invoice.getClientInvoiceDescriptionValue()) {
	                    Map<String, Object> descriptionMap = new HashMap<>();
	                    descriptionMap.put("itemDescription", description.getItemDescription() != null ? description.getItemDescription() : "Not provided");
	                    descriptionMap.put("baseValue", description.getBaseValue() != null ? description.getBaseValue() : "Not provided");
	                    descriptionMap.put("gstPer", description.getGstPer() != null ? description.getGstPer() : "Not provided");
	                    descriptionMap.put("cgst", description.getCgst() != null ? description.getCgst() : "Not provided");
	                    descriptionMap.put("sgst", description.getSgst() != null ? description.getSgst() : "Not provided");
	                    descriptionMap.put("igst", description.getIgst() != null ? description.getIgst() : "Not provided");
	                    descriptionMap.put("amtInclGst", description.getAmtInclGst() != null ? description.getAmtInclGst() : "Not provided");
	                    
	                 // New fields added
	                    descriptionMap.put("milestone", description.getMilestone() != null ? description.getMilestone() : "Not provided");
	                    descriptionMap.put("subMilestone", description.getSubMilestone() != null ? description.getSubMilestone() : "Not provided");
	                    descriptionValues.add(descriptionMap);
	                }
	                invoiceMap.put("clientInvoiceDescriptionValue", descriptionValues);
	            }

	            return invoiceMap;

	        }).toList();

	        // Return the final response
	        return new ResponseEntity<>(responseList, HttpStatus.OK);

	    } catch (Exception ex) {
	        // Handle any exceptions that occur
	        return CommonUtils.createResponse(Constants.FAIL, "An error occurred while fetching invoices: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	

	 
	 
//	@GetMapping("/getAllClientInvoices")
//	public ResponseEntity<?> getAllClientInvoices() {
//	    try {
//	        // Fetch all client invoices
//	        List<ClientInvoiceMasterEntity> clientInvoices = clientInvoiceService.findAll();
//	        
//	     // Sort the client invoices by ID in descending order
//	        if (clientInvoices != null) {
//	            clientInvoices.sort(Comparator.comparing(ClientInvoiceMasterEntity::getId).reversed());
//	        }
//
//	        // Map entities to response structure
//	        List<Map<String, Object>> responseList = clientInvoices.stream().map(invoice -> {
//	            Map<String, Object> invoiceMap = new HashMap<>();
//
//	            invoiceMap.put("id", invoice.getId());
//	            
//	            // Fetch client details from ClientMasterEntity using clientId
////	            Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(invoice.getClientName()));
////	            if (clientMasterEntity != null) {
////	                invoiceMap.put("clientName", clientMasterEntity.get().getClientName()); // clientName from ClientMasterEntity
////	                invoiceMap.put("clientId", clientMasterEntity.get().getId());  // clientId added
////	            } else {
////	                invoiceMap.put("clientName", "Not found");
////	                invoiceMap.put("clientId", "Not found");
////	            }
//	            Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(invoice.getClientName()));
////	            Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(invoice.getClientName().longValue());
//
//	            if (clientMasterEntity.isPresent()) {
//	                invoiceMap.put("clientName", clientMasterEntity.get().getClientName()); // clientName from ClientMasterEntity
//	                invoiceMap.put("clientId", clientMasterEntity.get().getId());  // clientId added
//	            } else {
//	                invoiceMap.put("clientName", "Not found");
//	                invoiceMap.put("clientId", "Not found");
//	            }
//
//
//	            // Add other invoice details
//	            invoiceMap.put("projectName", invoice.getProjectName());
//	            invoiceMap.put("discom", invoice.getDiscom());
//	            invoiceMap.put("invoiceDate", invoice.getInvoiceDate());
////	            invoiceMap.put("invoiceDate", new SimpleDateFormat("yyyy-MM-dd").format(invoice.getInvoiceDate()));
//	            invoiceMap.put("invoiceNo", invoice.getInvoiceNo());
//	            invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription());
//	            invoiceMap.put("invoiceDueDate", invoice.getInvoiceDueDate());
////	            invoiceMap.put("invoiceAmountExcluGst", invoice.getInvoiceAmountExcluGst());
////	            invoiceMap.put("gstPer", invoice.getGstPer());
////	            invoiceMap.put("gstAmount", invoice.getGstAmount());
//	            invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst());
//	            invoiceMap.put("status", invoice.getStatus());
//	            invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue());
//	            invoiceMap.put("gstBaseValue", invoice.getGstBaseValue());
//	            invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst());
//	            invoiceMap.put("tdsper", invoice.getTdsPer());
//	            invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue());
////	            invoiceMap.put("tdsOnGst", invoice.getTdsOnGstPer());
//	            invoiceMap.put("billableState", invoice.getBillableState());
//	            invoiceMap.put("cgstOnTds", invoice.getCgstOnTds());
//	            invoiceMap.put("sgstOnTds", invoice.getSgstOnTds());
////	            invoiceMap.put("igstOnTds", invoice.getIgstOnTds());
//	            invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted());
//	            invoiceMap.put("balance", invoice.getBalance());
//	            invoiceMap.put("penalty", invoice.getPenalty());
//	            invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase());
//	            invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty());
//	            invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction());
//	            invoiceMap.put("creditNote", invoice.getCreditNote());
//	            invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived());
//	            
//	            invoiceMap.put("milestone", invoice.getMilestone()); // Added milestone field to the response
//
//
//	            // Map nested descriptions
//	            List<ClientInvoiceDescriptionValue> descriptions = clientInvoiceDescriptionValueService.findByClientInvoice(invoice);
//	            List<Map<String, Object>> descriptionList = descriptions.stream().map(desc -> {
//	                Map<String, Object> descMap = new HashMap<>();
//	                descMap.put("itemDescription", desc.getItemDescription());
//	                descMap.put("baseValue", desc.getBaseValue());
//	                return descMap;
//	            }).toList();
//
//	            invoiceMap.put("descriptionsAndBaseValues", descriptionList);
//	            return invoiceMap;
//	        }).toList();
//
//	        return new ResponseEntity<>(responseList, HttpStatus.OK);
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}


	
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
	public ResponseEntity<?> updateClientInvoiceMaster(@RequestBody ClientInvoiceMasterDTO dto) {
	    // Check if ID is provided
	    if (dto.getId() == null) {
	        return ResponseEntity.badRequest().body("Invoice ID is missing");
	    }

	    // Find the existing invoice entity
	    ClientInvoiceMasterEntity invoiceEntity = clientInvoiceService.findById(dto.getId());
	    if (invoiceEntity == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
	    }

	    // Preserve the existing client details (DO NOT update clientId or clientName)
	    String existingClientId = invoiceEntity.getClientId();
	    String existingClientName = invoiceEntity.getClientName();

	    // Update basic fields
	    invoiceEntity.setProjectName(dto.getProjectName());
	    invoiceEntity.setDiscom(dto.getDiscom());
	    invoiceEntity.setInvoiceDate(dto.getInvoiceDate());
	    invoiceEntity.setInvoiceNo(dto.getInvoiceNo());
	    invoiceEntity.setInvoiceDescription(dto.getInvoiceDescription());
	    invoiceEntity.setInvoiceDueDate(dto.getInvoiceDueDate());
	    invoiceEntity.setInvoiceAmountIncluGst(dto.getInvoiceAmountIncluGst());
	    invoiceEntity.setInvoiceAmtIncluGst(dto.getInvoiceAmtIncluGst() != null ? Double.valueOf(dto.getInvoiceAmtIncluGst()) : null);
//	    invoiceEntity.setMilestone(dto.getMilestone() != null ? dto.getMilestone().toString() : null); 
	    invoiceEntity.setBillableState(dto.getBillableState());
	    invoiceEntity.setStatus(dto.getStatus());
	    invoiceEntity.setAmountExcluGst(dto.getAmountExcluGst());
	    invoiceEntity.setTotalCgst(dto.getTotalCgst());
	    invoiceEntity.setTotalSgst(dto.getTotalSgst());
	    invoiceEntity.setTotalIgst(dto.getTotalIgst());

	    // Restore the original client details
	    invoiceEntity.setClientId(existingClientId);
	    invoiceEntity.setClientName(existingClientName);
	    
	    // Update paymentDate (non-mandatory field)
	    if (dto.getPaymentDate() != null) {  
	        invoiceEntity.setPaymentDate(dto.getPaymentDate());
	    } else {
	        invoiceEntity.setPaymentDate(null); // Ensures it's stored as null if not provided
	    }

	    // Update the Client Invoice Description Values
	    if (dto.getClientInvoiceDescriptionValue() != null) {
	        invoiceEntity.getClientInvoiceDescriptionValue().clear();  // Clear existing collection
	        for (ClientInvoiceDescriptionValue descDTO : dto.getClientInvoiceDescriptionValue()) {
	            ClientInvoiceDescriptionValue descEntity = new ClientInvoiceDescriptionValue();
	            descEntity.setItemDescription(descDTO.getItemDescription());
	            descEntity.setBaseValue(descDTO.getBaseValue());
	            descEntity.setGstPer(descDTO.getGstPer());
	            descEntity.setCgst(descDTO.getCgst());
	            descEntity.setSgst(descDTO.getSgst());
	            descEntity.setIgst(descDTO.getIgst());
	            descEntity.setAmtInclGst(descDTO.getAmtInclGst());
	                        
	            // New fields added
	            descEntity.setMilestone(descDTO.getMilestone());
	            descEntity.setSubMilestone(descDTO.getSubMilestone());
	            descEntity.setClientInvoice(invoiceEntity);  // Set the parent reference
	            invoiceEntity.getClientInvoiceDescriptionValue().add(descEntity);  // Add new item
	        }
	    }

	    // Persist the updated entity
	    clientInvoiceService.update(invoiceEntity);

	    // Build response
	    Map<String, Object> response = new HashMap<>();
	    response.put("status", "SUCCESS");
	    response.put("message", "Invoice updated successfully");
	    response.put("invoice", invoiceEntity);
	    return ResponseEntity.ok(response);
	}

	
//	@EncryptResponse
//	@PutMapping("/updateClientInvoiceMaster")
//	public ResponseEntity<?> updateClientInvoiceMaster(@RequestBody ClientInvoiceMasterDTO dto) {
//	    // Check if ID is provided
//	    if (dto.getId() == null) {
//	        return ResponseEntity.badRequest().body("Invoice ID is missing");
//	    }
//	    
//	    // Retrieve the existing invoice entity
//	    ClientInvoiceMasterEntity invoiceEntity = clientInvoiceService.findById(dto.getId());
//	    if (invoiceEntity == null) {
//	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
//	    }
//	    
////	    // Update client details:
////	    if (dto.getClientName() != null) {
////	        Optional<ClientMasterEntity> clientOpt = clientMasterRepository.findById(Long.valueOf(dto.getClientName().toString()));
////	        if (clientOpt.isPresent()) {
////	            invoiceEntity.setClientId(String.valueOf(clientOpt.get().getId()));
////	            invoiceEntity.setClientName(clientOpt.get().getClientName());
////	        } else {
////	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
////	        }
////	    }
//	    
//	    // Update basic fields
//	    invoiceEntity.setProjectName(dto.getProjectName());
//	    invoiceEntity.setDiscom(dto.getDiscom());
//	    invoiceEntity.setInvoiceDate(dto.getInvoiceDate());
//	    invoiceEntity.setInvoiceNo(dto.getInvoiceNo());
//	    invoiceEntity.setInvoiceDescription(dto.getInvoiceDescription());
//	    invoiceEntity.setInvoiceDueDate(dto.getInvoiceDueDate());
//	    invoiceEntity.setInvoiceAmountIncluGst(dto.getInvoiceAmountIncluGst());
//	    invoiceEntity.setInvoiceAmtIncluGst(dto.getInvoiceAmtIncluGst() != null ? Double.valueOf(dto.getInvoiceAmtIncluGst()) : null);
//	    invoiceEntity.setMilestone(dto.getMilestone() != null ? dto.getMilestone().toString() : null);
//	    invoiceEntity.setBillableState(dto.getBillableState());
//	    invoiceEntity.setStatus(dto.getStatus());
//	    invoiceEntity.setAmountExcluGst(dto.getAmountExcluGst());
//	    invoiceEntity.setTotalCgst(dto.getTotalCgst());
//	    invoiceEntity.setTotalSgst(dto.getTotalSgst());
//	    invoiceEntity.setTotalIgst(dto.getTotalIgst());
//	    
//	    // Update the Client Invoice Description Values
//	    if (dto.getClientInvoiceDescriptionValue() != null) {
//	        invoiceEntity.getClientInvoiceDescriptionValue().clear();  // Clear existing collection
//	        for (ClientInvoiceDescriptionValue descDTO : dto.getClientInvoiceDescriptionValue()) {
//	            ClientInvoiceDescriptionValue descEntity = new ClientInvoiceDescriptionValue();
//	            descEntity.setItemDescription(descDTO.getItemDescription());
//	            descEntity.setBaseValue(descDTO.getBaseValue());
//	            descEntity.setGstPer(descDTO.getGstPer());
//	            descEntity.setCgst(descDTO.getCgst());
//	            descEntity.setSgst(descDTO.getSgst());
//	            descEntity.setIgst(descDTO.getIgst());
//	            descEntity.setAmtInclGst(descDTO.getAmtInclGst());
//	            descEntity.setClientInvoice(invoiceEntity);  // Set the parent reference
//	            invoiceEntity.getClientInvoiceDescriptionValue().add(descEntity);  // Add new item
//	        }
//	    }
//	    
//	    // Persist the updated entity
//	    clientInvoiceService.update(invoiceEntity);
//	    
//	    // Build response
//	    Map<String, Object> response = new HashMap<>();
//	    response.put("status", "SUCCESS");
//	    response.put("message", "Invoice updated successfully");
//	    response.put("invoice", invoiceEntity);
//	    return ResponseEntity.ok(response);
//	}

	
//	    @PutMapping("/updateClientInvoiceMaster")
//	    public ResponseEntity<?> updateClientInvoiceMaster(@RequestBody ClientInvoiceMasterDTO dto) {
//	        // Check if ID is provided
//	        if (dto.getId() == null) {
//	            return ResponseEntity.badRequest().body("Invoice ID is missing");
//	        }
//	        
//	        // Retrieve the existing invoice entity
//	        ClientInvoiceMasterEntity invoiceEntity = clientInvoiceService.findById(dto.getId());
//	        if (invoiceEntity == null) {
//	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found");
//	        }
//	        
//	        // Update client details:
//	        // Assuming the clientName field in the JSON is actually a client ID (e.g. 607)
//	        if (dto.getClientName() != null) {
//	            // Convert to Long if needed. Here, clientName is treated as a client ID.
//	            Optional<ClientMasterEntity> clientOpt = clientMasterRepository.findById(Long.valueOf(dto.getClientName().toString()));
//	            if (clientOpt.isPresent()) {
//	                // Save both the client ID and its actual name in the invoice
//	                invoiceEntity.setClientId(String.valueOf(clientOpt.get().getId()));
//	                invoiceEntity.setClientName(clientOpt.get().getClientName());
//	            } else {
//	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Client not found");
//	            }
//	        }
//	        
//	        // Update basic fields
//	        invoiceEntity.setProjectName(dto.getProjectName());
//	        invoiceEntity.setDiscom(dto.getDiscom());
//	        invoiceEntity.setInvoiceDate(dto.getInvoiceDate());
//	        invoiceEntity.setInvoiceNo(dto.getInvoiceNo());
//	        invoiceEntity.setInvoiceDescription(dto.getInvoiceDescription());
//	        invoiceEntity.setInvoiceDueDate(dto.getInvoiceDueDate());
//	        invoiceEntity.setInvoiceAmountIncluGst(dto.getInvoiceAmountIncluGst());
//	        invoiceEntity.setInvoiceAmtIncluGst(dto.getInvoiceAmtIncluGst() != null
//	                ? Double.valueOf(dto.getInvoiceAmtIncluGst()) : null);
//	        invoiceEntity.setMilestone(dto.getMilestone() != null ? dto.getMilestone().toString() : null);
//	        invoiceEntity.setBillableState(dto.getBillableState());
//	        invoiceEntity.setStatus(dto.getStatus());
//	        invoiceEntity.setAmountExcluGst(dto.getAmountExcluGst());
//	        invoiceEntity.setTotalCgst(dto.getTotalCgst());
//	        invoiceEntity.setTotalSgst(dto.getTotalSgst());
//	        invoiceEntity.setTotalIgst(dto.getTotalIgst());
//	        
//	        // Update the Client Invoice Description Values (if provided)
//	        if (dto.getClientInvoiceDescriptionValue() != null) {
//	            List<ClientInvoiceDescriptionValue> descriptionList = new ArrayList<>();
//	            for (ClientInvoiceDescriptionValue descDTO : dto.getClientInvoiceDescriptionValue()) {
//	                ClientInvoiceDescriptionValue descEntity = new ClientInvoiceDescriptionValue();
//	                descEntity.setItemDescription(descDTO.getItemDescription());
//	                descEntity.setBaseValue(descDTO.getBaseValue());
//	                descEntity.setGstPer(descDTO.getGstPer());
//	                descEntity.setCgst(descDTO.getCgst());
//	                descEntity.setSgst(descDTO.getSgst());
//	                descEntity.setIgst(descDTO.getIgst());
//	                descEntity.setAmtInclGst(descDTO.getAmtInclGst());
//	                // (If a bidirectional relationship exists, set the parent invoice too)
//	                descEntity.setClientInvoice(invoiceEntity);
//	                descriptionList.add(descEntity);
//	            }
//	            invoiceEntity.setClientInvoiceDescriptionValue(descriptionList);
//	        }
//	        
//	        // Update additional fields
//	        invoiceEntity.setInvoiceBaseValue(dto.getInvoiceBaseValue() != null ? dto.getInvoiceBaseValue().toString() : null);
//	        invoiceEntity.setGstBaseValue(dto.getGstBaseValue() != null ? dto.getGstBaseValue().toString() : null);
//	        invoiceEntity.setInvoiceInclusiveOfGst(dto.getInvoiceInclusiveOfGst() != null ? dto.getInvoiceInclusiveOfGst().toString() : null);
//	        invoiceEntity.setTdsPer(dto.getTdsPer());
//	        invoiceEntity.setTdsBaseValue(dto.getTdsBaseValue() != null ? dto.getTdsBaseValue().toString() : null);
//	        invoiceEntity.setTdsOnGst(dto.getTdsOnGst());
//	        invoiceEntity.setCgstOnTds(dto.getCgstOnTds());
//	        invoiceEntity.setSgstOnTds(dto.getSgstOnTds());
//	        invoiceEntity.setTotalTdsDeducted(dto.getTotalTdsDeducted());
//	        invoiceEntity.setBalance(dto.getBalance());
//	        invoiceEntity.setPenalty(dto.getPenalty());
//	        invoiceEntity.setPenaltyDeductionOnBase(dto.getPenaltyDeductionOnBase());
//	        invoiceEntity.setGstOnPenalty(dto.getGstOnPenalty());
//	        invoiceEntity.setTotalPenaltyDeduction(dto.getTotalPenaltyDeduction());
//	        invoiceEntity.setCreditNote(dto.getCreditNote());
//	        invoiceEntity.setTotalPaymentReceived(dto.getTotalPaymentReceived());
//	        
//	        // Persist the updated entity
//	        clientInvoiceService.update(invoiceEntity);
//	        
//	        // Build response
//	        Map<String, Object> response = new HashMap<>();
//	        response.put("status", "SUCCESS");
//	        response.put("message", "Invoice updated successfully");
//	        response.put("invoice", invoiceEntity);
//	        return ResponseEntity.ok(response);
//	    }


	
	
	
//	@EncryptResponse
//	@PutMapping("/updateClientInvoiceMaster")
//	public ResponseEntity<?> updateClientInvoiceMaster(@RequestBody ClientInvoiceMasterDTO clientInvoiceMasterDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    try {
//	        // Check that the invoice ID is provided
//	        if (clientInvoiceMasterDTO.getId() == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Invoice ID is missing", HttpStatus.BAD_REQUEST);
//	        }
//	        
//	        // Retrieve the existing invoice entity
//	        ClientInvoiceMasterEntity clientInvoiceEntity = clientInvoiceService.findById(clientInvoiceMasterDTO.getId());
//	        if (clientInvoiceEntity == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Invoice not found", HttpStatus.NOT_FOUND);
//	        }
//	        
//	        // If status is not "completed", ensure mandatory fields are provided
//	        if (!"completed".equalsIgnoreCase(clientInvoiceMasterDTO.getStatus())) {
//	            if (UtilValidate.isEmpty(clientInvoiceMasterDTO.getClientName()) ||
//	                UtilValidate.isEmpty(clientInvoiceMasterDTO.getProjectName()) ||
//	                UtilValidate.isEmpty(clientInvoiceMasterDTO.getDiscom()) ||
//	                clientInvoiceMasterDTO.getInvoiceDate() == null ||
//	                UtilValidate.isEmpty(clientInvoiceMasterDTO.getInvoiceNo()) ||
//	                UtilValidate.isEmpty(clientInvoiceMasterDTO.getInvoiceDescription()) ||
//	                clientInvoiceMasterDTO.getInvoiceDueDate() == null ||
//	                UtilValidate.isEmpty(clientInvoiceMasterDTO.getInvoiceAmountIncluGst())) {
//	                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	            }
//	        }
//	        
//	        // Update each field if a new value is provided; otherwise, retain the existing value.
//	        if (!UtilValidate.isEmpty(clientInvoiceMasterDTO.getClientName())) {
//	            clientInvoiceEntity.setClientName(clientInvoiceMasterDTO.getClientName());
//	        }
//	        if (!UtilValidate.isEmpty(clientInvoiceMasterDTO.getProjectName())) {
//	            clientInvoiceEntity.setProjectName(clientInvoiceMasterDTO.getProjectName());
//	        }
//	        if (!UtilValidate.isEmpty(clientInvoiceMasterDTO.getDiscom())) {
//	            clientInvoiceEntity.setDiscom(clientInvoiceMasterDTO.getDiscom());
//	        }
//	        if (clientInvoiceMasterDTO.getInvoiceDate() != null) {
//	            clientInvoiceEntity.setInvoiceDate(clientInvoiceMasterDTO.getInvoiceDate());
//	        }
//	        if (!UtilValidate.isEmpty(clientInvoiceMasterDTO.getInvoiceNo())) {
//	            clientInvoiceEntity.setInvoiceNo(clientInvoiceMasterDTO.getInvoiceNo());
//	        }
//	        if (!UtilValidate.isEmpty(clientInvoiceMasterDTO.getInvoiceDescription())) {
//	            clientInvoiceEntity.setInvoiceDescription(clientInvoiceMasterDTO.getInvoiceDescription());
//	        }
//	        if (clientInvoiceMasterDTO.getInvoiceDueDate() != null) {
//	            clientInvoiceEntity.setInvoiceDueDate(clientInvoiceMasterDTO.getInvoiceDueDate());
//	        }
////	        if (clientInvoiceMasterDTO.getGstPer() != null) {
////	            clientInvoiceEntity.setGstPer(clientInvoiceMasterDTO.getGstPer());
////	        }
////	        if (clientInvoiceMasterDTO.getGstAmount() != null) {
////	            clientInvoiceEntity.setGstAmount(clientInvoiceMasterDTO.getGstAmount());
////	        }
////	        if (clientInvoiceMasterDTO.getInvoiceAmountExcluGst() != null) {
////	            clientInvoiceEntity.setInvoiceAmountExcluGst(clientInvoiceMasterDTO.getInvoiceAmountExcluGst());
////	        }
//	        if (clientInvoiceMasterDTO.getInvoiceAmountIncluGst() != null) {
//	            clientInvoiceEntity.setInvoiceAmountIncluGst(clientInvoiceMasterDTO.getInvoiceAmountIncluGst());
//	        }
//	        if (!UtilValidate.isEmpty(clientInvoiceMasterDTO.getStatus())) {
//	            clientInvoiceEntity.setStatus(clientInvoiceMasterDTO.getStatus());
//	        }
//	        if (clientInvoiceMasterDTO.getInvoiceBaseValue() != null) {
//	            clientInvoiceEntity.setInvoiceBaseValue(clientInvoiceMasterDTO.getInvoiceBaseValue());
//	        }
//	        if (clientInvoiceMasterDTO.getGstBaseValue() != null) {
//	            clientInvoiceEntity.setGstBaseValue(clientInvoiceMasterDTO.getGstBaseValue());
//	        }
//	        if (clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null) {
//	            clientInvoiceEntity.setInvoiceInclusiveOfGst(clientInvoiceMasterDTO.getInvoiceInclusiveOfGst());
//	        }
//	        if (clientInvoiceMasterDTO.getTdsBaseValue() != null) {
//	            clientInvoiceEntity.setTdsBaseValue(clientInvoiceMasterDTO.getTdsBaseValue());
//	        }
//	        if (clientInvoiceMasterDTO.getCgstOnTds() != null) {
//	            clientInvoiceEntity.setCgstOnTds(clientInvoiceMasterDTO.getCgstOnTds());
//	        }
//	        if (clientInvoiceMasterDTO.getSgstOnTds() != null) {
//	            clientInvoiceEntity.setSgstOnTds(clientInvoiceMasterDTO.getSgstOnTds());
//	        }
//	        if (clientInvoiceMasterDTO.getTotalTdsDeducted() != null) {
//	            clientInvoiceEntity.setTotalTdsDeducted(clientInvoiceMasterDTO.getTotalTdsDeducted());
//	        }
//	        if (clientInvoiceMasterDTO.getBalance() != null) {
//	            clientInvoiceEntity.setBalance(clientInvoiceMasterDTO.getBalance());
//	        }
//	        if (clientInvoiceMasterDTO.getPenalty() != null) {
//	            clientInvoiceEntity.setPenalty(clientInvoiceMasterDTO.getPenalty());
//	        }
//	        if (clientInvoiceMasterDTO.getPenaltyDeductionOnBase() != null) {
//	            clientInvoiceEntity.setPenaltyDeductionOnBase(clientInvoiceMasterDTO.getPenaltyDeductionOnBase());
//	        }
//	        if (clientInvoiceMasterDTO.getGstOnPenalty() != null) {
//	            clientInvoiceEntity.setGstOnPenalty(clientInvoiceMasterDTO.getGstOnPenalty());
//	        }
//	        if (clientInvoiceMasterDTO.getTotalPenaltyDeduction() != null) {
//	            clientInvoiceEntity.setTotalPenaltyDeduction(clientInvoiceMasterDTO.getTotalPenaltyDeduction());
//	        }
//	        if (clientInvoiceMasterDTO.getTotalPaymentReceived() != null) {
//	            clientInvoiceEntity.setTotalPaymentReceived(clientInvoiceMasterDTO.getTotalPaymentReceived());
//	        }
//
//	        // Persist the updated invoice entity
//	        clientInvoiceService.update(clientInvoiceEntity);
//
//	        statusMap.put("clientInvoiceMasterEntity", clientInvoiceEntity);
//	        statusMap.put("status", "SUCCESS");
//	        statusMap.put("statusCode", "RU_200");
//	        statusMap.put("statusMessage", "Successfully updated");
//
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	
//	@EncryptResponse
//	@PutMapping("/updateClientInvoiceMaster")
//	public ResponseEntity<?>updateClientMaster(@RequestBody ClientInvoiceMasterDTO clientInvoiceMasterDTO ){
//
//		Map<String,Object> statusMap=new HashMap<String,Object>();
//		try {
//			ClientInvoiceMasterEntity clientInvoiceEntity=clientInvoiceService.findById(clientInvoiceMasterDTO.getId());
//
//				
//			clientInvoiceEntity.setClientName(clientInvoiceMasterDTO.getClientName()!=null?clientInvoiceMasterDTO.getClientName():clientInvoiceEntity.getClientName());
//			clientInvoiceEntity.setProjectName(clientInvoiceMasterDTO.getProjectName()!=null?clientInvoiceMasterDTO.getProjectName():clientInvoiceEntity.getProjectName());
//			clientInvoiceEntity.setDiscom(clientInvoiceMasterDTO.getDiscom() != null ? clientInvoiceMasterDTO.getDiscom() : clientInvoiceEntity.getDiscom());
//		    clientInvoiceEntity.setInvoiceDate(clientInvoiceMasterDTO.getInvoiceDate() != null ? clientInvoiceMasterDTO.getInvoiceDate() : clientInvoiceEntity.getInvoiceDate());
//		    clientInvoiceEntity.setInvoiceNo(clientInvoiceMasterDTO.getInvoiceNo() != null ? clientInvoiceMasterDTO.getInvoiceNo() : clientInvoiceEntity.getInvoiceNo());
//		    clientInvoiceEntity.setInvoiceDescription(clientInvoiceMasterDTO.getInvoiceDescription() != null ? clientInvoiceMasterDTO.getInvoiceDescription() : clientInvoiceEntity.getInvoiceDescription());
//		    clientInvoiceEntity.setInvoiceDueDate(clientInvoiceMasterDTO.getInvoiceDueDate() != null ? clientInvoiceMasterDTO.getInvoiceDueDate() : clientInvoiceEntity.getInvoiceDueDate());
//		    clientInvoiceEntity.setGstPer(clientInvoiceMasterDTO.getGstPer() != null ? clientInvoiceMasterDTO.getGstPer() : clientInvoiceEntity.getGstPer());
//		    clientInvoiceEntity.setGstAmount(clientInvoiceMasterDTO.getGstAmount() != null ? clientInvoiceMasterDTO.getGstAmount() : clientInvoiceEntity.getGstAmount());
//		    clientInvoiceEntity.setInvoiceAmountExcluGst(clientInvoiceMasterDTO.getInvoiceAmountExcluGst() != null ? clientInvoiceMasterDTO.getInvoiceAmountExcluGst() : clientInvoiceEntity.getInvoiceAmountExcluGst());
//		    clientInvoiceEntity.setInvoiceAmountIncluGst(clientInvoiceMasterDTO.getInvoiceAmountIncluGst() != null ? clientInvoiceMasterDTO.getInvoiceAmountIncluGst() : clientInvoiceEntity.getInvoiceAmountIncluGst());
//		    clientInvoiceEntity.setStatus(clientInvoiceMasterDTO.getStatus() != null ? clientInvoiceMasterDTO.getStatus() : clientInvoiceEntity.getStatus());
//		    clientInvoiceEntity.setInvoiceBaseValue(clientInvoiceMasterDTO.getInvoiceBaseValue() != null ? clientInvoiceMasterDTO.getInvoiceBaseValue() : clientInvoiceEntity.getInvoiceBaseValue());
//		    clientInvoiceEntity.setGstBaseValue(clientInvoiceMasterDTO.getGstBaseValue() != null ? clientInvoiceMasterDTO.getGstBaseValue() : clientInvoiceEntity.getGstBaseValue());
//		    clientInvoiceEntity.setInvoiceInclusiveOfGst(clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null ? clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() : clientInvoiceEntity.getInvoiceInclusiveOfGst());
//		    clientInvoiceEntity.setTdsBaseValue(clientInvoiceMasterDTO.getTdsBaseValue() != null ? clientInvoiceMasterDTO.getTdsBaseValue() : clientInvoiceEntity.getTdsBaseValue());
//		    clientInvoiceEntity.setCgstOnTds(clientInvoiceMasterDTO.getCgstOnTds() != null ? clientInvoiceMasterDTO.getCgstOnTds() : clientInvoiceEntity.getCgstOnTds());
//		    clientInvoiceEntity.setSgstOnTds(clientInvoiceMasterDTO.getSgstOnTds() != null ? clientInvoiceMasterDTO.getSgstOnTds() : clientInvoiceEntity.getSgstOnTds());
//		    clientInvoiceEntity.setTotalTdsDeducted(clientInvoiceMasterDTO.getTotalTdsDeducted() != null ? clientInvoiceMasterDTO.getTotalTdsDeducted() : clientInvoiceEntity.getTotalTdsDeducted());
//		    clientInvoiceEntity.setBalance(clientInvoiceMasterDTO.getBalance() != null ? clientInvoiceMasterDTO.getBalance() : clientInvoiceEntity.getBalance());
//		    clientInvoiceEntity.setPenalty(clientInvoiceMasterDTO.getPenalty() != null ? clientInvoiceMasterDTO.getPenalty() : clientInvoiceEntity.getPenalty());
//		    clientInvoiceEntity.setPenaltyDeductionOnBase(clientInvoiceMasterDTO.getPenaltyDeductionOnBase() != null ? clientInvoiceMasterDTO.getPenaltyDeductionOnBase() : clientInvoiceEntity.getPenaltyDeductionOnBase());
//		    clientInvoiceEntity.setGstOnPenalty(clientInvoiceMasterDTO.getGstOnPenalty() != null ? clientInvoiceMasterDTO.getGstOnPenalty() : clientInvoiceEntity.getGstOnPenalty());
//		    clientInvoiceEntity.setTotalPenaltyDeduction(clientInvoiceMasterDTO.getTotalPenaltyDeduction() != null ? clientInvoiceMasterDTO.getTotalPenaltyDeduction() : clientInvoiceEntity.getTotalPenaltyDeduction());
//		    clientInvoiceEntity.setTotalPaymentReceived(clientInvoiceMasterDTO.getTotalPaymentReceived() != null ? clientInvoiceMasterDTO.getTotalPaymentReceived() : clientInvoiceEntity.getTotalPaymentReceived());
//		  
//
//			clientInvoiceService.update(clientInvoiceEntity);
//						
//		statusMap.put("clientInvoiceMasterEntity",clientInvoiceEntity);
//		statusMap.put("status", "SUCCESS");
//		statusMap.put("statusCode", "RU_200");
//		statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 
//
//		return new ResponseEntity<>(statusMap,HttpStatus.OK);
//	}catch(Exception e) {
//		e.printStackTrace();
//
//	}
//	return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//}
	
//	@EncryptResponse
//	@PutMapping("/updateClientInvoiceMaster")
//	public ResponseEntity<?> updateClientMaster(@RequestBody ClientInvoiceMasterDTO clientInvoiceMasterDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    try {
//	        ClientInvoiceMasterEntity clientInvoiceEntity = clientInvoiceService.findById(clientInvoiceMasterDTO.getId());
//
//	        // Update basic fields
//	        clientInvoiceEntity.setClientName(clientInvoiceMasterDTO.getClientName() != null ? clientInvoiceMasterDTO.getClientName() : clientInvoiceEntity.getClientName());
//	        clientInvoiceEntity.setProjectName(clientInvoiceMasterDTO.getProjectName() != null ? clientInvoiceMasterDTO.getProjectName() : clientInvoiceEntity.getProjectName());
//	        clientInvoiceEntity.setDiscom(clientInvoiceMasterDTO.getDiscom() != null ? clientInvoiceMasterDTO.getDiscom() : clientInvoiceEntity.getDiscom());
//	        clientInvoiceEntity.setInvoiceDate(clientInvoiceMasterDTO.getInvoiceDate() != null ? clientInvoiceMasterDTO.getInvoiceDate() : clientInvoiceEntity.getInvoiceDate());
//	        clientInvoiceEntity.setInvoiceNo(clientInvoiceMasterDTO.getInvoiceNo() != null ? clientInvoiceMasterDTO.getInvoiceNo() : clientInvoiceEntity.getInvoiceNo());
//	        clientInvoiceEntity.setInvoiceDescription(clientInvoiceMasterDTO.getInvoiceDescription() != null ? clientInvoiceMasterDTO.getInvoiceDescription() : clientInvoiceEntity.getInvoiceDescription());
//	        clientInvoiceEntity.setInvoiceDueDate(clientInvoiceMasterDTO.getInvoiceDueDate() != null ? clientInvoiceMasterDTO.getInvoiceDueDate() : clientInvoiceEntity.getInvoiceDueDate());
//	        clientInvoiceEntity.setGstPer(clientInvoiceMasterDTO.getGstPer() != null ? clientInvoiceMasterDTO.getGstPer() : clientInvoiceEntity.getGstPer());
//	        clientInvoiceEntity.setInvoiceAmountExcluGst(clientInvoiceMasterDTO.getInvoiceAmountExcluGst() != null ? clientInvoiceMasterDTO.getInvoiceAmountExcluGst() : clientInvoiceEntity.getInvoiceAmountExcluGst());
//	        clientInvoiceEntity.setInvoiceAmountIncluGst(clientInvoiceMasterDTO.getInvoiceAmountIncluGst() != null ? clientInvoiceMasterDTO.getInvoiceAmountIncluGst() : clientInvoiceEntity.getInvoiceAmountIncluGst());
//	        clientInvoiceEntity.setStatus(clientInvoiceMasterDTO.getStatus() != null ? clientInvoiceMasterDTO.getStatus() : clientInvoiceEntity.getStatus());
//	        clientInvoiceEntity.setInvoiceBaseValue(clientInvoiceMasterDTO.getInvoiceBaseValue() != null ? clientInvoiceMasterDTO.getInvoiceBaseValue() : clientInvoiceEntity.getInvoiceBaseValue());
//	        clientInvoiceEntity.setGstBaseValue(clientInvoiceMasterDTO.getGstBaseValue() != null ? clientInvoiceMasterDTO.getGstBaseValue() : clientInvoiceEntity.getGstBaseValue());
//	        clientInvoiceEntity.setInvoiceInclusiveOfGst(clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() != null ? clientInvoiceMasterDTO.getInvoiceInclusiveOfGst() : clientInvoiceEntity.getInvoiceInclusiveOfGst());
//
//	        // Update additional fields
//	        clientInvoiceEntity.setTdsPer(clientInvoiceMasterDTO.getTdsPer() != null ? clientInvoiceMasterDTO.getTdsPer() : clientInvoiceEntity.getTdsPer());
//	        clientInvoiceEntity.setTdsBaseValue(clientInvoiceMasterDTO.getTdsBaseValue() != null ? clientInvoiceMasterDTO.getTdsBaseValue() : clientInvoiceEntity.getTdsBaseValue());
//	        clientInvoiceEntity.setTdsOnGst(clientInvoiceMasterDTO.getTdsOnGst() != null ? clientInvoiceMasterDTO.getTdsOnGst() : clientInvoiceEntity.getTdsOnGst());
//	        clientInvoiceEntity.setBillableState(clientInvoiceMasterDTO.getBillableState() != null ? clientInvoiceMasterDTO.getBillableState() : clientInvoiceEntity.getBillableState());
//	        clientInvoiceEntity.setCgstOnTds(clientInvoiceMasterDTO.getCgstOnTds() != null ? clientInvoiceMasterDTO.getCgstOnTds() : clientInvoiceEntity.getCgstOnTds());
//	        clientInvoiceEntity.setSgstOnTds(clientInvoiceMasterDTO.getSgstOnTds() != null ? clientInvoiceMasterDTO.getSgstOnTds() : clientInvoiceEntity.getSgstOnTds());
//	        clientInvoiceEntity.setIgstOnTds(clientInvoiceMasterDTO.getIgstOnTds() != null ? clientInvoiceMasterDTO.getIgstOnTds() : clientInvoiceEntity.getIgstOnTds());
//	        clientInvoiceEntity.setTotalTdsDeducted(clientInvoiceMasterDTO.getTotalTdsDeducted() != null ? clientInvoiceMasterDTO.getTotalTdsDeducted() : clientInvoiceEntity.getTotalTdsDeducted());
//	        clientInvoiceEntity.setBalance(clientInvoiceMasterDTO.getBalance() != null ? clientInvoiceMasterDTO.getBalance() : clientInvoiceEntity.getBalance());
//	        clientInvoiceEntity.setPenalty(clientInvoiceMasterDTO.getPenalty() != null ? clientInvoiceMasterDTO.getPenalty() : clientInvoiceEntity.getPenalty());
//	        clientInvoiceEntity.setPenaltyDeductionOnBase(clientInvoiceMasterDTO.getPenaltyDeductionOnBase() != null ? clientInvoiceMasterDTO.getPenaltyDeductionOnBase() : clientInvoiceEntity.getPenaltyDeductionOnBase());
//	        clientInvoiceEntity.setGstOnPenalty(clientInvoiceMasterDTO.getGstOnPenalty() != null ? clientInvoiceMasterDTO.getGstOnPenalty() : clientInvoiceEntity.getGstOnPenalty());
//	        clientInvoiceEntity.setTotalPenaltyDeduction(clientInvoiceMasterDTO.getTotalPenaltyDeduction() != null ? clientInvoiceMasterDTO.getTotalPenaltyDeduction() : clientInvoiceEntity.getTotalPenaltyDeduction());
//	        clientInvoiceEntity.setTotalPaymentReceived(clientInvoiceMasterDTO.getTotalPaymentReceived() != null ? clientInvoiceMasterDTO.getTotalPaymentReceived() : clientInvoiceEntity.getTotalPaymentReceived());
//	        clientInvoiceEntity.setMilestone(clientInvoiceMasterDTO.getMilestone() != null ? clientInvoiceMasterDTO.getMilestone() : clientInvoiceEntity.getMilestone());
//	        // Optional: Handling Client Description and Base Values (if you need to update these as well)
//	        if (clientInvoiceMasterDTO.getClientInvoiceDescriptionValue() != null) {
//	            // Process the description updates
//	            for (ClientInvoiceDescriptionValue description : clientInvoiceMasterDTO.getClientInvoiceDescriptionValue()) {
//	                // Update logic for clientDescriptionAndBaseValue (could be part of a separate service if needed)
//	                // Example:
//	                ClientInvoiceDescriptionValue descriptionValue = new ClientInvoiceDescriptionValue();
//	                descriptionValue.setClientInvoice(clientInvoiceEntity);
//	                descriptionValue.setItemDescription(description.getItemDescription());
//	                descriptionValue.setBaseValue(description.getBaseValue());
//	                clientInvoiceDescriptionValueService.save(descriptionValue);
//	            }
//	        }
//
//	        // Save updated entity
//	        clientInvoiceService.update(clientInvoiceEntity);
//
//	        // Prepare response
//	        statusMap.put("clientInvoiceMasterEntity", clientInvoiceEntity);
//	        statusMap.put("status", "SUCCESS");
//	        statusMap.put("statusCode", "RU_200");
//	        statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");
//
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	    }
//
//	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	
	@EncryptResponse
	@DeleteMapping("/deleteClientInvoice")
	public ResponseEntity<?> deleteClientInvoice(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			ClientInvoiceMasterEntity clientMaster = clientInvoiceService.findById(id);
			if(clientMaster!=null) {
//				clientMaster.setActive(0);
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
//	
	@GetMapping("/getClientInvoice/{invoiceNo}")
	public ResponseEntity<?> getClientInvoice(@PathVariable String invoiceNo) {
	    try {
	        invoiceNo = invoiceNo.trim();
	        String correctedInvoiceNo = invoiceNo.replaceAll("=", "/").trim();
	        invoiceNo = correctedInvoiceNo;
	        // Fetch invoice by invoiceNo
	        ClientInvoiceMasterEntity invoice = clientInvoiceService.findByInvoiceNo(invoiceNo);

	        if (invoice == null) {
	            return new ResponseEntity<>(Map.of("message", "Invoice not found"), HttpStatus.NOT_FOUND);
	        }

	        Map<String, Object> invoiceMap = new HashMap<>();

	        // Handle invoice ID
	        invoiceMap.put("id", invoice.getId());

	        // Fetch client details from ClientMasterEntity using clientId
            String clientName = invoice.getClientName();
            if (clientName != null && !clientName.trim().isEmpty()) {
                Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(clientName));
                if (clientMasterEntity.isPresent()) {
                    invoiceMap.put("clientName", clientMasterEntity.get().getClientName());
                    invoiceMap.put("clientId", clientMasterEntity.get().getId());
                } else {
                    invoiceMap.put("clientName", "Not found");
                    invoiceMap.put("clientId", "Not found");
                }
            } else {
                invoiceMap.put("clientName", "Not provided");
                invoiceMap.put("clientId", "Not provided");
            } 

	        // Add all invoice details with null-checks
	        invoiceMap.put("projectName", invoice.getProjectName() != null ? invoice.getProjectName() : "Not provided");
	        invoiceMap.put("discom", invoice.getDiscom() != null ? invoice.getDiscom() : "Not provided");
	        invoiceMap.put("invoiceNo", invoice.getInvoiceNo() != null ? invoice.getInvoiceNo() : "Not provided");
	        invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription() != null ? invoice.getInvoiceDescription() : "Not provided");
	        invoiceMap.put("invoiceDate", invoice.getInvoiceDate() != null ? invoice.getInvoiceDate() : "Not provided");
	        invoiceMap.put("invoiceDueDate", invoice.getInvoiceDueDate() != null ? invoice.getInvoiceDueDate() : "Not provided");

	        invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst() != null ? invoice.getInvoiceAmountIncluGst() : "Not provided");
//	        invoiceMap.put("milestone", invoice.getMilestone() != null ? invoice.getMilestone() : "Not provided");
	        invoiceMap.put("billableState", invoice.getBillableState() != null ? invoice.getBillableState() : "Not provided");
	        invoiceMap.put("status", invoice.getStatus() != null ? invoice.getStatus() : "Not provided");
	        invoiceMap.put("amountExcluGst", invoice.getAmountExcluGst() != null ? invoice.getAmountExcluGst() : "Not provided");
	        invoiceMap.put("totalCgst", invoice.getTotalCgst() != null ? invoice.getTotalCgst() : "Not provided");
	        invoiceMap.put("totalSgst", invoice.getTotalSgst() != null ? invoice.getTotalSgst() : "Not provided");
	        invoiceMap.put("totalIgst", invoice.getTotalIgst() != null ? invoice.getTotalIgst() : "Not provided");
	        invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue() != null ? invoice.getInvoiceBaseValue() : "Not provided");
	        invoiceMap.put("gstBaseValue", invoice.getGstBaseValue() != null ? invoice.getGstBaseValue() : "Not provided");
	        invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst() != null ? invoice.getInvoiceInclusiveOfGst() : "Not provided");
	        invoiceMap.put("tdsPer", invoice.getTdsPer() != null ? invoice.getTdsPer() : "Not provided");
	        invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue() != null ? invoice.getTdsBaseValue() : "Not provided");
	        invoiceMap.put("tdsOnGst", invoice.getTdsOnGst() != null ? invoice.getTdsOnGst() : "Not provided");
	        invoiceMap.put("cgstOnTds", invoice.getCgstOnTds() != null ? invoice.getCgstOnTds() : "Not provided");
	        invoiceMap.put("sgstOnTds", invoice.getSgstOnTds() != null ? invoice.getSgstOnTds() : "Not provided");
	        invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted() != null ? invoice.getTotalTdsDeducted() : "Not provided");
	        invoiceMap.put("balance", invoice.getBalance() != null ? invoice.getBalance() : "Not provided");
	        invoiceMap.put("penalty", invoice.getPenalty() != null ? invoice.getPenalty() : "Not provided");
	        invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase() != null ? invoice.getPenaltyDeductionOnBase() : "Not provided");
	        invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty() != null ? invoice.getGstOnPenalty() : "Not provided");
	        invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction() != null ? invoice.getTotalPenaltyDeduction() : "Not provided");
	        invoiceMap.put("creditNote", invoice.getCreditNote() != null ? invoice.getCreditNote() : "Not provided");
	        invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived() != null ? invoice.getTotalPaymentReceived() : "Not provided");
	        invoiceMap.put("paymentDate", invoice.getPaymentDate() != null ? invoice.getPaymentDate() : "Not provided");

	        // Include client invoice descriptions if present
	        if (invoice.getClientInvoiceDescriptionValue() != null) {
	            List<Map<String, Object>> descriptionValues = new ArrayList<>();
	            for (ClientInvoiceDescriptionValue description : invoice.getClientInvoiceDescriptionValue()) {
	                Map<String, Object> descriptionMap = new HashMap<>();
	                descriptionMap.put("itemDescription", description.getItemDescription() != null ? description.getItemDescription() : "Not provided");
	                descriptionMap.put("baseValue", description.getBaseValue() != null ? description.getBaseValue() : "Not provided");
	                descriptionMap.put("gstPer", description.getGstPer() != null ? description.getGstPer() : "Not provided");
	                descriptionMap.put("cgst", description.getCgst() != null ? description.getCgst() : "Not provided");
	                descriptionMap.put("sgst", description.getSgst() != null ? description.getSgst() : "Not provided");
	                descriptionMap.put("igst", description.getIgst() != null ? description.getIgst() : "Not provided");
	                descriptionMap.put("amtInclGst", description.getAmtInclGst() != null ? description.getAmtInclGst() : "Not provided");
	                
	                // New fields added
	                descriptionMap.put("milestone", description.getMilestone() != null ? description.getMilestone() : "Not provided");
	                descriptionMap.put("subMilestone", description.getSubMilestone() != null ? description.getSubMilestone() : "Not provided");
	                
	                descriptionValues.add(descriptionMap);
	            }
	            invoiceMap.put("clientInvoiceDescriptionValue", descriptionValues);
	        }

	        return new ResponseEntity<>(invoiceMap, HttpStatus.OK);

	    } catch (Exception ex) {
	        return new ResponseEntity<>(Map.of("error", "An error occurred while fetching the invoice: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


//	@GetMapping("/getClientInvoice/{invoiceNo}")
//	public ResponseEntity<?> getClientInvoice(@PathVariable String invoiceNo) {
//	    try {
//	        System.out.println("Original invoiceNo from path: " + invoiceNo);
//
//	        // Replace '=' with '/' to reconstruct the original invoice number
//	        String correctedInvoiceNo = invoiceNo.replaceAll("=", "/").trim();
//	        System.out.println("Corrected invoiceNo: " + correctedInvoiceNo);
//
//	        // Fetch invoice by corrected invoice number
//	        ClientInvoiceMasterEntity clientInvoice = clientInvoiceService.findByInvoiceNo(correctedInvoiceNo);
//
//	        // Check if the invoice exists
//	        if (clientInvoice == null) {
//	            return new ResponseEntity<>(Map.of("message", "Invoice not found"), HttpStatus.NOT_FOUND);
//	        }
//
//	        // Prepare response data
//	        Map<String, Object> responseMap = new HashMap<>();
//	        responseMap.put("clientName", clientInvoice.getClientName());
//	        responseMap.put("projectName", clientInvoice.getProjectName());
//	        responseMap.put("discom", clientInvoice.getDiscom());
//	        responseMap.put("invoiceDate", clientInvoice.getInvoiceDate());
//	        responseMap.put("invoiceNo", clientInvoice.getInvoiceNo());
//	        responseMap.put("invoiceDescription", clientInvoice.getInvoiceDescription());
//	        responseMap.put("invoiceDueDate", clientInvoice.getInvoiceDueDate());
//	        responseMap.put("invoiceAmountIncluGst", clientInvoice.getInvoiceAmountIncluGst());
//	        responseMap.put("status", clientInvoice.getStatus());
//	        responseMap.put("invoiceBaseValue", clientInvoice.getInvoiceBaseValue());
//	        responseMap.put("gstBaseValue", clientInvoice.getGstBaseValue());
//	        responseMap.put("invoiceInclusiveOfGst", clientInvoice.getInvoiceInclusiveOfGst());
//	        responseMap.put("tdsPer", clientInvoice.getTdsPer());
//	        responseMap.put("tdsBaseValue", clientInvoice.getTdsBaseValue());
//	        responseMap.put("cgstOnTds", clientInvoice.getCgstOnTds());
//	        responseMap.put("sgstOnTds", clientInvoice.getSgstOnTds());
//	        responseMap.put("totalTdsDeducted", clientInvoice.getTotalTdsDeducted());
//	        responseMap.put("balance", clientInvoice.getBalance());
//	        responseMap.put("penalty", clientInvoice.getPenalty());
//	        responseMap.put("penaltyDeductionOnBase", clientInvoice.getPenaltyDeductionOnBase());
//	        responseMap.put("gstOnPenalty", clientInvoice.getGstOnPenalty());
//	        responseMap.put("totalPenaltyDeduction", clientInvoice.getTotalPenaltyDeduction());
//	        responseMap.put("creditNote", clientInvoice.getCreditNote());
//	        responseMap.put("totalPaymentReceived", clientInvoice.getTotalPaymentReceived());
//	        responseMap.put("billableState", clientInvoice.getBillableState());
//
//	     // Fetch and map DescriptionAndBaseValue
//	        System.out.println("Fetching descriptions for invoice: " + correctedInvoiceNo);
//	        List<ClientInvoiceDescriptionValue> descriptions = clientInvoiceDescriptionValueService.findByInvoiceNo(correctedInvoiceNo);
//
//	        if (descriptions == null || descriptions.isEmpty()) {
//	            System.out.println("No description data found for invoice: " + correctedInvoiceNo);
//	        }
//
//	        List<Map<String, Object>> descriptionList = descriptions.stream().map(desc -> {
//	            Map<String, Object> descMap = new HashMap<>();
//	            descMap.put("itemDescription", desc.getItemDescription());
//	            descMap.put("baseValue", desc.getBaseValue());
//	            return descMap;
//	        }).toList();
//
//	        responseMap.put("descriptionsAndBaseValues", descriptionList);
//
//	        return new ResponseEntity<>(responseMap, HttpStatus.OK);
//	    } catch (Exception ex) {
//	        ex.printStackTrace();
//	        return new ResponseEntity<>(Map.of("error", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	
//	@GetMapping("/getClientInvoicesByProjectId/{projectId}")
//	public ResponseEntity<?> getClientInvoicesByProjectId(@PathVariable("projectId") String projectId) {
//	    try {
//	        List<ClientInvoiceMasterEntity> filteredInvoices = clientInvoiceService.getInvoicesByProjectName(projectId);
//
//	        if (filteredInvoices.isEmpty()) {
//	            return CommonUtils.createResponse(Constants.SUCCESS, "No invoices found for the given project ID.", HttpStatus.OK);
//	        }
//
//	        List<Map<String, Object>> responseList = filteredInvoices.stream().map(invoice -> {
//	            Map<String, Object> invoiceMap = new HashMap<>();
//
//	            invoiceMap.put("id", invoice.getId());
//
//	            String clientName = invoice.getClientName();
//	            if (clientName != null && !clientName.trim().isEmpty()) {
//	                Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(clientName));
//	                if (clientMasterEntity.isPresent()) {
//	                    invoiceMap.put("clientName", clientMasterEntity.get().getClientName());
//	                    invoiceMap.put("clientId", clientMasterEntity.get().getId());
//	                } else {
//	                    invoiceMap.put("clientName", "Not found");
//	                    invoiceMap.put("clientId", "Not found");
//	                }
//	            } else {
//	                invoiceMap.put("clientName", "Not provided");
//	                invoiceMap.put("clientId", "Not provided");
//	            }
//
//	            invoiceMap.put("projectName", invoice.getProjectName());
//	            invoiceMap.put("discom", invoice.getDiscom());
//	            invoiceMap.put("invoiceNo", invoice.getInvoiceNo());
//	            invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription());
//	            invoiceMap.put("invoiceDate", invoice.getInvoiceDate());
//	            invoiceMap.put("invoiceDueDate", invoice.getInvoiceDueDate());
//
//	            invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst());
//	            invoiceMap.put("billableState", invoice.getBillableState());
//	            invoiceMap.put("status", invoice.getStatus());
//	            invoiceMap.put("amountExcluGst", invoice.getAmountExcluGst());
//	            invoiceMap.put("totalCgst", invoice.getTotalCgst());
//	            invoiceMap.put("totalSgst", invoice.getTotalSgst());
//	            invoiceMap.put("totalIgst", invoice.getTotalIgst());
//	            invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue());
//	            invoiceMap.put("gstBaseValue", invoice.getGstBaseValue());
//	            invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst());
//	            invoiceMap.put("tdsPer", invoice.getTdsPer());
//	            invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue());
//	            invoiceMap.put("tdsOnGst", invoice.getTdsOnGst());
//	            invoiceMap.put("cgstOnTds", invoice.getCgstOnTds());
//	            invoiceMap.put("sgstOnTds", invoice.getSgstOnTds());
//	            invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted());
//	            invoiceMap.put("balance", invoice.getBalance());
//	            invoiceMap.put("penalty", invoice.getPenalty());
//	            invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase());
//	            invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty());
//	            invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction());
//	            invoiceMap.put("creditNote", invoice.getCreditNote());
//	            invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived());
//	            invoiceMap.put("paymentDate", invoice.getPaymentDate());
//
//	            if (invoice.getClientInvoiceDescriptionValue() != null) {
//	                List<Map<String, Object>> descriptionValues = new ArrayList<>();
//	                for (ClientInvoiceDescriptionValue description : invoice.getClientInvoiceDescriptionValue()) {
//	                    Map<String, Object> descriptionMap = new HashMap<>();
//	                    descriptionMap.put("itemDescription", description.getItemDescription());
//	                    descriptionMap.put("baseValue", description.getBaseValue());
//	                    descriptionMap.put("gstPer", description.getGstPer());
//	                    descriptionMap.put("cgst", description.getCgst());
//	                    descriptionMap.put("sgst", description.getSgst());
//	                    descriptionMap.put("igst", description.getIgst());
//	                    descriptionMap.put("amtInclGst", description.getAmtInclGst());
//	                    descriptionMap.put("milestone", description.getMilestone());
//	                    descriptionMap.put("subMilestone", description.getSubMilestone());
//	                    descriptionValues.add(descriptionMap);
//	                }
//	                invoiceMap.put("clientInvoiceDescriptionValue", descriptionValues);
//	            }
//
//	            return invoiceMap;
//	        }).toList();
//
//	        return new ResponseEntity<>(responseList, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        return CommonUtils.createResponse(Constants.FAIL, "Error while fetching client invoices: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}


	@GetMapping("/getClientInvoicesByProjectId/{projectId}")
	public ResponseEntity<?> getClientInvoicesByProjectId(@PathVariable("projectId") Long projectId) {
	    try {
	        // Step 1: Get project name from project ID
	        String projectName = projectMasterRepository.findProjectNameById(projectId);
	        if (projectName == null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Project not found for ID: " + projectId, HttpStatus.NOT_FOUND);
	        }

	        // Step 2: Fetch client invoices by project name
	        List<ClientInvoiceMasterEntity> filteredInvoices = clientInvoiceService.getInvoicesByProjectName(projectName);

	        if (filteredInvoices.isEmpty()) {
	            return CommonUtils.createResponse(Constants.SUCCESS, "No invoices found for the given project ID.", HttpStatus.OK);
	        }

	        // Step 3: Build response
	        List<Map<String, Object>> responseList = filteredInvoices.stream().map(invoice -> {
	            Map<String, Object> invoiceMap = new HashMap<>();

	            invoiceMap.put("id", invoice.getId());

	            String clientName = invoice.getClientName();
	            if (clientName != null && !clientName.trim().isEmpty()) {
	                Optional<ClientMasterEntity> clientMasterEntity = clientMasterRepository.findById(Long.parseLong(clientName));
	                if (clientMasterEntity.isPresent()) {
	                    invoiceMap.put("clientName", clientMasterEntity.get().getClientName());
	                    invoiceMap.put("clientId", clientMasterEntity.get().getId());
	                } else {
	                    invoiceMap.put("clientName", "Not found");
	                    invoiceMap.put("clientId", "Not found");
	                }
	            } else {
	                invoiceMap.put("clientName", "Not provided");
	                invoiceMap.put("clientId", "Not provided");
	            }

	            invoiceMap.put("projectName", invoice.getProjectName());
	            invoiceMap.put("discom", invoice.getDiscom());
	            invoiceMap.put("invoiceNo", invoice.getInvoiceNo());
	            invoiceMap.put("invoiceDescription", invoice.getInvoiceDescription());
	            invoiceMap.put("invoiceDate", invoice.getInvoiceDate());
	            invoiceMap.put("invoiceDueDate", invoice.getInvoiceDueDate());
	            invoiceMap.put("invoiceAmountIncluGst", invoice.getInvoiceAmountIncluGst());
	            invoiceMap.put("billableState", invoice.getBillableState());
	            invoiceMap.put("status", invoice.getStatus());
	            invoiceMap.put("amountExcluGst", invoice.getAmountExcluGst());
	            invoiceMap.put("totalCgst", invoice.getTotalCgst());
	            invoiceMap.put("totalSgst", invoice.getTotalSgst());
	            invoiceMap.put("totalIgst", invoice.getTotalIgst());
	            invoiceMap.put("invoiceBaseValue", invoice.getInvoiceBaseValue());
	            invoiceMap.put("gstBaseValue", invoice.getGstBaseValue());
	            invoiceMap.put("invoiceInclusiveOfGst", invoice.getInvoiceInclusiveOfGst());
	            invoiceMap.put("tdsPer", invoice.getTdsPer());
	            invoiceMap.put("tdsBaseValue", invoice.getTdsBaseValue());
	            invoiceMap.put("tdsOnGst", invoice.getTdsOnGst());
	            invoiceMap.put("cgstOnTds", invoice.getCgstOnTds());
	            invoiceMap.put("sgstOnTds", invoice.getSgstOnTds());
	            invoiceMap.put("totalTdsDeducted", invoice.getTotalTdsDeducted());
	            invoiceMap.put("balance", invoice.getBalance());
	            invoiceMap.put("penalty", invoice.getPenalty());
	            invoiceMap.put("penaltyDeductionOnBase", invoice.getPenaltyDeductionOnBase());
	            invoiceMap.put("gstOnPenalty", invoice.getGstOnPenalty());
	            invoiceMap.put("totalPenaltyDeduction", invoice.getTotalPenaltyDeduction());
	            invoiceMap.put("creditNote", invoice.getCreditNote());
	            invoiceMap.put("totalPaymentReceived", invoice.getTotalPaymentReceived());
	            invoiceMap.put("paymentDate", invoice.getPaymentDate());

	            if (invoice.getClientInvoiceDescriptionValue() != null) {
	                List<Map<String, Object>> descriptionValues = new ArrayList<>();
	                for (ClientInvoiceDescriptionValue description : invoice.getClientInvoiceDescriptionValue()) {
	                    Map<String, Object> descriptionMap = new HashMap<>();
	                    descriptionMap.put("itemDescription", description.getItemDescription());
	                    descriptionMap.put("baseValue", description.getBaseValue());
	                    descriptionMap.put("gstPer", description.getGstPer());
	                    descriptionMap.put("cgst", description.getCgst());
	                    descriptionMap.put("sgst", description.getSgst());
	                    descriptionMap.put("igst", description.getIgst());
	                    descriptionMap.put("amtInclGst", description.getAmtInclGst());
	                    descriptionMap.put("milestone", description.getMilestone());
	                    descriptionMap.put("subMilestone", description.getSubMilestone());
	                    descriptionValues.add(descriptionMap);
	                }
	                invoiceMap.put("clientInvoiceDescriptionValue", descriptionValues);
	            }

	            return invoiceMap;
	        }).toList();

	        return new ResponseEntity<>(responseList, HttpStatus.OK);

	    } catch (Exception e) {
	        return CommonUtils.createResponse(Constants.FAIL, "Error while fetching client invoices: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}






	
	

}