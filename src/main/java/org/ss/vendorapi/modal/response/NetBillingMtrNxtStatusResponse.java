package org.ss.vendorapi.modal.response;

public class NetBillingMtrNxtStatusResponse {
	private String status;
	private String caseID;
	private String tfrEnableFlag;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCaseID() {
		return caseID;
	}
	public void setCaseID(String caseID) {
		this.caseID = caseID;
	}
	public String getTfrEnableFlag() {
		return tfrEnableFlag;
	}
	public void setTfrEnableFlag(String tfrEnableFlag) {
		this.tfrEnableFlag = tfrEnableFlag;
	}
}