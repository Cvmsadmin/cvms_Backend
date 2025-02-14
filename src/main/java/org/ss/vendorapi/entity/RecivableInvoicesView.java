package org.ss.vendorapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pending_recivable_invoices")
public class RecivableInvoicesView {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 
	
	private String client_name;
    private String project_name;
	private String milestone;
	private int invoice_inclusive_of_gst;
	
	
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public int getInvoice_inclusive_of_gst() {
		return invoice_inclusive_of_gst;
	}
	public void setInvoice_inclusive_of_gst(int invoice_inclusive_of_gst) {
		this.invoice_inclusive_of_gst = invoice_inclusive_of_gst;
	}
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
