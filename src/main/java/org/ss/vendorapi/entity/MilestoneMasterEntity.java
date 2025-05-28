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

@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Where(clause="ACTIVE=1")
@Table(name="milestone_master")
@Entity

public class MilestoneMasterEntity extends ParentEntity implements Serializable{
	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String projectId;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date tDate;
	private String serialNumber;
	private String days;
	private String deliverables;
	private String amountExclGst;
	private String gstRate;
	private String gstAmount;
	private String amountInclGst;	
	private String status; 
	
	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "completiondate")  // explicitly set the column name as in your DB
	private Date completiondate;
		
	public String getAmountIncluGst() {		
		return amountInclGst;
	}

	public String getAmountExcluGst() {
		return amountExclGst;
	}

	public Date getCompletionDate() {		
		return completiondate;
	}

	public void setCompletionDate(Date completionDate) {
	    this.completiondate = completionDate;
	}
	
	@Column(name = "servicetypes")
	private String serviceTypes;    //new field
	
	@Column(name = "payment_per")
	private Double paymentPer;     //new field

	public Double getPaymentPer() {
	    return this.paymentPer;
	}	
	
	public String getServiceTypes() {	
		 return this.serviceTypes;
	}

	public void setServiceTypes(String serviceTypes) {
	    this.serviceTypes = serviceTypes;
	}
	
}



