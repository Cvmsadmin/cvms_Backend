package org.ss.vendorapi.modal.response;

import lombok.Data;

@Data
public class NetBillingMTRChargesResponse {

	private String description;
	private String resMsg;
	//getNetMeterRegistrationFee
	private String processingFee;
}