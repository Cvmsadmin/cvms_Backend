package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ProfitLossMasterDTO {
	
	 private String srNo;
	    private String description;
	    private String gstPercent;
	    private String clientBillIncludeGst;
	    private String clientGstAmount;
	    private String clientBillExcludeGst;
	    private String vendorBillIncludeGst;
	    private String vendorGstAmount;
	    private String vendorBillExcludeGst;
	    private String marginPercent;
	    private String margin;

	    // Getters and Setters

}
