package org.ss.vendorapi.util;

import java.util.Set;

public class Constants {
	
	public static final String SUCCESS = "SUCCESS";
	public static final String ERROR = "ERROR";
	public static final String FAIL = "FAIL";
	public static final String NA = "NA";
	public static final String AUTH_ONLINE_USER = "AuthenticatedOnlineUser";
	public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
	public static final String UNAUTH_ACCESS = "Unauthorized Access";
	public static final String PARAMETERS_MISSING = "Parameter(s) missing";
	public static final String NO_RECORD_FOUND = "No record found.";
	public static final String INVALID_USER = "Invalid User.";
	public static final String DISCOMID_NOT_MATCHED = "DiscomId not matched.";
	public static final String DETAILS_NOT_MATCHING = "Details not matching.";
	public static final String NEW = "NEW";
	
	// WSDL API Constants URL'S
	public static final String CM_CaseSearch = "CM_CaseSearch";
	public static final String CM_Bills12MnthsAcct = "CM_Bills12MnthsAcct";
	public static final String CM_BillSegmentSearch_Or = "CM_BillSegmentSearch_Or";
	public static final String CM_MeterSearch = "CM_MeterSearch";
	public static final String CM_CustPayment12Mnths = "CM_CustPayment12Mnths";
	public static final String CM_PersonUpdate_WS = "CM_PersonUpdate_WS";
	public static final String CM_DeRegistrationEmailFromWSS = "CM_DeRegistrationEmailFromWSS";
	public static final String CM_SARateHistoryList = "CM_SARateHistoryList";
	public static final String CM_Case = "CM-Case";
	public static final String CM_ServiceAgreement = "CM_ServiceAgreement";
	public static final String CM_CalculateIsdForFatstrack_WS = "CM_CalculateIsdForFatstrack_WS";
	public static final String CM_Order_Or = "CM_Order_Or";
	public static final String CM_MakePaymentFromWSS = "CM_MakePaymentFromWSS";
	public static final String CM_MakeDepositPaymentFromWSS = "CM_MakeDepositPaymentFromWSS";
	public static final String CM_CustomerContact = "CM_CustomerContact";
	public static final String CM_CustomerContactSearch1 = "CM_CustomerContactSearch1";
	public static final String CM_CaseSearch_CR = "CM_CaseSearch_CR";
	
	public static final Set<String> PayType = Set.of(
			"NEWPAY","ASD-FEE","2MMD-FEE","MSD-FEE","AISD-FEE","FTSR-FEE","FTNEW-FEE","REG-FEE"
		);
	
	// for Charges
	public static final double CGSTRate = 9;
	public static final String single_msd = "934";
//	public static final String three_msd = "1731";
	public static final String three_msd_18 = "2321";
	
	// for Reconcilation
	public static final Integer RETRYCOUNT = 5;
	
	public static final String MOBILE = "MOBILE";
	public static final String LANDLINE = "LANDLINE";

	public static final String KNO = "%kNo%";
	public static final String CUSTNAME ="%custName%";
	public static final String PHONE ="%phone%";
	public static final String EMAIL_NOTIFY ="EmailNotify";
	
	
	//For login API
	public static final String TRUE = "true";
	public static final String LOGIN_URL = "%loginURL%";
	//For Email
	public static final String EMAIL_CACHE_KEY = "EmailMap";
	public static final String EMAIL_HEADER_IMG_FIRST = "%headerFirstImg%";
	public static final String EMAIL_HEADER_IMG_SEC = "%headerSecImg%";
	public static final String REGISTARTION_MODULE = "Registration";
	public static final String SOURCE = "source";
	public static final String MODE = "BOTH";
	public static final String VERIFY_MODE = "MOBILE";
	public static final String ONLINE_BILLING_STATUS = "POSTAL";
	public static final String BILLING = "Billing";
	public static final String UPDATE_CONSUMER_DETAILS = "updateConsumerDetails";
	public static final String GET_CONSUMER_DETAILS = "getConsumerDetails";
	public static final String SEND_EMAIL_SERVICE = "sendEmail";
	//For Know your Account Number
	public static final String KNOW_YOUR_ACCOUNT_NUMBER = "knowYourAccountNumber";
	//For Update Mobile Number
//	public static final String SEND_OTP_EMAIL_MOBILE = "sendOTPOnEmailAndMob";
	public static final String SEND_OTP_EMAIL_MOBILE = "sendOTP";
	
	//For whatsApp Subscription
	public static final String UPDATE_WHATSAPP_SUBS_SERVICE = "updateWhatsUpSubscription";
	public static final String ONLINE_BILLING_STATUS_EMAIL = "Email";
	public static final String SERVICE_REQ_FOR_WHATSAPP_SUBS = "wp_subs";
	public static final String SEND_OTP_ON_EMAIL_MOBILE_NO = "sendOptEmailAndMobile";
	public static final String SUCCESS_CODE = "200";
	public static final String EXPECTATION_FAILED = "417";
	public static final String INTERNAL_SERVER_ERROR_CODE = "500";
	public static final String SERVICE_UNAVAILABLE = "503";
	public static final String ALREADY_REPORTED = "208";


	public static final String GET_ACCOUNT_USING_METER_SERIAL ="getAccountUsingMeterSerial";
	public static final String ACCOUNT_ARREAR_STATUS ="getArrearStatus";
	public static final String IFSC_CODE ="ifscCode";
	public static final String BILL_ON_HOLD = "onHold";
	public static final String VIEW_BILL_PDF_FLAG = "ACC";
	public static final String VIEW_BILL_DOWNLOAD_PDF_FLAG = "BILL";
	public static final String VIEW_BILL="viewBill";
	public static final String BANK_CODE_PREFIX="bankCodePrefix";
	// email for feedback
	public static final String NAME="%name%";
	public static final String ADDRESS="%addr%";
	public static final String ACCNO="%accno%";
	public static final String SERVCONNNO="%servconn%";
	public static final String DISCOM="%discom%";
	public static final String CITY="%city%";
	public static final String STATE="%state%";
	public static final String PIN="%pin%";
	public static final String COUNTRY="%country%";
	
	
	public static final String COMMENTS="%comm%";
	//public static final String PHONE="%phone%";	
	public static final String FEEDBACK_SERVICE_NAME="feedbackDomainName";
	public static final String EMAIL="%emailId%";
	public static final String FEEDBACK="feedback";
	public static final String EMAIL_SOURCE="WSS";
	public static final String EMAIL_MODE="EMAIL";
	public static final String commonErrorCode = "CCB_ISE_SE_"+SERVICE_UNAVAILABLE;
	public static final String commonExceptionCode = "CCB_EX_"+Constants.EXPECTATION_FAILED;
	public static final String commonDateValidation = "COM_DVE_416"; //Common Date Validation Error
	public static final String PREPAID_ACC_ERROR_CODE= "PREPAID_ACC_306";
	public static final String disconnecedErrorCode = "CON_DIS_112";
	public static final String fromDateGreaterthanToDateErrorCode = "DT_FDGTTD_417"; //From Date is greater than to date
	
	public static final String INVLD_ACC ="INVLD_ACC_308";
	public static final String DIS_VAL ="DIS_VAL_303";
	public static final String DIS_VAL_302 ="DIS_VAL_302";
	public static final String DIS_VAL_301="DIS_VAL_301";
	public static final String SND_OTP_200="SND_OTP_200";
	public static final String SND_OTP_301="SND_OTP_301";
	public static final String SND_OTP_302="SND_OTP_302";
	public static final String ENTR_VLD="ENTR_VLD_305";
	public static final String USR_ALRD="USR_ALRD_200";
	public static final String SVD_USR="SVD_USR_307";
	public static final String UPD_USR_CCB="UPD_USR_CCB_305";
	public static final String UPD_USR="UPD_USR_302";
	public static final String VRFY_OTP="VRFY_OTP_200";
	public static final String VRFY_OTP_303="VRFY_OTP_303";
	public static final String VRFY_OTP_302="VRFY_OTP_302";
	public static final String VRFY_OTP_304="VRFY_OTP_304";
	public static final String BILL_VALI_303="BILL_VALI_303";
	public static final String BILL_VALI_302="BILL_VALI_302";
	public static final String USR_CON_TYPE="USR_CON_TYPE_307";
	public static final String ACCT_VALI="ACCT_VALI_200";
	public static final String ACCT_VALI_303="ACCT_VALI_303";
	public static final String ACCT_VALI_302="ACCT_VALI_302";
	public static final String SEND_OTP_303="SEND_OTP_303";
	public static final String SEND_OTP_304="SEND_OTP_304";
	public static final String UPD_MOB_200="UPD_MOB_200";
	public static final String UPD_USER_305="UPD_USER_305";
	public static final String USR_NOT_EXIST="USR_NOT_EXIST_200";
	public static final String UPD_MOB="UPD_MOB_303";
	public static final String INDV_BILL="INDV_BILL_303";
	public static final String WRNG_BILL_NO="WRNG_BILL_NO";
	public static final String INVLD_DTL="INVLD_DTL";
	
	//Service Request Codes
	public static final String prepaidConnection = "PREPAID_303";
	public static final String requestRaised = "SR_100";
	public static final String caseIdError = "SR_103";
	public static final String billPending = "SR_105";
	public static final String successCode = "SR_200";
	public static final String dynamicMessageCode = "SR_110";
	public static final String eligibleAccount = "CONN_TRANS_200";
	public static final String disputedBill = "SR_104";
	public static final String outstandingAmount = "CONN_DISCONN_100";
	public static final String eligibleAccountConnDisconn =  "CONN_DISCON_200";
	public static final String getPancard =  "GET_PAN_304";
	public static final String fetchPancard =  "GET_PAN_303";
	public static final String fetchPancardSuccess =  "GET_PAN_200";
	public static final String panUpdateFailure =  "PAN_304";
	public static final String pancardSuccess =  "PAN_200";
	public static final String invalidPanDetails= "PAN_305";
	public static final String userNotFoundPF = "PF_404";
	public static final String updateProfileSuccessCode = "PF_200";
	public static final String profileNotUpdated = "PF_301";
	public static final String securityQuestionSuccess = "SQ_200";
	public static final String userNotFoundSQ ="SQ_404";
	public static final String validOldPassword = "CP_300";
	public static final String invalidOldPassword = "CP_301";
	public static final String nullNewPassword = "CP_201";
	public static final String passwordUpdated= "CP_200";
	public static final String failedToUpdatePassword= "CP_302";
	public static final String eligibleForLE = "CODE_LE_200";
	public static final String amountPendingLE= "CODE_LE_301";
	public static final String BHPaccounts = "CODE_LE_302";
	public static final String alreadyRiasedLE = "CODE_LE_303";
	public static final String dynamicMessageLE = "CODE_LE_305";
	public static final String loadChargeSuccess = "CODE_LC_200";
	public static final String wrongDiscom = "WR_DIS_300";
	public static final String discomDetailsNF = "DIS_NDF_300";
	public static final String consumerDetailsNF = "GCD_NA_205";
	public static final String billDetailsNF = "GBILLD_NA_205";
	public static final String invalidAccount = "IA_300";
}
