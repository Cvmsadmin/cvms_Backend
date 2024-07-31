package org.ss.vendorapi.modal;

import lombok.Data;

@Data
public class RequestPayloadDTO {
	
	private String mercid;
	private String orderid;
	private String amount;
	private String order_date;
	private String currency;
	private String ru;
	private AdditionalinfoDTO additional_info;
	private String itemcode;
	private DeviceDTO device;
}