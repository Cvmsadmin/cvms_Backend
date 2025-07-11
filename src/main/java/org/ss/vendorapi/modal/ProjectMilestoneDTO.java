package org.ss.vendorapi.modal;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ProjectMilestoneDTO {
	
	private Long id;  // Added to identify the milestone record

	private String projectId;
	private String serialNumber;
	
	@JsonFormat(pattern="dd/MM/yyyy")
	private Date tDate;
	
    private String days;
    private String deliverables;
    private String amountExcluGst;
    
    private String gstRate;
    private String gstAmount;
    private String amountIncluGst;
    
    private String status;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private Date completionDate;
}
