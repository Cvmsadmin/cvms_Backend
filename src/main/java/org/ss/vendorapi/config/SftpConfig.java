package org.ss.vendorapi.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "sftp")
public class SftpConfig {
	
	
	private String host;
    private int port;
    private String username;
    private String password;

}
