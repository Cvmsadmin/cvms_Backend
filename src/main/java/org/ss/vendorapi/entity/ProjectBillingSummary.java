package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@Where(clause="ACTIVE=1")
@Entity
@Table(name = "project_billing_summary")

public class ProjectBillingSummary extends ParentEntity implements Serializable{
	
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="SrNo")
    private String SrNo;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "total_billable_amount")
	private String totalBillableAmount;
	
	@Column(name = "total_received_amount")
	private String totalReceivedAmount;
	
	@Column(name = "balance_to_be_billed")
	private String balanceToBeBilled;
	
	@Column(name = "view_details")
	private String viewDetails;
	

}
