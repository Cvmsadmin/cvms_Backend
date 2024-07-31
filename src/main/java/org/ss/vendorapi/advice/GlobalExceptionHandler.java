package org.ss.vendorapi.advice;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
import org.ss.vendorapi.exceptions.TokenRefreshException;
import org.ss.vendorapi.exceptions.UserNotFoundException;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.Parameters;
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<?> fileNotFoundExceptionHandler(FileNotFoundException ex){
	
		HashMap<String, Object> statusMap = new HashMap<>();
		
		statusMap.put(Parameters.statusMsg, ex.getMessage());
		statusMap.put(Parameters.status, Constants.FAIL);
		return new ResponseEntity<>(statusMap,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RequestNotFoundException.class)
	public ResponseEntity<?> requestNotFoundExceptionHandler(RequestNotFoundException ex){
		
		HashMap<String, Object> statusMap = new HashMap<>();
		
		statusMap.put(Parameters.statusMsg, ex.getMessage());
		statusMap.put(Parameters.status, Constants.FAIL);
		return new ResponseEntity<>(statusMap,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> badCredentialsExceptionHandler() {
    	
    	Map<String,Object> statusMap = new HashMap<>();
    	
    	statusMap.put(Parameters.statusMsg, "Credentials Invalid !!");
    	statusMap.put(Parameters.status, Constants.FAIL);
        return new ResponseEntity<Object>(statusMap, HttpStatus.UNAUTHORIZED);
    }
    
	@EncryptResponse
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundExceptionHandler(UserNotFoundException ex) {
    	
    	Map<String,Object> statusMap = new HashMap<>();
    	statusMap.put(Parameters.statusMsg, ex.getMessage());
    	statusMap.put(Parameters.status, Constants.FAIL);
        return new ResponseEntity<Object>(statusMap, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorMessage handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
      return new ErrorMessage(
          HttpStatus.FORBIDDEN.value(),
          new Date(),
          ex.getMessage(),
          request.getDescription(false));
    }
}
