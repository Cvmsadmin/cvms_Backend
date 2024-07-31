package org.ss.vendorapi.modal;

public class PaymentThankYouDTO {

    private String name;

    private String requestNum;

    private String kNo;

    private String caseOrBillNo;

    private String amount;

    private String paymentDate;

    private String serviceName;

    private String status;

    private String trackId;

    private String bankName;

    private String paymentId;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status) {
        this.status = status;
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the requestNum
     */
    public String getRequestNum() {
        return requestNum;
    }

    /**
     * @param requestNum
     *            the requestNum to set
     */
    public void setRequestNum(String requestNum) {
        this.requestNum = requestNum;
    }

    /**
     * @return the kNo
     */
    public String getkNo() {
        return kNo;
    }

    /**
     * @param kNo
     *            the kNo to set
     */
    public void setkNo(String kNo) {
        this.kNo = kNo;
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

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     * @param amount
     *            the amount to set
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     * @return the paymentDate
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * @param paymentDate
     *            the paymentDate to set
     */
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

}
