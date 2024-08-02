package org.ss.vendorapi.modal;

import java.util.Date;

import lombok.Data;

@Data
public class ClientInvoiceMasterDTO {

	private Long id;
	private String clientId;
	private String clientName;
	private String projectName;
	private String discom;
	private Date invoiceDate;
	private String invoiceNo;
	private String invoiceDescription;
	private Date invoiceDueDate;
	private String gstPer;
	private String gstAmount;
	private String invoiceAmountExcluGst;
	private String invoiceAmountIncluGst;
	private String status;
	private String invoiceBaseValue;
	private String gstBaseValue;
	private String invoiceInclusiveOfGst;
	private String tdsBaseValue;
	private String cgstOnTds;
	private String sgstOnTds;
	private String totalTdsDeducted;
	private String balance;
	private String penalty;
	private String penaltyDeductionOnBase;
	private String gstOnPenalty;
	private String totalPenaltyDeduction;
	private String totalPaymentReceived;
	private String tdsDeductionIncluGst;
//	private byte[] invoiceUpload;
//	private byte[] po;
//	private byte[] deliveryAcceptance;
//	private byte[] eWayBill;
//	private byte[] miscellaneous;

}
