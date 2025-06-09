package org.ss.vendorapi.modal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

//import org.ss.vendorapi.entity.ClientDescriptionAndBaseValue;
import org.ss.vendorapi.entity.ClientInvoiceDescriptionValue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ClientInvoiceMasterDTO {

	private Long id;
//	private String clientId;
	private long clientId;  // Change from String to Long

	private String clientName;
	private String projectName;
	private String discom;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate invoiceDate;
	private String invoiceNo;
	private String invoiceDescription;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate invoiceDueDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate paymentDate;
	
//	private String gstPer;
//	private String gstAmount;
//	private String invoiceAmountExcluGst;
	private String invoiceAmountIncluGst;

	private String invoiceAmtIncluGst;
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
	private Double totalPaymentReceived;
	private String tdsDeductionIncluGst;

	private String tdsPer;
//	private String TdsOnGstPer;
//	private String IgstOnTds;
	private String creditNote;
	
	private String tdsOnGst;
//	private String milestone;
	
	private String billableState;
	private List<ClientInvoiceDescriptionValue> clientInvoiceDescriptionValue;
	
//	private String DescriptionsAndBaseValues;
	
	// New fields added
	private String amountExcluGst;  

    private String totalCgst;
    private String totalSgst;
    private String totalIgst;
    
    private String accountManagerEmail;
    private String projectManagerEmail;
     

    
//    public String getAccountManagerEmail() {
//    	return getAccountManagerEmail();
//    }
//    
//	public String getProjectManagerEmail() {
//		return getProjectManagerEmail();
//	}

 
}
