package org.ss.vendorapi.modal.response;

import lombok.Data;

@Data
public class CaseStatus {
	
	String caseId;
	String caseCreationDateTime;
	String caseDesc;
	String caseStatus;
	String remarks;
	String errCode;
	String errDesc;

}