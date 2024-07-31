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

package org.ss.vendorapi.modal;

import java.util.Date;

public class PayBillDetailsDTO extends UPPCLDTO {

    /**
     * @return the dueDate
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate
     *            the dueDate to set
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    private String kno;

    private String custName;

    private Date dueDate;

    private String payableAmount;

    private String dueAmount;

    private String selectedKno;

    private String paymentMode;

    private String caseOrBillNo;

    private String discomName;

    private String paymentId;

    private String trackId;
    private String divisionName;
    
    private String divisionCode;
    
    private String districtName;
    
	private String empRefNo;

    public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

    /**
     * @return the trackId
     */
    public String getTrackId() {
        return trackId;
    }

    /**
     * @param trackId
     *            the trackId to set
     */
    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    /**
     * @return the paymentId
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * @param paymentId
     *            the paymentId to set
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * @return the discomName
     */
    public String getDiscomName() {
        return discomName;
    }

    /**
     * @param discomName
     *            the discomName to set
     */
    public void setDiscomName(String discomName) {
        this.discomName = discomName;
    }

    /**
     * @return the caseOrBillNo
     */
    public String getCaseOrBillNo() {
        return caseOrBillNo;
    }

    /**
     * @param caseOrBillNo
     *            the caseOrBillNo to set
     */
    public void setCaseOrBillNo(String caseOrBillNo) {
        this.caseOrBillNo = caseOrBillNo;
    }

    private String excessAmount;

    private String serviceName;

    private Date billDate;

    private String billDueDate;

    private Date paymentDate;

    private String emailID;

    private String mobileNo;

    private String bankId;

    /**
     * @return the bankId
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * @param bankId
     *            the bankId to set
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     * @return the emailID
     */
    public String getEmailID() {
        return emailID;
    }

    /**
     * @param emailID
     *            the emailID to set
     */
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    /**
     * @return the mobileNo
     */
    public String getMobileNo() {
        return mobileNo;
    }

    /**
     * @param mobileNo
     *            the mobileNo to set
     */
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    /**
     * @return the billDate
     */
    public Date getBillDate() {
        return billDate;
    }

    /**
     * @param billDate
     *            the billDate to set
     */
    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    /**
     * @return the billDueDate
     */
    public String getBillDueDate() {
        return billDueDate;
    }

    /**
     * @param billDueDate
     *            the billDueDate to set
     */
    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }

    /**
     * @return the paymentDate
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * @param paymentDate
     *            the paymentDate to set
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * @return the custName
     */
    public String getCustName() {
        return custName;
    }

    /**
     * @param custName
     *            the custName to set
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * @param serviceName
     *            the serviceName to set
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    /**
     * @return the selectedKno
     */
    public String getSelectedKno() {
        return selectedKno;
    }

    /**
     * @param selectedKno
     *            the selectedKno to set
     */
    public void setSelectedKno(String selectedKno) {
        this.selectedKno = selectedKno;
    }

    /**
     * @return the kno
     */
    public String getKno() {
        return kno;
    }

    /**
     * @param kno
     *            the kno to set
     */
    public void setKno(String kno) {
        this.kno = kno;
    }

    /**
     * @return the payableAmount
     */
    public String getPayableAmount() {
        return payableAmount;
    }

    /**
     * @param payableAmount
     *            the payableAmount to set
     */
    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    /**
     * @return the dueAmount
     */
    public String getDueAmount() {
        return dueAmount;
    }

    /**
     * @param dueAmount
     *            the dueAmount to set
     */
    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    /**
     * @return the excessAmount
     */
    public String getExcessAmount() {
        return excessAmount;
    }

    /**
     * @param excessAmount
     *            the excessAmount to set
     */
    public void setExcessAmount(String excessAmount) {
        this.excessAmount = excessAmount;
    }

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	
	public String getEmpRefNo() {
		return empRefNo;
	}

	public void setEmpRefNo(String empRefNo) {
		this.empRefNo = empRefNo;
	}


}
