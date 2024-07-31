package org.ss.vendorapi.modal.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class LastOnlinePaymentReciept implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String paymentSource;
	private String receiptId;
	private String paymentAmount;
	private String paymentDate;
	private String billId;
	private String transmitId;
	private String response;
	private String mobileNumber;
	private String emailAddress;
	private String subDivision;
	private String division;
	private String discom;
	private String customerName;
	private String accountNo;
	private String accountInfo;
	private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String pinCode;
	private String paymentAmountInWords;

	private String city;
	private String state;
	
	
	

	

}
