package org.ss.vendorapi.modal;

import java.util.Date;
import java.util.List;

import org.ss.vendorapi.entity.MilestoneMasterEntity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProjectRequestDTO {
	
	private List<MilestoneMasterEntity> moe;
	private Long id;
	private String clientName;
    private String projectName;
    private String address;
    private String city;
    private String state;
    private String pincode;
    private String district;
    private String contactPerson;
    private String contactNo;
    private String email;
    private String gstNo;
    
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date startDate;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date endDate;
    private String duration;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateOfLoi;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date tDate;
    
    private String loiNo;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dateOfLoa;
    private String accountManager;
    private String projectManager;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date contractDate;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date singOfDate;
    private String contractPrice;
	public String projectId;
       

}
