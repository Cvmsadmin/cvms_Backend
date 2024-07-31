package org.ss.vendorapi.modal.response;

import lombok.Data;

@Data
public class ConsumptionCalculationDisplay {
	
	private String appliances;
	private String power;
	private String noOfEquipment;
	private String hourUsePerDay;
	private String dayUsePerMonth;
	private Integer totalLoad;
	private String totalConsumption;

	

}
