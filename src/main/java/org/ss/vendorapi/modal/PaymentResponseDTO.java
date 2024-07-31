package org.ss.vendorapi.modal;

public class PaymentResponseDTO {
    private String track_id;

    private String transId;

    private String status;

    private String bankName;

    private String bankrefNum;
    
    private String paymentMode;

   
	/**
     * @return the track_id
     */
    public String getTrack_id() {
        return track_id;
    }

    /**
     * @param trackId
     *            the track_id to set
     */
    public void setTrack_id(String trackId) {
        track_id = trackId;
    }

    /**
     * @return the transId
     */
    public String getTransId() {
        return transId;
    }

    /**
     * @param transId
     *            the transId to set
     */
    public void setTransId(String transId) {
        this.transId = transId;
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
     * @return the bankName
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * @param bankName
     *            the bankName to set
     */
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    /**
     * @return the bankrefNum
     */
    public String getBankrefNum() {
        return bankrefNum;
    }

    /**
     * @param bankrefNum
     *            the bankrefNum to set
     */
    public void setBankrefNum(String bankrefNum) {
        this.bankrefNum = bankrefNum;
    }
    
    public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}


}
