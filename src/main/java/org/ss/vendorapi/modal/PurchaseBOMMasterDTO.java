package org.ss.vendorapi.modal;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PurchaseBOMMasterDTO {

	private Long id;
	private String bomDescription;
	private String service;
	private String typeOfExpenditure;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date startDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date endDate;
	private String renewable;
	private String rateUnit;
	private String quantity;
	private String gstRate;
	private String amountExclGst;
	private String amountInclGst;
	private String purchaseId;



}
