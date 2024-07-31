package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ProfitLossMasterDTO {
	
	 private String srNo;
	    private String description;
	    private Double gstPercent;
	    private Double clientBillIncludeGst;
	    private Double clientGstAmount;
	    private Double clientBillExcludeGst;
	    private Double vendorBillIncludeGst;
	    private Double vendorGstAmount;
	    private Double vendorBillExcludeGst;
	    private Double marginPercent;
	    private Double margin;

	    // Getters and Setters

	    public String getSrNo() {
	        return srNo;
	    }

	    public void setSrNo(String srNo) {
	        this.srNo = srNo;
	    }

	    public String getDescription() {
	        return description;
	    }

	    public void setDescription(String description) {
	        this.description = description;
	    }

	    public Double getGstPercent() {
	        return gstPercent;
	    }

	    public void setGstPercent(Double gstPercent) {
	        this.gstPercent = gstPercent;
	    }

	    public Double getClientBillIncludeGst() {
	        return clientBillIncludeGst;
	    }

	    public void setClientBillIncludeGst(Double clientBillIncludeGst) {
	        this.clientBillIncludeGst = clientBillIncludeGst;
	    }

	    public Double getClientGstAmount() {
	        return clientGstAmount;
	    }

	    public void setClientGstAmount(Double clientGstAmount) {
	        this.clientGstAmount = clientGstAmount;
	    }

	    public Double getClientBillExcludeGst() {
	        return clientBillExcludeGst;
	    }

	    public void setClientBillExcludeGst(Double clientBillExcludeGst) {
	        this.clientBillExcludeGst = clientBillExcludeGst;
	    }

	    public Double getVendorBillIncludeGst() {
	        return vendorBillIncludeGst;
	    }

	    public void setVendorBillIncludeGst(Double vendorBillIncludeGst) {
	        this.vendorBillIncludeGst = vendorBillIncludeGst;
	    }

	    public Double getVendorGstAmount() {
	        return vendorGstAmount;
	    }

	    public void setVendorGstAmount(Double vendorGstAmount) {
	        this.vendorGstAmount = vendorGstAmount;
	    }

	    public Double getVendorBillExcludeGst() {
	        return vendorBillExcludeGst;
	    }

	    public void setVendorBillExcludeGst(Double vendorBillExcludeGst) {
	        this.vendorBillExcludeGst = vendorBillExcludeGst;
	    }

	    public Double getMarginPercent() {
	        return marginPercent;
	    }

	    public void setMarginPercent(Double marginPercent) {
	        this.marginPercent = marginPercent;
	    }

	    public Double getMargin() {
	        return margin;
	    }

	    public void setMargin(Double margin) {
	        this.margin = margin;
	    }

}
