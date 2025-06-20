package org.ss.vendorapi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Milestone_Payment_Details")
public class EmailNotificationView {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generate ID for JPA
    private Long id; // Synthetic ID (not mapped to the database)
	
	  private String email;
	  private String client_name;
	  private String project_name;
	  private String account_manager;
	  //private String email,
	  private String milestone;
	  private String t_date;
	  private String pending_payment_for_milestone;
	  private String mail_date;
	  private String milestone_duration;
	  private String start_date;
	  private String Status;
//	  private String RecipientEmail;
	  
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
	public String getAccount_manager() {
		return account_manager;
	}
	public void setAccount_manager(String account_manager) {
		this.account_manager = account_manager;
	}
	public String getMilestone() {
		return milestone;
	}
	public void setMilestone(String milestone) {
		this.milestone = milestone;
	}
	public String getPending_payment_for_milestone() {
		return pending_payment_for_milestone;
	}
	public void setPending_payment_for_milestone(String pending_payment_for_milestone) {
		this.pending_payment_for_milestone = pending_payment_for_milestone;
	}
	public String getMail_date() {
		return mail_date;
	}
	public void setMail_date(String mail_date) {
		this.mail_date = mail_date;
	}
	public String getT_date() {
		return t_date;
	}
	public void setT_date(String t_date) {
		this.t_date = t_date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMilestone_duration() {
		return milestone_duration;
	}
	public void setMilestone_duration(String milestone_duration) {
		this.milestone_duration = milestone_duration;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
//	public String getRecipientEmail() {
//		
//		return recipientEmail;
//	}
	
//	public void setRecipientEmail(String recipientEmail) {
//		RecipientEmail = recipientEmail;
//	}

	  

	  
	}
