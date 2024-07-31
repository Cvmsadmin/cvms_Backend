package org.ss.vendorapi.modal.response;

import org.json.JSONObject;

import lombok.Data;

@Data
public class LoadLEPaymentModal {
	
	private String processingFee;
	private String securityFee;
	private String sysLoadingChrgs;
	private String cgstFee;
    private String sgstFee;
    private String totalCharge;
    
    
    public LoadLEPaymentModal (JSONObject json) {
    	this.processingFee=json.getString("ProcessingFee");
    	this.securityFee=json.getString("SecurityFee");
    	this.sysLoadingChrgs=json.getString("SysLoadingChrgs");
    	this.cgstFee=json.getString("CgstFee");
    	this.sgstFee=json.getString("SgstFee");
    	this.totalCharge=json.getString("TotalChrg");
    }

}
