package org.ss.vendorapi.modal;

import java.util.Date;

import lombok.Data;

@Data
public class BillDetailsResDTO {

	private String billDueDate;
//	private Date billDueDate;
    private String billNo;
    private Date fromDate;
    private Date toDate;
    private String billIssuedDate;
    private String paymentDate;
    private Double paymentMade;
    private String paymentDue;
    
    //Extra added start
    private String billAmount;
    
    public String getBillAmount() {
		return billAmount;
	}

	public void setBillAmount(String billAmount) {
		this.billAmount = billAmount;
	}

	//Extra added end
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
	public Date getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the toDate
	 */
	public Date getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the billIssuedDate
	 */
	public String getBillIssuedDate() {
		return billIssuedDate;
	}

	/**
	 * @param billIssuedDate the billIssuedDate to set
	 */
	public void setBillIssuedDate(String billIssuedDate) {
		this.billIssuedDate = billIssuedDate;
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
	public Double getPaymentMade() {
		return paymentMade;
	}

	/**
	 * @param paymentMade the paymentMade to set
	 */
	public void setPaymentMade(Double paymentMade) {
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