package org.ss.vendorapi.modal;

import java.io.Serializable;

public class NameChangeModel implements Serializable {
	
	private static final long serialVersionUID = 2L;
	
	private String requestType;

	private String correctionType;

	private String changeReason;
	
	private String uName;

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCorrectionType() {
		return correctionType;
	}

	public void setCorrectionType(String correctionType) {
		this.correctionType = correctionType;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	

}
