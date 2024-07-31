package org.ss.vendorapi.modal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KnowYourAccountModel {
	
	private String discomName;
	private String kno;
	private String message;
	private String oldAccountNo;
	private String verifyCode;
	private String verifyStatus;
	
	

}
