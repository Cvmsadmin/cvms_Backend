package org.ss.vendorapi.controller;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.config.EncryptSecurityUtil;
//import org.ss.vendorapi.entity.UserCreationEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.CustomerDetailsModel;
//import org.ss.vendorapi.modal.ResetPasswordDTO;v
import org.ss.vendorapi.repository.RoleMasterRepository;
import org.ss.vendorapi.service.DataValidationService;
import org.ss.vendorapi.service.EmailService;
import org.ss.vendorapi.service.UserCreationService;
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

	@Autowired
	private EmailService emailService;

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
	public ResponseEntity<?> userCreation(@RequestBody CustomerDetailsDTO userMasterMEntity, 
	        @RequestParam String id, 
	        HttpServletRequest request) {
	    ResponseEntity<?> responseEntity = null;
	    String methodName = request.getRequestURI();
	    Map<String, Object> statusMap = new HashMap<>();

	    try {
	        // Check if any required fields are empty
	        if (UtilValidate.isEmpty(userMasterMEntity.getEmail()) || 
	                UtilValidate.isEmpty(userMasterMEntity.getBaseLocation()) || 
	                UtilValidate.isEmpty(userMasterMEntity.getEmployeeId()) || 
	                UtilValidate.isEmpty(userMasterMEntity.getFirstName()) || 
	                UtilValidate.isEmpty(userMasterMEntity.getLastName()) ||  
	                UtilValidate.isEmpty(userMasterMEntity.getPhone()) || 
	                UtilValidate.isEmpty(userMasterMEntity.getPhysicalLocation())) {
	            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
	        }

	        // Check if user with the same email or phone already exists
	        if (userCreationService.existsByEmail(userMasterMEntity.getEmail()) || 
	                userCreationService.existsByPhone(userMasterMEntity.getPhone())) {
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
	        registerUserCreationEntityObj.setBaseLocation(userMasterMEntity.getBaseLocation());
	        registerUserCreationEntityObj.setEmail(userMasterMEntity.getEmail());
	        registerUserCreationEntityObj.setFirstName(userMasterMEntity.getFirstName());
	        registerUserCreationEntityObj.setLastName(userMasterMEntity.getLastName());
	        registerUserCreationEntityObj.setMiddleName(userMasterMEntity.getMiddleName());
	        registerUserCreationEntityObj.setEmployeeId(userMasterMEntity.getEmployeeId());
	        registerUserCreationEntityObj.setPhone(userMasterMEntity.getPhone());
	        registerUserCreationEntityObj.setPhysicalLocation(userMasterMEntity.getPhysicalLocation());
	        registerUserCreationEntityObj.setRole(id);
	        registerUserCreationEntityObj.setPassword(encodedPassword);
	        registerUserCreationEntityObj.setActive(1);

	        try {
	            // Save the user to the database
	            registerUserCreationEntityObj = userCreationService.save(registerUserCreationEntityObj);

	            if (registerUserCreationEntityObj != null) {
	                // Send an email with the user's details
	                String subject = "Welcome to Our Service";
	                String text = String.format("Dear %s,\n\nYour account has been created successfully.\n\nUsername: %s\nPassword: %s\n\nPlease keep your credentials safe.\n\nBest Regards,\nCVMSADMIN", 
	                        userMasterMEntity.getFirstName(), 
	                        userMasterMEntity.getEmail(), 
	                        generatedPassword);

	                try {
	                    emailService.sendEmail(userMasterMEntity.getEmail(), subject, text);
	                } catch (MessagingException ex) {
	                    // Handle the email sending failure
	                    statusMap.put(Parameters.statusMsg, "Email sending failed: " + ex.getMessage());
	                    return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
	                }

	                // Corrected message with clear formatting
	                String successMessage = StatusMessageConstants.USER_REGISTERED_SUCCESSFULLY 
	                        + ". Your credentials have been sent to your email.";
	                statusMap.put(Parameters.statusMsg, successMessage);
	                statusMap.put(Parameters.status, Constants.SUCCESS);
	                statusMap.put(Parameters.statusCode, "RU_200");
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
			List<UserMasterEntity> users = userCreationService.findAll();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 


	@PutMapping("/updateUserMaster") 
	public ResponseEntity<?>updateUserMaster(@RequestBody CustomerDetailsDTO userDto ){

		Map<String,Object> statusMap=new HashMap<String,Object>(); 
		try {
			UserMasterEntity userEntity=userCreationService.findById(userDto.getId());

			userEntity.setBaseLocation(userDto.getBaseLocation()!=null? userDto.getBaseLocation():userEntity.getBaseLocation());
			userEntity.setEmail(userDto.getEmail()!=null? userDto.getEmail():userEntity.getEmail());
			userEntity.setEmployeeId(userDto.getEmployeeId() != null ? userDto.getEmployeeId() : userEntity.getEmployeeId());
			userEntity.setFirstName(userDto.getFirstName() != null ? userDto.getFirstName() : userEntity.getFirstName());
			userEntity.setLastName(userDto.getLastName() != null ? userDto.getLastName() : userEntity.getLastName());
			userEntity.setMiddleName(userDto.getMiddleName() != null ? userDto.getMiddleName() : userEntity.getMiddleName());
			userEntity.setPassword(userDto.getPassword() != null ? userDto.getPassword() : userEntity.getPassword());
			userEntity.setUserId(userDto.getUserId() != null ? userDto.getUserId() : userEntity.getUserId());
			userEntity.setPhone(userDto.getPhone() != null ? userDto.getPhone() : userEntity.getPhone());
			userEntity.setPhysicalLocation(userDto.getPhysicalLocation() != null ? userDto.getPhysicalLocation() : userEntity.getPhysicalLocation());
			userEntity.setRole(userDto.getRole() != null ? userDto.getRole() : userEntity.getRole());


			userCreationService.update(userEntity);

			statusMap.put("userMasterEntity",userEntity);
			statusMap.put("status","SUCCESS"); statusMap.put("statusCode", "RU_200");
			statusMap.put("statusMessage", "SUCCESSFULLY UPDATED");

			return new ResponseEntity<>(statusMap,HttpStatus.OK); 
		}catch(Exception e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); 
	}


	@DeleteMapping("/deleteUser")
	public ResponseEntity<?> deleteUserMaster(@RequestParam Long id){
		Map<String, Object> statusMap=new HashMap<String, Object>();


		try {

			UserMasterEntity userMaster = userCreationService.findById(id);
			if(userMaster!=null) { userMaster.setActive(0);
			userCreationService.update(userMaster);

			statusMap.put("status", "SUCCESS"); statusMap.put("statusCode", "RME_200");
			statusMap.put("statusMessage", "SUCCESSFULLY DELETED"); 
			return new ResponseEntity<>(statusMap,HttpStatus.OK);


			}else { 
				statusMap.put("status", "FAIL"); 
				statusMap.put("statusCode","RME_404"); 
				statusMap.put("statusMessage", "DATA NOT FOUND"); 
				return new ResponseEntity<>(statusMap,HttpStatus.EXPECTATION_FAILED);
			}
		}
		catch(Exception ex) { 
			ex.printStackTrace(); 
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR); 

		} 
	} 

}