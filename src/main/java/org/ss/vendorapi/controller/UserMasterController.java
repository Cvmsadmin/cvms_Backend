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
import org.ss.vendorapi.advice.EncryptResponse;
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
	


	@EncryptResponse
	@PostMapping("/userCreation")
	public ResponseEntity<?> userCreation(@RequestBody CustomerDetailsDTO userMasterMEntity, 
	        @RequestParam String id, 
	        HttpServletRequest request) {
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
	                userCreationService.existsByPhone(userMasterMEntity.getPhone()) ||
	        	    userCreationService.existsByEmployeeId(userMasterMEntity.getEmployeeId())) {
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
	                String text = "<html><body>" +
	                        "<p>Dear " + (userMasterMEntity.getFirstName() != null ? userMasterMEntity.getFirstName() : "") + ",</p>" +
	                        "<p>Welcome to CVMS!</p>" +	                   
	                        "<p>Your account has been successfully created. Below are your login credentials:</p>" +
	                        "<p>Username: <b>" + userMasterMEntity.getEmail() + "</b></p>" +
	                        "<p>Password: <b>" + generatedPassword + "</b></p>" +
	                        "<p>Please keep your credentials safe and do not share them with anyone. For security reasons, we strongly recommend changing your password upon your first login.</p>" +
	                        "<p>Best Regards,</p><p><b>CVMS Admin</b></p>" +
	                        "</body></html>";
	                	
	                try {
	                    emailService.sendEmail(userMasterMEntity.getEmail(), subject, text);
	                } catch (MessagingException ex) {
	                    // Handle the email sending failure
	                    statusMap.put(Parameters.statusMsg, "Email sending failed: " + ex.getMessage());
	                    return new ResponseEntity<>(statusMap, HttpStatus.INTERNAL_SERVER_ERROR);
	                }

	                // Add username and password to the response
	                statusMap.put("username", userMasterMEntity.getEmail());
	                statusMap.put("password", generatedPassword);

	                // Success message
	                String successMessage = StatusMessageConstants.USER_REGISTERED_SUCCESSFULLY 
	                        + ". Your credentials have been sent to your email.";
	                statusMap.put(Parameters.statusMsg, successMessage);
	                statusMap.put(Parameters.status, Constants.SUCCESS);
	                statusMap.put(Parameters.statusCode, "RU_200");
	                
	                // Return response including username and password
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


	@EncryptResponse
	@GetMapping("/getAllUsers")
	public ResponseEntity<?> getAllUsers() {
		try {
			List<UserMasterEntity> users = userCreationService.findAll();
			return new ResponseEntity<>(users, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 


	@EncryptResponse
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


	@EncryptResponse
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