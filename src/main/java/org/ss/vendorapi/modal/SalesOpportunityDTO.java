package org.ss.vendorapi.modal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class SalesOpportunityDTO {
	
    private Long id;
	
	private String srNumber;
	private String nameOfCustomer;
	private String geography;
	private String rfpNumber;
	private String eprocId;
	private String rfpTitle;
	private String modeOfSelection;
	private String modeOfSubmission;
	private String projectDuration;
	private String estimatedProjectValue;
	private String expectedOEMs;
	private String jvConsortiumSubContractors;
	private String expectedCompetitors;
	private String consultant ;
	private String salesSPOC;
	private String remarksStatus;
	
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date rfpPublished;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date preBidQueries;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date preBidMeeting;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date submissionEndDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date submissionOfBGHardCopy;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date openingDate;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date dateOfPresentation;
	
	private String emdAmount;
	private String modeOfEMD;
	private String tenderDocFee;
	private String tenderProcessingFee;
	private String portalCharges;
	
	private String approvalForBidParticipation;
	private String currentStatus;

}
