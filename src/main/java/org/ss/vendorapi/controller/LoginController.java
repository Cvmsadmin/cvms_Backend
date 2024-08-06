package org.ss.vendorapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.ss.vendorapi.config.AESDecryption
import org.ss.vendorapi.config.AESDecryptionService;
//import org.ss.vendorapi.entity.LoginRequest;Service;
import org.ss.vendorapi.config.EncryptSecurityUtil;
import org.ss.vendorapi.entity.LoginRequest;
import org.ss.vendorapi.entity.RefreshToken;
import org.ss.vendorapi.modal.response.JwtResponse;
import org.ss.vendorapi.security.JwtHelper;
import org.ss.vendorapi.service.CustomUserDetailService;
import org.ss.vendorapi.service.RefreshTokenService;
import org.ss.vendorapi.service.UserMasterService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
import org.ss.vendorapi.util.UtilValidate;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/v2/api")
public class LoginController{

	private static final Class<?> CLASS_NAME = LoginController.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_BILLING,CLASS_NAME.toString());

	@Autowired private Environment env;
	
	@Autowired private UserMasterService registerUserService;
	
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

	@Value("${spring.security.aes.key}")
	private String aesKey;

	@Autowired
	private ObjectMapper objMapper;


	public static final String UTILITY_USER_ROLE="UtilityUser";
	public static final String END_CONSUMER_ROLE="EndConsumer";


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
                return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
            }

            if (registerUserService.authenticateByEmail(email, encryptUtil.encode(password), END_CONSUMER_ROLE)) {

                UserDetails userDetails = userDetailsService.loadUserByEmail(email);
                String token = this.helper.generateToken(userDetails);
                RefreshToken refreshToken = refreshTokenService.createRefreshTokenByEmail(userDetails.getUsername());
                response = JwtResponse.builder().accessToken(token).refreshToken(refreshToken.getToken())
                        .username(userDetails.getUsername().split("_")[0]).status(Constants.SUCCESS).build();
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
	public ResponseEntity<?> loginAdmin(@RequestParam (required = false) String userId,
			@RequestParam(required = false) String mobileNumber,@RequestParam String password , HttpServletRequest request)
	{
		Map<String,Object> statusMap= new HashMap<String,Object>();
		String methodName = request.getRequestURI();
//		logger.logMethodStart(methodName);
		JwtResponse response = new JwtResponse();
		try
		{

			//String decrypString = aesDecryptionService.decrypt(aesKey, jwtRequest.getLoginDetails());
			//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "decrypted loginDetails : {}" + decrypString);
			//			//RegisterUserEntity objUser = objMapper.readValue(decrypString, RegisterUserEntity.class);
			//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "LoginDetails Object :" + objUser);

			if(userId!=null) 
			{
				if(UtilValidate.isEmpty(userId)||  UtilValidate.isEmpty(password))
				{
					return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
				}
				if(registerUserService.authenticateByUserId(userId, encryptUtil.encode(password), UTILITY_USER_ROLE)) 
				{

					UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
					String token = this.helper.generateToken(userDetails);
					RefreshToken refreshToken = refreshTokenService.createRefreshTokenByUserId(userDetails.getUsername());
					response = JwtResponse.builder().accessToken(token).refreshToken(refreshToken.getToken()).username(userDetails.getUsername().split("_")[0]).status(Constants.SUCCESS).build();
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "LOGIN_200");
					statusMap.put("response", response);
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}
				else 
				{
					statusMap.put(Parameters.statusMsg, "Please enter a valid login ID, mobile number, or password.");
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "LOGIN_201");
					return new ResponseEntity<>(statusMap,HttpStatus.BAD_REQUEST); 

				}
			}
			else  
			{
				if(UtilValidate.isEmpty(mobileNumber)||  UtilValidate.isEmpty(password))
				{
					return CommonUtils.createResponse(Constants.FAIL, Constants.PARAMETERS_MISSING, HttpStatus.EXPECTATION_FAILED);
				}
				if(registerUserService.authenticateByMobileNumber(mobileNumber, encryptUtil.encode(password), UTILITY_USER_ROLE )) 
				{

					UserDetails userDetails = userDetailsService.loadUserByMobileNumber(mobileNumber);
					String token = this.helper.generateToken(userDetails);
					RefreshToken refreshToken = refreshTokenService.createRefreshTokenByMobileNumber(userDetails.getUsername());
					response = JwtResponse.builder().accessToken(token).refreshToken(refreshToken.getToken()).username(userDetails.getUsername().split("_")[0]).status(Constants.SUCCESS).build();
					statusMap.put(Parameters.status, Constants.SUCCESS);
					statusMap.put(Parameters.statusCode, "LOGIN_200");
					statusMap.put("response", response);
					return new ResponseEntity<>(statusMap,HttpStatus.OK);
				}
				else 
				{
					statusMap.put(Parameters.statusMsg, "Please enter a valid login ID, mobile number, or password.");
					statusMap.put(Parameters.status, Constants.FAIL);
					statusMap.put(Parameters.statusCode, "LOGIN_201");
					return new ResponseEntity<>(statusMap,HttpStatus.BAD_REQUEST); 
				}
			}
		}
		catch (Exception ex) 
		{
//			if (logger.isErrorLoggingEnabled()) {
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName, "@@@@ 1. Exception when getConsumerDetails @@@ " + ex.getMessage() );
//			}
			return CommonUtils.createResponse(Constants.FAIL, ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}


	}
}