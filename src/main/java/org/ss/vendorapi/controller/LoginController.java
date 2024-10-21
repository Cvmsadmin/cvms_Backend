package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.ss.vendorapi.advice.EncryptResponse;
import org.ss.vendorapi.config.AESDecryptionService;
import org.ss.vendorapi.config.EncryptSecurityUtil;
import org.ss.vendorapi.entity.FeatureMasterEntity;
import org.ss.vendorapi.entity.ForgotPasswordVerifyRequest;
import org.ss.vendorapi.entity.LoginRequest;
import org.ss.vendorapi.entity.RefreshToken;
import org.ss.vendorapi.entity.ResetPasswordRequest;
import org.ss.vendorapi.entity.RoleResourceMasterEntity;
import org.ss.vendorapi.entity.UserMasterEntity;
import org.ss.vendorapi.modal.FeatureDTO;
import org.ss.vendorapi.modal.ForgotPasswordRequest;
import org.ss.vendorapi.modal.response.JwtResponse;
import org.ss.vendorapi.security.JwtHelper;
import org.ss.vendorapi.service.CustomUserDetailService;
import org.ss.vendorapi.service.EmailService;
import org.ss.vendorapi.service.FeatureMasterService;
import org.ss.vendorapi.service.OtpService;
import org.ss.vendorapi.service.RefreshTokenService;
import org.ss.vendorapi.service.ResourceMasterService;
import org.ss.vendorapi.service.RoleResourceMasterService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.UtilValidate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v2/api")
public class LoginController {
	private static final Class<?> CLASS_NAME = LoginController.class;
	//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_BILLING,CLASS_NAME.toString());

	@Autowired
	private Environment env;
	
	@Autowired
	private OtpService otpService;
	
	@Autowired
	private EmailService emailSenderService;

	@Autowired
	private UserMasterService userMasterService;

	@Autowired
	private AESDecryptionService aesDecryptionService;

	@Autowired
	private JwtHelper helper;

	@Autowired
	private EncryptSecurityUtil encryptUtil;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private CustomUserDetailService userDetailsService;

	@Autowired
	private RoleResourceMasterService roleResourceMasterService;

	@Autowired
	private FeatureMasterService featureMasterService;

	@Autowired
	private ResourceMasterService resourceMasterService;

	@Value("${spring.security.aes.key}")
	private String aesKey;

	@Autowired
	private ObjectMapper objMapper;

	private String secret_key;

	private String secret_iv;

	public static final String UTILITY_USER_ROLE = "UtilityUser";
	public static final String END_CONSUMER_ROLE = "EndConsumer";

	@EncryptResponse
	@PostMapping("/userLogin")
	public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {

		Map<String, Object> statusMap = new HashMap<>();
		String methodName = request.getRequestURI();
		// logger.logMethodStart(methodName);
		JwtResponse response = new JwtResponse();
		try {
			String email = loginRequest.getEmail();
			String password = loginRequest.getPassword();

			if (UtilValidate.isEmpty(email) || UtilValidate.isEmpty(password)) {
				return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING,
						HttpStatus.EXPECTATION_FAILED);
			}

			UserMasterEntity userMasterEntity= userMasterService.authenticateByEmail(email, encryptUtil.encode(password));

			if (userMasterEntity!=null && encryptUtil.encode(password).equals(userMasterEntity.getPassword())) {

				UserDetails userDetails = userDetailsService.loadUserByEmail(email);
				String token = this.helper.generateToken(userDetails);

				RefreshToken refreshToken = refreshTokenService.createRefreshTokenByEmail(userDetails.getUsername());

				/** @Author Lata Bisht */
				/** START ::: GET RESOURCES ::: 9, September August 2024 */

				List<RoleResourceMasterEntity> roleResourceMasterEntityList=roleResourceMasterService.findByRole(userMasterEntity.getRole());	

				String featureIdsString = roleResourceMasterEntityList.stream()
						.map(roleResourceMasterEntity -> "" + roleResourceMasterEntity.getFeatureId() + "")  // Enclose featureId in single quotes
						.collect(Collectors.joining(",", "(", ")"));  // Add parentheses around the joined string

				String where="o.id in "+featureIdsString;
				List<FeatureMasterEntity> features=featureMasterService.findByWhere(where);

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

				List<FeatureDTO> featureDTOList = features.stream()
				    .map(feature -> {
				        try {
				            // Convert FeatureMasterEntity to FeatureDTO
				            return objectMapper.convertValue(feature, FeatureDTO.class);
				        } catch (IllegalArgumentException e) {
				            // Handle conversion failure (log the error, return null, or other handling logic)
				            System.err.println("Failed to map entity to FeatureDTO: " + e.getMessage());
				            return null; // Handle the error (e.g., skip the entity)
				        }
				    })
				    .filter(Objects::nonNull) // Filter out any null values from failed conversions
				    .collect(Collectors.toList());
				
				response = JwtResponse.builder().urls(featureDTOList).accessToken(token).refreshToken(refreshToken.getToken())
						.id(userMasterEntity.getId().toString()).username(userDetails.getUsername().split("_")[0]).status(Constants.SUCCESS).build();
				
				/** END ::: GET RESOURCES ::: 9, September 2024 */

				statusMap.put(Parameters.status, Constants.SUCCESS);
				statusMap.put(Parameters.statusCode, "LOGIN_200");
				statusMap.put("response", response);

				return new ResponseEntity<>(statusMap, HttpStatus.OK);
			} else {
				statusMap.put(Parameters.statusMsg, "Please enter a valid email or password.");
				statusMap.put(Parameters.status, Constants.FAIL);
				statusMap.put(Parameters.statusCode, "LOGIN_201");
				return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception ex) {
			// if (logger.isErrorLoggingEnabled()) {
			// logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,
			// "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage());
			// }
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/adminLogin")
	public ResponseEntity<?> loginAdmin(@RequestParam(required = false) String userId,
			@RequestParam(required = false) String mobileNumber, @RequestParam String password,
			HttpServletRequest request) {
		Map<String, Object> statusMap = new HashMap<String, Object>();
		String methodName = request.getRequestURI();
		//		logger.logMethodStart(methodName);
		JwtResponse response = new JwtResponse();
		try {

			// String decrypString = aesDecryptionService.decrypt(aesKey,
			// jwtRequest.getLoginDetails());
			// logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "decrypted loginDetails :
			// {}" + decrypString);
			// //RegisterUserEntity objUser = objMapper.readValue(decrypString,
			// RegisterUserEntity.class);
			// logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "LoginDetails Object :" +
			// objUser);

			if (userId != null) {
				if (UtilValidate.isEmpty(userId) || UtilValidate.isEmpty(password)) {
					return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING,
							HttpStatus.EXPECTATION_FAILED);
				}
				if (userMasterService.authenticateByUserId(userId, encryptUtil.encode(password), UTILITY_USER_ROLE)) {

					UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
					String token = this.helper.generateToken(userDetails);
					RefreshToken refreshToken = refreshTokenService
							.createRefreshTokenByUserId(userDetails.getUsername());
					response = JwtResponse.builder().accessToken(token).refreshToken(refreshToken.getToken())
							.username(userDetails.getUsername().split("_")[0]).status(Constants.SUCCESS).build();
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "LOGIN_200");
					statusMap.put("response", response);
					return new ResponseEntity<>(statusMap, HttpStatus.OK);
				} else {
					statusMap.put(Parameters.statusMsg, "Please enter a valid login ID, mobile number, or password.");
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "LOGIN_201");
					return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);

				}
			} else {
				if (UtilValidate.isEmpty(mobileNumber) || UtilValidate.isEmpty(password)) {
					return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING,
							HttpStatus.EXPECTATION_FAILED);
				}
				if (userMasterService.authenticateByMobileNumber(mobileNumber, encryptUtil.encode(password),
						UTILITY_USER_ROLE)) {

					UserDetails userDetails = userDetailsService.loadUserByMobileNumber(mobileNumber);
					String token = this.helper.generateToken(userDetails);
					RefreshToken refreshToken = refreshTokenService
							.createRefreshTokenByMobileNumber(userDetails.getUsername());
					response = JwtResponse.builder().accessToken(token).refreshToken(refreshToken.getToken())
							.username(userDetails.getUsername().split("_")[0]).status(Constants.SUCCESS).build();
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "LOGIN_200");
					statusMap.put("response", response);
					return new ResponseEntity<>(statusMap, HttpStatus.OK);
				} else {
					statusMap.put(Parameters.statusMsg, "Please enter a valid login ID, mobile number, or password.");
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "LOGIN_201");
					return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);
				}
			}
		} catch (Exception ex) {
			//			if (logger.isErrorLoggingEnabled()) {
			//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage() );
			//			}
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@EncryptResponse
	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
	    String email = forgotPasswordRequest.getEmail();
	    String phone = forgotPasswordRequest.getPhone();

	    if (UtilValidate.isEmpty(email) || UtilValidate.isEmpty(phone)) {
	        return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING,
	                HttpStatus.EXPECTATION_FAILED);
	    }

	    UserMasterEntity user = userMasterService.findByEmailAndPhone(email, phone);

	    if (user == null) {
	        return CommonUtils.createResponse(Constants.FAIL, "Invalid email or phone number", HttpStatus.BAD_REQUEST);
	    }

	    // Generate OTP and store with expiration (e.g., 2 minutes)
	    String otp = otpService.generateOtpForEmail(email);

	    try {
	        // Send OTP to the user's email
	        emailSenderService.sendEmail(email, "OTP for Password Reset", 
	                                     "Your OTP for password reset is: " + otp);

	        // If no exception, OTP email was successfully sent
	        return CommonUtils.createResponse(Constants.SUCCESS, "OTP sent to your email", HttpStatus.OK);
	    } catch (Exception e) {
	        // Handle exception if email sending fails
	        return CommonUtils.createResponse(Constants.FAIL, "Failed to send OTP. Please try again later.",
	                HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	@EncryptResponse
    @PostMapping("/verifyOtp")
    public ResponseEntity<?> verifyOtp(@RequestBody ForgotPasswordVerifyRequest forgotPasswordVerifyRequest) {
        String email = forgotPasswordVerifyRequest.getEmail();
        String otp = forgotPasswordVerifyRequest.getOtp();
        String newPassword = forgotPasswordVerifyRequest.getNewPassword();
        String confirmPassword = forgotPasswordVerifyRequest.getConfirmPassword();

        if (UtilValidate.isEmpty(email) || UtilValidate.isEmpty(otp) || UtilValidate.isEmpty(newPassword)
                || UtilValidate.isEmpty(confirmPassword)) {
            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING,
                    HttpStatus.EXPECTATION_FAILED);
        }

        // Validate OTP
        boolean isOtpValid = otpService.validateOtp(email, otp);
        if (!isOtpValid) {
            return CommonUtils.createResponse(Constants.FAIL, "Invalid or expired OTP", HttpStatus.BAD_REQUEST);
        }

        // Validate new password
        if (!newPassword.equals(confirmPassword)) {
            return CommonUtils.createResponse(Constants.FAIL, "Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(newPassword)) {
            return CommonUtils.createResponse(Constants.FAIL, "Password must be at least 8 characters long and contain a mix of letters, numbers, and special characters.", HttpStatus.BAD_REQUEST);
        }

        // Update password in the database
        UserMasterEntity user = userMasterService.findByEmail(email);
        if (user != null) {
            user.setPassword(encryptUtil.encode(newPassword));
            userMasterService.save(user);
        }

        // Invalidate the OTP after use
        otpService.invalidateOtp(email);

        return CommonUtils.createResponse(Constants.SUCCESS, "Password updated successfully", HttpStatus.OK);
    }

    // Helper method to validate password strength
    private boolean isValidPassword(String password) {
        // Example validation: Password must contain at least 8 characters, one letter, one number, and one special character.
        String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }
    
    
    @EncryptResponse
    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();
        String oldPassword = resetPasswordRequest.getOldPassword();
        String newPassword = resetPasswordRequest.getNewPassword();
        String confirmPassword = resetPasswordRequest.getConfirmPassword();

        if (UtilValidate.isEmpty(email) || UtilValidate.isEmpty(oldPassword) || 
            UtilValidate.isEmpty(newPassword) || UtilValidate.isEmpty(confirmPassword)) {
            return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING,
                    HttpStatus.EXPECTATION_FAILED);
        }

        // Fetch user by email
        UserMasterEntity user = userMasterService.findByEmail(email);
        if (user == null) {
            return CommonUtils.createResponse(Constants.FAIL, "User not found", HttpStatus.BAD_REQUEST);
        }

        // Verify old password
        if (!encryptUtil.encode(oldPassword).equals(user.getPassword())) {
            return CommonUtils.createResponse(Constants.FAIL, "Old password is incorrect", HttpStatus.BAD_REQUEST);
        }

        // Validate new password
        if (!newPassword.equals(confirmPassword)) {
            return CommonUtils.createResponse(Constants.FAIL, "Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(newPassword)) {
            return CommonUtils.createResponse(Constants.FAIL, 
                "Password must be at least 8 characters long and contain a mix of letters, numbers, and special characters.",
                HttpStatus.BAD_REQUEST);
        }

        // Update password in the database
        user.setPassword(encryptUtil.encode(newPassword));
        userMasterService.save(user);

        return CommonUtils.createResponse(Constants.SUCCESS, "Password updated successfully", HttpStatus.OK);
    }

    // Helper method to validate password strength
    private boolean isValidPassword1(String password) {
        String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return password.matches(passwordPattern);
    }

}
