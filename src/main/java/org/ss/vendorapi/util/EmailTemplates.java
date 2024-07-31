package org.ss.vendorapi.util;

import java.util.HashMap;
import java.util.Map;

public class EmailTemplates {
	
	public static final Map<String, String> emailTemplateMap = getEmailTemplateMap();
	
	private static Map<String, String> getEmailTemplateMap() {
		
		String newConnEmailTemplate = "";
		newConnEmailTemplate += " Dear #consumerName#,<br><br>";
		newConnEmailTemplate += " Thanks for raising New Connection request with #discomNameCap#.<br><br>";
		newConnEmailTemplate += " Your Request ID is <b>#registrationNo#</b>.<br><br>";
		newConnEmailTemplate += " Online Payment of Registration Fee is Mandatory. Kindly Visit Sub Division Office for Mandatory Document Verification.<br><br>";
		newConnEmailTemplate += " Upon successful verification of original documents furnished, your request will be processed and premises will be serviced:<br><br>";
		newConnEmailTemplate += " Within 30 days in case where service is feasible from existing network.<br><br>";
		newConnEmailTemplate += " Within 45 days if network expansion/enhancement is required for providing connect, or call us at 1912, or e-mail us at gmcr.work@#discomNameSmall#.co.in<br><br>";
		newConnEmailTemplate += " Best Regards,<br>";
		newConnEmailTemplate += " General Manager (Customer Relations), #discomNameCap#<br>";
	    
		
		//use in when account get created(/api/registerUser)
		String accountCreationTemplate = "";
		accountCreationTemplate += " Dear <b> #consumerName# </b>,<br><br>";
		accountCreationTemplate += " Your Account for accessing Online Services provided by #discomNameCap#, has been successfully created.<br> "
								  + "Please contact Customer Care at<br>";
		accountCreationTemplate += " 1912, if you have any problem logging in.<br> Please access WebLink for logging into your Account.<br><br>";
		accountCreationTemplate += " User name: <b>#userName#</b><br>";
		accountCreationTemplate += " Password: <b>#password#</b><br><br>";
		accountCreationTemplate += " Best Regards,<br>";
		accountCreationTemplate += " General Manager (Customer Relations), #discomNameCap#<p>";
		
		
		 
		// used in (/api/PGPaymentResponseController)
		String billPaymentConfirmation = "";
		billPaymentConfirmation += " Dear Consumer,<br><br>";
		billPaymentConfirmation += " Greetings from BESCOM.Thank you for making Payment of Rs. "
									+ " <b>#amount# </b> against Account ID <b> #accountNo#</b>.<br>The transaction ID for this payment is #transactionId#,"
									+ "and the payment was processed on #payDate#<br><br> "
									+ "Kindly note that Cheque payment is subject to realization.<br><br>"
									+ "Yours sincerely,<br>";
		billPaymentConfirmation += " Asst. Executive Engineer,BESCOM</p>";
		
		
		// used in (/api/resetpassword)
		String passwordResetConfirmation = "";
		passwordResetConfirmation += " Dear #consumerName#,<br /><br />";
		
		passwordResetConfirmation += " Your Password for your BESCOM Online Account with Username #userName#"
									  + " has been Reset.<br /><br />";
		passwordResetConfirmation += " You need to change the Password, "
									  + "when you access your Account for the first time, after Password Reset.<br /><br />";
		
		passwordResetConfirmation += "UserName:  #userName#<br />";
		passwordResetConfirmation += "Password: #password#<br /><br />";
		
		passwordResetConfirmation += "Please contact Customer Care at 1912, if you have any problem logging in.<br /><br />";
		
		passwordResetConfirmation += "Please access<a href=\"https://bescom.co.in/SCP/Myhome.aspx\"> WebLink </a>, for logging into your Account.<br /><br />"
									+ "Best Regards,<br />"
									+ "General Manager (Customer Relations), BESCOM</p>";
		
		
		
		          // used in (/api/service)
				String serviceRequestNotification = "";
				serviceRequestNotification  += "<p>Dear Consumer,<br /><br />";
				serviceRequestNotification  += " Thanks for raising #requestType# Service Request with BESCOM.<br />";
				serviceRequestNotification  += "Your request ID is #requestId#.<br />";
						
											
				serviceRequestNotification  += "Please access www.bescom.org to get the latest update on this Service Request, or call us at 1912, "
												+ "or email us at wss@bescom.co.in.<br />Best Regards,<br />";
					
				serviceRequestNotification  += "General Manager (Customer Relations), BESCOM</p>";
		
		
		
	    Map<String,String> templateMap = new HashMap<String,String>();
	    templateMap.put("newConnectionTemplate", newConnEmailTemplate);
	    templateMap.put("accountCreationTemplate", accountCreationTemplate);
	    templateMap.put("billPaymentConfirmation", billPaymentConfirmation);
	    templateMap.put("passwordResetConfirmation", passwordResetConfirmation);
	    templateMap.put("serviceRequestNotification", serviceRequestNotification);
	    return templateMap;
	}
}
