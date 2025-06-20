package org.ss.vendorapi.modal;

import java.util.List;

import lombok.Data;

@Data
public class FinancialYearDTO {

	 private String year;
	 private List<LineItemDTO> lineItems;
}
