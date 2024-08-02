package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Entity
@Table(name = "project_master")
public class ProjectMasterEntity implements Serializable{

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
	
	@Column(name = "client_name")
	private String clientName;

	@Column(name = "project_name")
	private String projectName;

	@Column(name = "address")
	private String address;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Column(name = "pincode")
	private String pincode;

	@Column(name = "district")
	private String district;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column(name = "contact_no")
	private String contactNo;

	@Column(name = "email")
	private String email;

	@Column(name = "gst_no")
	private String gstNo;

	@Column(name = "start_date")
	private Date startDate;

	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "duration")
	private String duration;

	@Column(name = "date_of_loi")
	private Date dateOfLoi;

	@Column(name = "t0_date")
	private Date t0Date;

	@Column(name = "loi_no")
	private String loiNo;

	@Column(name = "date_of_loa")
	private Date dateOfLoa;

	@Column(name = "account_manager")
	private String accountManager;

	@Column(name = "project_manager")
	private String projectManager;

	@Column(name = "contract_date")
	private Date contractDate;

	@Column(name = "sing_of_date")
	private Date singOfDate;

	@Column(name = "contract_price")
	private String contractPrice;

	@Column(name = "serial_no")
	private String srNo;
	
	@Column(name = "days")
	private String days;
	
	@Column(name = "deliverables")
	private String deliverables;
	
	@Column(name = "amount_exclu_gst")
	private String amountExcluGst;
	
	@Column(name = "gst_per")
	private String gstPer;
	
	@Column(name = "gst_amount")
	private String gstAmount;
	
	@Column(name = "amount_inclu_gst")
	private String amountIncluGst;


	    
//	    @Column(name = "agreement")
//	    private byte[] agreement;
//	
//	    @Column(name = "dwa")
//	    private byte[] dwa;
//	
//	    @Column(name = "nda")
//	    private byte[] nda;
//	
//	    @Column(name = "gst_certificate")
//	    private byte[] gstCertificate;
//	
//	    @Column(name = "pan")
//	    private byte[] pan;
//	    
//	    @Column(name = "miscellaneous")
//	    private byte[] miscellaneous;



	
}
