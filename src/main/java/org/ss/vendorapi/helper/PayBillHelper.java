/*
 * package org.ss.vendorapi.helper;
 * 
 * import java.util.regex.Matcher; import java.util.regex.Pattern;
 * 
 * import org.ss.vendorapi.util.CommonUtils;
 * 
 * 
 * 
 * public class PayBillHelper {
 * 
 * private static Pattern pattern;
 * 
 * 
 *//**
	 * This method is used for validation of Form Data.
	 * 
	 * @param custDetailsDTO
	 * @param amount
	 * @param paymentMode
	 * @param errors
	 * @return
	 */
/*
 * public boolean validatePayBillForm(String totAmount, String amount, String
 * payableAmount, String paymentMode) { final String METHOD_NAME =
 * "validateForm(CustomerDetailsDTO custDetailsDTO, String amount, String paymentMode)"
 * ; CommonUtils.logProcStartTime(METHOD_NAME);
 * 
 * String numericPattern = "^[0-9]+$";
 * 
 * if (amount == null || "".equals(amount)) { //errors.add("totalAmt", new
 * ActionError("payBill.amount.numericAmount")); return false; }
 * 
 * if (payableAmount != null && !"".equals(payableAmount)) { pattern =
 * Pattern.compile(numericPattern); Matcher match =
 * pattern.matcher(payableAmount); boolean found = match.find(); if (!found) {
 * ////errors.add("amt", new ActionError("payBill.payAmount.numericAmount"));
 * return false; } }
 * 
 * if (amount != null && !"".equals(amount)) { pattern =
 * Pattern.compile(numericPattern); Matcher match = pattern.matcher(amount);
 * boolean found = match.find(); if (!found) { ////errors.add("amt", new
 * ActionError("payBill.amount.numericAmount")); return false; }
 * 
 * 
 * if (Integer.parseInt(amount) <
 * Integer.parseInt(custDetailsDTO.getDueAmount())) { //errors.add("totamount",
 * new ActionError("payBill.amount.greaterAmount")); return false; }
 * 
 * }
 * 
 * if (paymentMode == null && !"".equals(paymentMode)) {
 * ////errors.add("paymentMode", new
 * ActionError("payBill.paymentMode.required")); return false; } if
 * (Integer.parseInt(totAmount) <= 0) {
 * 
 * ////errors.add("totAmt", new ActionError("payBill.totAmount.zeroAmount"));
 * return false; }
 * 
 * CommonUtils.logProcEndTime(METHOD_NAME); return true;
 * 
 * }
 * 
 * 
 *//**
	 * This method is used for validation of Kno.
	 * 
	 * @param custDetailsDTO
	 * @param amount
	 * @param paymentMode
	 * @param errors
	 * @return
	 *//*
		 * public boolean validateKno( String kno) { final String METHOD_NAME =
		 * "validateForm(CustomerDetailsDTO custDetailsDTO, String kno,ActionMessages errors"
		 * ; CommonUtils.logProcStartTime(METHOD_NAME); if (kno == null ||
		 * "".equals(kno)) { return false; } Pattern pattern =
		 * Pattern.compile("\\d{10}"); Matcher matcher = pattern.matcher(kno); if
		 * (!matcher.matches()) { return false; }
		 * CommonUtils.logProcEndTime(METHOD_NAME); return true; }
		 * 
		 * 
		 * 
		 * 
		 * public boolean validateEmail(String email) { final String METHOD_NAME =
		 * "validateForm(CustomerDetailsDTO custDetailsDTO, String kno,ActionMessages errors"
		 * ; CommonUtils.logProcStartTime(METHOD_NAME); if (email == null ||
		 * "".equals(email)) { //errors.add("email", new
		 * ActionError("paybill.email.required")); return false; } String emailPattern =
		 * "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})";
		 * Pattern pattern = Pattern.compile(emailPattern); Matcher matcher =
		 * pattern.matcher(email); if (!matcher.matches()) { //errors.add("email", new
		 * ActionError("paybill.email.invalid")); return false; }
		 * 
		 * CommonUtils.logProcEndTime(METHOD_NAME); return true; }
		 * 
		 * 
		 * public boolean validateSMReconnForm(String totAmount,String paymentMode) {
		 * final String METHOD_NAME =
		 * "validateForm(CustomerDetailsDTO custDetailsDTO, String amount, String paymentMode)"
		 * ; CommonUtils.logProcStartTime(METHOD_NAME);
		 * 
		 * String numericPattern = "^[0-9]+$";
		 * 
		 * if (totAmount == null || "".equals(totAmount)) { //errors.add("totalAmt", new
		 * ActionError("smReconn.amount.numericAmount")); return false; }
		 * 
		 * if (totAmount != null && !"".equals(totAmount)) { pattern =
		 * Pattern.compile(numericPattern); Matcher match = pattern.matcher(totAmount);
		 * boolean found = match.find(); if (!found) { //errors.add("totalAmt", new
		 * ActionError("smReconn.amount.numericAmount")); return false; }
		 * 
		 * 
		 * if (Integer.parseInt(amount) <
		 * Integer.parseInt(custDetailsDTO.getDueAmount())) { //errors.add("totamount",
		 * new ActionError("payBill.amount.greaterAmount")); return false; }
		 * 
		 * }
		 * 
		 * if (paymentMode == null && !"".equals(paymentMode)) {
		 * //errors.add("paymentMode", new ActionError("payBill.paymentMode.required"));
		 * return false; } if (Integer.parseInt(totAmount) <= 0) {
		 * 
		 * //errors.add("totAmt", new ActionError("smReconn.totAmount.zeroAmount"));
		 * return false; }
		 * 
		 * CommonUtils.logProcEndTime(METHOD_NAME); return true;
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */