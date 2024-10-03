/*
 * package org.ss.vendorapi.util;
 * 
 * 
 * import java.math.BigDecimal; import java.text.DateFormat; import
 * java.text.SimpleDateFormat; import java.util.ArrayList; import
 * java.util.List;
 * 
 * import org.springframework.core.env.Environment; import
 * org.ss.vendorapi.exceptions.RequestNotFoundException; //import
 * org.ss.vendorapi.logging.UPPCLLogger; import
 * org.ss.vendorapi.modal.BillDetailsResDTO; import
 * org.ss.vendorapi.modal.BillDisplayDTO; import
 * org.ss.vendorapi.modal.CustomerDetailsDTO; import
 * org.ss.vendorapi.modal.GetRelatedConsumerDTO;
 * 
 * public class LoginHelper {
 * 
 * private static final Class<?> CLASS_NAME = LoginHelper.class; // private
 * static UPPCLLogger logger =
 * UPPCLLogger.getInstance(UPPCLLogger.MODULE_BILLING,CLASS_NAME.toString());
 * 
 * //@Autowired ConsumerMasterRepository consumerMasterRepository;
 * 
 * public CustomerDetailsDTO populateUserDetailsInDTO(CustomerDetailsDTO
 * customerDetailsDTO, ArrayList<String> relatedConsumers, BillDetailsResDTO
 * billDetailsResDTO) {
 * 
 * final String methodName = "populateUserDetailsInDTO"; //
 * logger.logMethodStart(methodName); CustomerDetailsDTO customerDetailsDTOObj =
 * new CustomerDetailsDTO();
 * customerDetailsDTOObj.setKno(customerDetailsDTO.getKno());
 * customerDetailsDTOObj.setName(customerDetailsDTO.getName());
 * customerDetailsDTOObj.setAccountInfo(customerDetailsDTO.getAccountInfo());
 * customerDetailsDTOObj.setBookNo(customerDetailsDTO.getBookNo()); String
 * billingAddress = customerDetailsDTO.getBillingAddresss().getAddressLine1() +
 * "," + customerDetailsDTO.getBillingAddresss().getAddressLine2() + "," +
 * customerDetailsDTO.getBillingAddresss().getAddressLine3() + "," +
 * customerDetailsDTO.getBillingAddresss().getCity() + "," +
 * customerDetailsDTO.getBillingAddresss().getState() + "," +
 * customerDetailsDTO.getBillingAddresss().getPinCode(); billingAddress =
 * billingAddress.replace(",,,", ","); billingAddress =
 * billingAddress.replace(",,", ",");
 * customerDetailsDTOObj.setBillingAddress(billingAddress);
 * 
 * String currentAddress =
 * customerDetailsDTO.getPremiseAddress().getAddressLine1() + "," +
 * customerDetailsDTO.getPremiseAddress().getAddressLine2() + "," +
 * customerDetailsDTO.getPremiseAddress().getAddressLine3() + "," +
 * customerDetailsDTO.getPremiseAddress().getCity() + "," +
 * customerDetailsDTO.getPremiseAddress().getState() + "," +
 * customerDetailsDTO.getPremiseAddress().getPinCode(); currentAddress =
 * currentAddress.replace(",,,", ","); currentAddress =
 * currentAddress.replace(",,", ",");
 * customerDetailsDTOObj.setCurrentAddress(currentAddress);
 * 
 * customerDetailsDTOObj.setCategory(customerDetailsDTO.getCategory());
 * customerDetailsDTOObj.setSubCategory(customerDetailsDTO.getSubCategory());
 * customerDetailsDTOObj.setDiscomName(customerDetailsDTO.getDiscom());
 * customerDetailsDTOObj.setDivision(customerDetailsDTO.getDivision()); try{
 * String [] data = customerResDTO.getDivision().split("\\$");
 * if(data.length>1){ customerDetailsDTO.setDivision(data[0]);
 * customerDetailsDTO.setDivCode(data[1]); } }catch(Exception e){}
 * 
 * customerDetailsDTOObj.setSubDivision(customerDetailsDTO.getSubDivision()); //
 * customerDetailsDTOObj.setGroupName("Consumer");
 * customerDetailsDTOObj.setOnlineBillingStatus(customerDetailsDTO.
 * getOnlineBillingStatus());
 * 
 * customerDetailsDTOObj.setPhoneNo(customerDetailsDTO.getPhoneNo());
 * customerDetailsDTOObj.setMobileNo(customerDetailsDTO.getMobileNo()); //
 * customerDetailsDTOObj.setDateOfBirth(customerDetailsDTO.getDateOfBirth());
 * customerDetailsDTOObj.setEmail(customerDetailsDTO.getEmail());
 * 
 * customerDetailsDTOObj.setPersonId((customerDetailsDTO.getPersonId()));
 * customerDetailsDTOObj.setSanctionedLoad(customerDetailsDTO.getSanctionedLoad(
 * ));
 * customerDetailsDTOObj.setSecurityAmount(customerDetailsDTO.getSecurityAmount(
 * )); customerDetailsDTOObj.setCustomerIndexNumber(customerDetailsDTO.
 * getCustomerIndexNumber());
 * customerDetailsDTOObj.setSupplyType(customerDetailsDTO.getSupplyType());
 * 
 * customerDetailsDTOObj.setSecondaryNumber(relatedConsumers);
 * customerDetailsDTOObj.setMothersName(customerDetailsDTO.getMothersName());
 * customerDetailsDTOObj.setTypeOfMeter(customerDetailsDTO.getTypeOfMeter());
 * customerDetailsDTOObj.setTypeOfPhase(customerDetailsDTO.getTypeOfPhase());
 * customerDetailsDTOObj.setTypeOfPlace(customerDetailsDTO.getTypeOfPlace());
 * customerDetailsDTOObj.setConsumerType(customerDetailsDTO.getConsumerType());
 * // Below code is used to set dueBillAmount in the custDetails in session.
 * BigDecimal amount = new BigDecimal(customerDetailsDTO.getAccountInfo()); if
 * (null != customerDetailsDTO.getAccountInfo()) {
 * customerDetailsDTOObj.setDueBillAmt(amount.setScale(0,
 * BigDecimal.ROUND_HALF_UP).toString()); } else
 * customerDetailsDTOObj.setDueBillAmt("0.0");
 * 
 * if (billDetailsResDTO != null) { DateFormat formatter = new
 * SimpleDateFormat("dd-MM-yyyy");
 * customerDetailsDTOObj.setBillNo(billDetailsResDTO.getBillNo()); //
 * customerDetailsDTOObj.setDueDate(formatter.format(billDetailsResDTO.
 * getBillDueDate()));
 * customerDetailsDTOObj.setDueDate(billDetailsResDTO.getBillDueDate()); //
 * customerDetailsDTOObj.setDueAmount(billDetailsResDTO.getPaymentDue() + "");
 * customerDetailsDTOObj.setDueAmount(billDetailsResDTO.getBillAmount());
 * customerDetailsDTOObj.setPaymentDate(billDetailsResDTO.getPaymentDate());
 * customerDetailsDTOObj.setPaymentMade(billDetailsResDTO.getPaymentMade());
 * customerDetailsDTOObj.setBillDate(billDetailsResDTO.getBillIssuedDate()); }
 * customerDetailsDTOObj.setTypeOfConnection(customerDetailsDTO.
 * getTypeOfConnection()); // logger.logMethodEnd(methodName); return
 * customerDetailsDTOObj; }
 * 
 * public CustomerDetailsDTO getConsumerDetails(String kno,String
 * discomName,Environment env) throws RequestNotFoundException{ final String
 * methodName = "getConsumerDetails"; // logger.logMethodStart(methodName);
 * CustomerDetailsDTO customerDetailsDTO = new CustomerDetailsDTO();
 * customerDetailsDTO
 * =ServiceDataUtil.getConsumerDetails(kno,discomName,env);//ConsumerDetails
 * Rest API //when consumer detail service data available start
 * CustomerDetailsDTO custDetailDTO = new CustomerDetailsDTO();
 * if(customerDetailsDTO!=null) { boolean tdco = false; tdco =
 * ("T").equalsIgnoreCase(customerDetailsDTO.getMothersName());
 * ArrayList<String> secondryAcc = new ArrayList<String>(); if
 * ((Constants.TRUE).equalsIgnoreCase(env.getProperty("relatedConsumerFlag"))) {
 * GetRelatedConsumerHelper getRelatedConsumerHelper = new
 * GetRelatedConsumerHelper(); GetRelatedConsumerDTO getRelatedConsumerDTO = new
 * GetRelatedConsumerDTO(); getRelatedConsumerDTO =
 * getRelatedConsumerHelper.populateGetRelatedConsumerDTO(customerDetailsDTO);
 * // List<RelatedConsumerResponseDTO> relatedConsumerResponseDTO =
 * consumerSOAAccessor.getRelatedConsumers(getRelatedConsumerDTO); // if
 * (relatedConsumerResponseDTO != null) { // if (logger.isDebugLoggingEnabled())
 * // logger.log(UPPCLLogger.LOGLEVEL_DEBUG, METHOD_NAME, new
 * StringBuilder("Account No : ").append(relatedConsumerResponseDTO.get(0).
 * getAccountNo()).toString()); // secondryAcc.add(customerResDTO.getKnumber());
 * // for (RelatedConsumerResponseDTO relatedConsumers :
 * relatedConsumerResponseDTO) { //
 * secondryAcc.add(relatedConsumers.getAccountNo());
 * secondryAcc.add(customerDetailsDTO.getKno());//temp // } } else {
 * secondryAcc.add(customerDetailsDTO.getKno()); } List<BillDetailsResDTO>
 * billDetailsResDTOList =
 * ServiceDataUtil.getBillingDetails(kno,customerDetailsDTO.getDiscom(),env);
 * if(billDetailsResDTOList == null) { throw new
 * RequestNotFoundException("Bill Details Not Found"); } else { LoginHelper
 * loginHelper = new LoginHelper(); if (billDetailsResDTOList != null &&
 * billDetailsResDTOList.size() > 0) {
 * 
 * custDetailDTO = loginHelper.populateUserDetailsInDTO(customerDetailsDTO,
 * secondryAcc,billDetailsResDTOList.get(0)); BillDisplayDTO billDisplayDTO =
 * ServiceDataUtil.getBillDisplayDetails(kno,customerDetailsDTO.getDiscom(),env)
 * ; if(billDisplayDTO!=null){
 * custDetailDTO.setPayAmtAfterDueDt(billDisplayDTO.getPayAmtAfterDt());
 * custDetailDTO.setPayAmtBeforeDueDt(billDisplayDTO.getPayAmtBeforeDt()); } //
 * else { // statusMap.put(Parameters.status, Constants.FAIL); //
 * statusMap.put("RegMsg",env.getProperty("billDisplayErrorMessage")); // } }
 * custDetailDTO.setDiscomName(customerDetailsDTO.getDiscom());
 * custDetailDTO.setDiscom(customerDetailsDTO.getDiscom());
 * custDetailDTO.setPremiseAddress(customerDetailsDTO.getPremiseAddress());
 * custDetailDTO.setBillingAddresss(customerDetailsDTO.getBillingAddresss());
 * custDetailDTO.setDateOfBirth(customerDetailsDTO.getDateOfBirth()); if
 * ("true".equalsIgnoreCase(custDetailDTO.getConfirmEmail())) {
 * 
 * //UPDATE LAST LOGIN DETAILS FROM USER MASTER
 * 
 * String lastLogin = consumerMasterRepository.getLastLogin(kno, discomName); if
 * (lastLogin == null||lastLogin == "") { custDetailDTO.setLastLogin(" "); }
 * else { custDetailDTO.setLastLogin(lastLogin); } String loginDate = "";
 * DateFormat formatterLogin = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
 * loginDate = formatterLogin.format(new Date()); int updateStatus = 0;
 * updateStatus = consumerMasterRepository.updateLastLogin(loginDate, kno,
 * discomName);
 * 
 * 
 * 
 * // return new ResponseEntity<>(statusMap, HttpStatus.OK); //// if(knoDiscom
 * !=null) //// updateStatus =
 * customerCredentialsRepository.updateLastLogin(loginDate, knoDiscom); ////
 * else //// updateStatus =
 * customerCredentialsRepository.updateLastLogin(loginDate,
 * customerDetailsDTO.getKno()); } // else { // //get URL for confirm Email and
 * put it into Hash map. //// loginHelper.sendMailWithDisplayErrMsg(pageUrl,
 * custDetailDTO,knoDiscom,custDetailDTO.getDiscomName());//shikha // } } } else
 * { throw new RequestNotFoundException("Consumer Details Not Found"); } //
 * logger.logMethodEnd(methodName); return custDetailDTO; } }
 */