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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.modal.ProjectMasterDTO;
import org.ss.vendorapi.modal.ProjectRequestDTO;
import org.ss.vendorapi.service.ProjectMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("v2/api")
public class ProjectMasterController {
	
	 private static final Class<?> CLASS_NAME = UserMasterController.class;
//	    private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION, CLASS_NAME.toString());

	    @Autowired
	    private Environment env;

	    @Autowired
	    private ProjectMasterService projectMasterService;
	    
	    
	    @PostMapping("/addProject")
	    public ResponseEntity<?> addProject(@RequestBody ProjectRequestDTO projectRequestDTO, HttpServletRequest request) {
	        String methodName = request.getRequestURI();
//	        logger.logMethodStart(methodName);

	        Map<String, Object> statusMap = new HashMap<>();
	        List<ProjectMasterEntity> savedEntities = new ArrayList<>();

	        try {
	        	for (ProjectMasterDTO projectMasterDTO : projectRequestDTO.getMilestone()) {
	        	    if (UtilValidate.isEmpty(projectMasterDTO.getSrNo()) ||
	        	        UtilValidate.isEmpty(projectMasterDTO.getDays()) ||
	        	        UtilValidate.isEmpty(projectMasterDTO.getDeliverables()) ||
	        	        UtilValidate.isEmpty(projectMasterDTO.getAmountExcluGst()) ||
	        	        UtilValidate.isEmpty(projectMasterDTO.getGstPer()) ||
	        	        UtilValidate.isEmpty(projectMasterDTO.getGstAmount()) ||
	        	        UtilValidate.isEmpty(projectMasterDTO.getAmountIncluGst()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getClientName()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getProjectName()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getAddress()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getCity()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getState()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getPincode()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getDistrict()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getContactPerson()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getContactNo()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getEmail()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getGstNo()) ||
	        	        projectRequestDTO.getStartDate() == null ||
	        	        projectRequestDTO.getEndDate() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getDuration()) ||
	        	        projectRequestDTO.getDateOfLoi() == null ||
	        	        projectRequestDTO.getT0Date() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getLoiNo()) ||
	        	        projectRequestDTO.getDateOfLoa() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getAccountManager()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getProjectManager()) ||
	        	        projectRequestDTO.getContractDate() == null ||
	        	        projectRequestDTO.getSingOfDate() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getContractPrice())) {

	        	        return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        	    }
	                ProjectMasterEntity projectCreationEntityObj = new ProjectMasterEntity();

	             // Master DTO fields
	                projectCreationEntityObj.setSrNo(projectMasterDTO.getSrNo());
	                projectCreationEntityObj.setDays(projectMasterDTO.getDays());
	                projectCreationEntityObj.setDeliverables(projectMasterDTO.getDeliverables());
	                projectCreationEntityObj.setAmountExcluGst(projectMasterDTO.getAmountExcluGst());
	                projectCreationEntityObj.setGstPer(projectMasterDTO.getGstPer());
	                projectCreationEntityObj.setGstAmount(projectMasterDTO.getGstAmount());
	                projectCreationEntityObj.setAmountIncluGst(projectMasterDTO.getAmountIncluGst());

	                // Request DTO fields
	                projectCreationEntityObj.setClientName(projectRequestDTO.getClientName());
	                projectCreationEntityObj.setProjectName(projectRequestDTO.getProjectName());
	                projectCreationEntityObj.setAddress(projectRequestDTO.getAddress());
	                projectCreationEntityObj.setCity(projectRequestDTO.getCity());
	                projectCreationEntityObj.setState(projectRequestDTO.getState());
	                projectCreationEntityObj.setPincode(projectRequestDTO.getPincode());
	                projectCreationEntityObj.setDistrict(projectRequestDTO.getDistrict());
	                projectCreationEntityObj.setContactPerson(projectRequestDTO.getContactPerson());
	                projectCreationEntityObj.setContactNo(projectRequestDTO.getContactNo());
	                projectCreationEntityObj.setEmail(projectRequestDTO.getEmail());
	                projectCreationEntityObj.setGstNo(projectRequestDTO.getGstNo());
	                projectCreationEntityObj.setStartDate(projectRequestDTO.getStartDate());
	                projectCreationEntityObj.setEndDate(projectRequestDTO.getEndDate());
	                projectCreationEntityObj.setDuration(projectRequestDTO.getDuration());
	                projectCreationEntityObj.setDateOfLoi(projectRequestDTO.getDateOfLoi());
	                projectCreationEntityObj.setT0Date(projectRequestDTO.getT0Date());
	                projectCreationEntityObj.setLoiNo(projectRequestDTO.getLoiNo());
	                projectCreationEntityObj.setDateOfLoa(projectRequestDTO.getDateOfLoa());
	                projectCreationEntityObj.setAccountManager(projectRequestDTO.getAccountManager());
	                projectCreationEntityObj.setProjectManager(projectRequestDTO.getProjectManager());
	                projectCreationEntityObj.setContractDate(projectRequestDTO.getContractDate());
	                projectCreationEntityObj.setSingOfDate(projectRequestDTO.getSingOfDate());
	                projectCreationEntityObj.setContractPrice(projectRequestDTO.getContractPrice());
	                try {
	                	projectCreationEntityObj = projectMasterService.save(projectCreationEntityObj);
	                    savedEntities.add(projectCreationEntityObj);

	                } catch (Exception ex) {
//	                    logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Failed to save user in DB: " + ex.getMessage());
	                    statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
	                    statusMap.put(Parameters.statusCode, Constants.SVD_USR);
	                    statusMap.put(Parameters.status, Constants.FAIL);
	                    return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	                }
	            }

	            if (!savedEntities.isEmpty()) {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROJECT_CREATED_SUCCESSFULLY);
	                statusMap.put(Parameters.status, Constants.SUCCESS);
	                statusMap.put(Parameters.statusCode, "RU_200");
	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
	            } else {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.PURCHASE_NOT_CREATED);
	                statusMap.put(Parameters.status, Constants.FAIL);
	                statusMap.put(Parameters.statusCode, "RU_301");
	                return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
	            }
	        } catch (Exception ex) {
//	            if (logger.isErrorLoggingEnabled()) {
//	                logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ Exception when processing service list: " + ex.getMessage());
//	            }
	            return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
//	    *****************************************************************************************************************************************************************
//	    *****************************************************************************get api ****************************************************************************
	
	    @GetMapping("/getAllProject")	    
	    public ResponseEntity<?> getAllProject() {
		    try {
		        List<ProjectMasterEntity> users = projectMasterService.getAllProject();
		        return new ResponseEntity<>(users, HttpStatus.OK);
		    } catch (Exception ex) {
		        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
		}

}
