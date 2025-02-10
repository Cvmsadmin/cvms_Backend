package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data 
@NoArgsConstructor @AllArgsConstructor
@Entity
@Where(clause="ACTIVE=1")
@Table(name = "client_invoice_master")
public class ClientInvoiceMasterEntity  extends ParentEntity implements Serializable{
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@Column(name = "client_id")
	private String clientId;

	@Column(name = "client_name")
	private String clientName;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "discom")
	private String discom;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "invoice_date")
	private Date invoiceDate;

	@Column(name = "invoice_no")
	private String invoiceNo;

	@Column(name = "invoice_description")
	private String invoiceDescription;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "invoice_due_date")
	private Date invoiceDueDate;
	
	@Column(name = "gst_per")
	private String gstPer;

	@Column(name = "gst_amount")
	private String gstAmount;

	@Column(name = "invoice_amount_exclu_gst")
	private String invoiceAmountExcluGst;

	@Column(name = "invoice_amount_inclu_gst")
	private String invoiceAmountIncluGst;

	@Column(name = "status")
	private String status;

	@Column(name = "invoice_base_value")
	private String invoiceBaseValue;

	@Column(name = "gst_base_value")
	private String gstBaseValue;

	@Column(name = "invoice_inclusive_of_gst")
	private String invoiceInclusiveOfGst;
	
	@Column(name = "tds_Per")
	private String tdsPer;
	
	@Column(name = "tds_on_gst_Per")
	private String tdsOnGstPer;

	@Column(name = "tds_base_value")
	private String tdsBaseValue;
	
	@Column(name = "igst_on_tds")
	private String igstOnTds;

	@Column(name = "cgst_on_tds")
	private String cgstOnTds;

	@Column(name = "sgst_on_tds")
	private String sgstOnTds;

	@Column(name = "total_tds_deducted")
	private String totalTdsDeducted;

	@Column(name = "balance")
	private String balance;

	@Column(name = "penalty")
	private String penalty;

	@Column(name = "penalty_deduction_on_base")
	private String penaltyDeductionOnBase;

	@Column(name = "gst_on_penalty")
	private String gstOnPenalty;

	@Column(name = "total_penalty_deduction")
	private String totalPenaltyDeduction;
	
	 @Column(name = "credit_note")
	 private String creditNote;
	
	@Column(name = "total_payment_received")
	private Double totalPaymentReceived;

	private String tdsOnGst;

	public Object getBillableState() {
	    // Determine the billable state based on the status or other logic
	    if ("Completed".equalsIgnoreCase(this.status)) {
	        return "Billable";
	    } else if ("Pending".equalsIgnoreCase(this.status)) {
	        return "Non-Billable";
	    } else if ("Rejected".equalsIgnoreCase(this.status)) {
	        return "Not Applicable";
	    } else {
	        return "Unknown";
	    }
	}

	public void setBillableState(Object object) {
				
	}

	public String getTdsOnGst() {
		
		return this.tdsOnGst;
	}


}
