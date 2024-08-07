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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.MilestoneMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.modal.ProjectMasterDTO;
import org.ss.vendorapi.modal.ProjectRequestDTO;
import org.ss.vendorapi.service.MilestoneMasterService;
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
	    
	    @Autowired
	    private MilestoneMasterService milestoneMasterService;


	    
	    @PostMapping("/addProject")
	    public ResponseEntity<?> addProject(@RequestBody ProjectRequestDTO projectRequestDTO, HttpServletRequest request) {
	        String methodName = request.getRequestURI();
//	        logger.logMethodStart(methodName);

	        Map<String, Object> statusMap = new HashMap<>();
	        List<MilestoneMasterEntity> savedEntities = new ArrayList<>();

	        try {
	        	
	        	System.out.println(projectRequestDTO);
	        	if(UtilValidate.isEmpty(projectRequestDTO.getClientName()) ||
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
	        	        projectRequestDTO.getTDate() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getLoiNo()) ||
	        	        projectRequestDTO.getDateOfLoa() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getAccountManager()) ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getProjectManager()) ||
	        	        projectRequestDTO.getContractDate() == null ||
	        	        projectRequestDTO.getSingOfDate() == null ||
	        	        UtilValidate.isEmpty(projectRequestDTO.getContractPrice())) {

	        	        return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        	    }
	                ProjectMasterEntity projectMasterEntity = new ProjectMasterEntity();

	               
	                projectMasterEntity.setClientName(projectRequestDTO.getClientName());
	                projectMasterEntity.setProjectName(projectRequestDTO.getProjectName());
	                projectMasterEntity.setAddress(projectRequestDTO.getAddress());
	                projectMasterEntity.setCity(projectRequestDTO.getCity());
	                projectMasterEntity.setState(projectRequestDTO.getState());
	                projectMasterEntity.setPincode(projectRequestDTO.getPincode());
	                projectMasterEntity.setDistrict(projectRequestDTO.getDistrict());
	                projectMasterEntity.setContactPerson(projectRequestDTO.getContactPerson());
	                projectMasterEntity.setContactNo(projectRequestDTO.getContactNo());
	                projectMasterEntity.setEmail(projectRequestDTO.getEmail());
	                projectMasterEntity.setGstNo(projectRequestDTO.getGstNo());
	                projectMasterEntity.setStartDate(projectRequestDTO.getStartDate());
	                projectMasterEntity.setEndDate(projectRequestDTO.getEndDate());
	                projectMasterEntity.setDuration(projectRequestDTO.getDuration());
	                projectMasterEntity.setDateOfLoi(projectRequestDTO.getDateOfLoi());
	                projectMasterEntity.setTDate(projectRequestDTO.getTDate());
	                projectMasterEntity.setLoiNo(projectRequestDTO.getLoiNo());
	                projectMasterEntity.setDateOfLoa(projectRequestDTO.getDateOfLoa());
	                projectMasterEntity.setAccountManager(projectRequestDTO.getAccountManager());
	                projectMasterEntity.setProjectManager(projectRequestDTO.getProjectManager());
	                projectMasterEntity.setContractDate(projectRequestDTO.getContractDate());
	                projectMasterEntity.setSingOfDate(projectRequestDTO.getSingOfDate());
	                projectMasterEntity.setContractPrice(projectRequestDTO.getContractPrice());
	               
	                projectMasterEntity = projectMasterService.save(projectMasterEntity);
	                    
	                for(MilestoneMasterEntity milestoneMasterDto:projectRequestDTO.getMoe()) {
	                	
	                	
	                	if( UtilValidate.isEmpty(milestoneMasterDto.getSerialNumber())||
	                	  milestoneMasterDto.getTDate()==null||
	                	   UtilValidate.isEmpty(milestoneMasterDto.getDays())||
	           			   UtilValidate.isEmpty(milestoneMasterDto.getDeliverables())||	  
	  					   UtilValidate.isEmpty(milestoneMasterDto.getAmountExclGst())||
	  				   	   UtilValidate.isEmpty(milestoneMasterDto.getAmountInclGst())||
	  				   	UtilValidate.isEmpty(milestoneMasterDto.getGstAmount())||
	  					   UtilValidate.isEmpty(milestoneMasterDto.getGstRate())) {
	                	   return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);		
	     	                }
	                	
	                	MilestoneMasterEntity milestoneMasterEntity=new MilestoneMasterEntity();
	                	milestoneMasterEntity.setTDate(milestoneMasterDto.getTDate());
	                	milestoneMasterEntity.setProjectId(projectMasterEntity.getId().toString());
	                	milestoneMasterEntity.setSerialNumber(milestoneMasterDto.getSerialNumber());
	                	milestoneMasterEntity.setDays(milestoneMasterDto.getDays());
	                	milestoneMasterEntity.setDeliverables(milestoneMasterDto.getDeliverables());
	                	milestoneMasterEntity.setAmountExclGst(milestoneMasterDto.getAmountExclGst());
	                	milestoneMasterEntity.setAmountInclGst(milestoneMasterDto.getAmountInclGst());
	                	milestoneMasterEntity.setGstRate(milestoneMasterDto.getGstRate());
	                	milestoneMasterEntity.setGstAmount(milestoneMasterDto.getGstAmount());
	                	
	                	try {
							
	                		milestoneMasterEntity = milestoneMasterService.save(milestoneMasterEntity);
	                		savedEntities.add(milestoneMasterEntity);
	    				}catch(Exception ex) {
	    					System.out.println(ex.getMessage());
	    				}
	    			}


	                if(projectMasterEntity!=null) {
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
	    	Map<String, Object> statusMap = new HashMap<>();
		    try {
		        List<ProjectMasterEntity> projectList = projectMasterService.getAllProject();
		        statusMap.put("ProjectMasterEntity",projectList);
				statusMap.put("Status","Success");
				statusMap.put("Status_Code","RU_200");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);
		    } catch (Exception ex) {
		        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	    }
	    
	    
	    @GetMapping("/getMilestone")
	    public ResponseEntity<?> getMilestoneDetails(@RequestParam String projectId) {
	    	Map<String, Object> statusMap = new HashMap<>();
	    try {
	    	List<MilestoneMasterEntity> milestoneList=milestoneMasterService.findByProjectId(projectId);
	    	
	    	statusMap.put("MilestoneMasterEntity",milestoneList);
			statusMap.put("Status","Success");
			statusMap.put("Status_Code","RU_200");

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

	    }catch(Exception ex) {
	    	ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		
	    }
	}


		}


