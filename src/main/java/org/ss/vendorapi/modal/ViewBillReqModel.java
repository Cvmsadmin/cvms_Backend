package org.ss.vendorapi.modal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ViewBillReqModel {	
	
	private String reportName;
	private String fromToBillID;
	private String kno;
	private String discomName;
	private String billNo;
	
}
