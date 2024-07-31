package org.ss.vendorapi.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.ss.vendorapi.exceptions.RequestNotFoundException;
//import org.ss.vendorapi.logging.UPPCLLogger;
import org.ss.vendorapi.modal.AddressResDTO;
import org.ss.vendorapi.modal.BillDetailsResDTO;
import org.ss.vendorapi.modal.BillDisplayDTO;
import org.ss.vendorapi.modal.BillSummaryDTO;
import org.ss.vendorapi.modal.ConsumpitonHistoryDTO;
import org.ss.vendorapi.modal.CustomerDetailsDTO;
import org.ss.vendorapi.modal.CustomerResponseModel;
import org.ss.vendorapi.modal.NameChangeModel;
import org.ss.vendorapi.modal.ViewBillResModel;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

@Service
public class ServiceDataUtil { 
	
	private static final Class<?> CLASS_NAME = ServiceDataUtil.class;
//	private static UPPCLLogger logger = UPPCLLogger.getInstance(UPPCLLogger.MODULE_BILLING,CLASS_NAME.toString());
	
	List<String> accountNoList;

	public List<String> getAccountNoList() {
		return accountNoList;
	}

	public void setAccountNoList(List<String> accountNoList) {
		this.accountNoList = accountNoList;
	}

	//@Autowired ConsumerMasterRepository consumerMasterRepository;
	
	public static ClientResponse callRestAPI(String input, String serviceName, String discomName,Environment env) {  
		return callRestAPI( input, serviceName, discomName,false,env,"json");
	}

	public static ClientResponse callRestAPI(String input, String serviceName, String discomName,boolean includeHeader,Environment env) {  
		return callRestAPI( input, serviceName, discomName,includeHeader,env,"json");
	}
	public static ClientResponse callRestAPI(String input, String serviceName, String discomName,boolean includeHeader,Environment env,String contentType) {  
		String methodName="callRestAPI(String input, String targetEndPoint, String discomName)";    	
//		logger.logMethodStart(methodName);
		
		ClientResponse resp = null;
		try{
			if(null != discomName)
				serviceName = serviceName+"_"+discomName;
			String url = env.getProperty(serviceName);
			String name = env.getProperty("api.authorization.username");
			String password = env.getProperty("api.authorization.password");
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,"input--> "+input+"---serviceName-->"+serviceName+"---discomName-->"+discomName+"---url-->"+url);
			Client c = Client.create(); 
			c.addFilter(new HTTPBasicAuthFilter(name, password));

			WebResource resource = c.resource(url);
			resource.setProperty("Content-Type", "application/"+contentType+";charset=UTF-8");
			
			if(includeHeader) {
				if(contentType.equals("xml"))
					resp = resource.accept(MediaType.APPLICATION_XML_TYPE).header(env.getProperty("getConsumerDetails_key"), env.getProperty("getConsumerDetails_value")).type(MediaType.APPLICATION_XML_TYPE).post(ClientResponse.class,input);
				else	
					resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header(env.getProperty("getConsumerDetails_key"), env.getProperty("getConsumerDetails_value")).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
//				resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("SRC", "WSSPORTAL").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
			}
			else
				resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
		}
		catch(Exception ex){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
		}
//		logger.logMethodEnd(methodName);
		return resp;                
	}


	public static String getdiscomNameFromTheService(String accNumber,String discomName,Environment env) throws RequestNotFoundException
	{
		String methodName = "getdiscomNameFromTheService";
//		logger.logMethodStart(methodName);
		String returnStatus="error";
		String input = "{\"AcctId\":\""+accNumber+"\"}";
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		ClientResponse resp = callRestAPI(input, "getDiscomName",discomName,env);
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
			throw new RequestNotFoundException("Internal Server Error from get Discom");
		}
		else{
			String output = resp.getEntity(String.class);
			JSONObject finalResult = new JSONObject(output);       
			String ec =  finalResult.getString("ResCode");
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
			//No detail found
			if(ec.equalsIgnoreCase("-1")){
				throw new RequestNotFoundException("Invalid Details"); //No Details from get Discom API
			}
			//Detail available
			else if(ec.equalsIgnoreCase("1"))
			{
				String disValue =  finalResult.getString("Discom"); 
				returnStatus = disValue;
			}
			//Service Error
			else{
				returnStatus = finalResult.getString("ResMsg");
				throw new RequestNotFoundException("Service Error from get Discom API");
			}
		}
//		logger.logMethodEnd(methodName);
		return returnStatus;
	}

	public static String getAccNoBasisOfDiscom(String accNo , String discom){
		String methodName = "getAccNoBasisOfDiscom";
//		logger.logMethodStart(methodName);
		String accNoWithDiscom = "";
		if("PVVNL".equalsIgnoreCase(discom))
			accNoWithDiscom = accNo+"-02";
		if("PUVNL".equalsIgnoreCase(discom))
			accNoWithDiscom = accNo+"-01";
		if("MVVNL".equalsIgnoreCase(discom))
			accNoWithDiscom = accNo+"-03";
		if("DVVNL".equalsIgnoreCase(discom))
			accNoWithDiscom = accNo+"-04";
		if("KESCO".equalsIgnoreCase(discom))
			accNoWithDiscom = accNo+"-05";
//		logger.logMethodEnd(methodName);
		return accNoWithDiscom;
	}

	public static String getJsonData(JSONObject finalResult, String key) {
		String data = "";
		try {
			if(finalResult.has(key))
				data = finalResult.getString(key); 
		}catch(Exception e) {		
		}
		return data;
	}
	
	
	/*
	 * public ClientResponse sendMail(ConsumerMasterEntity
	 * consumerMasterEntity,String usrName,Environment env) throws
	 * RequestNotFoundException{ String methodName = "sendMail";
	 * logger.logMethodStart(methodName); ClientResponse resp =null; try{ String
	 * from=env.getProperty("send.email.from"); String
	 * subject=env.getProperty("send.email.subject"); String
	 * contentType=env.getProperty("send.email.content.type");
	 * //ConsumerMasterEntity
	 * usrSetUpEntity=consumerMasterRepository.findByKnoAndDiscomName(
	 * consumerMasterEntity.getKno(), consumerMasterEntity.getDiscomName());
	 * 
	 * String knoName=consumerMasterEntity.getKno()+","+usrName;
	 * 
	 * String input =
	 * "{\"From\" : \""+from+"\",\"To\" : \""+consumerMasterEntity.getEmail()
	 * +"\",\"Subject\" : \""+subject+"\",\"ContentType\" : \""
	 * +contentType+"\",\"Content\" : \""+knoName+"\"}";
	 * logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName
	 * +"API request "+input); resp
	 * =callRestAPI(input,Constants.SEND_EMAIL_SERVICE,consumerMasterEntity.
	 * getDiscomName(),env); logger.logMethodEnd(methodName); return resp; }
	 * catch(Exception ex){ ex.printStackTrace(); } logger.logMethodEnd(methodName);
	 * return resp; }
	 */

	public static CustomerDetailsDTO getConsumerDetails(String accNumber,String discomName,Environment env) throws RequestNotFoundException
	{
		CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
		String methodName = "getConsumerDetails";
//		logger.logMethodStart(methodName);
		String input = "{\"KNumber\":\""+accNumber+"\"}";
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		ClientResponse resp = callRestAPI(input,"getConsumerDetails",discomName,true,env,"json");
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
			throw new RequestNotFoundException("Internal Server Error from Consumer Details");
		}
		else{
			String output = resp.getEntity(String.class);
			JSONObject finalResult = new JSONObject(output);
			String ec =  finalResult.getString("ResCode");
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
			if(ec.equalsIgnoreCase("-1")){
				throw new RequestNotFoundException("NoDetails");
			}
			else if(ec.equalsIgnoreCase("1")){
				customerDetailsDTO.setKno(getJsonData(finalResult,"KNumber"));
				customerDetailsDTO.setName(getJsonData(finalResult,"ConsumerName"));
				customerDetailsDTO.setRelationName(getJsonData(finalResult,"HusbandOrFatherName"));
				customerDetailsDTO.setMothersName(getJsonData(finalResult,"MotherName"));
				customerDetailsDTO.setSecurityAmount(String.valueOf(finalResult.get("SecurityDeposit")));
				customerDetailsDTO.setAccountInfo(getJsonData(finalResult,"AccountInfo"));
				if(getJsonBigDecimalData(finalResult,"SanctionedLoadInBHP")!=null) {
	    	    	BigDecimal tempBHPSanctionedLoad = finalResult.getBigDecimal("SanctionedLoadInBHP");
	    	    	tempBHPSanctionedLoad = tempBHPSanctionedLoad.setScale(2, BigDecimal.ROUND_UP);
	    	    	customerDetailsDTO.setSanctionedLoad(String.valueOf(tempBHPSanctionedLoad)+" BHP");
	    	    }
	    	    else {
	    	    	customerDetailsDTO.setSanctionedLoad(null);
	    	    }
				if("null".equalsIgnoreCase(customerDetailsDTO.getSanctionedLoad())||
					"".equalsIgnoreCase(customerDetailsDTO.getSanctionedLoad())||
					"0.00 BHP".equalsIgnoreCase(customerDetailsDTO.getSanctionedLoad())
					){
					if(getJsonBigDecimalData(finalResult,"SanctionedLoadInKVA")!=null) {
		    	    	BigDecimal tempKVASanctionedLoad = finalResult.getBigDecimal("SanctionedLoadInKVA");
		    	    	tempKVASanctionedLoad = tempKVASanctionedLoad.setScale(2, BigDecimal.ROUND_UP);
		    	    	customerDetailsDTO.setSanctionedLoad(String.valueOf(tempKVASanctionedLoad)+" KVA");
		    	    }
		    	    else {
		    	    	customerDetailsDTO.setSanctionedLoad(null);
		    	    }
		    	    if(getJsonBigDecimalData(finalResult,"SanctionedLoadInKW")!=null) {
		    	    	BigDecimal tempKWSanctionedLoad = finalResult.getBigDecimal("SanctionedLoadInKW");
		    	    	tempKWSanctionedLoad = tempKWSanctionedLoad.setScale(2, BigDecimal.ROUND_UP);
		    	    	customerDetailsDTO.setSanctionedLoad(String.valueOf(tempKWSanctionedLoad)+" KW");
		    	    }
		    	    else {
		    	    	customerDetailsDTO.setSanctionedLoad(null);
		    	    }
				}
	    	    customerDetailsDTO.setMobileNo(getJsonData(finalResult,"MobileNumber"));////.getMSISDN11Type());
	    	    customerDetailsDTO.setEmail(getJsonData(finalResult,"EmailAddress"));
	    	    customerDetailsDTO.setDateOfBirth(getJsonData(finalResult,"DateOfBirth"));
	    	    customerDetailsDTO.setPhoneNo(getJsonData(finalResult,"PhoneNumber"));
	    	    customerDetailsDTO.setConsumerType(getJsonData(finalResult,"ConsumerType"));
	    	    
	    	    JSONObject billingAddress =(JSONObject) finalResult.get("BillingAddress");
	    	    AddressResDTO consumerAddress=new AddressResDTO();
	    	    consumerAddress.setAddressLine1(getJsonData(billingAddress,"AddressLine1"));
	    	    consumerAddress.setAddressLine2(getJsonData(billingAddress,"AddressLine2"));
	    	    consumerAddress.setAddressLine3(getJsonData(billingAddress,"AddressLine3"));
	    	    consumerAddress.setAddressLine4(getJsonData(billingAddress,"AddressLine4"));
	    	    consumerAddress.setCity(getJsonData(billingAddress,"City"));
	    	    consumerAddress.setState(getJsonData(billingAddress,"State"));
	    	    consumerAddress.setPinCode(getJsonData(billingAddress,"PinCode"));
	    	    customerDetailsDTO.setBillingAddresss(consumerAddress);
	        	        
	        	JSONObject premiseAddress =(JSONObject) finalResult.get("PremiseAddress");
	        	consumerAddress=new AddressResDTO();
	        	consumerAddress.setAddressLine1(getJsonData(premiseAddress,"AddressLine1"));
	        	consumerAddress.setAddressLine2(getJsonData(premiseAddress,"AddressLine2"));
	        	consumerAddress.setAddressLine3(getJsonData(premiseAddress,"AddressLine3"));
	        	consumerAddress.setAddressLine4(getJsonData(premiseAddress,"AddressLine4"));
	        	consumerAddress.setState(getJsonData(premiseAddress,"State"));
	        	consumerAddress.setCity(getJsonData(premiseAddress,"City"));
	        	consumerAddress.setPinCode(getJsonData(premiseAddress,"PinCode"));
	        	customerDetailsDTO.setPremiseAddress(consumerAddress);
	        	
	    	    if ("10A OR 10B".equals(getJsonData(finalResult,"Category"))) {
	    	    	customerDetailsDTO.setCategory("10");
	    	    } else {
	    	    	customerDetailsDTO.setCategory(getJsonData(finalResult,"Category"));
	    	    }
	    	       
	    	    customerDetailsDTO.setDivision(getJsonData(finalResult,"Division"));
	    	    customerDetailsDTO.setSubDivision(getJsonData(finalResult,"SubDivision"));
	    	    customerDetailsDTO.setBookNo(getJsonData(finalResult,"BookNo"));
	    	    
	    	    String discom = getJsonData(finalResult,"Discom");
	    	    discom = discom.split("-")[0].toString();
	    	    if (null != discom)
	    	    	customerDetailsDTO.setDiscom(discom.trim());
		    	else
		    		customerDetailsDTO.setDiscom(discom);
		    	   
	    	    customerDetailsDTO.setPersonId(getJsonData(finalResult,"PersonID"));//..getPersonIDType() need to set .getPersonIDType()
	    	    customerDetailsDTO.setTypeOfMeter(getJsonData(finalResult,"TypeOfMeter"));
	    	    customerDetailsDTO.setTypeOfPhase(getJsonData(finalResult,"TypeOfPhase"));
	    	    customerDetailsDTO.setTypeOfPlace(getJsonData(finalResult,"TypeOfPlace"));
	    	    customerDetailsDTO.setConnectionStatus(getJsonData(finalResult,"ConnectionStatus"));
	    	    customerDetailsDTO.setTypeOfConnection(getJsonData(finalResult,"TypeOfConnection"));
	    	    customerDetailsDTO.setOnlineBillingStatus(getJsonData(finalResult,"OnlinebillingStatus"));
	    	    customerDetailsDTO.setCustomerIndexNumber(getJsonData(finalResult,"CIN"));
	    	    customerDetailsDTO.setPurposeOfSupply(getJsonData(finalResult,"PurposeOfSupply"));
	    	    customerDetailsDTO.setTypeOfConnectionPoint(getJsonData(finalResult,"TypeOfConnectionPoint"));
	    	    customerDetailsDTO.setTypeOfConnection(getJsonData(finalResult,"TypeOfConnection"));
	    	    customerDetailsDTO.setConnectionStatus(getJsonData(finalResult,"ConnectionStatus"));//ConnectionStatus
	    	    customerDetailsDTO.setChequeDshnrCount(getJsonData(finalResult,"ChequeDshnrCount"));
	    	    customerDetailsDTO.setAmpISP(getJsonData(finalResult,"AMPISP"));
			}
			//0
			else{
				String resMsg = getJsonData(finalResult,"ResMsg");
				throw new RequestNotFoundException("Service Error Consumer Details API");
			}
		}
//		logger.logMethodEnd(methodName);
		return customerDetailsDTO;
	}

	public static List<BillDetailsResDTO> getBillingDetails(String accNumber,String discomName,Environment env)  throws RequestNotFoundException
	{
		String methodName = "getBillingDetails";
//		logger.logMethodStart(methodName);
		List<BillDetailsResDTO> billDetailsResDTOList = null;
		String input = "{\"KNumber\":\""+accNumber+"\",\"SearchParameters\":{\"Duration\":\""+env.getProperty("getBillDetails_duration")+"\"}}";
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		ClientResponse resp = callRestAPI(input,"getBillDetails",discomName,env);
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
			throw new RequestNotFoundException("Internal Server Error from Billing Details");
		}
		else{
			String output = resp.getEntity(String.class);
			JSONObject finalResult = new JSONObject(output);
			String ec =  finalResult.getString("ResCode");
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
			if("-1".equalsIgnoreCase(ec)){
				throw new RequestNotFoundException("Billing Details Not Found");
			}
			else if("1".equalsIgnoreCase(ec))
			{
				billDetailsResDTOList = new ArrayList<BillDetailsResDTO>();
				JSONArray jsonArray = finalResult.getJSONArray("BillDetails");
				for(int i=0;i<jsonArray.length();i++) {
					JSONObject explrObject = jsonArray.getJSONObject(i);  
					BillDetailsResDTO billDetailsResDTOObj = new BillDetailsResDTO();
					billDetailsResDTOObj.setBillAmount((String)explrObject.get("BillAmount"));
					JSONObject billInfoJSON = explrObject.getJSONObject("BillInfo");//getting nested object
					billDetailsResDTOObj.setBillNo(billInfoJSON.getString("BillNo"));
					billDetailsResDTOObj.setBillDueDate(billInfoJSON.getString("BillDueDate"));
					billDetailsResDTOObj.setBillIssuedDate((String)explrObject.get("BillIssuedDate"));
					
					String paymentMade = null;
					if(explrObject.has("PaymentMade")) {
						paymentMade =explrObject.get("PaymentMade").toString();
						billDetailsResDTOObj.setPaymentMade((Double.parseDouble(paymentMade)));
					}
					else {
						billDetailsResDTOObj.setPaymentMade(0.0);
					}
					
					String paymentDate = (String)getJsonData(explrObject, "PaymentDate");
					if(paymentDate == null|| "".equals(paymentDate)) {
						billDetailsResDTOObj.setPaymentDate(null);
					}
					else {
						billDetailsResDTOObj.setPaymentDate(paymentDate);
					}
					billDetailsResDTOList.add(billDetailsResDTOObj);
				}
			}
			else{
				String resMsg= null;
				resMsg = getJsonData(finalResult, "ResMsg").trim();
				if("No detail found".equalsIgnoreCase(resMsg))
					throw new RequestNotFoundException("Billing Details Not Found");
				else
					throw new RequestNotFoundException("Service Error from get Billing Details");
			}
		}
//		logger.logMethodEnd(methodName);
		return billDetailsResDTOList;
	}
	
	public static BillDisplayDTO getBillDisplayDetails(String accNumber,String discomName,Environment env)  throws RequestNotFoundException{
		BillDisplayDTO billDisplayDTO = new BillDisplayDTO();
		String methodName = "getBillDisplayDetails";
//		logger.logMethodStart(methodName);
		String input = "{\"Acctd\":\""+accNumber+"\"}";
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		ClientResponse resp = callRestAPI(input,"BillDisplayDetails",discomName,true,env);
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
			throw new RequestNotFoundException("Internal Server Error from Bill Display");
		}
		else{
			String output = resp.getEntity(String.class);
			JSONObject finalResult = new JSONObject(output);
			String ec =  finalResult.getString("ResCd");
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
			if(ec.equalsIgnoreCase("-1")){
				throw new RequestNotFoundException("Service Error from Bill Display API");
			}
			else if(ec.equalsIgnoreCase("1"))
			{
				billDisplayDTO.setAccNo(String.valueOf(accNumber));
				
				billDisplayDTO.setPayAmtBeforeDt("");
				if(finalResult.get("AMTPayWIDueDT") !=null) 
					billDisplayDTO.setPayAmtBeforeDt(new BigDecimal(finalResult.get("AMTPayWIDueDT").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString());
				
				billDisplayDTO.setPayAmtAfterDt("");
				if(finalResult.get("TotPayblAmt") != null)
					billDisplayDTO.setPayAmtAfterDt(new BigDecimal(finalResult.get("TotPayblAmt").toString()).setScale(0, BigDecimal.ROUND_HALF_UP).toString());	
			}
			else{
				return null;
			}
		}
//		logger.logMethodEnd(methodName);
		return billDisplayDTO;
	}

	public static String getCurrentDate() {
		String methodName = "getCurrentDate";
//		logger.logMethodStart(methodName);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS");
	    String currentDate = simpleDateFormat.format(new Date());
//	    logger.logMethodEnd(methodName);
	    return currentDate;
	}
	
	public static ClientResponse sendAsyncEmail(String source,String MobileNo,String email,String mode,boolean includeHeader,String serviceName,String discomName,Environment env) {
		String methodName = "sendAsyncEmail";
//		logger.logMethodStart(methodName);
		if(null != discomName)
			serviceName = serviceName+"_"+discomName;
		String uri = env.getProperty(serviceName);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API url "+uri);
 	    String usrName = env.getProperty("portal.api.username");
 	    String pass = env.getProperty("portal.api.password");
 	    ClientResponse resp = null;
    	try {
    		String input =  "{\"Source\" : \""+source+"\",\"MobileNo\" : \""+MobileNo+"\",\"Email\" :\""+email+"\",\"Mode\" : \""+mode+"\"}";
//    		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
    		Client c = Client.create(); 
		    c.addFilter(new HTTPBasicAuthFilter(usrName, pass));
		    WebResource resource = c.resource(uri);
		    resource.setProperty("Content-Type", "application/json;charset=UTF-8");
			if(includeHeader)
				resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header(env.getProperty("getConsumerDetails_key"), env.getProperty("getConsumerDetails_value")).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
			else
				resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
//			logger.logMethodEnd(methodName);
			return resp;		
    	}catch(Exception ex)
    	{
//    		logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
		}
//    	logger.logMethodEnd(methodName);
		return resp;
    }
	
	public static ClientResponse veryfyOTP(String mobileNo,String OTP, String discomName,String verifyMode,Boolean includeHeader,Environment env) {
		String methodName = "veryfyOTP";
//		logger.logMethodStart(methodName);
		ClientResponse resp = null;
    	try {    		
    	   String input = "{\"MobileNo\" : \""+mobileNo+"\",\"OTP\" : \""+OTP+"\",\"VerifyMode\" : \""+verifyMode+"\"}" ;
//    	   logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
    	   resp = callRestAPI(input,"verifyOTP",discomName,env);
//    	   logger.logMethodEnd(methodName);
    	return resp;
		
	}catch(Exception ex)
    	{
//		logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
		}
//    	logger.logMethodEnd(methodName);
		return resp;
    }
	
	public static ClientResponse veryfyEmailOTP(String email,String OTP, String discomName,String verifyMode,Boolean includeHeader,Environment env) {
		String methodName = "veryfyEmailOTP";
//		logger.logMethodStart(methodName);
		ClientResponse resp = null;
    	try{    		
    		String input = "{\"Email\":\""+email+"\",\"OTP\":\""+OTP+"\",\"VerifyMode\":\""+verifyMode+"\"}" ;
//    	    logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API input request "+input);
    	    resp = callRestAPI(input,"verifyOTP",discomName,env);
//    	    logger.logMethodEnd(methodName);
    	    return resp;		
    	}catch(Exception ex)
    	{
//    		logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
		}
//    	logger.logMethodEnd(methodName);
		return resp;
    }

	public static List<ConsumpitonHistoryDTO> getConsumptionDetails(String accNumber,String discomName,String fromDate,String toDate,Environment env)  throws RequestNotFoundException{		
		String methodName = "getConsumptionDetails";
//		logger.logMethodStart(methodName);
		String input = "{\"KNumber\":\""+accNumber+"\",\"SearchParameters\":{\"DateRange\":{\"FromDate\":\""+fromDate+"\",\"ToDate\":\""+toDate+"\"}}}";
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		ClientResponse resp = callRestAPI(input, "ConsumptionHistoryDetails",discomName,env);
		List<ConsumpitonHistoryDTO> listObj = new ArrayList<ConsumpitonHistoryDTO>();
		ConsumpitonHistoryDTO consumpitonHistoryDTO = null;
		String output = resp.getEntity(String.class);
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
			JSONObject jsonData = new JSONObject(output);
			String errorCode =  jsonData.getString("errorCode");
			//when data is not available block start
			if("OSB-382510".equalsIgnoreCase(errorCode)) {
				throw new RequestNotFoundException("NoDetails");
			}
			//server Error block start
			else if("-1".equalsIgnoreCase(errorCode)) {
				throw new RequestNotFoundException("Server Error");
			}
			//Internal Server Error
			else {
				throw new RequestNotFoundException("Internal Server Error");
			}
		}
		else{
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response data available");
			if(output.equals("null"))
			{
				throw new RequestNotFoundException("NoDetails");
			}
			else {
				JSONObject initialResult1 = new JSONObject(output);                    
				JSONArray jsonArray = initialResult1.getJSONArray("ConsumptionDetails");
				for(int i=0;i<jsonArray.length();i++) {
					consumpitonHistoryDTO= new ConsumpitonHistoryDTO();
					JSONObject explrObject = jsonArray.getJSONObject(i);
					//getting Bill no start
					JSONObject billInfoJSON = explrObject.getJSONObject("BillInfo");//getting nested object
					consumpitonHistoryDTO.setBillNo(billInfoJSON.getString("BillNo"));
					//getting Bill no end
					//getting Bill period start
					JSONObject billDateInfoJSON = explrObject.getJSONObject("BillDateInfo");//getting nested object
					consumpitonHistoryDTO.setBillFrom(billDateInfoJSON.getString("FromDate"));
					consumpitonHistoryDTO.setBillTo(billDateInfoJSON.getString("ToDate"));
					//getting Bill period end
					//getting MeterReading start
					JSONObject meterReadingJSON = explrObject.getJSONObject("MeterReading");//getting nested object
					consumpitonHistoryDTO.setMeterReading_prev(String.valueOf(meterReadingJSON.getDouble("StartReading")));
					consumpitonHistoryDTO.setMeterReading_curr(String.valueOf(meterReadingJSON.getDouble("EndReading")));
					consumpitonHistoryDTO.setMeterReading_unit(meterReadingJSON.getString("MeasurementUnit"));
					//getting MeterReading end
					//getting UnitBilled start
					JSONObject unitBilledJSON = explrObject.getJSONObject("UnitBilled");//getting nested object
					consumpitonHistoryDTO.setUnitsBilled_consumption(String.valueOf(unitBilledJSON.getDouble("UnitsBilled")));
					consumpitonHistoryDTO.setUnitsBilled_unit(unitBilledJSON.getString("MeasurementUnit"));
					//getting UnitBilled end
					//getting UnitBilled start
					consumpitonHistoryDTO.setMeterReadingDate(explrObject.getString("MeterReadingDate"));
					//getting UnitBilled end
					//getting no. of days start
					long period = 0;
					try {
						SimpleDateFormat obj = new SimpleDateFormat("dd-MM-yyyy"); 
						String from_date = consumpitonHistoryDTO.getBillFrom();
						String to_date = consumpitonHistoryDTO.getBillTo();
						Date dateFrom = obj.parse(from_date);   
				           Date dateTo = obj.parse(to_date); 
				           long difference = dateTo.getTime() - dateFrom.getTime();
		                    period = difference / (24 * 60 * 60 * 1000);
//				           long time_difference = dateTo.getTime() - dateFrom.getTime();  
//				            long days_difference = TimeUnit.MILLISECONDS.toDays(time_difference) % 365;
				            consumpitonHistoryDTO.setPeriod(String.valueOf(period));
					} 
					catch(Exception e) {
						throw new RequestNotFoundException(e.getMessage());
					}
					//getting no. of days end
					///getting BillAmount start
					consumpitonHistoryDTO.setAmount(String.valueOf(explrObject.get("BillAmount")));
					//getting BillAmount end
					listObj.add(consumpitonHistoryDTO);
				}
			}
		}
//		logger.logMethodEnd(methodName);
		return listObj;
	}
	
	public static ClientResponse nameChange(NameChangeModel nameChangeModel,Boolean includeHeader,Environment env)
	{
		String methodName = "nameChange";
//		logger.logMethodStart(methodName);
		String uri = env.getProperty("service.req.uri");
 	    String usrName = env.getProperty("portal.api.username");
 	    String pass = env.getProperty("portal.api.password");
 	    ClientResponse resp = null;
    	try {
    		
    	   String input = "{\"RequestType\":\""+nameChangeModel.getRequestType()+"\",\"CorrectionType\":\""+nameChangeModel.getCorrectionType()+"\",\"ChangeReason\":\""+nameChangeModel.getChangeReason()+"\",\"UName\":\""+nameChangeModel.getuName()+"\"}" ;
//    	   logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
    	   Client c = Client.create(); 
		   c.addFilter(new HTTPBasicAuthFilter(usrName, pass));

			WebResource resource = c.resource(uri);
			resource.setProperty("Content-Type", "application/json;charset=UTF-8");
			if(includeHeader)
				resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header(env.getProperty("getConsumerDetails_key"), env.getProperty("getConsumerDetails_value")).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);

			else
				resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
			
//			logger.logMethodEnd(methodName);
			return resp;
		
	}catch(Exception ex)
    	{
//		logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
		}
//    	logger.logMethodEnd(methodName);
		return resp;
    }
	
	
	
	
	
	 public ClientResponse billValidate(String kno, String billNo, String isSBMBill, String discomName,Environment env)throws RequestNotFoundException{
		 String methodName = "billValidate";
//		 logger.logMethodStart(methodName);
		 ClientResponse resp =null;		    
		 try{
			 String duration=env.getProperty("bill.validate.duration");
		  	 String billVldServName=env.getProperty("bill.validate.service.name");
		  	 String input = "{\"KNo\":\""+kno+"\",\"BillNumber\":\""+billNo+"\",\"Duration\":"+duration+",\"SBMBillFlag\":\""+isSBMBill+"\"}";		  		
//		  	 logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		  	 resp =callRestAPI(input,billVldServName,discomName,env);
//		  	 logger.logMethodEnd(methodName);
		  	 return resp;
		  }
		 catch(Exception ex){
//		 	logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
		 }
//		 logger.logMethodEnd(methodName);
		 return resp;
	  }
	
		public List<String> getAcountIDs(String mNumber,String discomName,Environment env){
			String methodName = "getAcountIDs";
//			logger.logMethodStart(methodName);
	    	List accountNoList =  new ArrayList<String>();
	    	try {
	    		 
	        	String input = "{\"MobileNo\":\""+mNumber+"\"}";
//	        	logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
	        	ClientResponse resp = ServiceDataUtil.callRestAPI(input, "getAccountNo",discomName, env);

	        	if(null == resp || resp.getStatus() != 200){
//	        		logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
					throw new RequestNotFoundException("Invalid Mobile Number!");
	        	}
	        	else{
	        		String output = resp.getEntity(String.class);
	        		JSONObject finalResult = new JSONObject(output);                    
	        		String ec =  finalResult.getString("errorCode");
//	        		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
	        		if(ec.equalsIgnoreCase("1")){
//	        			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,
	        					new StringBuilder("Error code is 1 in Get Account ID in Rest Processing for Input-->"+input).toString();
	        		}
	        		else
	        		{
	        			JSONArray accountIds =  finalResult.getJSONArray("AcctIdList"); 
	        			try {
	        	            for (int i=0; i<accountIds.length(); i++){
	        	            	String accId = ((JSONObject)accountIds.get(i)).getString("AccountId");
	        	            	accountNoList.add(accId);
	        	            }
	        	        } catch (Exception ex) {        	        	
//	        	        	logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
	        	        }        	        
	        			
	        		}
	        	}
	        } catch (Exception ex) {
//	        	logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
	        }
//	    	logger.logMethodEnd(methodName);
	        return accountNoList;
	    }

	 public static BigDecimal getJsonBigDecimalData(JSONObject finalResult, String key) {
		 BigDecimal data = null;
			try {
				if(finalResult.has(key))
					data = finalResult.getBigDecimal(key); 
			}catch(Exception e) {		
			}
			return data;
		}
	 
	 /**
	     * Method is invoking the REST for initiating the mail which will be
	     * dispatched to the consumer on given emailID for Re-Confirming.
	     * 
	     * @throws RequestNotFoundException
	     * @throws Exceptioon
	     */
		/*
		 * public ClientResponse sendPasswordMail(CustomerDetailsDTO
		 * customerResDTO,Environment env) throws RequestNotFoundException{ String
		 * methodName = "sendPasswordMail"; logger.logMethodStart(methodName);
		 * ClientResponse resp =null; try{ String
		 * from=env.getProperty("send.email.from"); String
		 * subject=env.getProperty("send.email.subject"); String
		 * contentType=env.getProperty("send.email.content.type"); ConsumerMasterEntity
		 * usrSetUpEntity=consumerMasterRepository.findByKnoAndDiscomName(customerResDTO
		 * .getKno(), customerResDTO.getDiscom()); String
		 * usrNamePass=usrSetUpEntity.getUpassword(); String input =
		 * "{\"From\" : \""+from+"\",\"To\" : \""+customerResDTO.getEmail()
		 * +"\",\"Subject\" : \""+subject+"\",\"ContentType\" : \""
		 * +contentType+"\",\"Content\" : \""+usrNamePass+"\"}";
		 * logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName
		 * +"API request "+input); resp
		 * =callRestAPI(input,Constants.SEND_EMAIL_SERVICE,customerResDTO.getDiscom(),
		 * env); logger.logMethodEnd(methodName); return resp; } catch(Exception ex){
		 * logger.log(UPPCLLogger.LOGLEVEL_ERROR,
		 * methodName,"Exception in "+methodName+" "+ex.getMessage()); }
		 * logger.logMethodEnd(methodName); return resp;
		 * 
		 * }
		 */
	    
	    
	    public boolean getAcountIDUsingMeterSerial(String mNumber,String discomName,Environment env ){
	    	String methodName = "getAcountIDUsingMeterSerial";
//	    	logger.logMethodStart(methodName);
	        boolean returnStatus=false;
	    	try {
	    		if(null != accountNoList && accountNoList.size()>0)
	    			return true;
	        	String input = "{\"MtrSNo\":\""+mNumber+"\"}";
//	        	logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
	        	ClientResponse resp = callRestAPI(input,Constants.GET_ACCOUNT_USING_METER_SERIAL,discomName,env);

	        	if(null == resp || resp.getStatus() != 200){
//	        		logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
	        	}
	        	else{
	        		String output = resp.getEntity(String.class);
	        		JSONObject finalResult = new JSONObject(output);                    
	        		String ec =  finalResult.getString("ResponseCode");
//	        		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
	        		String message =  finalResult.getString("ResponseMsg");
	        		
	        		if(ec.equalsIgnoreCase("1") && message.equalsIgnoreCase("Success")){
	        			JSONArray accountIds =  finalResult.getJSONArray("AccountDetail");        			
	        			accountNoList =  new ArrayList<String>();        			
	        			try {
	        	            for (int i=0; i<accountIds.length(); i++){
	        	            	String accId = ((JSONObject)accountIds.get(i)).getString("AccountId");
	        	            	accountNoList.add(accId);
	        	            }
	        	        } catch (Exception ex) {        	        	
//	        	        	logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
	        	        }        	        
	        			returnStatus = true;
	        			
	        		}
	        		else
	        		{
	        			/*errors.add("mobile", new ActionError("paybill.meterSerialNumber.notFound"));
	        			logger.log(UPPCLLogger.LOGLEVEL_ERROR, METHOD_NAME,
	        					new StringBuilder("Error code is 1 in Get Account in SOA Processing for Input-->"+input).toString());  */      			
	        		}
	        	}
	        } catch (Exception ex) {
//	        	logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
	        }
//	    	logger.logMethodEnd(methodName);
	        return returnStatus;
	    }
	    
	    
		public CustomerDetailsDTO populateUserDetailsInDTO(CustomerDetailsDTO customerDetailsDTOObj,
				ArrayList<String> relatedConsumers,
				BillDetailsResDTO billDetailsResDTO) {
			CustomerDetailsDTO CustomerDetailsDTO = new CustomerDetailsDTO();
			String methodName = "populateUserDetailsInDTO";
//			logger.logMethodStart(methodName);
			CustomerDetailsDTO customerDetails = new CustomerDetailsDTO();
			customerDetails.setKno(customerDetailsDTOObj.getKno());
			customerDetails.setName(customerDetailsDTOObj.getName());
			customerDetails.setAccountInfo(customerDetailsDTOObj.getAccountInfo());
			customerDetails.setBookNo(customerDetailsDTOObj.getBookNo());
			String billingAddress = customerDetailsDTOObj.getBillingAddresss().getAddressLine1() + ","
			+ customerDetailsDTOObj.getBillingAddresss().getAddressLine2() + ","
			+ customerDetailsDTOObj.getBillingAddresss().getAddressLine3() + ","
			+ customerDetailsDTOObj.getBillingAddresss().getCity()
			+ "," + customerDetailsDTOObj.getBillingAddresss().getState() + ","
			+ customerDetailsDTOObj.getBillingAddresss().getPinCode();
			billingAddress = billingAddress.replace(",,,", ",");
			billingAddress = billingAddress.replace(",,", ",");
			customerDetails.setBillingAddress(billingAddress);

			String currentAddress = customerDetailsDTOObj.getPremiseAddress().getAddressLine1() + ","
			+ customerDetailsDTOObj.getPremiseAddress().getAddressLine2() + ","
			+ customerDetailsDTOObj.getPremiseAddress().getAddressLine3() + ","
			+ customerDetailsDTOObj.getPremiseAddress().getCity()
			+ "," + customerDetailsDTOObj.getPremiseAddress().getState() + ","
			+ customerDetailsDTOObj.getPremiseAddress().getPinCode();
			currentAddress = currentAddress.replace(",,,", ",");
			currentAddress = currentAddress.replace(",,", ",");
			customerDetails.setCurrentAddress(currentAddress);

			customerDetails.setCategory(customerDetailsDTOObj.getCategory());
			customerDetails.setSubCategory(customerDetailsDTOObj.getSubCategory());
			customerDetails.setDiscomName(customerDetailsDTOObj.getDiscom());
			customerDetails.setDivision(customerDetailsDTOObj.getDivision());
			customerDetails.setSubDivision(customerDetailsDTOObj.getSubDivision());
			customerDetails.setGroupName("Consumer");
			customerDetails.setOnlineBillingStatus(customerDetailsDTOObj.getOnlineBillingStatus());

			customerDetails.setPhoneNo(customerDetailsDTOObj.getPhoneNo());
			customerDetails.setMobileNo(customerDetailsDTOObj.getMobileNo());
			// customerDetails.setDateOfBirth(customerDetailsDTOObj.getDateOfBirth());
			customerDetails.setEmail(customerDetailsDTOObj.getEmail());

			customerDetails.setPersonId((customerDetailsDTOObj.getPersonId()));
			customerDetails.setSanctionedLoad(customerDetailsDTOObj.getSanctionedLoad());

			customerDetails.setSecurityAmount(customerDetailsDTOObj.getSecurityAmount());
			customerDetails.setCustomerIndexNumber(customerDetailsDTOObj.getCustomerIndexNumber());
			customerDetails.setSupplyType(customerDetailsDTOObj.getSupplyType());

			customerDetails.setSecondaryNumber(relatedConsumers);
			customerDetails.setMothersName(customerDetailsDTOObj.getMothersName());
			customerDetails.setTypeOfMeter(customerDetailsDTOObj.getTypeOfMeter());
			customerDetails.setTypeOfPhase(customerDetailsDTOObj.getTypeOfPhase());
			customerDetails.setTypeOfPlace(customerDetailsDTOObj.getTypeOfPlace());
			customerDetails.setConsumerType(customerDetailsDTOObj.getConsumerType());
			// Below code is used to set dueBillAmount in the custDetails in session.
			BigDecimal amount = new BigDecimal(customerDetailsDTOObj.getAccountInfo());
			if (null != customerDetailsDTOObj.getAccountInfo()) {
				customerDetails.setDueBillAmt(amount.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
			} else
				customerDetails.setDueBillAmt("0.0");

			if (billDetailsResDTO != null) {
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				customerDetails.setBillNo(billDetailsResDTO.getBillNo());
//				customerDetails.setDueDate(formatter.format(billDetailsResDTO.getBillDueDate()));
				customerDetails.setDueDate(billDetailsResDTO.getBillDueDate());
				customerDetails.setDueAmount(billDetailsResDTO.getPaymentDue() + "");
				customerDetails.setPaymentDate(billDetailsResDTO.getPaymentDate());
				customerDetails.setPaymentMade(billDetailsResDTO.getPaymentMade());
				customerDetails.setBillDate(billDetailsResDTO.getBillIssuedDate());
			}
			customerDetails.setTypeOfConnection(customerDetailsDTOObj.getTypeOfConnection());
//			logger.logMethodEnd(methodName);
			return customerDetails;
		}

		public CustomerDetailsDTO populateUserDetailsInDTO(CustomerResponseModel customerResDTO,
				ArrayList<String> relatedConsumers,
				BillDetailsResDTO billDetailsResDTO) {
			String methodName = "populateUserDetailsInDTO(CustomerResponseModel customerResDTO,ArrayList<String> relatedConsumers,BillDetailsResDTO billDetailsResDTO)";
//			logger.logMethodStart(methodName);
			CustomerDetailsDTO customerDetails = new CustomerDetailsDTO();
			customerDetails.setKno(customerResDTO.getKnumber());
			customerDetails.setName(customerResDTO.getConsumerName());
			customerDetails.setAccountInfo(customerResDTO.getAccountInfo());
			customerDetails.setBookNo(customerResDTO.getBookNo());
			String billingAddress = customerResDTO.getBillingAddress().getAddressLine1() + ","
			+ customerResDTO.getBillingAddress().getAddressLine2() + ","
			+ customerResDTO.getBillingAddress().getAddressLine3() + ","
			+ customerResDTO.getBillingAddress().getCity()
			+ "," + customerResDTO.getBillingAddress().getState() + ","
			+ customerResDTO.getBillingAddress().getPinCode();
			billingAddress = billingAddress.replace(",,,", ",");
			billingAddress = billingAddress.replace(",,", ",");
			customerDetails.setBillingAddress(billingAddress);

			String currentAddress = customerResDTO.getPremiseAddress().getAddressLine1() + ","
			+ customerResDTO.getPremiseAddress().getAddressLine2() + ","
			+ customerResDTO.getPremiseAddress().getAddressLine3() + ","
			+ customerResDTO.getPremiseAddress().getCity()
			+ "," + customerResDTO.getPremiseAddress().getState() + ","
			+ customerResDTO.getPremiseAddress().getPinCode();
			currentAddress = currentAddress.replace(",,,", ",");
			currentAddress = currentAddress.replace(",,", ",");
			customerDetails.setCurrentAddress(currentAddress);

			customerDetails.setCategory(customerResDTO.getCategory());
			customerDetails.setSubCategory(customerResDTO.getSubCategory());
			customerDetails.setDiscomName(customerResDTO.getDiscom());
			customerDetails.setDivision(customerResDTO.getDivision());
			
			customerDetails.setSubDivision(customerResDTO.getSubDivision());
			customerDetails.setGroupName("Consumer");
			customerDetails.setOnlineBillingStatus(customerResDTO.getOnlineBillingStatus());

			customerDetails.setPhoneNo(customerResDTO.getPhoneNo());
			customerDetails.setMobileNo(customerResDTO.getMobileNo());
			// customerDetails.setDateOfBirth(customerResDTO.getDateOfBirth());
			customerDetails.setEmail(customerResDTO.getEmail());

			customerDetails.setPersonId((customerResDTO.getPersonId()));
			customerDetails.setSanctionedLoad(customerResDTO.getSanctionLoad());
			customerDetails.setSecurityAmount(customerResDTO.getSecurityAmount());
			customerDetails.setCustomerIndexNumber(customerResDTO.getCustomerIndexNo());
			customerDetails.setSupplyType(customerResDTO.getSupplyType());

			customerDetails.setSecondaryNumber(relatedConsumers);
			customerDetails.setMothersName(customerResDTO.getMothersName());
			customerDetails.setTypeOfMeter(customerResDTO.getTypeOfMeter());
			customerDetails.setTypeOfPhase(customerResDTO.getTypeOfPhase());
			customerDetails.setTypeOfPlace(customerResDTO.getTypeOfPlace());
			customerDetails.setConsumerType(customerResDTO.getConsumerType());
			// Below code is used to set dueBillAmount in the custDetails in session.
			BigDecimal amount = new BigDecimal(customerResDTO.getAccountInfo());
			if (null != customerResDTO.getAccountInfo()) {
				customerDetails.setDueBillAmt(amount.setScale(0, BigDecimal.ROUND_HALF_UP).toString());
			} else
				customerDetails.setDueBillAmt("0.0");

			if (billDetailsResDTO != null) {
				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				customerDetails.setBillNo(billDetailsResDTO.getBillNo());
//				customerDetails.setDueDate(formatter.format(billDetailsResDTO.getBillDueDate()));
				customerDetails.setDueDate(billDetailsResDTO.getBillDueDate());
				customerDetails.setDueAmount(billDetailsResDTO.getPaymentDue() + "");
				customerDetails.setPaymentDate(billDetailsResDTO.getPaymentDate());
				customerDetails.setPaymentMade(billDetailsResDTO.getPaymentMade());
				customerDetails.setBillDate(billDetailsResDTO.getBillIssuedDate());
			}
			customerDetails.setTypeOfConnection(customerResDTO.getConnectionType());
//			logger.logMethodEnd(methodName);
			return customerDetails;
		}	  
		

		 public ClientResponse viewBill(String billNO, String discomName,Environment env)  throws RequestNotFoundException {
			 String methodName = "viewBill";
//			 logger.logMethodStart(methodName);
			 ClientResponse viewBillPdfResp =null;
			 try{			  		
				String input = "{\"BillId\":\""+billNO+"\",\"Flag\":\""+Constants.VIEW_BILL_DOWNLOAD_PDF_FLAG+"\"}";
//				logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
				viewBillPdfResp =callRestAPI(input,Constants.VIEW_BILL,discomName,env);
			}
			catch(Exception ex){
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
			}
//			logger.logMethodEnd(methodName);
			return viewBillPdfResp;
		 }
		 
		 
		 public ViewBillResModel viewBillvv(String kno, String discomName,Environment env)  throws RequestNotFoundException {
			 String methodName = "viewBillvv";
//			 logger.logMethodStart(methodName);
		           ClientResponse resp =null;	
			       ViewBillResModel viewBillResDTO =null;
					  	try{			  		
					  		String input = "{\"ActId\":\""+kno+"\",\"Flag\":\""+Constants.VIEW_BILL_PDF_FLAG+"\"}";
//					  		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
					  		ClientResponse	viewBillPdfResp =callRestAPI(input,Constants.VIEW_BILL,discomName,env);
					  		if(null == viewBillPdfResp || viewBillPdfResp.getStatus() != 200){				        		
//					  			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
								throw new RequestNotFoundException("Invalid Details!");//service error
				        	}else {
					  				String output = viewBillPdfResp.getEntity(String.class);
					        		JSONObject finalResult = new JSONObject(output);                    
					        		String ec =  finalResult.getString("ResCode");
//					        		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
					        		String message =  finalResult.getString("ResMsg");
					        		
					        		if(ec.equalsIgnoreCase("1") && message.equalsIgnoreCase("success")){					        			
					        			viewBillResDTO = new ViewBillResModel();
					     		        viewBillResDTO.setContentType(getJsonData(finalResult,"ReportContentType"));
					     		        //viewBillResDTO.setContent(ConverterUtil.getStringFromDatahandler(viewBillResType.getReportContents()));
					     		        viewBillResDTO.setContent(getJsonData(finalResult,"ReportContents"));
					        			
					        		 return viewBillResDTO;
					        		 
					        	}else if(ec.equalsIgnoreCase("-1")){				        	
					        		
									throw new RequestNotFoundException("Invalid Request For View Bill");//service error
					        	}else if(ec.equalsIgnoreCase("0")){				        	
					        		
					        		throw new RequestNotFoundException("Service Error");//service error
					        	}
					  		
					  		}
					  		
					  	    }
					  		catch(Exception ex){
//					  			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
					  		}
//					  	logger.logMethodEnd(methodName);
						return viewBillResDTO;
		    }
		 
		 
		 public static ClientResponse sendOTPOnEmail(String source,String email,String mode,boolean includeHeader,String serviceName,String discomName,Environment env) {
			 String methodName = "sendOTPOnEmail";
//			 logger.logMethodStart(methodName);
				if(null != discomName)
					serviceName = serviceName+"_"+discomName;
				String uri = env.getProperty(serviceName);
				//String uri = env.getProperty("send.otp.email.mobileNo.details.uri");
		 	    String usrName = env.getProperty("portal.api.username");
		 	    String pass = env.getProperty("portal.api.password");
		 	   ClientResponse resp = null;
		    	try {
		    		
		    	   String input =  "{\"Source\":\""+source+"\",\"Email\":\""+email+"\",\"Mode\":\""+mode+"\"}";
//		    	   logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		    	   Client c = Client.create(); 
				   c.addFilter(new HTTPBasicAuthFilter(usrName, pass));
				   WebResource resource = c.resource(uri);
				   resource.setProperty("Content-Type", "application/json;charset=UTF-8");
					if(includeHeader)
						resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header(env.getProperty("getConsumerDetails_key"), env.getProperty("getConsumerDetails_value")).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
					  //resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("SRC", "WSSPORTAL").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
					else
						resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
//					logger.logMethodEnd(methodName);
				return resp;
				
			}catch(Exception ex)
		    	{
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
				}
//		    	logger.logMethodEnd(methodName);
				return resp;
		    }
		 
			/*
			 * public ClientResponse sendMailSelfBillGen(SelfBillGenEmailModel
			 * selfBillGenEmailModel,Environment env) throws RequestNotFoundException{
			 * String methodName = "sendMailSelfBillGen"; logger.logMethodStart(methodName);
			 * ClientResponse resp =null; try{ String
			 * from=env.getProperty("send.email.from"); String
			 * subject=env.getProperty("send.email.subject"); String
			 * contentType=env.getProperty("send.email.content.type"); ConsumerMasterEntity
			 * usrSetUpEntity=consumerMasterRepository.findByKnoAndDiscomName(
			 * selfBillGenEmailModel.getKNumber(), selfBillGenEmailModel.getDiscom());
			 * 
			 * String knoName=selfBillGenEmailModel.getKNumber()+","+selfBillGenEmailModel.
			 * getConsumerName();
			 * 
			 * String input =
			 * "{\"From\" : \""+from+"\",\"To\" : \""+selfBillGenEmailModel.getEmail()
			 * +"\",\"Subject\" : \""+subject+"\",\"ContentType\" : \""
			 * +contentType+"\",\"Content\" : \""+knoName+"\"}";
			 * logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName
			 * +"API request "+input); resp
			 * =callRestAPI(input,Constants.SEND_EMAIL_SERVICE,selfBillGenEmailModel.
			 * getDiscom(),env); logger.logMethodEnd(methodName); return resp; }
			 * catch(Exception ex){ logger.log(UPPCLLogger.LOGLEVEL_ERROR,
			 * methodName,"Exception in "+methodName+" "+ex.getMessage()); }
			 * logger.logMethodEnd(methodName); return resp;
			 * 
			 * }
			 */
		 public static ClientResponse sendOTPOnMobile(String source,String email,String mobile,String mode,boolean includeHeader,String serviceName,String discomName,Environment env) {
			 String methodName = "sendOTPOnMobile";
//			 logger.logMethodStart(methodName);
				if(null != discomName)
					serviceName = serviceName+"_"+discomName;
				String uri = env.getProperty(serviceName);
				//String uri = env.getProperty("send.otp.email.mobileNo.details.uri");
		 	    String usrName = env.getProperty("portal.api.username");
		 	    String pass = env.getProperty("portal.api.password");
		 	   ClientResponse resp = null;
		    	try {
		    		
		    	   String input =  "{\"Source\":\""+source+"\",\"Email\":\""+email+"\",\"MobileNo\":\""+mobile+"\",\"Mode\":\""+mode+"\"}";
//		    	   logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		    	   Client c = Client.create(); 
				   c.addFilter(new HTTPBasicAuthFilter(usrName, pass));
				   WebResource resource = c.resource(uri);
				   resource.setProperty("Content-Type", "application/json;charset=UTF-8");
					if(includeHeader)
						resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header(env.getProperty("getConsumerDetails_key"), env.getProperty("getConsumerDetails_value")).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
					  //resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).header("SRC", "WSSPORTAL").type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
					else
						resp = resource.accept(MediaType.APPLICATION_JSON_TYPE).type(MediaType.APPLICATION_JSON_TYPE).post(ClientResponse.class,input);
//				logger.logMethodEnd(methodName);
				return resp;
				
			}catch(Exception ex)
		    	{
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
				}
//		    	logger.logMethodEnd(methodName);
				return resp;
		    }
		 
		 public static ClientResponse veryfyMobileOTP(Long mobile,String email,String OTP, String discomName,String verifyMode,Boolean includeHeader,Environment env) {	
			 String methodName = "veryfyMobileOTP";
//			 logger.logMethodStart(methodName);	
			 ClientResponse resp = null;
		    	try {
		    		String mob=String.valueOf(mobile);
		    		
		    	   String input = "{\"MobileNo\":\""+mob+"\",\"Email\":\""+email+"\",\"OTP\":\""+OTP+"\",\"VerifyMode\":\""+verifyMode+"\"}" ;
//		    	   logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		    	   resp = callRestAPI(input,"verifyOTP",discomName,env);
//		    	   logger.logMethodEnd(methodName);
		    	return resp;
				
			}catch(Exception ex)
		    	{
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,"Exception in "+methodName+" "+ex.getMessage());
				}
//		    	logger.logMethodEnd(methodName);
				return resp;
		    }
		 
			/*
			 * public ClientResponse sendMobileUpdateMail(CustomerDetailsDTO
			 * updateMobile,Environment env) throws RequestNotFoundException{ String
			 * methodName = "sendMobileUpdateMail"; logger.logMethodStart(methodName);
			 * ClientResponse resp =null; try{ String
			 * from=env.getProperty("send.email.from"); String
			 * subject=env.getProperty("send.email.subject"); String
			 * contentType=env.getProperty("send.email.content.type"); ConsumerMasterEntity
			 * usrSetUpEntity=consumerMasterRepository.findByKnoAndDiscomName(updateMobile.
			 * getKno(), updateMobile.getDiscom());
			 * 
			 * String
			 * updMobVal=updateMobile.getKno()+","+updateMobile.getName()+""+updateMobile.
			 * getLastName()+updateMobile.getNewMobileNo();
			 * 
			 * String input = "{\"From\" : \""+from+"\",\"To\" : \""+updateMobile.getEmail()
			 * +"\",\"Subject\" : \""+subject+"\",\"ContentType\" : \""
			 * +contentType+"\",\"Content\" : \""+updMobVal+"\"}";
			 * logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName
			 * +"API request "+input); resp
			 * =callRestAPI(input,Constants.SEND_EMAIL_SERVICE,updateMobile.getDiscomName(),
			 * env); logger.logMethodEnd(methodName); return resp; } catch(Exception ex){
			 * logger.log(UPPCLLogger.LOGLEVEL_ERROR,
			 * methodName,"Exception in "+methodName+" "+ex.getMessage()); }
			 * logger.logMethodEnd(methodName); return resp;
			 * 
			 * }
			 */
		 public String sendEmail(String discomName,String input,Environment env) {
			 String methodName = "sendEmail(String discomName,String input,Environment env)";
//			 logger.logMethodStart(methodName);
			String sendMailStatus = null;
			ClientResponse resp = callRestAPI(input, "sendEmail",discomName,env);
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
			if(null == resp || resp.getStatus() != 200){
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
//				throw new RequestNotFoundException("Internal Server Error from get Discom");
			}
			else{
				String output = resp.getEntity(String.class);
//				logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response not null");
				JSONObject finalResult = new JSONObject(output);       
				sendMailStatus =  finalResult.getString("Result");
			}
//			logger.logMethodEnd(methodName);
			return sendMailStatus;
		}
		 
	public String sendSMS(String discomName,String input,Environment env) {
		String methodName = "sendSMS";
//		 logger.logMethodStart(methodName);
		String sendSMSStatus = null;
//		String input = "{\"From\" : \"self.service@uppclonline.com\",\"To\" : \"mathewt@infinite.com\",\"Subject\" : \"UTTAR PRADESH POWER CORPORATION LTD.\",\"ContentType\" : \"text/plain\",\"Content\" : \"Testing1\"}";
		ClientResponse resp = callRestAPI(input, "sendsms",discomName,env);
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
//		throw new RequestNotFoundException("Internal Server Error from get Discom");
		}
		else{
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response not null");
			String output = resp.getEntity(String.class);
			JSONObject finalResult = new JSONObject(output);       
			sendSMSStatus =  finalResult.getString("ReqID");//Sucess
		}
//		logger.logMethodEnd(methodName);
		return sendSMSStatus;
	}

	public static List<BillDetailsResDTO> getBillingSummary(BillSummaryDTO billSummaryDTO,Environment env)  throws RequestNotFoundException
	{
		String methodName = "getBillingSummary";
//		logger.logMethodStart(methodName);
		List<BillDetailsResDTO> billDetailsResDTOList = null;
		String input = "{\"KNumber\":\""+billSummaryDTO.getKno()+"\",\"SearchParameters\":{\"DateRange\":{\"FromDate\":\""+billSummaryDTO.getFromDate()+"\",\"ToDate\":\""+billSummaryDTO.getToDate()+"\"}}}";
//		logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API request "+input);
		ClientResponse resp = callRestAPI(input,"getBillDetails",billSummaryDTO.getDiscomName(),env);
		if(null == resp || resp.getStatus() != 200){
//			logger.log(UPPCLLogger.LOGLEVEL_ERROR, methodName,methodName +"API response null");
			throw new RequestNotFoundException("Internal Server Error from Billing Details");
		}
		else{
			String output = resp.getEntity(String.class);
			JSONObject finalResult = new JSONObject(output);
			String ec =  finalResult.getString("ResCode");
//			logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,methodName +"API response "+ec);
			if(ec.equalsIgnoreCase("-1")){
				throw new RequestNotFoundException("Billing Details Not Found");
			}
			else if(ec.equalsIgnoreCase("1"))
			{
				billDetailsResDTOList = new ArrayList<BillDetailsResDTO>();
				JSONArray jsonArray = finalResult.getJSONArray("BillDetails");
				for(int i=0;i<jsonArray.length();i++) {
					JSONObject explrObject = jsonArray.getJSONObject(i);  
					BillDetailsResDTO billDetailsResDTOObj = new BillDetailsResDTO();
					billDetailsResDTOObj.setBillAmount((String)explrObject.get("BillAmount"));
					JSONObject billInfoJSON = explrObject.getJSONObject("BillInfo");//getting nested object
					billDetailsResDTOObj.setBillNo(billInfoJSON.getString("BillNo"));
					billDetailsResDTOObj.setBillDueDate(billInfoJSON.getString("BillDueDate"));
					billDetailsResDTOObj.setBillIssuedDate((String)explrObject.get("BillIssuedDate"));
					String paymentMade = null;
					if(explrObject.has("PaymentMade")) {
						paymentMade =explrObject.get("PaymentMade").toString();
						billDetailsResDTOObj.setPaymentMade((Double.parseDouble(paymentMade)));
					}
					else {
						billDetailsResDTOObj.setPaymentMade(0.0);
					}
					String paymentDate = (String)getJsonData(explrObject, "PaymentDate");
					if(paymentDate == null|| "".equals(paymentDate)) {
						billDetailsResDTOObj.setPaymentDate(null);
					}
					else {
						billDetailsResDTOObj.setPaymentDate(paymentDate);
					}
					billDetailsResDTOList.add(billDetailsResDTOObj);
				}
			}
			else{
				String resMsg = null;
				resMsg= getJsonData(finalResult, "ResMsg");
				if(resMsg==null)
					throw new RequestNotFoundException("Service Error from get Billing Details");
				else
					throw new RequestNotFoundException("Billing Details Not Found");
			}	
		}
//		logger.logMethodEnd(methodName);
		return billDetailsResDTOList;
	}
	
	/*
	 * //send Old Password in forgot password public String
	 * sendOldPassword(@RequestBody CustomerDetailsDTO sendPassword,Environment env)
	 * throws RequestNotFoundException{ String methodName = "sendOldPassword";
	 * logger.logMethodStart(methodName); String key, iv; key =
	 * env.getProperty("encrypDecrypt.key"); iv =
	 * env.getProperty("encrypDecrypt.iv"); ConsumerMasterEntity
	 * consumerMasterEntityObj=null; consumerMasterEntityObj =new
	 * ConsumerMasterEntity();
	 * consumerMasterEntityObj.setKno(sendPassword.getKno());
	 * consumerMasterEntityObj.setDiscomName(sendPassword.getDiscomName());
	 * ConsumerMasterEntity chkUserInPortal =
	 * consumerMasterRepository.findByKnoAndDiscomName(consumerMasterEntityObj.
	 * getKno(),consumerMasterEntityObj.getDiscomName()); String pswdStrw =
	 * EncryptUtil.Encryptor.decrypt(key, iv, chkUserInPortal.getUpassword());
	 * String decrypString = pswdStrw.substring(10);
	 * logger.logMethodEnd(methodName); return decrypString; }
	 */
}