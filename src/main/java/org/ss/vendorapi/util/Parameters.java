package org.ss.vendorapi.util;

public class Parameters {
	
	public static final String status = "status";
	public static final String statusMsg = "statusMsg";
	public static final String errorCode = "errorCode";
	public static final String statusCode = "statusCode";
	public static final String roadCutTrackStatus = "roadCutTrackStatus";
	public static final String INVALID_LOGIN_COUNT = "INVALID_LOGIN_COUNT";
	public static final String RESPONSE = "Response";
	public static final String MeterID = "MeterID";
	public static final String MeterConfigurationID = "MeterConfigurationID";
	public static final String PreviousReading = "PreviousReading";
	public static final String CurrentReading = "CurrentReading";
	public static final String Usage = "Usage";
	public static final String Unit = "Unit";
	public static final String CONSUMER_RESPONSE = "ConsumerResponse";
	public static final String CUSTOMER_RESPONSE = "CustomerResponse";
	public static final String StartRegisterReading = "StartRegisterReading";
	public static final String EndRegisterReading = "EndRegisterReading";
	public static final String MeasuredQuantity = "MeasuredQuantity";
	public static final String REPORT_NAME = "ReportName";
	public static final String IS_BILL_ON_HOLD = "isBillOnHold";
	
	public class USP_LOGUSEREXISTS{
		public static final String v_CustomerAccountNumber = "v_CustomerAccountNumber";
		public static final String v_PersonId = "v_PersonId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String v_opResult = "v_opResult";
	}
	
	public class SCP_ASM_AuthenticateUser_Get{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v_ipPassword = "v_ipPassword";
		public static final String v_ipAuthType = "v_ipAuthType";
		public static final String v_opResult = "v_opResult";
		public static final String v_opFirstName = "v_opFirstName";
		public static final String v_ipEmailAddress = "v_ipEmailAddress";
		public static final String v_AutoGenFlag = "v_AutoGenFlag";
		public static final String v_opPersonID = "v_opPersonID";
		public static final String v_opAccount = "v_opAccount";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
	}
	
	public class SCP_ASM_LOCKUSER{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_opResult = "v_opResult";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";		
	}

	public class SCP_USP_NEWCONNGETACCOUNTIDS{
		public static final String v_RequestID = "v_RequestID";
		public static final String v_AccountID = "v_AccountID";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
	}
	
	public class SCP_USP_GETROADCUTDETAILS{
		public static final String cv_1 = "cv_1";
		public static final String OrderId_v = "OrderId_v";
	}
	
	public class GETCUSTOMERACCOUNTID{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
	}
	
	public class GETDISCOMIDOFUSER{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String cv_1 = "cv_1";
	}
	
	public class SCP_ASM_USERDETAILS_GET{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_opValidationType = "v_opValidationType";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
	}
	
	public class SCP_SETPASSWORD{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_ipPassword = "v_ipPassword";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String v_opResult = "v_opResult";
	}
	
	public class USP_GETKEYVALUE{
		public static final String v_Condition = "v_Condition";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
	}

	public class USP_SET_WSS_TRANSACTION_ID{
		public static final String v_PersonId = "v_PersonId";
		public static final String v_ClientIP = "v_ClientIP";
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_WSS_Transaction_Id = "v_WSS_Transaction_Id";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
	}
	
	public class USP_SET_PAYMENT_TRANSACTION{
		public static final String v_Payer_Account_Id = "v_Payer_Account_Id";
		public static final String v_Payment_Due_Amount = "v_Payment_Due_Amount";
		public static final String v_Payment_Amount = "v_Payment_Amount";
		public static final String v_Payment_Due_Date = "v_Payment_Due_Date";
		public static final String v_WSS_Transaction_Id = "v_WSS_Transaction_Id";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v_Payment_Type = "v_Payment_Type";
		public static final String v_RequestID = "v_RequestID";
		public static final String v_OrderId = "v_OrderId";
		public static final String v_BdOrderId = "v_BdOrderId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
	}
	
	public class USP_SET_GATEWAY_TRANSACT{
		public static final String v_WSS_Transaction_Id = "v_WSS_Transaction_Id";
		public static final String v_PG_Instance_Id = "v_PG_Instance_Id";
		public static final String v_Merchant_Id = "v_Merchant_Id";
		public static final String v_Perform = "v_Perform";
		public static final String v_Device_Category = "v_Device_Category";
		public static final String v_Currency_Code = "v_Currency_Code";
		public static final String v_Order_Desc = "v_Order_Desc";
		public static final String v_Recur_Frequency = "v_Recur_Frequency";
		public static final String v_Recur_Expiry = "v_Recur_Expiry";
		public static final String v_Installments = "v_Installments";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v_Amount_Paying = "v_Amount_Paying";
		public static final String v_PGCharges = "v_PGCharges";
		public static final String v_Payment_Mode = "v_Payment_Mode";
		public static final String v_OrderId = "v_OrderId";
		public static final String v_BdOrderId = "v_BdOrderId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
	}
	
	/**
	 * Procedure Name : USP_USERACCOUNTEXISTS
	 * @author Anukriti Gusain
	 * @since 04-01-2023
	 *
	 */
	public final class USP_USERACCOUNTEXISTS{
		public static final String V_CUSTOMERACCOUNTNUMBER="v_CustomerAccountNumber";  
		public static final String V_PERSONID="v_PersonId";              
		public static final String V__DBTIME="v__DbTime";                 
		public static final String V__ENDTIME="v__EndTime";           
		public static final String V_OPRESULT="v_opResult";
	}
	/**
	 * Procedure Name : USP_CCBWSSACDETAILS1
	 * @author Anukriti Gusain
	 * @since 06-Jan-2023
	 *
	 */
	public final class USP_CCBWSSACDETAILS1{
		public static final String V_IPPERSONID ="V_IPPERSONID";
		public static final String CV_1="CV_1";
	}
	
	/**
	 * Procedure Name : USP_GET_LASTTRAXSTATUSDETAILS
	 * @author Anukriti Gusain
	 * @since 10-Jan-2023
	 *
	 */
	public final class USP_GET_LASTTRAXSTATUSDETAILS{
		public static final String V_IPPERSONID ="V_IPPERSONID";
		public static final String CV_1="CV_1";
	}
	
	public final class USP_GET_OPEN_FASTSRS{
		public static final String V_ACCOUNTID ="V_ACCOUNTID";
		public static final String V_SRCATEGORY="V_SRCATEGORY";
		public static final String v_No_Of_Open_Cases="v_No_Of_Open_Cases";
	}
	
	public final class SCP_ASM_REGISTERUSER{
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_ipFirstName = "v_ipFirstName";
		public static final String v_ipLastName = "v_ipLastName";
		public static final String v_ipHasAccountNumber = "v_ipHasAccountNumber";
		public static final String v_ipEmailAddress = "v_ipEmailAddress";
		public static final String v_ipPassword = "v_ipPassword";
		public static final String v_ipPersonID = "v_ipPersonID";
		public static final String v_ipSecurityQuestionID = "v_ipSecurityQuestionID";
		public static final String v_ipSubscriptionPreference = "v_ipSubscriptionPreference";
		public static final String v_ipZipCode = "v_ipZipCode";
		public static final String v_ipBirthDate = "v_ipBirthDate";
		public static final String v_ipResidentialPhone = "v_ipResidentialPhone";
		public static final String v_ipMobilePhone = "v_ipMobilePhone";
		public static final String v_ipSecurityAnswer = "v_ipSecurityAnswer";
		public static final String v_ipAlternateEmailAddress = "v_ipAlternateEmailAddress";
		public static final String v_ipCustomerAccountsList = "v_ipCustomerAccountsList";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v_opResult = "v_opResult";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String v_ipNewUserID = "v_ipNewUserID";
	}
	
	public final class USP_INSERT_SRLOG{
		public static final String v_SRCATEGORY = "v_SRCATEGORY";
		public static final String v_RAISEDBY = "v_RAISEDBY";
		public static final String v_CLIENTIP = "v_CLIENTIP";
		public static final String v_SRID = "v_SRID";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";

	}
	
	public final class USP_MASTERDATA{
		public static final String v_TableName = "v_TableName";
		public static final String v_Condition = "v_Condition";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String CV_1="cv_1";

	}
	
	public final class USP_SAVESRLOG{
		public static final String v_SRCATEGORY ="v_SRCATEGORY";
		public static final String v_RAISEDBY="v_RAISEDBY";
		public static final String v_CLIENTIP="v_CLIENTIP";
		public static final String v_SRID="v_SRID";
		public static final String v_ipDiscomId="v_ipDiscomId";
		public static final String v_SRENTITY="v_SRENTITY";
		public static final String v_AccountID="v_AccountID";
		public static final String v_SubdivisionCode="v_SubdivisionCode";
		public static final String v_SubdivisionName="v_SubdivisionName";
		public static final String v_ConsumerName="v_ConsumerName";
		public static final String v_WSS_SRID="v_WSS_SRID";
		public static final String v_person_Id="v_person_Id";
	}
	
	public final class USP_SET_FASTSR_CHARGES{
		public static final String v_RequestID ="v_RequestID";
		public static final String v_Charge_TYPE="v_Charge_TYPE";
		public static final String v_Charge_Amount="v_Charge_Amount";
		public static final String v_Is_Addtional="v_Is_Addtional";
		public static final String v__DbTime="v__DbTime";
		public static final String v__EndTime="v__EndTime";
	}
	
	public final class USP_SET_FASTSR_DOCUMENTS{
		public static final String v_RequestID ="v_RequestID";
		public static final String v_DOCUMENT_TYPE="v_DOCUMENT_TYPE";
		public static final String v_DOCUMENT_PATH="v_DOCUMENT_PATH";
		public static final String v_DOCUMENT_ID="v_DOCUMENT_ID";
		public static final String v_DOCUMENTSUB_ID="v_DOCUMENTSUB_ID";
		public static final String v__DbTime="v__DbTime";
		public static final String v__EndTime="v__EndTime";
	}
	
	public class SCP_GET_FTSRDETAILSTRACK{
		public static final String v_RequestID = "v_RequestID";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
	}
	
	public class SCP_GET_FTSRCHARGESDETAILS{
		public static final String v_RequestID = "v_RequestID";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
	}
	
	public class SCP_GET_FTSRTRANDETAILS{
		public static final String v_RequestId = "v_RequestId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
	}
	
	public class SCP_GET_FTSRDETAILSTRACK_SDO{
		public static final String v_Request = "v_Request";
		public static final String v_caseType = "v_caseType";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";	
	}
	
	public class SCP_GET_FTSRTRACK_FRACTNO_SDO{
		public static final String v_AccountNo = "v_AccountNo";
		public static final String v_caseType = "v_caseType";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		public static final String cv_1 = "cv_1";
		
	}
	
	public class USP_SET_WSS_ACKNOWLEDGEMENT_ID{
		public static final String v_WSS_Transaction_Id = "v_WSS_Transaction_Id";
		public static final String v_Payer_Account_Id = "v_Payer_Account_Id";
		public static final String v_WSS_Acknowledgement_Id = "v_WSS_Acknowledgement_Id";
	}
	
	public class USP_UPDATE_BANK_TRANSACTION{
		public static final String v_Transaction_Id = "v_Transaction_Id";
		public static final String v_Transaction_Status = "v_Transaction_Status";
		public static final String v_WSS_Transaction_Id = "v_WSS_Transaction_Id";
		public static final String v_Transation_Type = "v_Transation_Type";
		public static final String v_Installments = "v_Installments";
		public static final String v_Credit_Amount = "v_Credit_Amount";
		public static final String v_Exponent = "v_Exponent";
		public static final String v_Threeds_ECI = "v_Threeds_ECI";
		public static final String v_PG_Error_Code = "v_PG_Error_Code";
		public static final String v_PG_Error_Detail = "v_PG_Error_Detail";
		public static final String v_PG_Error_MSG = "v_PG_Error_MSG";
		public static final String v_PG_HashMatch = "v_PG_HashMatch";
		public static final String v_BDBankId = "v_BDBankId";
		public static final String v_BankMerchantId = "v_BankMerchantId";
		public static final String v_ItemCode = "v_ItemCode";
		public static final String v_SecurityType = "v_SecurityType";
		public static final String v_SecurityId = "v_SecurityId";
		public static final String v_SecurityPassword = "v_SecurityPassword";
		public static final String v_SettlementType = "v_SettlementType";
		public static final String v_BankReferenceNo = "v_BankReferenceNo";
		public static final String v_mode_of_payment = "v_mode_of_payment";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		
	}
	
	public class USP_SET_NEWCONNREQUEST{
		public static final String v_PersonID = "v_PersonID";
		public static final String v_RequestID = "v_RequestID";
		public static final String v_CustomerAccountNumber = "v_CustomerAccountNumber";
		public static final String v_NCEntity = "v_NCEntity";
		public static final String v_IPAddress = "v_IPAddress";
		public static final String v_RegFee = "v_RegFee";
		public static final String v_SubdivisionName = "v_SubdivisionName";
		public static final String v_SubdivisionCode = "v_SubdivisionCode";
		public static final String v_ipDiscomId = "v_ipDiscomId";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
	}

	public class SCP_ASM_RESETPASSWORD_MODIFY{
		public static final String v_ipNewUserID = "v_ipNewUserID";
		public static final String v_ipUserID = "v_ipUserID";
		public static final String v_ipNewPassword  = "v_ipNewPassword";
		public static final String v_ipPassword = "v_ipPassword";
		public static final String v_opResult = "v_opResult";
		public static final String v__DbTime = "v__DbTime";
		public static final String v__EndTime = "v__EndTime";
		
	}
	
}
