package org.ss.vendorapi.modal;

/**
 * This code contains copyright information which is the proprietary property
 * of Uttar Pradesh Power Corporation Ltd. No part of this code may be reproduced,
 * stored or transmitted in any form without the prior written permission of Uttar
 * Pradesh Power Corporation Ltd.
 *
 * Copyright (C) Uttar Pradesh Power Corporation Ltd. 2010
 * All rights reserved.
 * 
 *@author Sanjeev Agarwal
 */

//package org.uppcl.components.dto;

public class BillDetailsDTO {
	
	private String billNo;
	
	private String fromDate;
	
	private String toDate ;
	
	private String billDate ;
	
	private String billDueDate ;
	
	private String paymentDate ;
	
	private String paymentMade ; 
	
	private String paymentDue ;
	
	private String startDt;
	
	private String endDt;
	
	
	

	/**
	 * @return the startDt
	 */
	public String getStartDt() {
		return startDt;
	}

	/**
	 * @param startDt the startDt to set
	 */
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}

	/**
	 * @return the endDt
	 */
	public String getEndDt() {
		return endDt;
	}

	/**
	 * @param endDt the endDt to set
	 */
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}

	/**
	 * @return the billNo
	 */
	public String getBillNo() {
		return billNo;
	}

	/**
	 * @param billNo the billNo to set
	 */
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 * @return the fromDate
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	

	/**
	 * @return the toDate
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the billDate
	 */
	public String getBillDate() {
		return billDate;
	}

	/**
	 * @param billDate the billDate to set
	 */
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	

	/**
	 * @return the billDueDate
	 */
	public String getBillDueDate() {
		return billDueDate;
	}

	/**
	 * @param billDueDate the billDueDate to set
	 */
	public void setBillDueDate(String billDueDate) {
		this.billDueDate = billDueDate;
	}

	/**
	 * @return the paymentDate
	 */
	public String getPaymentDate() {
		return paymentDate;
	}

	/**
	 * @param paymentDate the paymentDate to set
	 */
	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	/**
	 * @return the paymentMade
	 */
	public String getPaymentMade() {
		return paymentMade;
	}

	/**
	 * @param paymentMade the paymentMade to set
	 */
	public void setPaymentMade(String paymentMade) {
		this.paymentMade = paymentMade;
	}

	/**
	 * @return the paymentDue
	 */
	public String getPaymentDue() {
		return paymentDue;
	}

	/**
	 * @param paymentDue the paymentDue to set
	 */
	public void setPaymentDue(String paymentDue) {
		this.paymentDue = paymentDue;
	}
	
	
	
}
