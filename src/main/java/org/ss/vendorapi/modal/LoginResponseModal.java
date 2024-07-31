package org.ss.vendorapi.modal;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginResponseModal implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	
	private String kno;
	private String discomName;
	
	@JsonIgnore
	private String upassword;
	private String emailId;
	private long mobile;
	private String confirmemail;
	
	private String bill_no;
	private String phoneno;
	private String secretQuestion;
	private String secretAnswer;
//	private String date_of_birth;
	private String confirmEmail;
	private String lastLogin;
	private String source_of_Registration;
	private String otp;

}

