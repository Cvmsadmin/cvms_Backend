package org.ss.vendorapi.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;
@Data 
@Entity
@Table(name = "client_invoice_details_dd")
public class ClientInvoiceDetailsEntity {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID for JPA
    private Long id; // Synthetic ID (not mapped to the database)
	
	
	@Column(name = "client_name")
	private String clientName;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "account_manager_email")
	private String accountManagerEmail1;
	
	@Column(name = "project_manager_email")
	private String prjectManagerEmail;
	
    // This field is stored as a String; hence we convert LocalDate to String in the controller
    @Column(name = "invoice_date")
    private String invoiceDate;
    
    @Column(name = "invoice_no")
    private String invoiceNo;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Column(name = "invoice_due_date")
    private LocalDate invoiceDueDate;
    
    @Column(name = "invoice_amount_inclu_gst")
    private Long invoiceAmountIncluGst;
	

}
