package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ClientInvoiceProjection {
	
	 private String Id;
	 private String Balance;
	 private String CgstOnTds;
	 private String ClientName;
	 private String Discom;
	 private String GstBaseValue;
	 private String GstOnPenalty;
	 private Double InvoiceAmountIncluGst;
	 private String InvoiceBaseValue;
	 private String InvoiceDate;
	 private String InvoiceDescription;
	 private String InvoiceDueDate;
	 private String InvoiceInclusiveOfGst;
	 private String InvoiceNo;
	 private String Penalty;
	 private String PenaltyDeductionOnBase;
	 private String ProjectName;
	 private String SgstOnTds;
	 private String Status; 
	 private String TdsBaseValue; 
	 private String TotalPaymentReceived;
	 private String TotalPenaltyDeduction;
	 private String TotalTdsDeducted;
     private String ClientId;
	 private String CreditNote;
	 private String TdsPer;
	 private String TdsOnGst;
	 private String TotalCgst;
	 private String TotalSgst;
	 private String TotalIgst;
	 private String AmountExcluGst;
	 private String BillableState;
	 private String InvoiceAmtIncluGst;
	 private String PaymentDate;
	
}
