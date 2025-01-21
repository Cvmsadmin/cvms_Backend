package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor@AllArgsConstructor
//@Where(clause="ACTIVE=1")
@Entity
@Table(name = "project_payment_summary")
//@SuppressWarnings("serial")


@Builder
public class ProjectPaymentSummary implements Serializable{
	
	
	private static final long serialVersionUID=1L;
	
	@Id
	@Column(name="id")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
//	@EmbeddedId
//	@Transient
//	private Long id; 

	private Long clientId;

//    private String projectId;
    	
//	@Column(name="sr_no")
//    private String SrNo;
	
	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "contract_price")
	private String contractPrice;
	
	@Column(name = "total_received_amount")
	private String totalReceivedAmount;
	
	@Column(name = "balance_to_be_billed")
	private String balanceToBeBilled;
	
//	@Column(name = "view_details")
//	private String viewDetails;
	
}


