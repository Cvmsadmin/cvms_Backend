package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class AdditionalInvoiceDataDTO {
	
	 private String invoiceBaseValue;
	    private String gstBaseValue;
	    private String invoiceInclusiveOfGst;
	    private String tdsBaseValue;
	    private String tdsPer;
	    private String tdsOnGstPer;
	    private String igstOnTds;
	    private String cgstOnTds;
	    private String sgstOnTds;
	    private String totalTdsDeducted;
	    private String balance;
	    private String penalty;
	    private String penaltyDeductionOnBase;
	    private String gstOnPenalty;
	    private String totalPenaltyDeduction;
	    private String creditNote;
	    private String totalPaymentReceived;
	    private String description;
		private String tdsOnGst;
	


}
