package org.ss.vendorapi.modal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class ProfitLossRequestDTO {
	
	 private ClientDTO clientName;
	 private ProjectDTO projectName;	  
	 private List<ProfitLossMasterDTO> formFields; // List for form fields
 
}


