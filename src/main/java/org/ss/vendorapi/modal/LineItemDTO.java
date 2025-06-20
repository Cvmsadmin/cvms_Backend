package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class LineItemDTO {
	
	private String item;
    private double budgetCost;
    private double actualCost;
    private double budgetIncome;
    private double actualIncome;

}
