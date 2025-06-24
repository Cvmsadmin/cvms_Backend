package org.ss.vendorapi.modal;

import java.util.List;

import org.ss.vendorapi.entity.MilestoneCategory;
import org.ss.vendorapi.entity.ProjectMasterEntity;

import lombok.Data;

@Data
public class ProjectResponseDTO {
	
	public ProjectResponseDTO(ProjectMasterEntity project, List<MilestoneCategory> parts) {
        this.project = project;
        this.parts = parts;
    } 
	
	private ProjectMasterEntity project;
    private List<MilestoneCategory> parts;

}
