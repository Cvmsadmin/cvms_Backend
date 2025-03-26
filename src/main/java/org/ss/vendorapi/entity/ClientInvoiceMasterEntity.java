package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Entity
//@Where(clause = "ACTIVE=1")
@Table(name = "client_invoice_master")
public class ClientInvoiceMasterEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate invoiceDate;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "invoice_description")
    private String invoiceDescription;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate invoiceDueDate;

//    @Column(name = "gst_per")
//    private Double gstPer;

//    @Column(name = "gst_amount")
//    private Double gstAmount;

//    @Column(name = "invoice_amount_exclu_gst")
//    private Double invoiceAmountExcluGst;

    @Column(name = "invoice_amount_inclu_gst")
    private String invoiceAmountIncluGst;
    
    @Column(name = "invoice_amt_inclu_gst")
    private Double invoiceAmtIncluGst;     

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

//    @Column(name = "tds_on_gst_Per")
//    private Double tdsOnGstPer;

    @Column(name = "tds_base_value")
    private String tdsBaseValue;

//    @Column(name = "igst_on_tds")
//    private Double igstOnTds;

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

    @Column(name = "milestone")
    private String milestone;

    private String tdsOnGst;

    // Newly added fields
    @Column(name = "total_cgst")
    private String totalCgst;

    @Column(name = "total_sgst")
    private String totalSgst;

    @Column(name = "total_igst")
    private String totalIgst;

    @Column(name = "amount_exclu_gst")
    private String amountExcluGst;

    @Column(name = "billable_state")
    private String billableState;

    @OneToMany(mappedBy = "clientInvoice", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ClientInvoiceDescriptionValue> clientInvoiceDescriptionValue;

    public String getBillableState() {
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

    public void setBillableState(String billableState) {
        this.billableState = billableState;
    }
   
    public List<ClientInvoiceDescriptionValue> getClientInvoiceDescriptionValue() {
        return clientInvoiceDescriptionValue;
    }

    public void setClientInvoiceDescriptionValue(List<ClientInvoiceDescriptionValue> clientInvoiceDescriptionValue) {
        this.clientInvoiceDescriptionValue = clientInvoiceDescriptionValue;
    }

}
