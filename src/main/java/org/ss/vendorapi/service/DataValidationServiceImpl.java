package org.ss.vendorapi.service;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;

@Service
public class DataValidationServiceImpl implements DataValidationService{
	
//	private static UPPCLLogger logger;

	private void setDataValidationServiceImpl(String module) {
//	    logger = UPPCLLogger.getInstance(module, DataValidationServiceImpl.class.toString());
	}

	@Override
	public ResponseEntity<?> checkValidMobileNumber(String mobileNumber, String methodName, String module) {
		
		setDataValidationServiceImpl(module); // set module name dynamically
		
		Pattern pattern = Pattern.compile("^([0-9]{10})$");
	    Matcher matcher = pattern.matcher(mobileNumber);
	    
	    if (!matcher.matches()) {               
//	        logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Please Enter valid  Mobile No.   : : " + matcher);
	        Map<String, Object> statusMap = new HashMap<>();
	        statusMap.put(Parameters.statusMsg, "please enter valid mobile number.");
	        statusMap.put(Parameters.status, Constants.FAIL);
	        statusMap.put(Parameters.statusCode, "RU_100");
	        
	        return new ResponseEntity<>(statusMap, HttpStatus.BAD_REQUEST);   // in case of invalid mobile number              
	    }
	    
	    return null; // in case of valid mobile number
	}

	@Override
	public ResponseEntity<?> checkValidEmailId(String email, String methodName, String module) {

		setDataValidationServiceImpl(module); // set module name dynamically
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if(!email.matches(emailRegex)) {
        	 Map<String, Object> statusMap = new HashMap<>();
//        	logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName, "@@@@ 1. Please Enter valid  email id.   : : ");
			statusMap.put(Parameters.statusMsg, "please enter valid email id.");
			statusMap.put(Parameters.status, Constants.FAIL);
			statusMap.put(Parameters.statusCode, "RU_102");
			return new ResponseEntity<>(statusMap,HttpStatus.BAD_REQUEST); 
        }
		return null;
	}

	

}
