package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import  org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.entity.ClientMasterEntity;
import org.ss.vendorapi.entity.ProjectMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
// org.ss.vendorapi.entity.RoleMasterEntity; //import
// org.ss.vendorapi.logging.UPPCLLogger; import
import org.ss.vendorapi.service.ClientMasterService;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.ProjectMasterService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.RoleConstants;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api") 
public class ClientMasterController {


	@Autowired 
	private Environment env;

	@Autowired 
	private ClientMasterService clientMasterService;


	@Autowired
	private ProjectMasterService projectMasterService;



	@Autowired
	private UserMasterService userMasterService;


	@Autowired 
	private DataValidationService dataValidationService;
	
	
	@PostMapping("/addClient") 
	public ResponseEntity<?> addClient(@RequestBody CustomerDetailsDTO addClientMEntity, HttpServletRequest request) {

	    String methodName = request.getRequestURI();
	    Map<String, Object> statusMap = new HashMap<>();

	    try {
	        // Validate required fields
	        if (UtilValidate.isEmpty(addClientMEntity.getClientName()) ||
	            UtilValidate.isEmpty(addClientMEntity.getAddress()) ||
	            UtilValidate.isEmpty(addClientMEntity.getCity()) ||
	            UtilValidate.isEmpty(addClientMEntity.getState()) ||
	            UtilValidate.isEmpty(addClientMEntity.getPinCode()) ||
	            UtilValidate.isEmpty(addClientMEntity.getDistrict()) ||
	            UtilValidate.isEmpty(addClientMEntity.getContactPerson()) ||
	            UtilValidate.isEmpty(addClientMEntity.getContactNo()) ||
	            UtilValidate.isEmpty(addClientMEntity.getEmail()) ||
	            UtilValidate.isEmpty(addClientMEntity.getGst()) ||
	            UtilValidate.isEmpty(addClientMEntity.getPan()) ||
	            UtilValidate.isEmpty(addClientMEntity.getTypeOfService()) ||
	            UtilValidate.isEmpty(addClientMEntity.getAccountManager())) { 

	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED); 
	        }

	        // Check if client already exists
	        ClientMasterEntity existingClient = clientMasterService.findByEmail(addClientMEntity.getEmail()); // or use any unique field
	        if (existingClient != null) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_ALREADY_REGISTERED);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_409");
	            return new ResponseEntity<>(statusMap, HttpStatus.CONFLICT);
	        }

	        // Proceed to create the new client
	        ClientMasterEntity clientCreationEntityObj = new ClientMasterEntity();
	        clientCreationEntityObj.setClientName(addClientMEntity.getClientName());
	        clientCreationEntityObj.setAddress(addClientMEntity.getAddress());
	        clientCreationEntityObj.setCity(addClientMEntity.getCity());
	        clientCreationEntityObj.setState(addClientMEntity.getState());
	        clientCreationEntityObj.setPincode(addClientMEntity.getPinCode());
	        clientCreationEntityObj.setDistrict(addClientMEntity.getDistrict());
	        clientCreationEntityObj.setContactPerson(addClientMEntity.getContactPerson());
	        clientCreationEntityObj.setContactNo(addClientMEntity.getContactNo());
	        clientCreationEntityObj.setEmail(addClientMEntity.getEmail());
	        clientCreationEntityObj.setGst(addClientMEntity.getGst());
	        clientCreationEntityObj.setPan(addClientMEntity.getPan());
	        clientCreationEntityObj.setTypeOfService(addClientMEntity.getTypeOfService());
	        clientCreationEntityObj.setAccountManagerId(addClientMEntity.getAccountManager()); 

	        // Save the client to the database
	        clientCreationEntityObj = clientMasterService.save(clientCreationEntityObj);

	        if (clientCreationEntityObj != null) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_REGISTERED_SUCCESSFULLY);
	            statusMap.put(Parameters.status, Constants.SUCCESS);
	            statusMap.put(Parameters.statusCode, "RU_200");
	            return new ResponseEntity<>(statusMap, HttpStatus.OK);
	        } else {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_NOT_REGISTERED);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_301");
	            return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
	        }

	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


//	@PostMapping("/addClient") 
//	public ResponseEntity<?> addClient(@RequestBody  CustomerDetailsDTO addClientMEntity,HttpServletRequest request){
//
//		String methodName =request.getRequestURI();
//		// logger.logMethodStart(methodName);
//
//		Map<String,Object> statusMap= new HashMap<String,Object>();
//
//		ClientMasterEntity clientCreationEntityObj=null; 
//		try{
//
//			if(UtilValidate.isEmpty(addClientMEntity.getClientName()) ||
//					UtilValidate.isEmpty(addClientMEntity.getAddress()) ||
//					UtilValidate.isEmpty(addClientMEntity.getCity()) ||
//					UtilValidate.isEmpty(addClientMEntity.getState()) ||
//					UtilValidate.isEmpty(addClientMEntity.getPinCode()) ||
//					UtilValidate.isEmpty(addClientMEntity.getDistrict()) ||
//					UtilValidate.isEmpty(addClientMEntity.getContactPerson()) ||
//					UtilValidate.isEmpty(addClientMEntity.getContactNo()) ||
//					UtilValidate.isEmpty(addClientMEntity.getEmail()) ||
//					UtilValidate.isEmpty(addClientMEntity.getGst()) ||
//					UtilValidate.isEmpty(addClientMEntity.getPan()) ||
//					UtilValidate.isEmpty(addClientMEntity.getTypeOfService()) ||
//					UtilValidate.isEmpty(addClientMEntity.getAccountManager())){ 
//
//				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED); 
//			}
//
//
//			clientCreationEntityObj=new ClientMasterEntity();
//			clientCreationEntityObj.setClientName(addClientMEntity.getClientName());
//			clientCreationEntityObj.setAddress(addClientMEntity.getAddress());
//			clientCreationEntityObj.setCity(addClientMEntity.getCity());
//			clientCreationEntityObj.setState(addClientMEntity.getState());
//			clientCreationEntityObj.setPincode(addClientMEntity.getPinCode());
//			clientCreationEntityObj.setDistrict(addClientMEntity.getDistrict());
//			clientCreationEntityObj.setContactPerson(addClientMEntity.getContactPerson());
//			clientCreationEntityObj.setContactNo(addClientMEntity.getContactNo());
//			clientCreationEntityObj.setEmail(addClientMEntity.getEmail());
//			clientCreationEntityObj.setGst(addClientMEntity.getGst());
//			clientCreationEntityObj.setPan(addClientMEntity.getPan());
//			clientCreationEntityObj.setTypeOfService(addClientMEntity.getTypeOfService());
//			clientCreationEntityObj.setAccountManager(addClientMEntity.getAccountManager()); 
//
//			// SAVE THE USER TO THE DB ENTITY
//			try { 
//				clientCreationEntityObj=clientMasterService.save(clientCreationEntityObj);
//
//				if (clientCreationEntityObj != null) {
//					statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_REGISTERED_SUCCESSFULLY);
//					statusMap.put(Parameters.status, Constants.SUCCESS);
//					statusMap.put(Parameters.statusCode, "RU_200");
//					return new ResponseEntity<>(statusMap, HttpStatus.OK);
//				} else {
//					statusMap.put(Parameters.statusMsg, StatusMessageConstants.CLIENT_NOT_REGISTERED);
//					statusMap.put(Parameters.status, Constants.FAIL);
//					statusMap.put(Parameters.statusCode, "RU_301");
//					return new ResponseEntity<>(statusMap, HttpStatus.EXPECTATION_FAILED);
//				}
//			} catch (Exception ex) {
//				// logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 2. Failed to save user in DB response : " + ex.getMessage());
//				statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//				statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//				statusMap.put(Parameters.status, Constants.FAIL);
//				return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		} catch (Exception ex) {
//			// if (logger.isErrorLoggingEnabled()) {
//			// logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage());
//			// }
//			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}



	@GetMapping("/getAllClient")
	public ResponseEntity<?> getAllClient() { 
		try {
			Map<String,Object> statusMap=new HashMap<>();
			List<ClientMasterEntity> clientList = clientMasterService.findAll();

			statusMap.put("ClientMasterEntity",clientList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_FOUND_SUCCESSFULLY );
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	/**
	 * @apiNote ::: 
	 * @author Lata Bisht
	 * @since 26 September, 2024
	 * @param userId ::::: This is id of user from user_master
	 * @return :: returning list of clients
	 */
	@GetMapping("/getAllClientByManager")
	public ResponseEntity<?> getAllClientByManager(@RequestParam("id") Long userId) { 
		Map<String,Object> statusMap=new HashMap<>();

		try {

			UserMasterEntity userMasterEntity=userMasterService.findById(userId);
			if(userMasterEntity==null) {
				//return error
			}

			List<ClientMasterEntity> clientList=null;

			/** START :::::  FETCH ALL THE REGISTERED USER FROM USER_MASTER TABLE */
			List<UserMasterEntity> userMasterList= userMasterService.findAll();
			Map<Long, UserMasterEntity> userMasterMap = userMasterList.stream()
					.collect(Collectors.toMap(UserMasterEntity::getId, userMaster -> userMaster));
			/** END :::::  FETCH ALL THER REGISTERED USER FROM USER_MASTER TABLE */


			/** FETCH CLIENTS FOR ACCOUNT MANAGER */
			if(RoleConstants.ACCOUNT_MANAGER.equals(userMasterEntity.getRole())) {

				clientList = clientMasterService.findByAccountManagerId(userId.toString());

				// Assuming client.getAccountManager() returns a String, convert it to Long for comparison
				clientList.stream()
				.filter(client -> client.getAccountManagerId() != null)  // Ensure accountManager is not null
				.forEach(client -> {
					// Convert client.getAccountManager() (String) to Long
					Long accountManagerId = Long.parseLong(client.getAccountManagerId());
					// Check if userMasterMap contains the corresponding account manager ID
					if (userMasterMap.containsKey(accountManagerId)) {
						// Set the account manager's name from the UserMasterEntity
						String name="";
						UserMasterEntity userMasterEntity1=userMasterMap.get(accountManagerId);
						if(userMasterEntity1.getFirstName()!=null && !userMasterEntity1.getFirstName().isEmpty())
							name=userMasterEntity1.getFirstName();
						if(userMasterEntity1.getMiddleName()!=null && !userMasterEntity1.getMiddleName().isEmpty())
							name+=" "+userMasterEntity1.getMiddleName();
						if(userMasterEntity1.getLastName()!=null && !userMasterEntity1.getLastName().isEmpty())
							name+=" "+userMasterEntity1.getLastName();

						client.setAccountManagerId(name);
					}
				});
			}else if(RoleConstants.PROJECT_MANAGER.equals(userMasterEntity.getRole())) { 

				List<ProjectMasterEntity> projects=projectMasterService.findByWhere("o.projectManager='"+userId+"'");

				String clientIds = "(" + projects.stream()
				.map(project ->  project.getClientId()) // Assuming 'getId()' gets the client ID
				.collect(Collectors.joining(",")) + ")";

				String where="o.id in "+clientIds;
				clientList=clientMasterService.findByWhere(where);

				clientList.stream()
				.filter(client -> client.getAccountManagerId() != null)  // Ensure accountManager is not null
				.forEach(client -> {
					// Convert client.getAccountManager() (String) to Long
					Long accountManagerId = Long.parseLong(client.getAccountManagerId());
					// Check if userMasterMap contains the corresponding account manager ID
					if (userMasterMap.containsKey(accountManagerId)) {
						// Set the account manager's name from the UserMasterEntity
						String name="";
						UserMasterEntity userMasterEntity1=userMasterMap.get(accountManagerId);
						if(userMasterEntity1.getFirstName()!=null && !userMasterEntity1.getFirstName().isEmpty())
							name=userMasterEntity1.getFirstName();
						if(userMasterEntity1.getMiddleName()!=null && !userMasterEntity1.getMiddleName().isEmpty())
							name+=" "+userMasterEntity1.getMiddleName();
						if(userMasterEntity1.getLastName()!=null && !userMasterEntity1.getLastName().isEmpty())
							name+=" "+userMasterEntity1.getLastName();

						client.setAccountManagerId(name);
					}
				});
			}
				else if(RoleConstants.ADMINISTRATION.equals(userMasterEntity.getRole())) {
					clientList=clientMasterService.findAll();
				}else {
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "RU_404");
					return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
			// After updating, put the updated clientList into statusMap
			statusMap.put("clientMasterEntities", clientList);
			statusMap.put(Parameters.statusMsg,  StatusMessageConstants.CLIENT_FOUND_SUCCESSFULLY );
			statusMap.put(Parameters.status, Constants.SUCCESS);
			statusMap.put(Parameters.statusCode, "RU_200");
			return new ResponseEntity<>(statusMap,HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 

	@PutMapping("/updateClientMaster")
	public  ResponseEntity<?>updateClientMaster(@RequestBody CustomerDetailsDTO addClientMEntity ){


		Map<String,Object> statusMap=new HashMap<String,Object>(); 
		try {
			ClientMasterEntity clientEntity=clientMasterService.findById(addClientMEntity.getId());

			clientEntity.setClientName(addClientMEntity.getClientName()!=null?addClientMEntity.getClientName():clientEntity.getClientName());
			clientEntity.setAddress(addClientMEntity.getAddress()!=null?addClientMEntity. getAddress():clientEntity.getAddress());
			clientEntity.setCity(addClientMEntity.getCity()!=null?addClientMEntity.getCity():clientEntity.getCity());
			clientEntity.setState(addClientMEntity.getState()!=null?addClientMEntity.getState():clientEntity.getState());
			clientEntity.setPincode(addClientMEntity.getPinCode()!=null?addClientMEntity.getPinCode():clientEntity.getPincode());

			clientEntity.setDistrict(addClientMEntity.getDistrict()!=null?addClientMEntity.getDistrict():clientEntity.getDistrict());

			clientEntity.setContactPerson(addClientMEntity.getContactPerson()!=null? addClientMEntity.getContactPerson():clientEntity.getContactPerson());

			clientEntity.setContactNo(addClientMEntity.getContactNo()!=null? addClientMEntity.getContactNo():clientEntity.getContactNo());

			clientEntity.setEmail(addClientMEntity.getEmail()!=null?addClientMEntity. getEmail():clientEntity.getEmail());

			clientEntity.setGst(addClientMEntity.getGst()!=null?addClientMEntity.getGst():clientEntity.getGst());

			clientEntity.setPan(addClientMEntity.getPan()!=null?addClientMEntity.getPan():clientEntity.getPan());

			clientEntity.setTypeOfService(addClientMEntity.getTypeOfService()!=null?addClientMEntity.getTypeOfService():clientEntity.getTypeOfService());

			clientEntity.setAccountManagerId(addClientMEntity.getAccountManager()!=null?addClientMEntity.getAccountManager():clientEntity.getAccountManagerId());

			clientEntity.setClientName(addClientMEntity.getClientName()!=null?addClientMEntity.getClientName():clientEntity.getClientName());

			clientMasterService.update(clientEntity);

			statusMap.put("clientMasterEntity",clientEntity); 
			statusMap.put("status", "SUCCESS"); statusMap.put("statusCode", "RU_200");
			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");

			return new ResponseEntity<>(statusMap,HttpStatus.OK); 
		}catch(Exception e) {
			e.printStackTrace();

		}
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}




	@DeleteMapping("/deleteClientMaster")
	public ResponseEntity<?>  deleteClient(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();

		try {

			ClientMasterEntity clientMaster = clientMasterService.findById(id);
			if(clientMaster!=null) {
				clientMaster.setActive(0);
				clientMasterService.update(clientMaster);

				statusMap.put("status", "SUCCESS");
				statusMap.put("statusCode", "RME_200");
				statusMap.put("statusMessage", "SUCCESSFULLY DELETED");
				return new ResponseEntity<>(statusMap,HttpStatus.OK);


			}else {
				statusMap.put("status", "FAIL");
				statusMap.put("statusCode","RME_404"); 
				statusMap.put("statusMessage", "DATA NOT FOUND"); 
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED); } }
		catch(Exception ex) { 
			ex.printStackTrace(); 
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}