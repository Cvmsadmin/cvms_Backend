package org.ss.vendorapi.modal;

import java.util.Date;
import java.util.List;

//import org.ss.vendorapi.entity.DescriptionAndBaseValue;
import org.ss.vendorapi.entity.InvoiceDescriptionValue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class VendorInvioceMasterDTO {
	
	private Long id;
    private String vendorName;
    private Long clientId;
    private String clientName;
    private String projectName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date invoiceDate;

    private String invoiceNo;
    private String poNo;	

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date invoiceDueDate;

    private String invoiceDescription;
//    private String gstPer;
//    private String invoiceAmountExcluGst;
    private String invoiceAmountIncluGst;
    private Double invoiceAmtIncluGst;
    private String status;

    private List<InvoiceDescriptionValue> invoiceDescriptionValue;

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
 
    private String TdsOnGst;
    private String igstOnTds;
    private String tdsPer;
    private String penalty;
    private String creditNote;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date startDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date endDate;

    private String modeOfPayment;

    // Newly added fields
    private Double totalCgst;
    private Double totalSgst;
    private Double totalIgst;
    private Double amountExcluGst;
    
    // Getters for the newly added fields
    public Double getTotalCgst() {
        return totalCgst;
    }

    public Double getTotalSgst() {
        return totalSgst;
    }

    public Double getTotalIgst() {
        return totalIgst;
    }

    public Double getAmountExcluGst() {
        return amountExcluGst;
    }

//	private String vendorId;
//	private Long id;
//	private String vendorName;
//	private Long clientId;
//	private String clientName;
//	private String projectName;
//	@JsonFormat(pattern="dd/MM/yyyy")
//	private Date invoiceDate;
//	private String invoiceNo;
//	private String poNo;
//	@JsonFormat(pattern="dd/MM/yyyy")
//	private Date invoiceDueDate;
//	private String invoiceDescription;
//	private String gstPer;
//	private String invoiceAmountExcluGst;
//	private String invoiceAmountIncluGst;
//	private String status;
//	
//	private List<InvoiceDescriptionValue> InvoiceDescriptionValue;
//	private String invoiceBaseValue;
//	private String gstBaseValue;
//	private String invoiceInclusiveOfGst;
//	private String tdsBaseValue;
//	private String cgstOnTds;
//	private String sgstOnTds;
//	private String totalTdsDeducted;
//	private String balance;
//	private String penaltyDeductionOnBase;
//	private String gstOnPenalty;
//	private String totalPenaltyDeduction;
//	private String totalPaymentReceived;
//	private String TdsPer;
//	private String TdsOnGst;
//	private String IgstOnTds;
//	private String Penalty;
//	private String CreditNote;
//	
//	@JsonFormat(pattern="dd/MM/yyyy")
//	private Date startDate;
//	
//	@JsonFormat(pattern="dd/MM/yyyy")
//	private Date EndDate;
//	
//	private String modeOfPayment;
//
//	public Double getTotalCgst() {		
//		return getTotalCgst();
//	}
//
//	public Double getTotalSgst() {
//		return getTotalSgst();
//	}
//
//	public Double getTotalIgst() {		
//		return getTotalIgst();
//	}
//
//	public Double getAmountExcluGst() {
//		return getAmountExcluGst();
//	}
		
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
