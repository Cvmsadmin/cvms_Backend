package org.ss.vendorapi.util;

import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.EmailDetailsDTO;
import org.ss.vendorapi.modal.EmailDetailsDTO_SOA;
import org.ss.vendorapi.modal.EmailDetailsModel;
import org.ss.vendorapi.modal.SMSDetailsDTO;
import org.ss.vendorapi.modal.SMSDetailsDTO_SOA;
public class EmailSMSHelper {

	private static final String CLASS_NAME = EmailSMSHelper.class.getName();

	 /**
     * Store data from EmailDetailsDTO into EmailDetailsDTO_SOA
     * @param emailDetailsDTO
     * @return emailDetailsDTO_SOA
     */
    public EmailDetailsDTO_SOA convertToEmailDetailsDTO_SOA(EmailDetailsModel emailDetailsDTO){
    	final String METHOD_NAME = "convertToEmailDetailsDTO_SOA(EmailDetailsDTO emailDetailsDTO)";
//    	logger.logMethodStart(METHOD_NAME);
//        if (logger.isDebugLoggingEnabled())
//            logger.log(UPPCLLogger.LOGLEVEL_DEBUG, METHOD_NAME, "Getting html path from EmailDetailsDTO:"
//            		+ new StringBuilder("").append(emailDetailsDTO.getHtmlPath()) );
    	EmailDetailsDTO_SOA emailDetailsDTOSOA= new EmailDetailsDTO_SOA();
    	emailDetailsDTOSOA.setFrom(emailDetailsDTO.getFrom());
    	emailDetailsDTOSOA.setContentType(emailDetailsDTO.getContentType());
    	emailDetailsDTOSOA.setContent(emailDetailsDTO.getHtmlBody());
    	emailDetailsDTOSOA.setSubject(emailDetailsDTO.getSubject());
//    	logger.logMethodEnd(METHOD_NAME);	
    	return emailDetailsDTOSOA;
    }	
    
    /**
     * Store data from SMSDetailsDTO into SMSDetailsDTO_SOA
     * @param smsDetailsDTO
     * @return smsDetailsDTO_SOA
     */
    
    public SMSDetailsDTO_SOA convertToSMSDetailsDTO_SOA(SMSDetailsDTO smsDetailsDTO){
    	final String METHOD_NAME = "convertToSMSDetailsDTO_SOA(SMSDetailsDTO smsDetailsDTO)";
//        logger.logMethodStart(METHOD_NAME);
    	SMSDetailsDTO_SOA smsDetailsDTOSOA= new SMSDetailsDTO_SOA();
    	smsDetailsDTOSOA.setFrom(smsDetailsDTO.getFrom());
    	smsDetailsDTOSOA.setMessage(smsDetailsDTO.getMessage());
//    	logger.logMethodEnd(METHOD_NAME);	
    	return smsDetailsDTOSOA;
    }	
    
	  /**
     * Convert EmailDetailsDTO into EmailDetailsDTO_SOA.
     * 
     * @param 
     * 			The EmailDetailsDTO, CustomerDetailsDTO and Attachment Data
     *      * 			
     * @return 
     * 			The EmailDetailsDTO_SOA.
     * 
     */
    public EmailDetailsDTO_SOA convertToEmailDetailsDTO_SOA(EmailDetailsDTO emailDetailsDTO,CustomerDetailsDTO custDetailsDTO, byte[] byteArray){
    	final String METHOD_NAME ="convertToEmailDetailsDTO_SOA(EmailDetailsDTO emailDetailsDTO,CustomerDetailsDTO custDetailsDTO, byte[] byteArray)";
//		logger.logMethodStart(METHOD_NAME);
    	EmailDetailsDTO_SOA emailDetailsDTOSOA= new EmailDetailsDTO_SOA();
    	emailDetailsDTOSOA.setFrom(emailDetailsDTO.getFrom());
    	emailDetailsDTOSOA.setTo(custDetailsDTO.getEmail());
    	emailDetailsDTOSOA.setContentType(emailDetailsDTO.getContentType());
    	emailDetailsDTOSOA.setContent((emailDetailsDTO.getHtmlBody()));
    	emailDetailsDTOSOA.setContentType("text/html");
    	emailDetailsDTOSOA.setSubject(emailDetailsDTO.getSubject());
    	emailDetailsDTOSOA.setAttachmentData(byteArray);
    	emailDetailsDTOSOA.setAttachmentType("application/pdf");
    	emailDetailsDTOSOA.setKnumber(custDetailsDTO.getKno());
//    	logger.logMethodEnd(METHOD_NAME);
    	return emailDetailsDTOSOA;
    }
}
