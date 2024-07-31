package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class PasswordChangeModal {

	private String kno;
	private String oldPassword;
	private String newPassword;
	private String discomName;
	private String email;
	private String name;
	private String mobileNo;
	
}
