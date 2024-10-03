/*
 * package org.ss.vendorapi.helper;
 * 
 * import java.text.DecimalFormat; import java.util.HashMap; import
 * java.util.Iterator; import java.util.Map;
 * 
 * import org.ss.vendorapi.modal.response.ConsumptionCalculationDisplay;
 * 
 * public class ConsumptionCalculatorHelper {
 * 
 * public static Map<String, Object> calculateGrandTotalLoadConsumption(Map<?,
 * ?> addedEquipMapSess) { Iterator<?> hdKey =
 * addedEquipMapSess.keySet().iterator(); String[] itrKey = new
 * String[addedEquipMapSess.keySet().size()]; int i = 0; int totalLoad = 0;
 * double totalConsump = 0; Map<String, Object> loadConsumpMap = new
 * HashMap<String, Object>(); DecimalFormat df = new DecimalFormat("0.00");
 * while (hdKey.hasNext()) {
 * 
 * itrKey[i] = hdKey.next().toString(); addedEquipMapSess.get(itrKey[i]);
 * ConsumptionCalculationDisplay consumptionCalculationDisplay =
 * (ConsumptionCalculationDisplay)
 * addedEquipMapSess.get(Integer.parseInt(itrKey[i])); totalLoad = totalLoad +
 * consumptionCalculationDisplay.getTotalLoad(); totalConsump = totalConsump +
 * Double.parseDouble(consumptionCalculationDisplay.getTotalConsumption());
 * 
 * i++;
 * 
 * } loadConsumpMap.put("TotalLoad", totalLoad);
 * loadConsumpMap.put("TotalConsumption", df.format(totalConsump));
 * 
 * 
 * 
 * return loadConsumpMap; }
 * 
 * }
 */