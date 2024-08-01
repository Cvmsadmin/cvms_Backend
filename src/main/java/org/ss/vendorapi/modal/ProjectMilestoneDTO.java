package org.ss.vendorapi.modal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProjectMilestoneDTO {

	private String projectId;
	private String serialNumber;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date tDate;
    private String days;
    private String deliverables;
    private String amountExcluGst;
    
    private String gst;
    private String amountIncluGst;
}
