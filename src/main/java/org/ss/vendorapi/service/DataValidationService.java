package org.ss.vendorapi.service;

import org.springframework.http.ResponseEntity;

/**
 * {@summary This service is used to check the mobile number and email is valid or not. }
 * @since 30 April, 2024
 * @author Anukriti Gusain
 */

public interface DataValidationService {
	
	public ResponseEntity<?> checkValidMobileNumber(String mobileNumber, String methodName, String module);
	public ResponseEntity<?> checkValidEmailId(String email, String methodName, String module);

}
