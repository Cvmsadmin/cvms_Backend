package org.ss.vendorapi.modal;

import java.util.Date;
import java.util.List;

import org.ss.vendorapi.entity.DescriptionAndBaseValue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VendorInvioceMasterDTO {

//	private String vendorId;
	private Long id;
	private String vendorName;
	private Long clientId;
	private String clientName;
	private String projectName;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date invoiceDate;
	private String invoiceNo;
	private String poNo;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date invoiceDueDate;
	private String invoiceDescription;
	private String gstPer;
	private String invoiceAmountExcluGst;
	private String invoiceAmountIncluGst;
	private String status;
	
	private List<DescriptionAndBaseValue> descriptionsAndBaseValues;
	private String invoiceBaseValue;
	private String gstBaseValue;
	private String invoiceInclusiveOfGst;
	private String tdsBaseValue;
	private String cgstOnTds;
	private String sgstOnTds;
	private String totalTdsDeducted;
	private String balance;
	private String penaltyDeductionOnBase;
	private String gstOnPenalty;
	private String totalPenaltyDeduction;
	private String totalPaymentReceived;
	private String TdsPer;
	private String TdsOnGst;
	private String IgstOnTds;
	private String Penalty;
	private String CreditNote;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date startDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date EndDate;
	
	private String modeOfPayment;
		
}	
	
	
//	@JsonFormat(pattern="dd/MM/yyyy")
//	private Date date;
//	private String tdsDeducted;
//	private String amount;
//	private String penalty;
//	private String laborCess;
//	private String totalAmount;
	//	    private byte[] invoiceUpload;
	//	    private byte[] po;
	//	    private byte[] deliveryAcceptance;
	//	    private byte[] eWayBill;
	//	    private byte[] miscellaneous;
