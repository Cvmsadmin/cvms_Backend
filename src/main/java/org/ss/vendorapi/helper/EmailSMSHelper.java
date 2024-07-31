package org.ss.vendorapi.helper;
import org.ss.vendorapi.modal.EmailDetailsModel;
import org.ss.vendorapi.modal.EmailDetailsResponseModel;

public class EmailSMSHelper {

	 public EmailDetailsResponseModel convertToEmailDetailsDTO_SOA(EmailDetailsModel emailDetailsDTO){
	    	final String METHOD_NAME = "convertToEmailDetailsDTO_SOA(EmailDetailsDTO emailDetailsDTO)";
	    	//logger.logMethodStart(METHOD_NAME);
	        //if (logger.isDebugLoggingEnabled())
	            //logger.log(UPPCLLogger.LOGLEVEL_DEBUG, METHOD_NAME, "Getting html path from EmailDetailsDTO:"
	            	//	+ new StringBuilder("").append(emailDetailsDTO.getHtmlPath()) );
	    	EmailDetailsResponseModel emailDetailsDTOSOA= new EmailDetailsResponseModel();
	    	emailDetailsDTOSOA.setFrom(emailDetailsDTO.getFrom());
	    	emailDetailsDTOSOA.setContentType(emailDetailsDTO.getContentType());
	    	emailDetailsDTOSOA.setContent(emailDetailsDTO.getHtmlBody());
	    	emailDetailsDTOSOA.setSubject(emailDetailsDTO.getSubject());
	    	//logger.logMethodEnd(METHOD_NAME);	
	    	return emailDetailsDTOSOA;
	    }
}
