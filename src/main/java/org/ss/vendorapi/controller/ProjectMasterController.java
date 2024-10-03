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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.MilestoneMasterEntity;
import org.ss.vendorapi.entity.ProfitLossMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.ProjectRequestDTO;
import org.ss.vendorapi.service.ClientMasterService;
import org.ss.vendorapi.service.MilestoneMasterService;
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
	private ClientMasterService clientMasterService;



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
				List<ClientMasterEntity> clients=clientMasterService.findByAccountManager(userId.toString());

				String clientIds = "(" + clients.stream()
				.map(client -> "'" + client.getId() + "'") // Assuming 'getId()' gets the client ID
				.collect(Collectors.joining(",")) + ")";

				String where="o.clientName in "+clientIds;
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
			statusMap.put("projectMasterEntities",projectList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
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
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.PROJECT_FOUND_SUCCESSFULLY);
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");

			return new ResponseEntity<>(statusMap,HttpStatus.OK);

		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@PutMapping("/updateProjectMaster")
	public ResponseEntity<?>updateClientMaster(@RequestBody ProjectRequestDTO projectRequestDTO){
		Map<String, Object> statusMap = new HashMap<>();
		try {
			ProjectMasterEntity projectMasterEntity=projectMasterService.findById(projectRequestDTO.getId());


			projectMasterEntity.setClientName(projectRequestDTO.getClientName()!=null?projectRequestDTO.getClientName():projectMasterEntity.getClientName());
			projectMasterEntity.setProjectName(projectRequestDTO.getProjectName()!=null?projectRequestDTO.getProjectName():projectMasterEntity.getProjectName());
			projectMasterEntity.setAddress(projectRequestDTO.getAddress()!=null?projectRequestDTO.getAddress():projectMasterEntity.getAddress());
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
			projectMasterEntity.setAccountManager(projectRequestDTO.getAccountManager() != null ? projectRequestDTO.getAccountManager() : projectMasterEntity.getAccountManager());
			projectMasterEntity.setProjectManager(projectRequestDTO.getProjectManager() != null ? projectRequestDTO.getProjectManager() : projectMasterEntity.getProjectManager());
			projectMasterEntity.setContractDate(projectRequestDTO.getContractDate() != null ? projectRequestDTO.getContractDate() : projectMasterEntity.getContractDate());
			projectMasterEntity.setSingOfDate(projectRequestDTO.getSingOfDate() != null ? projectRequestDTO.getSingOfDate() : projectMasterEntity.getSingOfDate());
			projectMasterEntity.setContractPrice(projectRequestDTO.getContractPrice() != null ? projectRequestDTO.getContractPrice() : projectMasterEntity.getContractPrice());

			projectMasterService.update(projectMasterEntity);

			statusMap.put("projectMasterEntity",projectMasterEntity);
			statusMap.put("status", "SUCCESS");
			statusMap.put("statusCode", "RU_200");
			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED"); 

			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		}catch(Exception ex) {
			ex.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}	
	@PutMapping("/updateMilestoneMaster")
	public ResponseEntity<?> updateMilestoneMaster(@RequestBody ProjectRequestDTO projectRequestDTO) {
	    Map<String, Object> statusMap = new HashMap<>();
	    try {
	        // Initialize the list that will hold updated entities
	        List<MilestoneMasterEntity> savedEntities = new ArrayList<>();

	        // Null check for projectRequestDTO.getMoe()
	        if (projectRequestDTO.getMoe() != null && !projectRequestDTO.getMoe().isEmpty()) {
	            // Iterate through the moe list
	            for (MilestoneMasterEntity projectMoe : projectRequestDTO.getMoe()) {

	                // Fetch the existing milestone entity
	                MilestoneMasterEntity milestoneMasterEntity = milestoneMasterService.findById(projectMoe.getId());

	                // Update fields only if the new values are not null
	                milestoneMasterEntity.setTDate(projectMoe.getTDate() != null ? projectMoe.getTDate() : milestoneMasterEntity.getTDate());
	                milestoneMasterEntity.setProjectId(projectMoe.getProjectId() != null ? projectMoe.getProjectId() : milestoneMasterEntity.getProjectId());
	                milestoneMasterEntity.setSerialNumber(projectMoe.getSerialNumber() != null ? projectMoe.getSerialNumber() : milestoneMasterEntity.getSerialNumber());
	                milestoneMasterEntity.setDays(projectMoe.getDays() != null ? projectMoe.getDays() : milestoneMasterEntity.getDays());
	                milestoneMasterEntity.setDeliverables(projectMoe.getDeliverables() != null ? projectMoe.getDeliverables() : milestoneMasterEntity.getDeliverables());
	                milestoneMasterEntity.setAmountExclGst(projectMoe.getAmountExclGst() != null ? projectMoe.getAmountExclGst() : milestoneMasterEntity.getAmountExclGst());
	                milestoneMasterEntity.setGstRate(projectMoe.getGstRate() != null ? projectMoe.getGstRate() : milestoneMasterEntity.getGstRate());
	                milestoneMasterEntity.setGstAmount(projectMoe.getGstAmount() != null ? projectMoe.getGstAmount() : milestoneMasterEntity.getGstAmount());
	                milestoneMasterEntity.setAmountInclGst(projectMoe.getAmountInclGst() != null ? projectMoe.getAmountInclGst() : milestoneMasterEntity.getAmountInclGst());

	                // Save the updated entity
	                milestoneMasterEntity = milestoneMasterService.update(milestoneMasterEntity);
	                savedEntities.add(milestoneMasterEntity);
	            }

	            // If the update is successful, return the status map with updated entities
	            statusMap.put("milestoneMasterEntity", savedEntities);
	            statusMap.put("status", "SUCCESS");
	            statusMap.put("statusCode", "RU_200");
	            statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");

	            return new ResponseEntity<>(statusMap, HttpStatus.OK);
	        } else {
	            // Handle the case where the list is null or empty
	            statusMap.put("status", "FAILURE");
	            statusMap.put("statusCode", "RU_400");
	            statusMap.put("statusMessage", "No milestones to update.");
	            return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        statusMap.put("status", "FAILURE");
	        statusMap.put("statusCode", "RU_500");
	        statusMap.put("statusMessage", "An error occurred while updating the milestones.");
	    }

	    return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
	}



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
}    
