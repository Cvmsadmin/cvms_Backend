package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ProjectMasterDTO {

	private String serialNumber;
    private String days;
    private String deliverables;
    private String amountExcluGst;
    private String gstPer;
    private String gstAmount;
    private String amountIncluGst;
}
