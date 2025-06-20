package org.ss.vendorapi.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.MilestoneCategory;
import org.ss.vendorapi.entity.MilestoneMasterEntity;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.ProjectRequestDTO;
import org.ss.vendorapi.repository.ProjectMasterRepository;
import org.ss.vendorapi.service.ClientInvoiceMasterService;
import org.ss.vendorapi.service.ClientMasterService;
import org.ss.vendorapi.service.MilestoneCategoryService;
import org.ss.vendorapi.service.MilestoneMasterService;
import org.ss.vendorapi.service.ProjectMasterService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.service.VendorInvoiceMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.RoleConstants;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

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
	private UserMasterService userMasterService;

	@Autowired
	private MilestoneMasterService milestoneMasterService;
	
	@Autowired
	private MilestoneCategoryService milestoneCategoryService;


	@Autowired
	private ClientMasterService clientMasterService;
	
	@Autowired
	private ClientInvoiceMasterService clientInvoiceMasterService;
	
	@Autowired
	private VendorInvoiceMasterService vendorInvoiceMasterService;
	
	@Autowired
	private ProjectMasterRepository projectMasterRepository;

	@EncryptResponse
	@PostMapping("/addProject")
	@Transactional  // Ensures atomicity
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

			projectMasterEntity.setClientId(projectRequestDTO.getClientName());
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
			projectMasterEntity.setAccountManagerId(projectRequestDTO.getAccountManager());
			projectMasterEntity.setProjectManagerId(projectRequestDTO.getProjectManager());
			projectMasterEntity.setContractDate(projectRequestDTO.getContractDate());
			projectMasterEntity.setSingOfDate(projectRequestDTO.getSingOfDate());
			projectMasterEntity.setContractPrice(projectRequestDTO.getContractPrice());

			projectMasterEntity = projectMasterService.save(projectMasterEntity);

//			for(MilestoneMasterEntity milestoneMasterDto:projectRequestDTO.getMoe()) {
				  for (MilestoneMasterEntity milestoneMasterDto : projectRequestDTO.getMoe()) {
			            if (UtilValidate.isEmpty(milestoneMasterDto.getSerialNumber()) ||
			                milestoneMasterDto.getTDate() == null ||
			                UtilValidate.isEmpty(milestoneMasterDto.getDays()) ||
			                UtilValidate.isEmpty(milestoneMasterDto.getDeliverables()) ||  
			                UtilValidate.isEmpty(milestoneMasterDto.getAmountExclGst()) ||
			                UtilValidate.isEmpty(milestoneMasterDto.getAmountInclGst()) ||
			                UtilValidate.isEmpty(milestoneMasterDto.getGstAmount()) ||
			                UtilValidate.isEmpty(milestoneMasterDto.getGstRate()) ||
			                UtilValidate.isEmpty(milestoneMasterDto.getStatus())) {  // Removed Completion Date check
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
				milestoneMasterEntity.setStatus(milestoneMasterDto.getStatus());  
			    milestoneMasterEntity.setServiceTypes(milestoneMasterDto.getServiceTypes());
	            milestoneMasterEntity.setPaymentPer(milestoneMasterDto.getPaymentPer());
//	            milestoneMasterEntity.setCompletionDate(milestoneMasterDto.getCompletionDate()); 
				 // Only set completion date if it's not empty
	            if (milestoneMasterDto.getCompletionDate() != null && !milestoneMasterDto.getCompletionDate().toString().trim().isEmpty()) {
	                milestoneMasterEntity.setCompletionDate(milestoneMasterDto.getCompletionDate());
	            }

				try {

					milestoneMasterEntity = milestoneMasterService.save(milestoneMasterEntity);
					savedEntities.add(milestoneMasterEntity);
				}catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
				// Save MilestoneCategory entries (parts)
			        if (projectRequestDTO.getParts() != null && !projectRequestDTO.getParts().isEmpty()) {
			            for (MilestoneCategory part : projectRequestDTO.getParts()) {
			                MilestoneCategory milestoneCategory = new MilestoneCategory();
			                milestoneCategory.setPartition(part.getPartition());
			                milestoneCategory.setPartitionAmount(part.getPartitionAmount());
			                milestoneCategory.setPartitionPer(part.getPartitionPer());
			                milestoneCategory.setProjectId(projectMasterEntity.getId());
			                milestoneCategory.setProjectName(projectMasterEntity.getProjectName());

			                // Save using service/repo (you should have one)
			                milestoneCategoryService.saveCategory(milestoneCategory);  // assumes this method exists
			            }
			        }

			if(projectMasterEntity!=null) {
				statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROJECT_CREATED_SUCCESSFULLY);
				statusMap.put(Parameters.status, Constants.SUCCESS);
				statusMap.put(Parameters.statusCode, "RU_200");
				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			} else {
				statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROJECT_NOT_CREATED);
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

	@EncryptResponse
	@GetMapping("/getAllProject")	    
	public ResponseEntity<?> getAllProject() {
		Map<String, Object> statusMap = new HashMap<>();
		try {
			List<ProjectMasterEntity> projectList = projectMasterService.findAll();
			statusMap.put("ProjectMasterEntity",projectList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@EncryptResponse
    @GetMapping("/getProjectByClientId/{clientId}")
    public ResponseEntity<?> getProjectByClientId(@PathVariable String clientId) {
        Map<String, Object> statusMap = new HashMap<>();
        try {
            List<ProjectMasterEntity> projectList = projectMasterService.getProjectsByClientId(clientId);
            if (projectList.isEmpty()) {
                statusMap.put(Parameters.statusMsg, StatusMessageConstants.NO_PROJECTS_FOUND);
                statusMap.put(Parameters.status, Constants.FAILURE);
                statusMap.put(Parameters.statusCode, "RU_404");
                return new ResponseEntity<>(statusMap, HttpStatus.NOT_FOUND);
            }
            statusMap.put("ProjectMasterEntity", projectList);
            statusMap.put(Parameters.statusMsg, StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
            statusMap.put(Parameters.status, Constants.SUCCESS);
            statusMap.put(Parameters.statusCode, "RU_200");
            return new ResponseEntity<>(statusMap, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//	@GetMapping("/getAllProjectByManager")	    
//	public ResponseEntity<?> getAllProjectByManager(@RequestParam("id") String userId) {
//		Map<String, Object> statusMap = new HashMap<>();
//		try {
//
//			List<ClientMasterEntity> clients=clientMasterService.findByAccountManager(userId);
//
//			String clientIds = "(" + clients.stream()
//			.map(client -> "'" + client.getId() + "'") // Assuming 'getId()' gets the client ID
//			.collect(Collectors.joining(",")) + ")";
//
//			String where="o.clientName in "+clientIds;
//			List<ProjectMasterEntity> projectList=projectMasterService.findByWhere(where);
//			statusMap.put("projectMasterEntities",projectList);
//			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
//			statusMap.put(Parameters.status, Constants.SUCCESS);
//			statusMap.put(Parameters.statusCode, "RU_200");
//			return new ResponseEntity<>(statusMap,HttpStatus.OK);
//		} catch (Exception ex) {
//			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

	
	@EncryptResponse
	@GetMapping("/getAllProjectByManager")	    
	public ResponseEntity<?> getAllProjectByManager(@RequestParam("id") Long userId) {
		Map<String, Object> statusMap = new HashMap<>();
		try {
			
			
			UserMasterEntity userMasterEntity=userMasterService.findById(userId);
			if(userMasterEntity==null) {
				//return error
			}
			List<ProjectMasterEntity> projectList=null;
			if(RoleConstants.ACCOUNT_MANAGER.equals(userMasterEntity.getRole())) {
				List<ClientMasterEntity> clients=clientMasterService.findByAccountManagerId(userId.toString());

				String clientIds = "(" + clients.stream()
				.map(client -> "'" + client.getId() + "'") // Assuming 'getId()' gets the client ID
				.collect(Collectors.joining(",")) + ")";

				String where="o.clientId in "+clientIds;
				 projectList=projectMasterService.findByWhere(where);

			}else if(RoleConstants.PROJECT_MANAGER.equals(userMasterEntity.getRole())) {
				String where="o.projectManager='"+userId+"'";
				projectList=projectMasterService.findByWhere(where);

			}else if(RoleConstants.ADMINISTRATION.equals(userMasterEntity.getRole())) {
				projectList=projectMasterService.findAll();
			}else {
				statusMap.put(Parameters.status, Constants.FAIL);
				statusMap.put(Parameters.statusCode, "RU_404");
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
			
			 // Sort the project list by ID in descending order
	        if (projectList != null) {
	            projectList.sort(Comparator.comparing(ProjectMasterEntity::getId).reversed());
	        }
			
			
			statusMap.put("projectMasterEntities",projectList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@EncryptResponse
	@GetMapping("/getMilestone")
	public ResponseEntity<?> getMilestoneDetails(@RequestParam String projectId) {
		Map<String, Object> statusMap = new HashMap<>();
		try {
			List<MilestoneMasterEntity> milestoneList=milestoneMasterService.findByProjectId(projectId);

			statusMap.put("MilestoneMasterEntity",milestoneList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@EncryptResponse
	@PutMapping("/updateProjectMaster")
	@Transactional
	public ResponseEntity<?> updateProjectMaster(@RequestBody ProjectRequestDTO projectRequestDTO) {
	    Map<String, Object> statusMap = new HashMap<>();
	    try {
	    	ProjectMasterEntity projectMasterEntity = projectMasterService.findById(Long.parseLong(projectRequestDTO.getProjectId()));


	        if (projectMasterEntity == null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Project not found", HttpStatus.NOT_FOUND);
	        }

	        // Update fields (only if not null)
	        projectMasterEntity.setClientId(projectRequestDTO.getClientName() != null ? projectRequestDTO.getClientName() : projectMasterEntity.getClientId());
	        projectMasterEntity.setProjectName(projectRequestDTO.getProjectName() != null ? projectRequestDTO.getProjectName() : projectMasterEntity.getProjectName());
	        projectMasterEntity.setAddress(projectRequestDTO.getAddress() != null ? projectRequestDTO.getAddress() : projectMasterEntity.getAddress());
	        projectMasterEntity.setCity(projectRequestDTO.getCity() != null ? projectRequestDTO.getCity() : projectMasterEntity.getCity());
	        projectMasterEntity.setState(projectRequestDTO.getState() != null ? projectRequestDTO.getState() : projectMasterEntity.getState());
	        projectMasterEntity.setPincode(projectRequestDTO.getPincode() != null ? projectRequestDTO.getPincode() : projectMasterEntity.getPincode());
	        projectMasterEntity.setDistrict(projectRequestDTO.getDistrict() != null ? projectRequestDTO.getDistrict() : projectMasterEntity.getDistrict());
	        projectMasterEntity.setContactPerson(projectRequestDTO.getContactPerson() != null ? projectRequestDTO.getContactPerson() : projectMasterEntity.getContactPerson());
	        projectMasterEntity.setContactNo(projectRequestDTO.getContactNo() != null ? projectRequestDTO.getContactNo() : projectMasterEntity.getContactNo());
	        projectMasterEntity.setEmail(projectRequestDTO.getEmail() != null ? projectRequestDTO.getEmail() : projectMasterEntity.getEmail());
	        projectMasterEntity.setGstNo(projectRequestDTO.getGstNo() != null ? projectRequestDTO.getGstNo() : projectMasterEntity.getGstNo());
	        projectMasterEntity.setStartDate(projectRequestDTO.getStartDate() != null ? projectRequestDTO.getStartDate() : projectMasterEntity.getStartDate());
	        projectMasterEntity.setEndDate(projectRequestDTO.getEndDate() != null ? projectRequestDTO.getEndDate() : projectMasterEntity.getEndDate());
	        projectMasterEntity.setDuration(projectRequestDTO.getDuration() != null ? projectRequestDTO.getDuration() : projectMasterEntity.getDuration());
	        projectMasterEntity.setDateOfLoi(projectRequestDTO.getDateOfLoi() != null ? projectRequestDTO.getDateOfLoi() : projectMasterEntity.getDateOfLoi());
	        projectMasterEntity.setTDate(projectRequestDTO.getTDate() != null ? projectRequestDTO.getTDate() : projectMasterEntity.getTDate());
	        projectMasterEntity.setLoiNo(projectRequestDTO.getLoiNo() != null ? projectRequestDTO.getLoiNo() : projectMasterEntity.getLoiNo());
	        projectMasterEntity.setDateOfLoa(projectRequestDTO.getDateOfLoa() != null ? projectRequestDTO.getDateOfLoa() : projectMasterEntity.getDateOfLoa());
	        projectMasterEntity.setAccountManagerId(projectRequestDTO.getAccountManager() != null ? projectRequestDTO.getAccountManager() : projectMasterEntity.getAccountManagerId());
	        projectMasterEntity.setProjectManagerId(projectRequestDTO.getProjectManager() != null ? projectRequestDTO.getProjectManager() : projectMasterEntity.getProjectManagerId());
	        projectMasterEntity.setContractDate(projectRequestDTO.getContractDate() != null ? projectRequestDTO.getContractDate() : projectMasterEntity.getContractDate());
	        projectMasterEntity.setSingOfDate(projectRequestDTO.getSingOfDate() != null ? projectRequestDTO.getSingOfDate() : projectMasterEntity.getSingOfDate());
	        projectMasterEntity.setContractPrice(projectRequestDTO.getContractPrice() != null ? projectRequestDTO.getContractPrice() : projectMasterEntity.getContractPrice());

	        // Save updated project
	        projectMasterService.update(projectMasterEntity);

	        // 🧹 Clear old milestones
	        milestoneMasterService.deleteByProjectId(projectMasterEntity.getId().toString());

	        // 🔁 Insert new milestones
	        if (projectRequestDTO.getMoe() != null) {
	            for (MilestoneMasterEntity milestoneDto : projectRequestDTO.getMoe()) {
	                MilestoneMasterEntity milestoneEntity = new MilestoneMasterEntity();
	                milestoneEntity.setProjectId(projectMasterEntity.getId().toString());
	                milestoneEntity.setTDate(milestoneDto.getTDate());
	                milestoneEntity.setSerialNumber(milestoneDto.getSerialNumber());
	                milestoneEntity.setDays(milestoneDto.getDays());
	                milestoneEntity.setDeliverables(milestoneDto.getDeliverables());
	                milestoneEntity.setServiceTypes(milestoneDto.getServiceTypes());
	                milestoneEntity.setAmountExclGst(milestoneDto.getAmountExclGst());
	                milestoneEntity.setGstAmount(milestoneDto.getGstAmount());
	                milestoneEntity.setPaymentPer(milestoneDto.getPaymentPer());
	                milestoneEntity.setGstRate(milestoneDto.getGstRate());
	                milestoneEntity.setAmountInclGst(milestoneDto.getAmountInclGst());
	                milestoneEntity.setStatus(milestoneDto.getStatus());

	                if (milestoneDto.getCompletionDate() != null) {
	                    milestoneEntity.setCompletionDate(milestoneDto.getCompletionDate());
	                }

	                milestoneMasterService.save(milestoneEntity);
	            }
	        }

	        // 🧹 Clear old milestone categories
	        milestoneCategoryService.deleteByProjectId(projectMasterEntity.getId());

	        // 🔁 Insert new milestone categories
	        if (projectRequestDTO.getParts() != null) {
	            for (MilestoneCategory part : projectRequestDTO.getParts()) {
	                MilestoneCategory category = new MilestoneCategory();
	                category.setProjectId(projectMasterEntity.getId());
	                category.setProjectName(projectMasterEntity.getProjectName());
	                category.setPartition(part.getPartition());
	                category.setPartitionAmount(part.getPartitionAmount());
	                category.setPartitionPer(part.getPartitionPer());

	                milestoneCategoryService.saveCategory(category);
	            }
	        }

	        statusMap.put("status", "SUCCESS");
	        statusMap.put("statusCode", "RU_200");
	        statusMap.put("statusMessage", "Project updated successfully");
	        statusMap.put("projectMasterEntity", projectMasterEntity);

	        return new ResponseEntity<>(statusMap, HttpStatus.OK);

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return new ResponseEntity<>(Map.of("status", "FAIL", "message", ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
//	@EncryptResponse
//	@PutMapping("/updateProjectMaster")
//	public ResponseEntity<?>updateClientMaster(@RequestBody ProjectRequestDTO projectRequestDTO){
//		Map<String, Object> statusMap = new HashMap<>();
//		try {
//			ProjectMasterEntity projectMasterEntity=projectMasterService.findById(projectRequestDTO.getId());
//
//
//			projectMasterEntity.setClientId(projectRequestDTO.getClientName()!=null?projectRequestDTO.getClientName():projectMasterEntity.getClientId());
//			projectMasterEntity.setProjectName(projectRequestDTO.getProjectName()!=null?projectRequestDTO.getProjectName():projectMasterEntity.getProjectName());
//			projectMasterEntity.setAddress(projectRequestDTO.getAddress()!=null?projectRequestDTO.getAddress():projectMasterEntity.getAddress());
//			projectMasterEntity.setCity(projectRequestDTO.getCity() != null ? projectRequestDTO.getCity() : projectMasterEntity.getCity());
//			projectMasterEntity.setState(projectRequestDTO.getState() != null ? projectRequestDTO.getState() : projectMasterEntity.getState());
//			projectMasterEntity.setPincode(projectRequestDTO.getPincode() != null ? projectRequestDTO.getPincode() : projectMasterEntity.getPincode());
//			projectMasterEntity.setDistrict(projectRequestDTO.getDistrict() != null ? projectRequestDTO.getDistrict() : projectMasterEntity.getDistrict());
//			projectMasterEntity.setContactPerson(projectRequestDTO.getContactPerson() != null ? projectRequestDTO.getContactPerson() : projectMasterEntity.getContactPerson());
//			projectMasterEntity.setContactNo(projectRequestDTO.getContactNo() != null ? projectRequestDTO.getContactNo() : projectMasterEntity.getContactNo());
//			projectMasterEntity.setEmail(projectRequestDTO.getEmail() != null ? projectRequestDTO.getEmail() : projectMasterEntity.getEmail());
//			projectMasterEntity.setGstNo(projectRequestDTO.getGstNo() != null ? projectRequestDTO.getGstNo() : projectMasterEntity.getGstNo());
//			projectMasterEntity.setStartDate(projectRequestDTO.getStartDate() != null ? projectRequestDTO.getStartDate() : projectMasterEntity.getStartDate());
//			projectMasterEntity.setEndDate(projectRequestDTO.getEndDate() != null ? projectRequestDTO.getEndDate() : projectMasterEntity.getEndDate());
//			projectMasterEntity.setDuration(projectRequestDTO.getDuration() != null ? projectRequestDTO.getDuration() : projectMasterEntity.getDuration());
//			projectMasterEntity.setDateOfLoi(projectRequestDTO.getDateOfLoi() != null ? projectRequestDTO.getDateOfLoi() : projectMasterEntity.getDateOfLoi());
//			projectMasterEntity.setTDate(projectRequestDTO.getTDate() != null ? projectRequestDTO.getTDate() : projectMasterEntity.getTDate());
//			projectMasterEntity.setLoiNo(projectRequestDTO.getLoiNo() != null ? projectRequestDTO.getLoiNo() : projectMasterEntity.getLoiNo());
//			projectMasterEntity.setDateOfLoa(projectRequestDTO.getDateOfLoa() != null ? projectRequestDTO.getDateOfLoa() : projectMasterEntity.getDateOfLoa());
//			projectMasterEntity.setAccountManagerId(projectRequestDTO.getAccountManager() != null ? projectRequestDTO.getAccountManager() : projectMasterEntity.getAccountManagerId());
//			projectMasterEntity.setProjectManagerId(projectRequestDTO.getProjectManager() != null ? projectRequestDTO.getProjectManager() : projectMasterEntity.getProjectManagerId());
//			projectMasterEntity.setContractDate(projectRequestDTO.getContractDate() != null ? projectRequestDTO.getContractDate() : projectMasterEntity.getContractDate());
//			projectMasterEntity.setSingOfDate(projectRequestDTO.getSingOfDate() != null ? projectRequestDTO.getSingOfDate() : projectMasterEntity.getSingOfDate());
//			projectMasterEntity.setContractPrice(projectRequestDTO.getContractPrice() != null ? projectRequestDTO.getContractPrice() : projectMasterEntity.getContractPrice());
//
//			projectMasterService.update(projectMasterEntity);
//
//			statusMap.put("projectMasterEntity",projectMasterEntity);
//			statusMap.put("status", "SUCCESS");
//			statusMap.put("statusCode", "RU_200");
//			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 
//
//			return new ResponseEntity<>(statusMap,HttpStatus.OK);
//		}catch(Exception ex) {
//			ex.printStackTrace();
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}
	
	@EncryptResponse
	@PutMapping("/updateMilestoneMaster")
	public ResponseEntity<?> updateMilestoneMaster(@RequestBody ProjectRequestDTO projectRequestDTO) {
	    Map<String, Object> statusMap = new HashMap<>();
	    List<MilestoneMasterEntity> savedEntities = new ArrayList<>();

	    try {
	        if (projectRequestDTO.getProjectId() == null) {
	            return ResponseEntity.badRequest().body(Map.of(
	                "status", "FAILURE",
	                "statusCode", "RU_400",
	                "statusMessage", "Project ID must not be null."
	            ));
	        }

	        // Update Milestones
	        if (!UtilValidate.isEmpty(projectRequestDTO.getMoe())) {
	            List<MilestoneMasterEntity> existingMilestones = milestoneMasterService.findByProjectId(projectRequestDTO.getProjectId());

	            for (MilestoneMasterEntity incomingMilestone : projectRequestDTO.getMoe()) {
	                Optional<MilestoneMasterEntity> match = existingMilestones.stream()
	                        .filter(m -> m.getSerialNumber().equals(incomingMilestone.getSerialNumber()))
	                        .findFirst();

	                MilestoneMasterEntity entityToUpdate = match.orElseGet(MilestoneMasterEntity::new);

	                entityToUpdate.setProjectId(projectRequestDTO.getProjectId());
	                Optional.ofNullable(incomingMilestone.getTDate()).ifPresent(entityToUpdate::setTDate);
	                Optional.ofNullable(incomingMilestone.getDays()).ifPresent(entityToUpdate::setDays);
	                Optional.ofNullable(incomingMilestone.getDeliverables()).ifPresent(entityToUpdate::setDeliverables);
	                Optional.ofNullable(incomingMilestone.getServiceTypes()).ifPresent(entityToUpdate::setServiceTypes);
	                Optional.ofNullable(incomingMilestone.getAmountExclGst()).ifPresent(entityToUpdate::setAmountExclGst);
	                Optional.ofNullable(incomingMilestone.getGstRate()).ifPresent(entityToUpdate::setGstRate);
	                Optional.ofNullable(incomingMilestone.getGstAmount()).ifPresent(entityToUpdate::setGstAmount);
	                Optional.ofNullable(incomingMilestone.getAmountInclGst()).ifPresent(entityToUpdate::setAmountInclGst);
	                Optional.ofNullable(incomingMilestone.getStatus()).ifPresent(entityToUpdate::setStatus);
	                Optional.ofNullable(incomingMilestone.getCompletionDate()).ifPresent(entityToUpdate::setCompletionDate);
	                Optional.ofNullable(incomingMilestone.getPaymentPer()).ifPresent(entityToUpdate::setPaymentPer);
	                Optional.ofNullable(incomingMilestone.getSerialNumber()).ifPresent(entityToUpdate::setSerialNumber);

	                savedEntities.add(milestoneMasterService.save(entityToUpdate));
	            }
	        }

	        // Update Parts (Milestone Categories)
	        if (!UtilValidate.isEmpty(projectRequestDTO.getParts())) {
	            // Delete old parts by projectId (optional)
//	            milestoneCategoryService.deleteByProjectId(Long.parseLong(projectRequestDTO.getProjectId()));

	            for (MilestoneCategory part : projectRequestDTO.getParts()) {
	                MilestoneCategory category = new MilestoneCategory();
	                category.setPartition(part.getPartition());
	                category.setPartitionAmount(part.getPartitionAmount());
	                category.setPartitionPer(part.getPartitionPer());
	                category.setProjectId(Long.parseLong(projectRequestDTO.getProjectId()));
	                category.setProjectName(projectRequestDTO.getProjectName());

	                milestoneCategoryService.saveCategory(category);
	            }
	        }

	        return ResponseEntity.ok(Map.of(
	            "status", "SUCCESS",
	            "statusCode", "RU_200",
	            "statusMessage", "Milestones and parts updated successfully.",
	            "milestoneMasterEntity", savedEntities
	        ));
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
	            "status", "FAILURE",
	            "statusCode", "RU_500",
	            "statusMessage", "An error occurred while updating: " + e.getMessage()
	        ));
	    }
	}

	
//	@EncryptResponse
//	@PutMapping("/updateMilestoneMaster")
//	public ResponseEntity<?> updateMilestoneMaster(@RequestBody ProjectRequestDTO projectRequestDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    List<MilestoneMasterEntity> savedEntities = new ArrayList<>();
//
//	    try {
//	        // Validate if the projectId is provided
//	        if (projectRequestDTO.getProjectId() == null) {
//	            statusMap.put("status", "FAILURE");
//	            statusMap.put("statusCode", "RU_400");
//	            statusMap.put("statusMessage", "Project ID must not be null.");
//	            return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
//	        }
//
//	        // Fetch existing milestones by projectId
//	        List<MilestoneMasterEntity> existingMilestones = milestoneMasterService.findByProjectId(projectRequestDTO.getProjectId());
//	        
//	        if (existingMilestones.isEmpty()) {
//	            statusMap.put("status", "FAILURE");
//	            statusMap.put("statusCode", "RU_404");
//	            statusMap.put("statusMessage", "No milestones found for the given project ID.");
//	            return new ResponseEntity<>(statusMap, HttpStatus.NOT_FOUND);
//	        }
//
//	        // Validate if the milestone list is present in the request
//	        if (UtilValidate.isEmpty(projectRequestDTO.getMoe())) {
//	            statusMap.put("status", "FAILURE");
//	            statusMap.put("statusCode", "RU_400");
//	            statusMap.put("statusMessage", "No milestones to update.");
//	            return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
//	        }
//
//	        // Iterate through the milestone list and update each one
//	        for (MilestoneMasterEntity milestoneDTO : projectRequestDTO.getMoe()) {
//	            Optional<MilestoneMasterEntity> existingMilestoneOpt = existingMilestones.stream()
//	                    .filter(m -> m.getSerialNumber().equals(milestoneDTO.getSerialNumber()))
//	                    .findFirst();
//
//	            if (!existingMilestoneOpt.isPresent()) {
//	                statusMap.put("status", "FAILURE");
//	                statusMap.put("statusCode", "RU_404");
//	                statusMap.put("statusMessage", "Milestone with Serial Number " + milestoneDTO.getSerialNumber() + " not found for the given project.");
//	                return new ResponseEntity<>(statusMap, HttpStatus.NOT_FOUND);
//	            }
//
//	            MilestoneMasterEntity milestoneMasterEntity = existingMilestoneOpt.get();
//	            
//	            // Update fields only if the new values are not null
//	            Optional.ofNullable(milestoneDTO.getTDate()).ifPresent(milestoneMasterEntity::setTDate);
//	            Optional.ofNullable(milestoneDTO.getDays()).ifPresent(milestoneMasterEntity::setDays);
//	            Optional.ofNullable(milestoneDTO.getDeliverables()).ifPresent(milestoneMasterEntity::setDeliverables);
//	            Optional.ofNullable(milestoneDTO.getAmountExclGst()).ifPresent(milestoneMasterEntity::setAmountExclGst);
//	            Optional.ofNullable(milestoneDTO.getGstRate()).ifPresent(milestoneMasterEntity::setGstRate);
//	            Optional.ofNullable(milestoneDTO.getGstAmount()).ifPresent(milestoneMasterEntity::setGstAmount);
//	            Optional.ofNullable(milestoneDTO.getAmountInclGst()).ifPresent(milestoneMasterEntity::setAmountInclGst);
//	            Optional.ofNullable(milestoneDTO.getStatus()).ifPresent(milestoneMasterEntity::setStatus);
//	            Optional.ofNullable(milestoneDTO.getCompletionDate()).ifPresent(milestoneMasterEntity::setCompletionDate);
//
//	            // Save the updated milestone entity
//	            milestoneMasterEntity = milestoneMasterService.update(milestoneMasterEntity);
//	            savedEntities.add(milestoneMasterEntity);
//	        }
//
//	        // Return successful response
//	        statusMap.put("milestoneMasterEntity", savedEntities);
//	        statusMap.put("status", "SUCCESS");
//	        statusMap.put("statusCode", "RU_200");
//	        statusMap.put("statusMessage", "Milestones successfully updated.");
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        statusMap.put("status", "FAILURE");
//	        statusMap.put("statusCode", "RU_500");
//	        statusMap.put("statusMessage", "An error occurred while updating the milestones: " + e.getMessage());
//	        return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	//***************************************************************************************************************************************************

	
//	@EncryptResponse
//	@PutMapping("/updateMilestoneMaster")
//	public ResponseEntity<?> updateMilestoneMaster(@RequestBody ProjectRequestDTO projectRequestDTO) {
//	    Map<String, Object> statusMap = new HashMap<>();
//	    try {
//	        // Initialize the list that will hold updated entities
//	        List<MilestoneMasterEntity> savedEntities = new ArrayList<>();
//
//	        // Null check for projectRequestDTO.getMoe()
//	        if (projectRequestDTO.getMoe() != null && !projectRequestDTO.getMoe().isEmpty()) {
//	            // Iterate through the moe list
//	            for (MilestoneMasterEntity projectMoe : projectRequestDTO.getMoe()) {
//
//	                // Fetch the existing milestone entity
//	                MilestoneMasterEntity milestoneMasterEntity = milestoneMasterService.findById(projectMoe.getId());
//
//	                // Update fields only if the new values are not null
//	                milestoneMasterEntity.setTDate(projectMoe.getTDate() != null ? projectMoe.getTDate() : milestoneMasterEntity.getTDate());
//	                milestoneMasterEntity.setProjectId(projectMoe.getProjectId() != null ? projectMoe.getProjectId() : milestoneMasterEntity.getProjectId());
//	                milestoneMasterEntity.setSerialNumber(projectMoe.getSerialNumber() != null ? projectMoe.getSerialNumber() : milestoneMasterEntity.getSerialNumber());
//	                milestoneMasterEntity.setDays(projectMoe.getDays() != null ? projectMoe.getDays() : milestoneMasterEntity.getDays());
//	                milestoneMasterEntity.setDeliverables(projectMoe.getDeliverables() != null ? projectMoe.getDeliverables() : milestoneMasterEntity.getDeliverables());
//	                milestoneMasterEntity.setAmountExclGst(projectMoe.getAmountExclGst() != null ? projectMoe.getAmountExclGst() : milestoneMasterEntity.getAmountExclGst());
//	                milestoneMasterEntity.setGstRate(projectMoe.getGstRate() != null ? projectMoe.getGstRate() : milestoneMasterEntity.getGstRate());
//	                milestoneMasterEntity.setGstAmount(projectMoe.getGstAmount() != null ? projectMoe.getGstAmount() : milestoneMasterEntity.getGstAmount());
//	                milestoneMasterEntity.setAmountInclGst(projectMoe.getAmountInclGst() != null ? projectMoe.getAmountInclGst() : milestoneMasterEntity.getAmountInclGst());
//
//	                // Save the updated entity
//	                milestoneMasterEntity = milestoneMasterService.update(milestoneMasterEntity);
//	                savedEntities.add(milestoneMasterEntity);
//	            }
//
//	            // If the update is successful, return the status map with updated entities
//	            statusMap.put("milestoneMasterEntity", savedEntities);
//	            statusMap.put("status", "SUCCESS");
//	            statusMap.put("statusCode", "RU_200");
//	            statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");
//
//	            return new ResponseEntity<>(statusMap, HttpStatus.OK);
//	        } else {
//	            // Handle the case where the list is null or empty
//	            statusMap.put("status", "FAILURE");
//	            statusMap.put("statusCode", "RU_400");
//	            statusMap.put("statusMessage", "No milestones to update.");
//	            return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
//	        }
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        statusMap.put("status", "FAILURE");
//	        statusMap.put("statusCode", "RU_500");
//	        statusMap.put("statusMessage", "An error occurred while updating the milestones.");
//	    }
//
//	    return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
//	}



	@EncryptResponse
	@DeleteMapping("/deleteProject")
	public ResponseEntity<?> deleteProjectMaster(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			ProjectMasterEntity projectMaster = projectMasterService.findById(id);
			if(projectMaster!=null) {
				projectMaster.setActive(0);
				projectMasterService.update(projectMaster);

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
	
	
	
	
//	 @GetMapping("/client/{clientId}")
//	    public List<ProjectMasterEntity> getProjectsByClient(@PathVariable Long clientId) {
//	        return projectMasterService.getProjectsByClientId(clientId);
//	    }
	
	
	@EncryptResponse
	@GetMapping("/getPartsByProjectId/{projectId}")
	public ResponseEntity<?> getPartsByProjectId(@PathVariable Long projectId) {
	    try {
	        List<MilestoneCategory> parts = milestoneCategoryService.getPartsByProjectId(projectId);
	        if (parts.isEmpty()) {
	            return CommonUtils.createResponse(Constants.FAIL, "No parts found for this project ID.", HttpStatus.NOT_FOUND);
	        }
	        return new ResponseEntity<>(parts, HttpStatus.OK);
	    } catch (Exception e) {
	        return CommonUtils.createResponse(Constants.FAIL, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
//	@EncryptResponse
//	@GetMapping("/getMilestoneByPartsId/{partsId}")
//	public ResponseEntity<?> getMilestoneByPartsId(@PathVariable Long partsId) {
//	    try {
//	        // Step 1: Fetch the part
//	        MilestoneCategory part = milestoneCategoryService.getById(partsId);
//	        if (part == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Part not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Step 2: Fetch milestones by projectId from the part
//	        List<MilestoneMasterEntity> milestones = milestoneMasterService.getMilestonesByProjectId(part.getProjectId());
//
//	        if (milestones.isEmpty()) {
//	            return CommonUtils.createResponse(Constants.FAIL, "No milestones found", HttpStatus.NOT_FOUND);
//	        }
//
//	        return new ResponseEntity<>(milestones, HttpStatus.OK);
//
//	    } catch (Exception e) {
//	        return CommonUtils.createResponse(Constants.FAIL, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	
	@EncryptResponse
	@GetMapping("/getMilestonesByProjectAndPartition")
	public ResponseEntity<?> getMilestonesByProjectAndPartition(
	        @RequestParam Long projectId,
	        @RequestParam String partition) {
	    try {
	        // Step 1: Get the part (MilestoneCategory) for the given projectId and partition
	        MilestoneCategory part = milestoneCategoryService.getByProjectIdAndPartition(projectId, partition);
	        if (part == null) {
	            return CommonUtils.createResponse(Constants.FAIL, "Partition not found", HttpStatus.NOT_FOUND);
	        }

	        // Step 2: Get all milestones for the projectId
	        List<MilestoneMasterEntity> milestones = milestoneMasterService.getMilestonesByProjectId(projectId);

	        // Step 3: Filter milestones that belong to the given partition
	        List<MilestoneMasterEntity> filtered = milestones.stream()
	                .filter(m -> partition.equalsIgnoreCase(m.getServiceTypes()))
	                .collect(Collectors.toList());

	        if (filtered.isEmpty()) {
	            return CommonUtils.createResponse(Constants.FAIL, "No milestones found for the given partition", HttpStatus.NOT_FOUND);
	        }

	        return new ResponseEntity<>(filtered, HttpStatus.OK);

	    } catch (Exception e) {
	        return CommonUtils.createResponse(Constants.FAIL, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

//	@EncryptResponse
//	@GetMapping("/getInvoiceSumsByProjectId/{projectId}")
//	public ResponseEntity<?> getInvoiceSumsByProjectId(@PathVariable Long projectId) {
//	    Map<String, Object> responseMap = new HashMap<>();
//	    try {
//	        // Get project name from project ID
//	        String projectName = projectMasterRepository.findProjectNameById(projectId);
//	        if (projectName == null) {
//	            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Sum from ClientInvoiceMasterEntity
//	        Double clientAmountExcluGst = clientInvoiceMasterService.getClientAmountExcluGstByProjectName(projectName);
//
//	        // Sum from VendorInvoiceMasterEntity
//	        Double vendorAmountExcluGst = vendorInvoiceMasterService.getVendorAmountExcluGstByProjectName(projectName);
//
//	        responseMap.put("clientAmountExcluGstSum", clientAmountExcluGst != null ? clientAmountExcluGst : 0.0);
//	        responseMap.put("vendorAmountExcluGstSum", vendorAmountExcluGst != null ? vendorAmountExcluGst : 0.0);
//	        responseMap.put(Parameters.statusMsg, "Invoice sums fetched successfully.");
//	        responseMap.put(Parameters.status, Constants.SUCCESS);
//	        responseMap.put(Parameters.statusCode, "RU_200");
//	        return new ResponseEntity<>(responseMap, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}


	@EncryptResponse
	@GetMapping("/getInvoiceSumsByProjectId/{projectId}")
	public ResponseEntity<?> getInvoiceSumsByProjectId(
	        @PathVariable Long projectId,
	        @RequestParam(required = false) String startDate,
	        @RequestParam(required = false) String endDate) {

	    Map<String, Object> responseMap = new HashMap<>();
	    try {
	        // Get project name from project ID
	        String projectName = projectMasterRepository.findProjectNameById(projectId);
	        if (projectName == null) {
	            return new ResponseEntity<>("Project not found", HttpStatus.NOT_FOUND);
	        }

	        // Convert date strings to LocalDate
	        LocalDate start = (startDate != null) ? LocalDate.parse(startDate) : null;
	        LocalDate end = (endDate != null) ? LocalDate.parse(endDate) : null;

	        // Sum from ClientInvoiceMasterEntity
	        Double clientAmountExcluGst = clientInvoiceMasterService
	                .getClientAmountExcluGstByProjectNameAndDate(projectName, start, end);

	        // Sum from VendorInvoiceMasterEntity
	        Double vendorAmountExcluGst = vendorInvoiceMasterService
	                .getVendorAmountExcluGstByProjectNameAndDate(projectName, start, end);

	        responseMap.put("clientAmountExcluGstSum", clientAmountExcluGst != null ? clientAmountExcluGst : 0.0);
	        responseMap.put("vendorAmountExcluGstSum", vendorAmountExcluGst != null ? vendorAmountExcluGst : 0.0);
	        responseMap.put(Parameters.statusMsg, "Invoice sums fetched successfully.");
	        responseMap.put(Parameters.status, Constants.SUCCESS);
	        responseMap.put(Parameters.statusCode, "RU_200");
	        return new ResponseEntity<>(responseMap, HttpStatus.OK);

	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}    
