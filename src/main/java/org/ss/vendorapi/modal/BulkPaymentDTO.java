package org.ss.vendorapi.modal;

import java.math.BigDecimal;
import java.util.Date;



public class BulkPaymentDTO extends UPPCLDTO {
    private String track_id;

    private String sub_track_id;

    private String accountNum;

    private BigDecimal amount;

    private Date paymentDate;

    private String trans_ref_num;;

    private String paymentStatus;

    private String paymentMode;

    private String bankId;

    private String caseOrBillNo;

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String trackId) {
        track_id = trackId;
    }

    public String getSub_track_id() {
        return sub_track_id;
    }

    public void setSub_track_id(String subTrackId) {
        sub_track_id = subTrackId;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getTrans_ref_num() {
        return trans_ref_num;
    }

    public void setTrans_ref_num(String transRefNum) {
        trans_ref_num = transRefNum;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCaseOrBillNo() {
        return caseOrBillNo;
    }

    public void setCaseOrBillNo(String caseOrBillNo) {
        this.caseOrBillNo = caseOrBillNo;
    }

}
