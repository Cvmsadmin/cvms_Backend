package org.ss.vendorapi.resources;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@ConfigurationProperties(prefix="spring.ss.email")
@Configuration
@Data
public class EmailTemplateCcProperties {

	private String bescomCc;
	private String mescomCc;
	private String cescomCc;
	private String gescomCc;
	private String hescomCc;
	
}
