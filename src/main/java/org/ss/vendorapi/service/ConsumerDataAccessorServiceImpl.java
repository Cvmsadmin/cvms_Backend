package org.ss.vendorapi.service;

import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
import org.ss.vendorapi.modal.AddressResDTO;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.CustomerDetailsModel;
import org.ss.vendorapi.util.Constants;
import org.ss.vendorapi.util.ServiceDataUtil;

@Service
public class ConsumerDataAccessorServiceImpl implements ConsumerDataAccessorService {

    @Autowired public Environment env;
    
    @Override
    public CustomerDetailsDTO getCustomerDetails(String kno, String serviceName,String discomName,Environment env)throws RequestNotFoundException 
	{
    	if(null != discomName)
    		serviceName = serviceName+"_"+discomName;
		String uri = env.getProperty(serviceName);
		String usrName = env.getProperty("portal.api.username");
 	    String pass = env.getProperty("portal.api.password");
 	    CustomerDetailsDTO customerResponseDTO=new CustomerDetailsDTO();
    	try{    		
    	    String input ="{\"KNumber\":\""+kno+"\"}";
    	    RestTemplate restTemplate = new RestTemplate();
    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
    	    headers.setContentType(MediaType.APPLICATION_JSON);
    	    headers.setBasicAuth(usrName, pass);
    	    headers.add(env.getProperty("getConsumerDetails_key"),env.getProperty("getConsumerDetails_value"));
    	    HttpEntity<String> entity = new HttpEntity(input,(MultiValueMap)headers);
    	    ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class, new Object[0]);
    	    JSONObject finalResult = new JSONObject(result.getBody());    	        
    	    if (finalResult.has("ResCode")){
    	    	if(ServiceDataUtil.getJsonData(finalResult,"ResCode").equals("1")){
		    	    customerResponseDTO.setKno(ServiceDataUtil.getJsonData(finalResult,"KNumber"));
		    	    customerResponseDTO.setName(ServiceDataUtil.getJsonData(finalResult,"ConsumerName"));
		    	    customerResponseDTO.setAccountInfo(ServiceDataUtil.getJsonData(finalResult,"AccountInfo"));
		    	    customerResponseDTO.setRelationName(ServiceDataUtil.getJsonData(finalResult,"HusbandOrFatherName"));
		    	    customerResponseDTO.setMobileNo(ServiceDataUtil.getJsonData(finalResult,"MobileNumber"));
		    	    customerResponseDTO.setEmail(ServiceDataUtil.getJsonData(finalResult,"EmailAddress"));
		    	    customerResponseDTO.setDateOfBirth(ServiceDataUtil.getJsonData(finalResult,"DateOfBirth"));
		    	    customerResponseDTO.setPhoneNo(ServiceDataUtil.getJsonData(finalResult,"PhoneNumber"));
		    	    customerResponseDTO.setTypeOfConnection(ServiceDataUtil.getJsonData(finalResult,"TypeOfConnection"));
		    	    customerResponseDTO.setConsumerType(ServiceDataUtil.getJsonData(finalResult,"ConsumerType"));
		    	    customerResponseDTO.setConnectionStatus(ServiceDataUtil.getJsonData(finalResult,"ConnectionStatus"));		    	    
		    	    JSONObject billingAddress =(JSONObject) finalResult.get("BillingAddress");
		    	    AddressResDTO consumerAddress=new AddressResDTO();		    	    
		    	    consumerAddress.setAddressLine1(ServiceDataUtil.getJsonData(billingAddress,"AddressLine1"));
		    	    consumerAddress.setAddressLine2(ServiceDataUtil.getJsonData(billingAddress,"AddressLine2"));
		    	    consumerAddress.setAddressLine3(ServiceDataUtil.getJsonData(billingAddress,"AddressLine3"));
		    	    consumerAddress.setAddressLine4("");
		    	    consumerAddress.setCity(ServiceDataUtil.getJsonData(billingAddress,"City"));
		    	    consumerAddress.setState(ServiceDataUtil.getJsonData(billingAddress,"State"));
		    	    customerResponseDTO.setBillingAddresss(consumerAddress) ;		        
		    	    
		        	JSONObject premiseAddress =(JSONObject) finalResult.get("PremiseAddress");
		        	consumerAddress=new AddressResDTO();		        	
		        	consumerAddress.setAddressLine1(ServiceDataUtil.getJsonData(premiseAddress,"AddressLine1"));
		        	consumerAddress.setAddressLine2(ServiceDataUtil.getJsonData(premiseAddress,"AddressLine2"));
		        	consumerAddress.setAddressLine3(ServiceDataUtil.getJsonData(premiseAddress,"AddressLine3"));
		        	consumerAddress.setState(ServiceDataUtil.getJsonData(premiseAddress,"State"));
		        	consumerAddress.setCity(ServiceDataUtil.getJsonData(premiseAddress,"City"));		        	       
		        	customerResponseDTO.setPremiseAddress(consumerAddress);
		    	    if ("10A OR 10B".equals(ServiceDataUtil.getJsonData(finalResult,"Category"))){
		    	    	customerResponseDTO.setCategory("10");
		    	    }else{
		    	    	customerResponseDTO.setCategory(ServiceDataUtil.getJsonData(finalResult,"Category"));
		    	    }		    	       
		    	    customerResponseDTO.setDivision(ServiceDataUtil.getJsonData(finalResult,"Division"));
		    	    customerResponseDTO.setSubDivision(ServiceDataUtil.getJsonData(finalResult,"SubDivision"));
		    	    customerResponseDTO.setBookNo(ServiceDataUtil.getJsonData(finalResult,"BookNo"));		    	    
		    	    String discom = ServiceDataUtil.getJsonData(finalResult,"Discom");
		    	    discom = discom.split("-")[0].toString();
		    	    if (null != discom)
		    	    	customerResponseDTO.setDiscom(discom.trim());
		    	    else
		    	    	customerResponseDTO.setDiscom(discom);
		    	    
		    	    customerResponseDTO.setPersonId(ServiceDataUtil.getJsonData(finalResult,"PersonID"));//..getPersonIDType() need to set .getPersonIDType()
		    	    customerResponseDTO.setConsumerType(ServiceDataUtil.getJsonData(finalResult,"ConsumerType"));
				    customerResponseDTO.setMothersName(ServiceDataUtil.getJsonData(finalResult,"MotherName"));
				    customerResponseDTO.setTypeOfMeter(ServiceDataUtil.getJsonData(finalResult,"TypeOfMeter"));
					customerResponseDTO.setTypeOfPhase(ServiceDataUtil.getJsonData(finalResult,"TypeOfPhase"));
					customerResponseDTO.setTypeOfPlace(ServiceDataUtil.getJsonData(finalResult,"TypeOfPlace"));
					customerResponseDTO.setAmpISP(ServiceDataUtil.getJsonData(finalResult,"AMPISP"));
					return customerResponseDTO;		    	        
		    	}else if(ServiceDataUtil.getJsonData(finalResult,"ResCode").equals("0")){
		    		return null; 
		    	}
    	    }
    	}catch(Exception e) { 
    		e.getMessage();
    	 } 
    	 return customerResponseDTO;
    }
	
    public String updateUserDetails(CustomerDetailsModel custDetails,String serviceName,String discomName,Environment env) throws RequestNotFoundException {
        String updtStatus = "";
        if(null != discomName)
			serviceName = serviceName+"_"+discomName;
		String uri = env.getProperty(serviceName);
 	    String usrName = env.getProperty("portal.api.username");
 	    String pass = env.getProperty("portal.api.password");
 	    String key = env.getProperty("getConsumerDetails_key");
 	    String value = env.getProperty("getConsumerDetails_value");
 	    try{
    		String input =  "{\"KNumber\":\""+custDetails.getKno()+"\",\"MobileNumber\":\""+custDetails.getMobileNo()+"\",\"EmailAddress\":\""+custDetails.getEmail()+"\",\"OnlineBillingStatus\":\""+Constants.ONLINE_BILLING_STATUS+"\",\"PersonID\":\""+custDetails.getPersonId()+"\"}";
    	    RestTemplate restTemplate = new RestTemplate();
    	    HttpHeaders headers = new HttpHeaders();
    	    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
    	    headers.setContentType(MediaType.APPLICATION_JSON);
    	    headers.setBasicAuth(usrName, pass);
    	    headers.add(key,value);
    	    HttpEntity<String> entity = new HttpEntity(input, (MultiValueMap)headers);
    	    ResponseEntity<String> result = restTemplate.postForEntity(uri, entity, String.class, new Object[0]);
    	    JSONObject finalResult = new JSONObject(result.getBody());
    	    if (finalResult.has("UpdateStatus")) {
    	    	updtStatus=ServiceDataUtil.getJsonData(finalResult,"UpdateStatus");
    	    	return updtStatus;
    	    }else if(ServiceDataUtil.getJsonData(finalResult,"ErrCode").equals("-1")){
    	    	return ServiceDataUtil.getJsonData(finalResult,"ErrDesc");
    	    }
 	    }catch (Exception e) { 
    	      e.getMessage();
    	} 
        return updtStatus;
    }
}
