package org.ss.vendorapi.modal;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ForgotPasswordSecQAModel implements Serializable {
	private static final long serialVersionUID = 2L;
	
	
	private String securityQuestion;
	private String securityAnswer;
	private String primaryEmail;
	private String newEmail;
	private String kno;
    private String discomName;
	

	

}
