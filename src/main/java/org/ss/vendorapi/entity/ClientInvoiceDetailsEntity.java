package org.ss.vendorapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
@Data 
@Entity
@Table(name = "client_invoice_details")
public class ClientInvoiceDetailsEntity {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID for JPA
    private Long id; // Synthetic ID (not mapped to the database)
	
	
	@Column(name = "client_name")
	private String ClientName;
	
	@Column(name = "project_name")
	private String ProjectName;
	
	@Column(name = "account_manager_email")
	private String AccountManagerEmail1;
	
	@Column(name = "project_manager_email")
	private String PrjectManagerEmail;
	
	@Column(name = "invoice_date")
	private String InvoiceDate;
	
	@Column(name = "invoice_no")
	private String InvoiceNo;
	
	@Column(name = "invoice_due_date")
	private String InvoiceDueDate;
	
	@Column(name = "invoice_amount_inclu_gst")
	private String InvoiceAmountIncluGst;

}
