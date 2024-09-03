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
import lombok.Data;

@Data
@Entity
@Where(clause="ACTIVE=1")
@Table(name = "purchase_master")
public class PurchaseMasterEntity extends ParentEntity implements Serializable{
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	
	@Column(name = "client_name")
	private String clientName;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "vendor")
	private String vendor;

	@Column(name = "requestor_name")
	private String requestorName;

	@Column(name = "description")
	private String description;

	@Column(name = "purchase_register_no")
	private String prNo;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "purchase_register_date")
	private Date prDate;

	@Column(name = "purchase_register_amount")
	private String prAmount;

	@Column(name = "status")
	private String status;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "approve_date")
	private Date approveDate;

	@Column(name = " purchase_order_no")
	private String poNo;

	


	    
//	    @Column(name = "agreement")
//	    private byte[] agreement;
//	
//	    @Column(name = "proposal")
//	    private byte[] dwa;
//	    
//	    @Column(name = "miscellaneous")
//	    private byte[] miscellaneous;



}
