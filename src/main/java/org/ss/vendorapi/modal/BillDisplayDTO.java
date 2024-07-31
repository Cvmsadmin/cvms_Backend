package org.ss.vendorapi.modal;
import lombok.Data;
@Data
public class BillDisplayDTO {

	private String accNo;
	private String payAmtBeforeDt;
	private String payAmtAfterDt;
	
	
	private String trackId;
	private String custName;
	private String caseOrBillNo;
	private String billDate;
	private String billDueDate;
	private String dueAmount;
	private String paymentAmount;
	private String convenienceCharge;
	private String excessAmount;
	private String paymentDate;
	private String bankId;
	private String paymentMode;
	private String tranRefNo;
	private String paymentStatus;
	private String elapseDays;
	private String paymentId;
	private String bankRefNo;
	private String serviceName;
	private String emailId;
	private String mobileNo;
	private String bankName;
	private String discomName;
	private String isBulk;
	private String latitude;
	private String longitude;
	private String ipAddress;
	private String paymentSource;
	private String partialPayment;
	private String modifiedDate;
	
}