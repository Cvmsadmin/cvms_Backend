package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="purchase_bom_master")
public class PurchaseBOMMasterEntity extends ParentEntity implements Serializable{
	
	private static final long serialVersionUID=1L;

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="PURCHASE_ID")
	private String purchaseId;

	@Column(name = "bom_description")
	private String bomDescription;

	@Column(name = "service")
	private String service;

	@Column(name = "type_of_expenditure")
	private String typeOfExpenditure;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "start_date")
	private Date startDate;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "renewable")
	private String renewable;

	@Column(name = "rate_unit")
	private String rateUnit;

	@Column(name = "quantity")
	private String quantity;

	@Column(name = "gst_rate")
	private String gstRate;

	@Column(name = "amount_excl_gst")
	private String amountExclGst;

	@Column(name = "amount_incl_gst")
	private String amountInclGst;



}


