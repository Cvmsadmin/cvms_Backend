package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class SMSDetailsDTO {

	private String moduleName;
	private String from;	
	private String message;
	private String mobileno;
}