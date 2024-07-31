package org.ss.vendorapi.helper;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.util.HashMap;

import org.springframework.core.env.Environment;
import org.ss.vendorapi.modal.SMSDetailsDTO;

public class SMSHelper {
//
	private static final Logger logger = System.getLogger("SMSHelper");
	
	public SMSDetailsDTO getSMSDetails(SMSDetailsDTO smsDetailsDTOObj,String sendTo,HashMap<String, String> mapForSMS,Environment env) {
		final String METHOD_NAME = "getSMSDetails";
		SMSDetailsDTO smsDetailsDTO =  new SMSDetailsDTO();
		String smsText = null;
		logger.log(Level.INFO, "Inside " +METHOD_NAME+" start");
		try {
//			smsDataMap = (HashMap<String, SMSDetailsDTO>) UPPCLCacheManager
//									.getCacheValue(CACHE_NAME,
//									FrameworkConstants.SMS_CACHE_KEY.toString());
			
//			env.getProperty(sms.registration.text);
//			smsDetailsCacheDTO =  smsDataMap.get(module);
			//set to smsDetailsDTO methods smsDetailsCacheDTO
			smsDetailsDTO.setFrom(smsDetailsDTOObj.getFrom());
			smsDetailsDTO.setMessage(smsDetailsDTOObj.getMessage());
			smsDetailsDTO.setMobileno(sendTo);
//			smsText =	SMSHelper.putValuesInFile(smsDetailsDTO.getMessage(),mapForSMS);
			smsText = env.getProperty("send.sms.registration.msg");
//			smsText = env.getProperty("send.sms.text")+sendTo.substring(6,10)+" -UPPCL";
			smsDetailsDTO.setMessage(smsText);
			logger.log(Level.INFO, "Inside " +METHOD_NAME+" end");
		}
		catch(Exception ex) {
			logger.log(Level.INFO, "Inside " +METHOD_NAME+" end");
			ex.getMessage();
		}
		return smsDetailsDTO;
	}
//	
//	public static String putValuesInFile(String msgText,HashMap<String, String> mapForSMS){
//		final String METHOD_NAME = "putValuesInFile(String htmlPath,String kno,String firstName,String lastName)";
//		logger.log(Level.INFO, "Inside " +METHOD_NAME+" start");
//		
//		// read file through the path
//		//smsFile = ConfigFileReader.getConfigFile(path);
//		// Replace the keys with specified values.
//		Iterator<String> itr = mapForSMS.keySet().iterator();
//		while(itr.hasNext()){
//			String keyVal = itr.next();
//			msgText = msgText.replaceAll(keyVal,mapForSMS.get(keyVal));
//		}
//				
////		if(logger.isDebugLoggingEnabled()){
////			logger.log(UPPCLLogger.LOGLEVEL_DEBUG, METHOD_NAME, 
////					new StringBuilder("SMS Content after replacing keys:  ")
////						.append(msgText).toString());
////		}
//		logger.log(Level.INFO, "Inside " +METHOD_NAME+" end");
//		return msgText;
//	}
//	
//	 
//    public SMSDetailsDTO_SOA convertToSMSDetailsDTO_SOA(SMSDetailsDTO smsDetailsDTO){
//    	final String METHOD_NAME = "convertToSMSDetailsDTO_SOA(SMSDetailsDTO smsDetailsDTO)";
//    	logger.log(Level.INFO, "Inside " +METHOD_NAME+" start");
//    	SMSDetailsDTO_SOA smsDetailsDTOSOA= new SMSDetailsDTO_SOA();
//    	smsDetailsDTOSOA.setFrom(smsDetailsDTO.getFrom());
//    	smsDetailsDTOSOA.setMessage(smsDetailsDTO.getMessage());
//    	logger.log(Level.INFO, "Inside " +METHOD_NAME+" end");
//    	return smsDetailsDTOSOA;
//    }	
}