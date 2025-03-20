package org.ss.vendorapi.modal;

import java.util.Date;
import java.util.List;

import org.ss.vendorapi.entity.PurchaseBOMMasterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PurchaseRequestDTO {

	private List<PurchaseBOMMasterEntity> bom; 
    
	private Long id;
	private String clientName;
	private Long clientId; 

	private String projectName;
	private String vendor;
	private String requestorName;
	private String description;
	private String prNo;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date prDate;
	private String prAmount;
	private String status;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date approveDate;
	private String poNo;
	
	private String prFor;
	
	private String rejectionReason;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date poApproveDate;

	@JsonFormat(pattern="dd/MM/yyyy")
	private Date startDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date EndDate;
}
