package org.ss.vendorapi.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
import org.ss.vendorapi.modal.EmailDetailsModel;
import org.ss.vendorapi.util.ServiceDataUtil;

import com.sun.jersey.api.client.ClientResponse;

public class EmailHelper {
	
	private static final Logger logger = System.getLogger("SMSHelper");
	
	@SuppressWarnings("unchecked")
    public static EmailDetailsModel getEmailDetails(String module, HashMap<String, String> mapForEmail)
    		throws RequestNotFoundException {
        Map<String, EmailDetailsModel> emailDataMap = null;
        EmailDetailsModel cacheEmailDetailsDTO = new EmailDetailsModel();
        EmailDetailsModel emailDetailsDTO = new EmailDetailsModel();
        String emailFile = null;

        /*try {
            emailDataMap = (Map<String, EmailDetailsModel>) UPPCLCacheManager
                    .getCacheValue(CACHE_NAME,
                    FrameworkConstants.EMAIL_CACHE_KEY.toString());

            cacheEmailDetailsDTO = emailDataMap.get(module);
            String htmlpath = EnvironmentPropertiesReader
                    .readProperty("CONFIG.FILE.PATH.PREFIX") + cacheEmailDetailsDTO.getHtmlPath();
            emailDetailsDTO.setContentType(cacheEmailDetailsDTO.getContentType());
            emailDetailsDTO.setFrom(cacheEmailDetailsDTO.getFrom());
            emailDetailsDTO.setHtmlPath(cacheEmailDetailsDTO.getHtmlPath());
            emailDetailsDTO.setModuleName(cacheEmailDetailsDTO.getModuleName());
            emailDetailsDTO.setSubject(cacheEmailDetailsDTO.getSubject());
            emailDetailsDTO.setHtmlPath(htmlpath);
            mapForEmail.put(FrameworkConstants.EMAIL_HEADER_IMG_FIRST.toString(), ModuleAccessHelper.getModuleDetails(
                    "mailImage", "headerFirstImg"));
            mapForEmail.put(FrameworkConstants.EMAIL_HEADER_IMG_SEC.toString(), ModuleAccessHelper.getModuleDetails(
                    "mailImage", "headerSecImg"));
            emailFile = EmailHelper.putValuesInFile(emailDetailsDTO.getHtmlPath(), mapForEmail);
            emailDetailsDTO.setHtmlBody(emailFile);

        } catch (UPPCLCacheException uppclCacheException) {
            // TODO: handle exception
            if (logger.isErrorLoggingEnabled()) {
                logger.log(UPPCLLogger.LOGLEVEL_ERROR, METHOD_NAME,
                        new StringBuilder("Error while retriveing the email details for this module: ")
                        .append(module).toString(), uppclCacheException);
            }
        }
        logger.logMethodEnd(METHOD_NAME);*/
        return emailDetailsDTO;
    }

	
//	  public String sendEmail(HashMap<String, String> emailMap,String discom,String toEmail,String module,Environment env) throws RequestNotFoundException{
//	    	final String METHOD_NAME = "sendEmail";
//	    	logger.log(Level.INFO, "Inside " +METHOD_NAME+" start");
//	    	
//	    	//emailDetailsDTO = getEmailDetails(module, emailMap,env);
//	    	String content = emailDetailsDTO.getContent().trim();
//	    	toEmail ="mathew.thomas@infinite.com"; //hard code 
//	    	String input = "{\"From\" : \""+emailDetailsDTO.getFrom()+"\",\"To\" : \""+toEmail+"\",\"Subject\" : \""+emailDetailsDTO.getSubject()+"\",\"ContentType\" : \""+emailDetailsDTO.getContentType()+"\",\"Content\" : \""+emailDetailsDTO.getContent().trim()+"\"}";
//	    	//ServiceDataUtil serviceDataUtil = new ServiceDataUtil();
//	    	//String sendMailStatus = serviceDataUtil.sendEmail(discom,input, env);
//	    	String sendMailStatus = sendEmailWithTemplate(discom,env,input);
//	    	logger.log(Level.INFO, "Inside " +METHOD_NAME+" end");
//			return sendMailStatus;
//	    }
	  
	public String sendEmailWithTemplate(String discom,Environment env, String str) {
		ClientResponse resp = ServiceDataUtil.callRestAPI(str, "sendEmail",discom,true, env,"xml");
		//System.out.println(str);
		String ec="";
		if(null == resp || resp.getStatus() != 200){					
			logger.log(Level.INFO, "METHOD_NAME",new StringBuilder("Get Account failed in SOA Processing for Input-->"+str.toString()).toString());
			ec="error";
		}
		else{
			String output = resp.getEntity(String.class);                    
			ec = output;
			
			//System.out.println("output "+output);
		}
		return ec;
	}
	
	public  String readFile(String fileName){
	      //New code starts
				StringBuffer sb = new StringBuffer();
				InputStream inputStream = getClass().getResourceAsStream("/EmailTemplates/"+fileName);
				try (
		                InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
		                BufferedReader br = new BufferedReader(isr)) {
		 
		            br.lines().forEach(line -> sb.append(line));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return sb.toString();
	}
	
}