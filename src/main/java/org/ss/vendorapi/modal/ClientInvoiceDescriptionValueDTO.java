package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class ClientInvoiceDescriptionValueDTO {
	
	 private Long id;
	 private String itemDescription;
	 private Double baseValue;
	 private Double gstPer;
	 private Double cgst;
	 private Double sgst;
	 private Double igst;
	 private Double amtInclGst;

}
