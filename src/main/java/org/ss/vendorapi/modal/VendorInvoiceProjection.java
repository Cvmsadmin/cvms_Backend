package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class VendorInvoiceProjection {

	private String id;                 //
    private String vendorName;//
    private String clientName;  //
    private String clientId;//
    private String projectName;//
    private String invoiceNo;//
    private String poNo;//
    private String invoiceDate;//
    private String invoiceDueDate;//
    private String invoiceDescription;//
    private String status;//
    private String invoiceAmountIncluGst;//
    
    private String invoiceAmtIncluGst;//
    
    private String invoiceBaseValue;//
    private String gstBaseValue;//
    private String invoiceInclusiveOfGst;//
    private String tdsBaseValue;//
    private String tdsPer;//
    private String tdsOnGst;//
    private String igstOnTds;//
    private String cgstOnTds;//
    private String sgstOnTds;//
    private String totalTdsDeducted;//
    private String balance;//
    private String penalty;//
    private String penaltyDeductionOnBase;//
    private String gstOnPenalty;//
    private String totalPenaltyDeduction;//
    private String creditNote;//
    private String totalPaymentReceived;//
    private String paymentDate;//
    private String totalCgst;//
    private String totalSgst;//
    private String totalIgst;//
    private String amountExcluGst;//
	private String modeOfPayment;//
	private String paymentRequestSentDate; //
	private String buApprovalDate;
	private String submissionToFinanceDate;

	private String paymentAdviceNo;//
	
	
}
