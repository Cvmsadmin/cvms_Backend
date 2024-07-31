package org.ss.vendorapi.modal;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@Entity
@Table(name = "Payment")
public class PaymentDTO implements Serializable,Persistable  {

	private static final long serialVersionUID = 2L;

	
	@Id 
	@Column(name = "track_id", nullable = false , updatable = false) 
    private String trackId;

	//@Version
	//private Long version;
	@Transient
	private boolean newRow = false;
	
	@Column(name = "ACCOUNT_NUM")
    private String accountNumber;

	@Column(name = "CASE_OR_BILL_NO")
    private String case_or_bill_no;

	@Column(name = "DUE_AMOUNT")
	private int due_amount;
    //private double due_amount;

	@Column(name = "PAYMENT_AMOUNT")
	private int payment_amount;
   //private double payment_amount;

	@Column(name = "CONVENIENCE_CHARGE")
    private int convinience_charge;	
	//private double convinience_charge;

	@Column(name = "EXCESS_AMOUNT")
	 private int excess_amount;
    //private double excess_amount;

	@Column(name = "PAYMENT_DATE")
    private Date paymentDate;

	@Column(name = "BILL_DATE")
    private Date billDate;

	@Column(name = "BILL_DUE_DATE")
    private Date billDueDate;

	@Column(name = "BANK_ID")
    private String bankID;

	@Column(name = "BANK_NAME")
    private String bankName;

	@Column(name = "discom_name")
    private String discomName;
	
	@Column(name = "PAYMENT_ID")
    private String paymentId;
	
	@Column(name = "IS_BULK")
	private String isBulk;
	
	@Column(name = "PAYMENT_MODE")
    private String payment_mode;

    @Column(name = "TRAN_REFRENCE_NUM")
    private String tran_ref_number;

    @Column(name = "ELAPSE_DAYS")
    private int elapse_days;

    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;

    @Column(name = "BANK_REF_NO")
    private String bank_ref_no;

    @Column(name = "SERVICE_NAME")
    private String serviceName;
    
    @Column(name = "EMAIL_ID")    
    private String emailID;

    @Column(name = "MOBILE_NO")
    private String mobileNo;

    @Column(name="PAYMENTSOURCE")
    private String paymentSource;
    
    @Column(name="EMP_REF_NO")
    private String empRefNo;
    
    private String custName;
    
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
    
	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCase_or_bill_no() {
		return case_or_bill_no;
	}

	public void setCase_or_bill_no(String case_or_bill_no) {
		this.case_or_bill_no = case_or_bill_no;
	}

	public int getDue_amount() {
		return due_amount;
	}

	public void setDue_amount(int due_amount) {
		this.due_amount = due_amount;
	}

	public int getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(int payment_amount) {
		this.payment_amount = payment_amount;
	}

	public int getConvinience_charge() {
		return convinience_charge;
	}

	public void setConvinience_charge(int convinience_charge) {
		this.convinience_charge = convinience_charge;
	}

	public int getExcess_amount() {
		return excess_amount;
	}

	public void setExcess_amount(int excess_amount) {
		this.excess_amount = excess_amount;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Date getBillDueDate() {
		return billDueDate;
	}

	public void setBillDueDate(Date billDueDate) {
		this.billDueDate = billDueDate;
	}

	public String getBankID() {
		return bankID;
	}

	public void setBankID(String bankID) {
		this.bankID = bankID;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
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

	public String getIsBulk() {
		return isBulk;
	}

	public void setIsBulk(String isBulk) {
		this.isBulk = isBulk;
	}

	public String getPayment_mode() {
		return payment_mode;
	}

	public void setPayment_mode(String payment_mode) {
		this.payment_mode = payment_mode;
	}

	public String getTran_ref_number() {
		return tran_ref_number;
	}

	public void setTran_ref_number(String tran_ref_number) {
		this.tran_ref_number = tran_ref_number;
	}

	public int getElapse_days() {
		return elapse_days;
	}

	public void setElapse_days(int elapse_days) {
		this.elapse_days = elapse_days;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getBank_ref_no() {
		return bank_ref_no;
	}

	public void setBank_ref_no(String bank_ref_no) {
		this.bank_ref_no = bank_ref_no;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public void setNewRow(boolean newRow) {
		this.newRow = newRow;
	}
	@Override
	public Object getId() {
		
		return trackId;
	}

	@Override
	public boolean isNew() {
		
		return newRow;
	}
}
