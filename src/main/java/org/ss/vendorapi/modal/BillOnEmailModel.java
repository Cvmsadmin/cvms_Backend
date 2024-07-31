package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class BillOnEmailModel {

	private String kno;
	private String discom;
	private String connectionType;
	private String onlineBillingStatus;
	//For updateOnlineBillRegDetails API
	private String statusSelection;
	private String personId;
	private String mobileNo;
	private String phoneNo;
	private String email;
	private String dateOfBirth;
}