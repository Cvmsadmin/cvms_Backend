package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="milestone_master")
@Entity
public class MilestoneMasterEntity extends ParentEntity implements Serializable{
	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String projectId;
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date tDate;
	private String serialNumber;
	private String days;
	private String deliverables;

	private String amountExclGst;
	private String gstRate;
	private String gstAmount;
	private String amountInclGst;
	
}



