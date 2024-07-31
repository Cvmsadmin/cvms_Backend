package org.ss.vendorapi.modal;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectRequestDTO {
	
	private List<ProjectMasterDTO> milestone;
	
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
    private Date startDate;
    private Date endDate;
    private String duration;
    private Date dateOfLoi;
    private Date t0Date;
    private String loiNo;
    private Date dateOfLoa;
    private String accountManager;
    private String projectManager;
    private Date contractDate;
    private Date singOfDate;
    private String contractPrice;
    
    public List<ProjectMasterDTO> getProject() {
        return milestone;
    }

    public void setProject(List<ProjectMasterDTO> milestone) {
        this.milestone = milestone;
    }

}
