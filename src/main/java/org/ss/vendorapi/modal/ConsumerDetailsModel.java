package org.ss.vendorapi.modal;

import java.io.Serializable;

import lombok.Data;

@Data
public class ConsumerDetailsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String kno;
	private String conumerName;
	private String fatherName;
	private String Address;
	private String sanctionLoad;
	private String unitOfLoad;
	private String supplyType;
	private String outstandingArr;		 
	private String lastPayDate;
	private String lastPayAmount;
	private String td_pd_status;
	private String voltageLevel;
}
