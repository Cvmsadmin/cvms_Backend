package org.ss.vendorapi.advice;

import java.io.BufferedReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.ss.vendorapi.config.AESDecryptionService;
import org.ss.vendorapi.config.AESEncryptionService;
import org.ss.vendorapi.util.CommonUtils;
import org.ss.vendorapi.wrapper.MultiReadHttpServletRequestWrapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component("DecryptionRequestAdvice")
public class DecryptionRequestAdvice extends OncePerRequestFilter {

    @Autowired
    private AESDecryptionService aesDecryptionService;

    @Value("${spring.security.aes.responseKey}")
    private String secretKey;

    @Value("${spring.security.enable.encryption}")	
    private String isEncryption;
    
    @Autowired
    private AESEncryptionService aesEncryptionService;

    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	
    	if(!request.getRequestURI().contains("/ThankYouBillDeskPayment")) {
    		MultiReadHttpServletRequestWrapper requestWrapper = new MultiReadHttpServletRequestWrapper((HttpServletRequest) request);

			String requestStr = getRequestBody(requestWrapper);
//			System.out.println("@@@@ Data Before Decryption is @@@@ " + requestStr);
			String decryptedData = decryptAndDeserialize(requestStr);

			// Set the deserialized object as an attribute in the request
//			System.out.println("@@@@ Data Object Before Decryption is @@@@ " + decryptedData.toString());

			requestWrapper.setRequestBody(decryptedData);
            filterChain.doFilter(requestWrapper, response);
    	}else {
    		filterChain.doFilter(request, response);
    	}    
    }

    private String getRequestBody(HttpServletRequest request) throws java.io.IOException {
        try (BufferedReader bufferedReader = request.getReader()) {
            StringBuilder stringBuilder = new StringBuilder();
            char[] charBuffer = new char[128];
            int bytesRead;

            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String decryptAndDeserialize(String encryptedData) {
        try {
        	String decryptedData = encryptedData;
        	if (encryptedData.contains("_cdata")) {
        		
        		String encr = aesEncryptionService.encrypt(secretKey, encryptedData);
        		System.out.println("Hello :: " + encr);
    			String encryptedData1 =  CommonUtils.convertJsonStringToJsonObject(encryptedData).get("_cdata").asText();
    			decryptedData = aesDecryptionService.decrypt(secretKey, encryptedData1);
    		}     	
            return decryptedData;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
