	package org.ss.vendorapi.entity;
	
	import java.io.Serializable;
	import org.hibernate.annotations.Where;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.FetchType;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.JoinColumn;
	import jakarta.persistence.ManyToOne;
	import jakarta.persistence.Table;
	import lombok.AllArgsConstructor;
	import lombok.Getter;
	import lombok.NoArgsConstructor;
	import lombok.Setter;
	
	@NoArgsConstructor @Setter @Getter @AllArgsConstructor
	@Entity
	@Where(clause="ACTIVE=1")
	@Table(name="profit_loss_master")
	public class ProfitLossMasterEntity extends ParentEntity implements Serializable{
			
		private static final long serialVersionUID=1L;
		
		@Id
		@Column(name="id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
			
		@Column(name="client_id")
		private int clientId;
		
		@Column(name="client_name")
		private String clientName;
	
		@Column(name="project_id")
		private int projectId;
		
		@Column(name="project_name")
		private String projectName;
		
		@Column(name="serial_no")
		private String SrNo;
			
		@Column(name="description")
		private String description;
		
		@Column(name="gst_percent")
		private String gstPercent;
		
		@Column(name="client_bill_include_gst")
		private String clientBillIncludeGst;
		
		@Column(name="client_gst_amount")
		private String clientGstAmount;
		
		@Column(name="client_billing_exclude_gst")
		private String clientBillExcludeGst;
		
		@Column(name="vendor_bill_include_gst")
		private String vendorBillIncludeGst;
		
		@Column(name="vendor_gst_amount")
		private String vendorGstAmount;
		
		@Column(name="vendor_bill_exclude_gst")
		private String vendorBillExcludeGst;
		
		@Column(name="margin_percent")
		private String marginPercent;
		
		@Column(name="margin")
		private String margin;
		
	}
