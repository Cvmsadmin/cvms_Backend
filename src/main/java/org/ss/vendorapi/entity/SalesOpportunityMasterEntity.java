package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.Where;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Where(clause="ACTIVE=1")
@Table(name="sales_master")
@Entity
public class SalesOpportunityMasterEntity extends ParentEntity implements Serializable{

	
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	

	@Column(name = "sr_number", unique = true, nullable = false)
	private String srNumber;
	private String nameOfCustomer;
	private String geography;
	private String rfpNumber;
	private String eProcId;
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
	
	private Date rfpPublished;
	private Date preBidQueries;
	private Date preBidMeeting;
	private Date submissionEndDate;
	private Date submissionOfBGHardCopy;
	private Date openingDate;
	private Date dateOfPresentation;
	
	private String emdAmount;
	private String modeOfEMD;
	private String tenderDocFee;
	private String tenderProcessingFee;
	private String portalCharges;
		
	private String approvalForBidParticipation;
	private String currentStatus;

	
	@PrePersist
    private void generateSrNumber() {
        if (this.srNumber == null) {
            this.srNumber = "SR-" + System.currentTimeMillis(); 
        }
    }
	
	
		
}
