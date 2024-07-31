package org.ss.vendorapi.modal.response;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class ConsumptionDetails {
	private String totalLoad;
	private String totalConsumption;

    // PageFlow instance created for handling with added equipment into calculated table.
    Map<Object, ConsumptionCalculationDisplay> addedEquipMapSess=new HashMap<Object,ConsumptionCalculationDisplay>();
	

}
