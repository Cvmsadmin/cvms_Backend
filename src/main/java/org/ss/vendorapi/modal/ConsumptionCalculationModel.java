package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ConsumptionCalculationModel {

	private String appliances;
	private String power;
	private String noOfEquipment;
	private String hourUsePerDay;
	private String dayUsePerMonth;

}
