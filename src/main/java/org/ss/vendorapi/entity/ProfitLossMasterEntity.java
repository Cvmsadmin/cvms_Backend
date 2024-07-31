package org.ss.vendorapi.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="profit_loss_master")
public class ProfitLossMasterEntity {
	
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="serial_no")
	private String SrNo;
	
	@Column(name="client_name")
	private String clientName;
	
	@Column(name="project_name")
	private String projectName;
	
	@Column(name="description")
	private String description;
	
	@Column(name="gst_percent")
	private Double gstPercent;
	
	@Column(name="client_bill_include_gst")
	private Double clientBillIncludeGst;
	
	@Column(name="client_gst_amount")
	private Double clientGstAmount;
	
	@Column(name="client_billing_exclude_gst")
	private Double clientBillExcludeGst;
	
	@Column(name="vendor_bill_include_gst")
	private Double vendorBillIncludeGst;
	
	@Column(name="vendor_gst_amount")
	private Double vendorGstAmount;
	
	@Column(name="vendor_bill_exclude_gst")
	private Double vendorBillExcludeGst;
	
	@Column(name="margin_percent")
	private Double marginPercent;
	
	@Column(name="margin")
	private Double margin;
	
	

}
