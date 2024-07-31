package org.ss.vendorapi.modal.response;

import lombok.Data;

/**
 * @author Komal Dagar
 * @since 20 Sep, 2023
 * @purpose Name Correction API Modal
 */

@Data
public class ServiceRequestModal {	
	

	private String kno;
	
	private String transctionRefrencenumber;

	private String serviceRequestNumber;

	private String amount;

	private String mobileNo;

	private String requestType;

	private String load;

	private String reason;

	private String nameAddPhone;

	private String newName;

	private String appDate;

	private String newCategory;

	private String newLoad;
	
	
	/** START ::  MODAL VARIABLES FOR ADDRESS CHANGE */
	
	private String houseNumber;
	
	private String cityName;
	
	private String districtName;
	
	private String pincode;
	
	private String areaName;
	
	private String buildingName;
	
	/** END ::  MODAL VARIABLES FOR ADDRESS CHANGE */
	
	
	/** START ::  MODAL VARIABLES FOR CONNECTION TRANSFER REQUEST  */

	private String applicantName;
	private String fatherName;
	
	/** END ::  MODAL VARIABLES FOR CONNECTION TRANSFER REQUEST */
	private String panNumber;
	/** START ::  MODAL VARIABLES FOR UPDATE PANCARD NUMBER REQUEST  */
	private String typeOfConnection;
	private String typeOfName;
	private String discomName;
	
	
	private String serialNumber;
	private String meterComplaintType;
	
	private String disputedBillNo;
    private String kwh;
    private String kvah;
    private String demand;
    
    private String oldAccountNo;
   	private String personName;
   	private String newAccountNo;
	private String caseFilter;
	private String email;
	private String name;
}
