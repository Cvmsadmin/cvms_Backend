package org.ss.vendorapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.ProfitLossMasterDTO;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.ProfitLossRequestDTO;
import org.ss.vendorapi.service.ClientMasterService;
import org.ss.vendorapi.service.ProfitLossMasterService;
import org.ss.vendorapi.service.ProjectMasterService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.RoleConstants;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class ProfiLossMasterController {

    private static final Class<?> CLASS_NAME = UserMasterController.class;
//    private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION, CLASS_NAME.toString());

    @Autowired
    private Environment env;
    
    @Autowired
	private ClientMasterService clientMasterService;

    @Autowired
    private ProfitLossMasterService profitLossMasterService;
    
    @Autowired
    private UserMasterService userMasterService;
    
    @Autowired
    private ProjectMasterService projectMasterService;
    
    
    @EncryptResponse
    @PostMapping("/addProfitLoss")
    public ResponseEntity<?> addProfitLoss(@RequestBody ProfitLossRequestDTO profitLossRequestDTO, HttpServletRequest request) {
        // Validate request payload
        if (profitLossRequestDTO == null) {
            return CommonUtils.createResponse(Constants.FAIL, "Request payload is missing", HttpStatus.BAD_REQUEST);
        }

        // Validate ClientName and ProjectName
        if (profitLossRequestDTO.getClientName() == null || profitLossRequestDTO.getClientName().getClientId() == 0 || 
            profitLossRequestDTO.getProjectName() == null || profitLossRequestDTO.getProjectName().getProjectId() == 0) {
            return CommonUtils.createResponse(Constants.FAIL, "ClientName or ProjectName is missing or invalid", HttpStatus.BAD_REQUEST);
        }

        // Validate form fields
        if (profitLossRequestDTO.getFormFields() == null || profitLossRequestDTO.getFormFields().isEmpty()) {
            return CommonUtils.createResponse(Constants.FAIL, "Form fields are missing or empty", HttpStatus.BAD_REQUEST);
        }

        try {
            int totalRecords = 0;

            for (ProfitLossMasterDTO profitLossMasterDTO : profitLossRequestDTO.getFormFields()) {
                if (UtilValidate.isEmpty(profitLossMasterDTO.getSrNo()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getDescription()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getGstPercent()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getClientBillIncludeGst()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getClientGstAmount()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getClientBillExcludeGst()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getVendorBillIncludeGst()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getVendorGstAmount()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getVendorBillExcludeGst()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getMarginPercent()) ||
                    UtilValidate.isEmpty(profitLossMasterDTO.getMargin())) {
                    return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
                }

                ProfitLossMasterEntity profitLossEntity = new ProfitLossMasterEntity();

                // Map DTO fields to Entity
                profitLossEntity.setSrNo(profitLossMasterDTO.getSrNo());
                profitLossEntity.setClientId(profitLossRequestDTO.getClientName().getClientId());
                profitLossEntity.setClientName(profitLossRequestDTO.getClientName().getClientName());
                profitLossEntity.setProjectId(profitLossRequestDTO.getProjectName().getProjectId());
                profitLossEntity.setProjectName(profitLossRequestDTO.getProjectName().getProjectName());
                profitLossEntity.setDescription(profitLossMasterDTO.getDescription());
                profitLossEntity.setGstPercent(profitLossMasterDTO.getGstPercent());
                profitLossEntity.setClientBillIncludeGst(profitLossMasterDTO.getClientBillIncludeGst());
                profitLossEntity.setClientGstAmount(profitLossMasterDTO.getClientGstAmount());
                profitLossEntity.setClientBillExcludeGst(profitLossMasterDTO.getClientBillExcludeGst());
                profitLossEntity.setVendorBillIncludeGst(profitLossMasterDTO.getVendorBillIncludeGst());
                profitLossEntity.setVendorGstAmount(profitLossMasterDTO.getVendorGstAmount());
                profitLossEntity.setVendorBillExcludeGst(profitLossMasterDTO.getVendorBillExcludeGst());
                profitLossEntity.setMarginPercent(profitLossMasterDTO.getMarginPercent());
                profitLossEntity.setMargin(profitLossMasterDTO.getMargin());

                // Save entity to database
                profitLossMasterService.save(profitLossEntity);
                totalRecords++;
            }

            // Construct success response
            Map<String, Object> response = new HashMap<>();
            response.put("status", "SUCCESS");
            response.put("message", "Profit/Loss details added successfully");
            response.put("totalRecords", totalRecords);

            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
    
//    @EncryptResponse
//    @PostMapping("/addProfitLoss")
//    public ResponseEntity<?> addProfitLoss(@RequestBody ProfitLossRequestDTO profitLossRequestDTO, HttpServletRequest request) {
//        String methodName = request.getRequestURI();
//
//        Map<String, Object> statusMap = new HashMap<>();
//        List<ProfitLossMasterEntity> savedEntities = new ArrayList<>();
//
//        try {
//            for (ProfitLossMasterDTO profitLossMasterDTO : profitLossRequestDTO.getProfitLoss()) {
//                if (UtilValidate.isEmpty(profitLossMasterDTO.getSrNo()) ||
//                        UtilValidate.isEmpty(profitLossMasterDTO.getDescription()) ||
//                        profitLossMasterDTO.getGstPercent() == null ||
//                        profitLossMasterDTO.getClientBillIncludeGst() == null ||
//                        profitLossMasterDTO.getClientGstAmount() == null ||
//                        profitLossMasterDTO.getClientBillExcludeGst() == null ||
//                        profitLossMasterDTO.getVendorBillIncludeGst() == null ||
//                        profitLossMasterDTO.getVendorGstAmount() == null ||
//                        profitLossMasterDTO.getVendorBillExcludeGst() == null ||
//                        profitLossMasterDTO.getMarginPercent() == null ||
//                        profitLossMasterDTO.getMargin() == null) {
//                    return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//                }
//
//                ProfitLossMasterEntity profitLossCreationEntityObj = new ProfitLossMasterEntity();
//
//                profitLossCreationEntityObj.setSrNo(profitLossMasterDTO.getSrNo());
//                profitLossCreationEntityObj.setClientId(profitLossRequestDTO.getClientId()); // Updated to use clientId
//                profitLossCreationEntityObj.setProjectId(profitLossRequestDTO.getProjectName());
//                profitLossCreationEntityObj.setDescription(profitLossMasterDTO.getDescription());
//                profitLossCreationEntityObj.setGstPercent(profitLossMasterDTO.getGstPercent());
//                profitLossCreationEntityObj.setClientBillIncludeGst(profitLossMasterDTO.getClientBillIncludeGst());
//                profitLossCreationEntityObj.setClientGstAmount(profitLossMasterDTO.getClientGstAmount());
//                profitLossCreationEntityObj.setClientBillExcludeGst(profitLossMasterDTO.getClientBillExcludeGst());
//                profitLossCreationEntityObj.setVendorBillIncludeGst(profitLossMasterDTO.getVendorBillIncludeGst());
//                profitLossCreationEntityObj.setVendorGstAmount(profitLossMasterDTO.getVendorGstAmount());
//                profitLossCreationEntityObj.setVendorBillExcludeGst(profitLossMasterDTO.getVendorBillExcludeGst());
//                profitLossCreationEntityObj.setMarginPercent(profitLossMasterDTO.getMarginPercent());
//                profitLossCreationEntityObj.setMargin(profitLossMasterDTO.getMargin());
//
//                try {
//                    profitLossCreationEntityObj = profitLossMasterService.save(profitLossCreationEntityObj);
//                    savedEntities.add(profitLossCreationEntityObj);
//                } catch (Exception ex) {
//                    statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//                    statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//                    statusMap.put(Parameters.status, Constants.FAIL);
//                    return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            }
//
//            if (!savedEntities.isEmpty()) {
//                statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROFIT_LOSS_GENERATED_SUCCESSFULLY);
//                statusMap.put(Parameters.status, Constants.SUCCESS);
//                statusMap.put(Parameters.statusCode, "RU_200");
//                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//            } else {
//                statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROFIT_LOSS__NOT_GENERATED);
//                statusMap.put(Parameters.status, Constants.FAIL);
//                statusMap.put(Parameters.statusCode, "RU_301");
//                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//            }
//        } catch (Exception ex) {
//            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


//    @EncryptResponse
//    @PostMapping("/addProfitLoss")
//    public ResponseEntity<?> addProfitLoss(@RequestBody ProfitLossRequestDTO profitLossRequestDTO, HttpServletRequest request) {
//        String methodName = request.getRequestURI();
////        logger.logMethodStart(methodName);
//
//        Map<String, Object> statusMap = new HashMap<>();
//        List<ProfitLossMasterEntity> savedEntities = new ArrayList<>();
//
//        try {
//            for (ProfitLossMasterDTO profitLossMasterDTO : profitLossRequestDTO.getProfitLoss()) {
//                if (UtilValidate.isEmpty(profitLossMasterDTO.getSrNo()) ||
//                        UtilValidate.isEmpty(profitLossMasterDTO.getDescription()) ||
//                        profitLossMasterDTO.getGstPercent() == null ||
//                        profitLossMasterDTO.getClientBillIncludeGst() == null ||
//                        profitLossMasterDTO.getClientGstAmount() == null ||
//                        profitLossMasterDTO.getClientBillExcludeGst() == null ||
//                        profitLossMasterDTO.getVendorBillIncludeGst() == null ||
//                        profitLossMasterDTO.getVendorGstAmount() == null ||
//                        profitLossMasterDTO.getVendorBillExcludeGst() == null ||
//                        profitLossMasterDTO.getMarginPercent() == null ||
//                        profitLossMasterDTO.getMargin() == null) {
//                    return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//                }
//
//                ProfitLossMasterEntity profitLossCreationEntityObj = new ProfitLossMasterEntity();
//
//                profitLossCreationEntityObj.setSrNo(profitLossMasterDTO.getSrNo());
//                profitLossCreationEntityObj.setClientId(profitLossRequestDTO.getClientName());
//                profitLossCreationEntityObj.setProjectId(profitLossRequestDTO.getProjectName());
//                profitLossCreationEntityObj.setDescription(profitLossMasterDTO.getDescription());
//                profitLossCreationEntityObj.setGstPercent(profitLossMasterDTO.getGstPercent());
//                profitLossCreationEntityObj.setClientBillIncludeGst(profitLossMasterDTO.getClientBillIncludeGst());
//                profitLossCreationEntityObj.setClientGstAmount(profitLossMasterDTO.getClientGstAmount());
//                profitLossCreationEntityObj.setClientBillExcludeGst(profitLossMasterDTO.getClientBillExcludeGst());
//                profitLossCreationEntityObj.setVendorBillIncludeGst(profitLossMasterDTO.getVendorBillIncludeGst());
//                profitLossCreationEntityObj.setVendorGstAmount(profitLossMasterDTO.getVendorGstAmount());
//                profitLossCreationEntityObj.setVendorBillExcludeGst(profitLossMasterDTO.getVendorBillExcludeGst());
//                profitLossCreationEntityObj.setMarginPercent(profitLossMasterDTO.getMarginPercent());
//                profitLossCreationEntityObj.setMargin(profitLossMasterDTO.getMargin());
//
//                try {
//                    profitLossCreationEntityObj = profitLossMasterService.save(profitLossCreationEntityObj);
//                    savedEntities.add(profitLossCreationEntityObj);
//
//                } catch (Exception ex) {
////                    logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Failed to save user in DB: " + ex.getMessage());
//                    statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//                    statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//                    statusMap.put(Parameters.status, Constants.FAIL);
//                    return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//                }
//            }
//
//            if (!savedEntities.isEmpty()) {
//                statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROFIT_LOSS_GENERATED_SUCCESSFULLY);
//                statusMap.put(Parameters.status, Constants.SUCCESS);
//                statusMap.put(Parameters.statusCode, "RU_200");
//                return new ResponseEntity<>(statusMap, HttpStatus.OK);
//            } else {
//                statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROFIT_LOSS__NOT_GENERATED);
//                statusMap.put(Parameters.status, Constants.FAIL);
//                statusMap.put(Parameters.statusCode, "RU_301");
//                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//            }
//        } catch (Exception ex) {
////            if (logger.isErrorLoggingEnabled()) {
////                logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Exception when processing service list: " + ex.getMessage());
////            }
//            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    
    
    
//    ************************************************************************************************************************************************************************
//    *****************************************************get api ***********************************************************************************************************
 
    
    @EncryptResponse
    @GetMapping("/getAllProfitLossByManager")    
    public ResponseEntity<?> getAllProfitLossByManager(@RequestParam("id") Long userId) {
    	Map<String, Object> statusMap = new HashMap<>();
		try {
			
			UserMasterEntity userMasterEntity=userMasterService.findById(userId);
			if(userMasterEntity==null) {
				//return error
			}
			List<ProfitLossMasterEntity> profitList=null;
			if(RoleConstants.ACCOUNT_MANAGER.equals(userMasterEntity.getRole())) {
				List<ClientMasterEntity> clients=clientMasterService.findByAccountManagerId(userId.toString());
				
				String clientIds = "(" + clients.stream()
			    .map(client -> "'" + client.getId() + "'") // Assuming 'getId()' gets the client ID
			    .collect(Collectors.joining(",")) + ")";
				
				String where="o.clientId in "+clientIds;
				
				profitList=profitLossMasterService.findByWhere(where);
				
				
			}else if(RoleConstants.PROJECT_MANAGER.equals(userMasterEntity.getRole())) {
				
				List<ProjectMasterEntity> projects=projectMasterService.findByWhere("o.projectManager='"+userId.toString()+"'");
				String projectIds = "(" + projects.stream()
			    .map(project -> "'" + project.getId() + "'") // Assuming 'getId()' gets the client ID
			    .collect(Collectors.joining(",")) + ")";
				
				String where="o.projectId in "+projectIds;		
				profitList=profitLossMasterService.findByWhere(where);
				
			}else if(RoleConstants.ADMINISTRATION.equals(userMasterEntity.getRole())) {
				profitList=profitLossMasterService.findAll();
			}else {
				statusMap.put(Parameters.status, Constants.FAIL);
				statusMap.put(Parameters.statusCode, "RU_404");
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
			statusMap.put("profitAndLossList",profitList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
    
    @EncryptResponse
    @GetMapping("/getAllProfitLoss")    
    public ResponseEntity<?> getAllProfitLoss() {
	    try {
	        List<ProfitLossMasterEntity> users = profitLossMasterService.findAll();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
    
    @EncryptResponse
    @GetMapping("/getAllProfitLossSum")    
    public ResponseEntity<?> getAllProfitLossSum() {
        try {
            List<ProfitLossMasterEntity> records = profitLossMasterService.findAll();
            
            // Grouping data by projectName
            Map<String, Map<String, Object>> groupedByProject = new HashMap<>();
            
            for (ProfitLossMasterEntity record : records) {
                String projectName = record.getProjectName();
                
                if (!groupedByProject.containsKey(projectName)) {
                    Map<String, Object> responseMap = new HashMap<>();
                    responseMap.put("id", record.getId());
                    responseMap.put("clientId", record.getClientId());
                    responseMap.put("clientName", record.getClientName());
                    responseMap.put("projectId", record.getProjectId());
                    responseMap.put("projectName", record.getProjectName());
                    responseMap.put("description", record.getDescription());
                    responseMap.put("gstPercent", record.getGstPercent());
                    responseMap.put("clientBillIncludeGst", parseDoubleSafe(record.getClientBillIncludeGst()));
                    responseMap.put("clientGstAmount", parseDoubleSafe(record.getClientGstAmount()));
                    responseMap.put("clientBillExcludeGst", parseDoubleSafe(record.getClientBillExcludeGst()));
                    responseMap.put("vendorBillIncludeGst", parseDoubleSafe(record.getVendorBillIncludeGst()));
                    responseMap.put("vendorGstAmount", parseDoubleSafe(record.getVendorGstAmount()));
                    responseMap.put("vendorBillExcludeGst", parseDoubleSafe(record.getVendorBillExcludeGst()));
                    responseMap.put("margin", parseDoubleSafe(record.getMargin()));
                    responseMap.put("marginPercent", calculateMarginPercent(parseDoubleSafe(record.getMargin()), parseDoubleSafe(record.getClientBillExcludeGst())));
                    responseMap.put("srNo", record.getSrNo());
                    groupedByProject.put(projectName, responseMap);
                } else {
                    Map<String, Object> existingMap = groupedByProject.get(projectName);
                    double updatedMargin = (double) existingMap.get("margin") + parseDoubleSafe(record.getMargin());
                    double updatedClientBillExcludeGst = (double) existingMap.get("clientBillExcludeGst") + parseDoubleSafe(record.getClientBillExcludeGst());
                    
                    existingMap.put("clientBillIncludeGst", (double) existingMap.get("clientBillIncludeGst") + parseDoubleSafe(record.getClientBillIncludeGst()));
                    existingMap.put("clientGstAmount", (double) existingMap.get("clientGstAmount") + parseDoubleSafe(record.getClientGstAmount()));
                    existingMap.put("clientBillExcludeGst", updatedClientBillExcludeGst);
                    existingMap.put("vendorBillIncludeGst", (double) existingMap.get("vendorBillIncludeGst") + parseDoubleSafe(record.getVendorBillIncludeGst()));
                    existingMap.put("vendorGstAmount", (double) existingMap.get("vendorGstAmount") + parseDoubleSafe(record.getVendorGstAmount()));
                    existingMap.put("vendorBillExcludeGst", (double) existingMap.get("vendorBillExcludeGst") + parseDoubleSafe(record.getVendorBillExcludeGst()));
                    existingMap.put("margin", updatedMargin);
                    existingMap.put("marginPercent", calculateMarginPercent(updatedMargin, updatedClientBillExcludeGst));
                }
            }
            
            return new ResponseEntity<>(new ArrayList<>(groupedByProject.values()), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Safely parses a string to double, returns 0.0 if parsing fails.
     */
    private double parseDoubleSafe(String value) {
        try {
            return value != null ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    /**
     * Calculates the margin percentage.
     */
    private double calculateMarginPercent(double margin, double clientBillExcludeGst) {
        return clientBillExcludeGst != 0 ? (margin / clientBillExcludeGst) * 100 : 0.0;
    }
    
    @EncryptResponse
    @GetMapping("/getProfitLossByProjectId")
    public ResponseEntity<?> getProfitLossByProjectId(@RequestParam int projectId) {
        try {
            List<ProfitLossMasterEntity> users = profitLossMasterService.findByProjectId(projectId);
            if (users.isEmpty()) {
                return new ResponseEntity<>("No data found for the given project ID", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    
    
}