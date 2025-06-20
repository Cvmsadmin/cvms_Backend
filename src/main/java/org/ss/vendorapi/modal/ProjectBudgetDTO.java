package org.ss.vendorapi.modal;

import java.util.List;

import lombok.Data;

@Data
public class ProjectBudgetDTO {
	
	 private String projectName;
	 private String clientName;
	 private List<FinancialYearDTO> financialYears;
	    

}
