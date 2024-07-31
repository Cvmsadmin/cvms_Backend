package org.ss.vendorapi.resources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix="spring.ss")
@Configuration
@Data
public class CCBWebServiceProperties {
	
	private String ccbWebServiceUserName;
	private String ccbWebServicePassword;
	private String cmCaseUrl;
	
}
