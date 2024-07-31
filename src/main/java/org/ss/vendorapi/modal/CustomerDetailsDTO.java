package org.ss.vendorapi.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import lombok.Data;
@Data
public class CustomerDetailsDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    // Added by Naman
    private String employeeId;
    private String baseLocation;
    private String MiddleName;
    private String Phone;
    private String PhysicalLocation;
    private String ClientName;
    private String Address;
    private String City;
    private String State;
    private String District;
    private String Pincode;
    private String ContactPerson;
    private String ContactNo;
    private String Gst;
    private String Pan;
    private String TypeOfService;
    private String ACcountManager;
    private String VendorName;
    private String PanNo;
    private String srNo;
    private String serviceName;
//    private String ACcountManager;
   //  
	private String kno;
    private String bookNo;
    private String firstName;
    private String lastName;
    private String mobileNo;
    private String status;
    private String phoneNo;
    private String currentAddress;
    private String billingAddress;
    private AddressResDTO installationAddress;
    private String email;
    private String confirmEmail;
    private String category;
    private String subCategory;
    private String dueAmount;
    private String dueDate;
    private String billNo;
    private String dateOfBirth;
    private String onlineBillingStatus;
    private String discomName;
    private String groupName;
    private String division;
    private String subDivision;
    private String supplyType;
    private String sanctionedLoad;
    private String securityAmount;
    private String customerIndexNumber;
    private String name;
    private String currentLoad;
    private HashMap mapForEmailTemplates;
    private ArrayList<String> secondaryNumber;
    private Double paymentMade;
    private String personId;
    private String paymentDate;
    private String accountInfo;
    private String consumerType;
    private String dueBillAmt;
    private String mothersName;
    private String typeOfMeter;
    private String typeOfPhase;
    private String typeOfPlace;
    private String billDate;
    private String lastLogin;
    private String lastPaidAmount = "0";
    private String lastPaymentDate="";
    private String typeOfConnection;  
	private String payAmtBeforeDueDt;
    private String payAmtAfterDueDt;
    private String divCode;
    private String modifiedDate;
    
    private String relationName;
    private AddressResDTO premiseAddress;
    private AddressResDTO billingAddresss;
    private String discom;
    private String upassword;
    private String securityQuestion;
    private String securityAnswer;
    private String sbmBillNo;
    private String updMobNoPgName;
    private String otp;
    
	private String primaryEmail;
	private String newEmail;
	private String newMobileNo;
	private String verifyStatus;
	private String statusSelection;	
	private String country;
	private String trackId;
    //Added by mathew start
    private String connectionStatus;
    private String purposeOfSupply;
    private String typeOfConnectionPoint;
    private String chequeDshnrCount;
    private String mode;
    //Added by mathew end
    private String resendPass;
    private String resendOldPass="false";
    private String ampISP;
    private String userId;
    private String password;
    private String retypePassword;
    private String role;
	}