package org.ss.vendorapi.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.ss.vendorapi.config.AESEncryptionService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ControllerAdvice
public class EncryptionResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	//private static final Logger log = System.getLogger("EncryptionResponseBodyAdvice");

	@Autowired
	private AESEncryptionService aesEncryptionService;

	@Autowired
	private ObjectMapper objectMapper;

	@Value("${spring.security.aes.responseKey}")
	private String secretKey;
	
	@Value("${spring.security.enable.encryption}")
	private String isEncryption;

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		// Return true to indicate that this advice should be applied to all controller
		// methods
		if("true".equals(isEncryption)) {
//			if (returnType.getMethod().isAnnotationPresent(EncryptResponse.class)) {
				return true;
//			}
		}
		
		return false;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {

		// Implement your encryption logic here.
		String encryptedData = encryptData(body);

		// Create and return the encrypted response
		EncryptedResponse<String> encryptedResponse = new EncryptedResponse<>();
		encryptedResponse.set_cdata(encryptedData);
		return encryptedResponse;
	}

	private String encryptData(Object data) {

		try {
			String json = objectMapper.writeValueAsString(data);

//			log.log(Level.INFO, "@@@@ Data Before Encryption is @@@@ " + data.toString());
			
//			log.log(Level.INFO, "@@@@ Data Before Encryption(JSON format) is @@@@ " + json);

			String encryptedData = aesEncryptionService.encrypt(secretKey, json);

//			log.log(Level.INFO, "@@@@ Data After Encryption is @@@@ " + encryptedData);

//			String decryptedData = aesDecryptionService.decrypt(secretKey, encryptedData);

//			log.log(Level.INFO, "@@@@ Data After Decryption is @@@@ " + decryptedData);

			return encryptedData;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "false";
	}
}
