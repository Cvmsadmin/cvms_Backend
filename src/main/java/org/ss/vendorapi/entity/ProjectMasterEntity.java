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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter@NoArgsConstructor@AllArgsConstructor
@Where(clause="ACTIVE=1")
@Entity
@Table(name = "project_master")
public class ProjectMasterEntity extends ParentEntity implements Serializable{
	
	private static final long serialVersionUID=1L;
	
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

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "start_date")
	private Date startDate;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "duration")
	private String duration;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "date_of_loi")
	private Date dateOfLoi;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "t_date")
	private Date tDate;

	@Column(name = "loi_no")
	private String loiNo;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "date_of_loa")
	private Date dateOfLoa;

	@Column(name = "account_manager")
	private String accountManager;

	@Column(name = "project_manager")
	private String projectManager;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "contract_date")
	private Date contractDate;

	@JsonFormat(pattern="dd/MM/yyyy")
	@Column(name = "sing_of_date")
	private Date singOfDate;

	@Column(name = "contract_price")
	private String contractPrice;

	

	    
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
