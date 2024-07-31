package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vendor_invoice_master")
public class VendorInvoiceMasterEntity implements Serializable{
	
	private static final long serialVersionUID = 10L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

    @Column(name = "vendor_id")
    private String vendorId;

    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @Column(name = "invoice_no")
    private String invoiceNo;

    @Column(name = "purchase_order_no")
    private String poNo;

    @Column(name = "invoice_due_date")
    private Date invoiceDueDate;

    @Column(name = "invoice_description")
    private String invoiceDescription;

    @Column(name = "gst_per")
    private String gstPer;

    @Column(name = "invoice_amount_exclu_gst")
    private String invoiceAmountExcluGst;

    @Column(name = "invoice_amount_inclu_gst")
    private String invoiceAmountIncluGst;

    @Column(name = "status")
    private String status;

    @Column(name = "date")
    private Date date;

    @Column(name = "tds_deducted")
    private String tdsDeducted;

    @Column(name = "amount")
    private String amount;

    @Column(name = "penalty")
    private String penalty;

    @Column(name = "labor_cess")
    private String laborCess;

    @Column(name = "total_amount")
    private String totalAmount;

//    @Column(name = "invoice_upload")
//    private byte[] invoiceUpload;
//
//    @Column(name = "po")
//    private byte[] po;
//
//    @Column(name = "delivery_acceptance")
//    private byte[] deliveryAcceptance;
//
//    @Column(name = "e_way_bill")
//    private byte[] eWayBill;
//
//    @Column(name = "miscellaneous")
//    private byte[] miscellaneous;

	
}
