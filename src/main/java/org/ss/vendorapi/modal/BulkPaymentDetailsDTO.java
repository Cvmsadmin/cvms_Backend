package org.ss.vendorapi.modal;

import java.util.Date;

public class BulkPaymentDetailsDTO extends UPPCLDTO {

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }

    /**
     * @param dueDate
     *            the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    private String kno;

    // private String custName;

    private String dueDate;

    private String payableAmount;

    private String dueAmount;

    private String caseOrBillNo;

    private String billDueDate;

    private String totalPayableAmt;

    private String mobileNo;

    private String bankId;

    private String custName;

    // private String selectedKno;

    private String paymentMode;

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getTotalPayableAmt() {
        return totalPayableAmt;
    }

    public void setTotalPayableAmt(String totalPayableAmt) {
        this.totalPayableAmt = totalPayableAmt;
    }

    public String getKno() {
        return kno;
    }

    public void setKno(String kno) {
        this.kno = kno;
    }

    public String getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(String payableAmount) {
        this.payableAmount = payableAmount;
    }

    public String getDueAmount() {
        return dueAmount;
    }

    public void setDueAmount(String dueAmount) {
        this.dueAmount = dueAmount;
    }

    private String discomName;

    private String paymentId;

    private String trackId;

    private String divisionName;

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

    // private String excessAmount;

    private String serviceName;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    private Date billDate;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDiscomName() {
        return discomName;
    }

    public void setDiscomName(String discomName) {
        this.discomName = discomName;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getBillDueDate() {
        return billDueDate;
    }

    public void setBillDueDate(String billDueDate) {
        this.billDueDate = billDueDate;
    }

    private Date paymentDate;

    private String emailID;

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    // private String mobileNo;

    // private String bankId;
    //
    /**
     * @return the bankId
     */

}
