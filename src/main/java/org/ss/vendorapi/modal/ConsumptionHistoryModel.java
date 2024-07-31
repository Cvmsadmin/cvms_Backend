package org.ss.vendorapi.modal;

public class ConsumptionHistoryModel {
	private String kNumber;
	private String discom;
	private String fromDate;
	private String toDate;
	private String connectionType;
	
	public String getkNumber() {
		return kNumber;
	}
	public void setkNumber(String kNumber) {
		this.kNumber = kNumber;
	}
	public String getDiscom() {
		return discom;
	}
	public void setDiscom(String discom) {
		this.discom = discom;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
}