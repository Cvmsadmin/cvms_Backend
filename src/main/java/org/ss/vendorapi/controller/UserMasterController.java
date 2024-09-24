package org.ss.vendorapi.controller;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.config.EncryptSecurityUtil;
import org.ss.vendorapi.entity.RoleMasterEntity;
//import org.ss.vendorapi.entity.UserCreationEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.CustomerDetailsModel;
//import org.ss.vendorapi.modal.ResetPasswordDTO;v
import org.ss.vendorapi.repository.RoleMasterRepository;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.UserCreationService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.StatusMessageConstants;
import org.ss.vendorapi.util.UtilValidate;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("v2/api")
public class UserMasterController{ 

	private static final Class<?> CLASS_NAME = UserMasterController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_REGISTRATION,CLASS_NAME.toString());

	@Autowired 
	private Environment env;
	
//	@Autowired
//	private ResetPasswordDTO resetPasswordDTO;
	

	//@Autowired private UserMasterService userMasterService;
	@Autowired 
	private UserCreationService userCreationService;
	
	@Autowired 
	private RoleMasterRepository roleMasterRepository;
	
	@Autowired
	private EncryptSecurityUtil encryptUtil;

	@Autowired
	private DataValidationService dataValidationService;
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;


	public CustomerDetailsModel customerDetailsDTO = new CustomerDetailsModel();

	public CustomerDetailsModel getCustomerDetailsDTO() {
		return customerDetailsDTO;
	}
	public void setCustomerDetailsDTO(CustomerDetailsModel customerDetailsDTO) {
		this.customerDetailsDTO = customerDetailsDTO;
	}

	public static final String UTILITY_USER_ROLE="UtilityUser";
	public static final String END_CONSUMER_ROLE="EndConsumer";
	
	
	
	@PostMapping("/resetPassword")
	public ResponseEntity<?> resetPassword(@RequestParam String email, 
	                                        @RequestParam String oldPassword,
	                                        @RequestParam String newPassword,
	                                        @RequestParam String confirmPassword) {
	    Map<String, Object> statusMap = new HashMap<>();

	    try {
	        Optional<UserMasterEntity> optionalUser = userCreationService.findByEmail(email);
	        if (!optionalUser.isPresent()) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.USER_NOT_FOUND);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_404");
	            return new ResponseEntity<>(statusMap, HttpStatus.NOT_FOUND);
	        }

	        UserMasterEntity user = optionalUser.get();
	        
	        // Log the user's stored password for debugging (consider security implications)
	        System.out.println("Stored password (hashed): " + user.getPassword());

	        // Verify old password
	        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.INVALID_OLD_PASSWORD);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_403");
	            return new ResponseEntity<>(statusMap, HttpStatus.FORBIDDEN);
	        }

	        // Check if new password and confirm password match
	        if (!newPassword.equals(confirmPassword)) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.PASSWORDS_DO_NOT_MATCH);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_400");
	            return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
	        }

	        // Encode and save the new password
	        String encodedPassword = passwordEncoder.encode(newPassword);
	        user.setPassword(encodedPassword);
	        userCreationService.save(user); // Save the updated user

	        statusMap.put(Parameters.statusMsg, StatusMessageConstants.PASSWORD_RESET_SUCCESS);
	        statusMap.put(Parameters.status, Constants.SUCCESS);
	        return new ResponseEntity<>(statusMap, HttpStatus.OK);

	    } catch (Exception ex) {
	        statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
	        statusMap.put(Parameters.status, Constants.FAIL);
	        statusMap.put(Parameters.statusCode, Constants.SVD_USR);
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	
	
	@PostMapping("/userCreation")
	public ResponseEntity<?> userCreation(@RequestBody CustomerDetailsDTO UserMasterMEntity, @RequestParam String id, HttpServletRequest request) {

	    ResponseEntity<?> responseEntity = null;
	    String methodName = request.getRequestURI();
	    Map<String, Object> statusMap = new HashMap<>();

	    try {
	        // Check if any required fields are empty
	    	
	    	
	        if (UtilValidate.isEmpty(UserMasterMEntity.getEmail()) || 
	            UtilValidate.isEmpty(UserMasterMEntity.getBaseLocation()) || 
	            UtilValidate.isEmpty(UserMasterMEntity.getEmployeeId()) || 
	            UtilValidate.isEmpty(UserMasterMEntity.getFirstName()) || 
	            UtilValidate.isEmpty(UserMasterMEntity.getLastName()) ||  
	            UtilValidate.isEmpty(UserMasterMEntity.getPhone()) || 
	            UtilValidate.isEmpty(UserMasterMEntity.getPhysicalLocation())) {
//	            UtilValidate.isEmpty(UserMasterMEntity.getRole())) 
	            
	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        // Check if user with the same email or phone already exists
	        if (userCreationService.existsByEmail(UserMasterMEntity.getEmail()) || 
	            userCreationService.existsByPhone(UserMasterMEntity.getPhone())) {
	            statusMap.put(Parameters.statusMsg, StatusMessageConstants.USER_ALREADY_REGISTERED);
	            statusMap.put(Parameters.status, Constants.FAIL);
	            statusMap.put(Parameters.statusCode, "RU_302");
	            return new ResponseEntity<>(statusMap, HttpStatus.CONFLICT);
	        }

	        // Generate a random password
	        String generatedPassword = generateRandomPassword();

	        // Encode the password
	        String encodedPassword = passwordEncoder.encode(generatedPassword);

	        // Create a new UserMasterEntity object
	        UserMasterEntity registerUserCreationEntityObj = new UserMasterEntity();
	        registerUserCreationEntityObj.setBaseLocation(UserMasterMEntity.getBaseLocation());
	        registerUserCreationEntityObj.setEmail(UserMasterMEntity.getEmail());
	        registerUserCreationEntityObj.setFirstName(UserMasterMEntity.getFirstName());
	        registerUserCreationEntityObj.setLastName(UserMasterMEntity.getLastName());
	        registerUserCreationEntityObj.setMiddleName(UserMasterMEntity.getMiddleName());
	        registerUserCreationEntityObj.setEmployeeId(UserMasterMEntity.getEmployeeId());
	        registerUserCreationEntityObj.setPhone(UserMasterMEntity.getPhone());
	        registerUserCreationEntityObj.setPhysicalLocation(UserMasterMEntity.getPhysicalLocation());
	        registerUserCreationEntityObj.setRole(id);
	        registerUserCreationEntityObj.setPassword(encodedPassword);

	        try {
	            // Save the user to the database
	            registerUserCreationEntityObj = userCreationService.save(registerUserCreationEntityObj);

	            if (registerUserCreationEntityObj != null) {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.USER_REGISTERED_SUCCESSFULLY);
	                statusMap.put(Parameters.status, Constants.SUCCESS);
	                statusMap.put(Parameters.statusCode, "RU_200");
	                statusMap.put("generatedPassword", generatedPassword); // Include the generated password in the response
	                statusMap.put("email", UserMasterMEntity.getEmail()); // Include the email in the response
	                return new ResponseEntity<>(statusMap, HttpStatus.OK);
	            } else {
	                statusMap.put(Parameters.statusMsg, StatusMessageConstants.USER_NOT_REGISTERED);
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
	    } catch (Exception ex) {
	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	// Method to generate a random password
	private String generateRandomPassword() {
	    int length = 8; // Password length
	    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$&-";
	    SecureRandom random = new SecureRandom();
	    StringBuilder sb = new StringBuilder(length);
	    for (int i = 0; i < length; i++) {
	        int index = random.nextInt(characters.length());
	        sb.append(characters.charAt(index));
	    }
	    return sb.toString();
	}

	
	
			
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
	    try {
	        List<UserMasterEntity> users = userCreationService.getAllUsers();
	        return new ResponseEntity<>(users, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
}
	
	
	
	


//	@PostMapping("/checkUserExistInPortal")
//	public ResponseEntity<?> checkUserInPortal(@RequestBody CustomerDetailsDTO customerDetailsDTO, HttpServletRequest request )
//	{
//		ResponseEntity<?> responseEntity;
//		boolean checkUserInportal=false;
//		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);	
//		Map<String,Object> statusMap= new HashMap<String,Object>();
//		try 
//		{
//
//			if(UtilValidate.isEmpty(customerDetailsDTO.getMobileNo()) ||  UtilValidate.isEmpty(customerDetailsDTO.getEmail()))
//			{
//				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//			}
//			/* START ::: CHECK VALID MOBILE ::: THIS METHOD WILL CHECK VALID MOBILE NUMBER AND RETURN NULL IN CASE OF VALID MOBILE NUMBER  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID MOBILE NUMBER/
//
//			responseEntity=dataValidationService.checkValidMobileNumber(customerDetailsDTO.getMobileNo(), methodName, UPPCLLogger.MODULE_REGISTRATION);
//			if(responseEntity!=null)
//				return responseEntity;
//
//			/** END ::: CHECK VALID MOBILE */
//
//			/** START ::: CHECK VALID EMAIL ::: THIS METHOD WILL CHECK VALID EMAIL AND RETURN NULL IN CASE OF VALID EMAIL ID  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID EMAIL ID */
//
//			responseEntity=dataValidationService.checkValidEmailId(customerDetailsDTO.getEmail(), methodName, UPPCLLogger.MODULE_REGISTRATION);
//			if(responseEntity!=null)
//				return responseEntity;
//
//			/** END ::: CHECK VALID EMAIl */
//
//			UserMasterEntity userMasterEntity=new UserMasterEntity();
//			userMasterEntity.setPhone(customerDetailsDTO.getMobileNo());
//			userMasterEntity.setEmail(customerDetailsDTO.getEmail());
//			//checking user Exist or Not in Portal
//			checkUserInportal=userMasterService.checkUserInPortal(userMasterEntity);
//			if(checkUserInportal) 
//			{
//				statusMap.put(Parameters.statusMsg, "user already Registerd.");
//				statusMap.put(Parameters.status, Constants.SUCCESS);
//				statusMap.put(Parameters.statusCode, "RU_101");
//				return new ResponseEntity<>(statusMap,HttpStatus.OK);
//			}
//			else 
//			{
//				statusMap.put(Parameters.statusMsg, "please Register user.");
//				statusMap.put(Parameters.status, Constants.FAIL);
//				statusMap.put(Parameters.statusCode, "RU_102");
//				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
//			}
//
//
//		}
//		catch (Exception ex)
//		{
//			ex.printStackTrace();
//			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}

//	@PostMapping("/registerUser")
//	public ResponseEntity<?> registerUser(@RequestBody CustomerDetailsDTO registeruserEntity,HttpServletRequest request){
//
//
//		ResponseEntity<?> responseEntity=null;
//		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);	
//
//		Map<String,Object> statusMap= new HashMap<String,Object>();
//
//		UserMasterEntity registerUserEntityObj=null;
//		try{
//
//
//			if(UtilValidate.isEmpty(registeruserEntity.getName()) ||  UtilValidate.isEmpty(registeruserEntity.getDateOfBirth())||UtilValidate.isEmpty(registeruserEntity.getMobileNo())
//					||  UtilValidate.isEmpty(registeruserEntity.getEmail())){
//				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//			}	
//
//			/* START ::: CHECK VALID MOBILE ::: THIS METHOD WILL CHECK VALID MOBILE NUMBER AND RETURN NULL IN CASE OF VALID MOBILE NUMBER  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID MOBILE NUMBER/
//
//			responseEntity=dataValidationService.checkValidMobileNumber(registeruserEntity.getMobileNo(), methodName, UPPCLLogger.MODULE_REGISTRATION);
//			if(responseEntity!=null)
//				return responseEntity;
//
//			/** END ::: CHECK VALID MOBILE */
//
//			/** START ::: CHECK VALID EMAIL ::: THIS METHOD WILL CHECK VALID EMAIL AND RETURN NULL IN CASE OF VALID EMAIL ID  OTHERWISE RETURN MESSAGE ENTITY FOR INVALID EMAIL ID */
//
//			responseEntity=dataValidationService.checkValidEmailId(registeruserEntity.getEmail(), methodName, UPPCLLogger.MODULE_REGISTRATION);
//			if(responseEntity!=null)
//				return responseEntity;
//
//			/** END ::: CHECK VALID EMAIl */
//
//
//			registerUserEntityObj=new UserMasterEntity();
//			registerUserEntityObj.setPhone(registeruserEntity.getMobileNo());
//			registerUserEntityObj.setEmail(registeruserEntity.getEmail());
//			registerUserEntityObj.setFirstName(registeruserEntity.getName());
//			registerUserEntityObj.setRole(END_CONSUMER_ROLE);
//
//			String userId = userMasterService.generateUserID(registeruserEntity.getMobileNo(), methodName, UPPCLLogger.MODULE_REGISTRATION);
//			registerUserEntityObj.setUserId(userId);
//
//			/* GENERATE RANDOM PASSWORD */
//			RandomPasswordUtil randpwd=new RandomPasswordUtil(8);
//			String password=randpwd.getNewPassword();
//			String encryptUserPassword = encryptUtil.encode(password);
//			registerUserEntityObj.setPassword(encryptUserPassword);
//
//
//			//Boolean userExist =  userMasterService.checkUserInPortal(registerUserEntityObj);
//
//			//			if(userExist) { /* IF USER ALREADY EXIST IN PORTAL */
//			//				
//			//				statusMap.put(Parameters.statusMsg, "user already Registerd.");
//			//				statusMap.put(Parameters.status, Constants.SUCCESS);
//			//				statusMap.put(Parameters.statusCode, "RU_101");
//			//				return new ResponseEntity<>(statusMap,HttpStatus.OK);
//			//				
//			//				
//			//			}else {
//			try
//			{
//				/* SAVE THE USER TO THE DB ENTITY */
//				userMasterService.saveUser(registerUserEntityObj);
//				statusMap.put(Parameters.statusMsg, env.getProperty("user.registered.successfully"));
//				statusMap.put(Parameters.status, Constants.SUCCESS);
//				statusMap.put(Parameters.statusCode, "RU_200");
//				statusMap.put("userId", userId);
//				statusMap.put("Password", password);
//				return new ResponseEntity<>(statusMap,HttpStatus.OK);
//			}
//			catch(Exception ex)
//			{
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 2. Failed to save user in DB response : "+ex.getMessage());
//				statusMap.put(Parameters.statusMsg, env.getProperty("common.api.error"));
//				statusMap.put(Parameters.statusCode, Constants.SVD_USR);
//				statusMap.put(Parameters.status, Constants.FAIL);
//				return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);	
//			}
//			//}
//		}
//		catch(Exception ex)
//		{
//			if (logger.isErrorLoggingEnabled()) {
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage() );
//			}
//			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}

//	@PostMapping("/resetPassword")
//	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDTO resetPasswordDTO, HttpServletRequest request) {
//	    ResponseEntity<?> responseEntity = null;
//	    Map<String, Object> statusMap = new HashMap<>();
//
//	    try {
//	        // Validate input fields
//	        if (UtilValidate.isEmpty(resetPasswordDTO.getEmail()) || 
//	            UtilValidate.isEmpty(resetPasswordDTO.getOldPassword()) || 
//	            UtilValidate.isEmpty(resetPasswordDTO.getNewPassword()) || 
//	            UtilValidate.isEmpty(resetPasswordDTO.getConfirmPassword())) {
//	                
//	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
//	        }
//
//	        // Check if new password and confirm password match
//	        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getConfirmPassword())) {
//	            return CommonUtils.createResponse(Constants.FAIL, "New password and confirmation do not match", HttpStatus.BAD_REQUEST);
//	        }
//
//	        // Retrieve user by email
//	        UserMasterEntity user = userCreationService.findByEmail(resetPasswordDTO.getEmail());
//	        if (user == null) {
//	            return CommonUtils.createResponse(Constants.FAIL, "User not found", HttpStatus.NOT_FOUND);
//	        }
//
//	        // Verify the old password
//	        if (!passwordEncoder.matches(resetPasswordDTO.getOldPassword(), user.getPassword())) {
//	            return CommonUtils.createResponse(Constants.FAIL, "Old password is incorrect", HttpStatus.UNAUTHORIZED);
//	        }
//
//	        // Encode the new password
//	        String encodedNewPassword = passwordEncoder.encode(resetPasswordDTO.getNewPassword());
//	        user.setPassword(encodedNewPassword);
//
//	        // Save the updated user entity
//	        userCreationService.save(user);
//
//	        statusMap.put(Parameters.statusMsg, "Password updated successfully");
//	        statusMap.put(Parameters.status, Constants.SUCCESS);
//	        statusMap.put(Parameters.statusCode, "RU_200");
//	        return new ResponseEntity<>(statusMap, HttpStatus.OK);
//
//	    } catch (Exception ex) {
//	        return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}

	
