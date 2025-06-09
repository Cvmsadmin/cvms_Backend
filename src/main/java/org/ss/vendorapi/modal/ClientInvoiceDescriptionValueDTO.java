package org.ss.vendorapi.modal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ClientInvoiceDescriptionValueDTO {
	
	 private Long id;
	 private String itemDescription;
	 private String baseValue;
	 private Double gstPer;
	 private Double cgst;
	 private Double sgst;
	 private Double igst;
	 private Double amtInclGst;
	 private String milestone;
	 
//	 @JsonProperty("SubMilestone")
	 private String subMilestone;



}
