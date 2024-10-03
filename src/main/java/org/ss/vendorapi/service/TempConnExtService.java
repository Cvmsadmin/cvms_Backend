/*
 * package org.ss.vendorapi.service;
 * 
 * import java.lang.System.Logger; import java.lang.System.Logger.Level;
 * 
 * import org.json.JSONObject; import org.springframework.core.env.Environment;
 * import org.springframework.stereotype.Service; import
 * org.ss.vendorapi.exceptions.RequestNotFoundException; import
 * org.ss.vendorapi.modal.TempConnExtModel; import
 * org.ss.vendorapi.util.ServiceDataUtil;
 * 
 * import com.sun.jersey.api.client.ClientResponse;
 * 
 * @Service public class TempConnExtService {
 * 
 * private static final Logger logger = System.getLogger("TempConnExtService");
 * private String className = this.getClass().getSimpleName();
 * 
 * public String getEligibilityForTempConnExt(TempConnExtModel
 * tempConnExt,Environment env) throws RequestNotFoundException{ final String
 * METHOD_NAME = "getEligibilityForTempConnExt"; logger.log(Level.INFO,
 * "Inside " +METHOD_NAME+" Start"); String input =
 * "{\"AcctId\":\""+tempConnExt.getKno()+
 * "\",\"ChangeType\":\"E\",\"ExtendDate\":\""+tempConnExt.getProposedDate()+
 * "\",\"ProposedCat\":\"\",\"CatType\":\"\",\"Comment\":\""+tempConnExt.
 * getRemarks()+"\",\"toDoRole\":\"TR-SDC\",\"toDoType\":\"INITCASE\"}";
 * logger.log(Level.INFO, "getEligibilityForTempConnExt request "+input);
 * ClientResponse resp = ServiceDataUtil.callRestAPI(input,
 * "getEligibilityForTempConnConversion",tempConnExt.getDiscom(),true, env);
 * logger.log(Level.INFO, "getEligibilityForTempConnExt response "+resp); String
 * returnValue = null; if(null == resp || resp.getStatus() != 200){ throw new
 * RequestNotFoundException("Internal Server Error"); } else{ String output =
 * resp.getEntity(String.class); JSONObject finalResult = new
 * JSONObject(output); String ec = finalResult.getString("RespCode");
 * if(ec.equalsIgnoreCase("S")){ String respMsg =
 * finalResult.getString("RespMsg"); returnValue = respMsg; } else
 * if(ec.equalsIgnoreCase("F")){ String respMsg =
 * finalResult.getString("RespMsg");
 * if("Enter Valid Extend Date".equalsIgnoreCase(respMsg)) throw new
 * RequestNotFoundException("Valid Date"); else
 * if("Not Eligible For Scheme".equalsIgnoreCase(respMsg)) throw new
 * RequestNotFoundException("Not Eligible For Scheme"); else
 * if("Temp to permanent conversion already done".equalsIgnoreCase(respMsg))
 * throw new RequestNotFoundException("already Done"); return returnValue; }
 * else if(ec.equalsIgnoreCase("E1")){ throw new
 * RequestNotFoundException("tempConverssionMandatoryDt"); } else
 * if(ec.equalsIgnoreCase("E2")){ throw new
 * RequestNotFoundException("tempConverssionMoreThanFiveYr"); } else
 * if(ec.equalsIgnoreCase("E4")){ throw new
 * RequestNotFoundException("tempConverssionProposedCustClass"); } else
 * if(ec.equalsIgnoreCase("E5")){ throw new
 * RequestNotFoundException("tempConverssionNotEligible"); } else
 * if(ec.equalsIgnoreCase("E6")){ throw new
 * RequestNotFoundException("tempConverssionInvalidAcc"); } else { throw new
 * RequestNotFoundException("Service Error"); } } logger.log(Level.INFO,
 * "Inside " +METHOD_NAME+" End"); return returnValue; } }
 */