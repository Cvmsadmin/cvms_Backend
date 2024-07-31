package org.ss.vendorapi.modal;

import java.util.Date;

import lombok.Data;

@Data
public class VendorInvioceMasterDTO {

	private String vendorId;
	private String vendorName;
	private String clientName;
	private String projectName;
	private Date invoiceDate;
	private String invoiceNo;
	private String poNo;
	private Date invoiceDueDate;
	private String invoiceDescription;
	private String gstPer;
	private String invoiceAmountExcluGst;
	private String invoiceAmountIncluGst;
	private String status;
	private Date date;
	private String tdsDeducted;
	private String amount;
	private String penalty;
	private String laborCess;
	private String totalAmount;
	//	    private byte[] invoiceUpload;
	//	    private byte[] po;
	//	    private byte[] deliveryAcceptance;
	//	    private byte[] eWayBill;
	//	    private byte[] miscellaneous;
}
