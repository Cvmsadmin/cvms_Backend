package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ISPModelDTO {
	private String kno;
	private String discom;
	private String name;
	
	//send OTP
	private String mobileno;
	private String otp;
	private String maskedMobileNo;
}