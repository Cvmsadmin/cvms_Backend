package org.ss.vendorapi.modal;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.ss.vendorapi.entity.ClientDescriptionAndBaseValue;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ClientInvoiceMasterDTO {

	private Long id;
	private String clientId;
	private String clientName;
	private String projectName;
	private String discom;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private LocalDate invoiceDate;
	private String invoiceNo;
	private String invoiceDescription;
	
	@JsonFormat(pattern="dd/MM/yyyy")
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
	private Double totalPaymentReceived;
	private String tdsDeductionIncluGst;

	private String TdsPer;
	private String TdsOnGstPer;
	private String IgstOnTds;
	private String CreditNote;
	
	private String TdsOnGst;
	private String milestone;
	
	private String BillableState;
	private List<ClientDescriptionAndBaseValue> clientDescriptionAndBaseValue;



	private String DescriptionsAndBaseValues;

}
