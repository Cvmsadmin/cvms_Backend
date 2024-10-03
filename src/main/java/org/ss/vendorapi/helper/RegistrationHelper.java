/*
 * package org.ss.vendorapi.helper;
 * 
 * import java.io.BufferedReader; import java.io.Closeable; import
 * java.io.FileInputStream; import java.io.FileNotFoundException; import
 * java.io.InputStreamReader; import java.io.Reader;
 * 
 * import org.ss.vendorapi.exceptions.RequestNotFoundException; //import
 * org.ss.vendorapi.logging.UPPCLLogger; import
 * org.ss.vendorapi.modal.CustomerDetailsDTO; import
 * org.ss.vendorapi.modal.CustomerDetailsModel;
 * 
 * import io.jsonwebtoken.io.IOException;
 * 
 * public class RegistrationHelper {
 * 
 * 
 * public CustomerDetailsModel populateUserDetailsInDTO(CustomerDetailsDTO
 * customrResDTO) { final String methodName =
 * "populateUserDetailsInDTO(CustomerDetailsDTO customrResDTO)";
 * CustomerDetailsModel custDetailsDTO = new CustomerDetailsModel();
 * custDetailsDTO.setKno(customrResDTO.getKno());
 * custDetailsDTO.setName(customrResDTO.getName());
 * custDetailsDTO.setBookNo(customrResDTO.getBookNo()); String billingAddress =
 * customrResDTO.getBillingAddresss().getAddressLine1() + "," +
 * customrResDTO.getBillingAddresss().getAddressLine2() + "," +
 * customrResDTO.getBillingAddresss().getAddressLine3() + "," +
 * customrResDTO.getBillingAddresss().getCity() + "," +
 * customrResDTO.getBillingAddresss().getState() + "," +
 * customrResDTO.getBillingAddresss().getPinCode(); billingAddress =
 * billingAddress.replace(",,,", ","); billingAddress =
 * billingAddress.replace(",,", ",");
 * custDetailsDTO.setBillingAddress(billingAddress);
 * 
 * String currentAddress = customrResDTO.getPremiseAddress().getAddressLine1() +
 * "," + customrResDTO.getPremiseAddress().getAddressLine2() + "," +
 * customrResDTO.getPremiseAddress().getAddressLine3() + "," +
 * customrResDTO.getPremiseAddress().getCity() + "," +
 * customrResDTO.getPremiseAddress().getState() + "," +
 * customrResDTO.getPremiseAddress().getPinCode(); currentAddress =
 * currentAddress.replace(",,,", ","); currentAddress =
 * currentAddress.replace(",,", ",");
 * custDetailsDTO.setCurrentAddress(currentAddress);
 * 
 * custDetailsDTO.setCategory(customrResDTO.getCategory());
 * custDetailsDTO.setSubCategory(customrResDTO.getSubCategory());
 * custDetailsDTO.setEmail(customrResDTO.getEmail());
 * custDetailsDTO.setPhoneNo(customrResDTO.getPhoneNo());
 * custDetailsDTO.setMobileNo(customrResDTO.getMobileNo());
 * custDetailsDTO.setDivision(customrResDTO.getDivision());
 * custDetailsDTO.setSubDivision(customrResDTO.getSubDivision()); String discom
 * = customrResDTO.getDiscom(); discom = discom.split("-")[0].toString(); if
 * (null != discom) custDetailsDTO.setDiscomName(discom.trim());
 * 
 * custDetailsDTO.setDiscomName(customrResDTO.getDiscom());
 * custDetailsDTO.setPersonId(customrResDTO.getPersonId());
 * custDetailsDTO.setSanctionedLoad(customrResDTO.getSanctionedLoad());
 * custDetailsDTO.setSecurityAmount(customrResDTO.getSecurityAmount());
 * custDetailsDTO.setSupplyType(customrResDTO.getSupplyType());
 * custDetailsDTO.setCustomerIndexNumber(customrResDTO.getCustomerIndexNumber())
 * ; // logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,
 * "@@@@ 1. Customer Details DTO : : " + custDetailsDTO); //
 * logger.logMethodEnd(methodName); return custDetailsDTO; }
 * 
 * public static String getConfigFile(String filePath) throws
 * RequestNotFoundException, Exception{ final String methodName =
 * "getConfigFile(String filePath)"; // logger.logMethodStart(methodName);
 * Reader reader = null; Closeable resource = reader; String configFile = "";
 * try { FileInputStream fis = new FileInputStream(filePath); BufferedReader
 * bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
 * resource = bufferedReader; String input = ""; while ((input =
 * bufferedReader.readLine()) != null){ configFile += input + '\n'; } }catch
 * (FileNotFoundException ex) { // logger.log(UPPCLLogger.LOGLEVEL_INFO,
 * methodName, "@@@@ 1. Exception while reading the File from location: : : " +
 * ex.getMessage()); } catch (IOException ex) { //
 * logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,
 * "@@@@ 1. Exception while retrieving the file"+ex.getMessage()); }finally {
 * try { if(null != resource){ resource.close(); } } catch (IOException ex) { //
 * logger.log(UPPCLLogger.LOGLEVEL_INFO, methodName,
 * "@@@@ 2.Exception Occured when read file : " + " "+ ex.getMessage()); } } //
 * logger.logMethodEnd(methodName); return configFile; } }
 */