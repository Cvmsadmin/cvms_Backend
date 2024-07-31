package org.ss.vendorapi.modal;

public class SelfBillGenModel {
	private String kNumber;
	private String discom;
	private String connectionType;
	//Inputs for Meter Reading Details
	private String sanctionedLoad;
	private String currentReading;
	private String demand;
	private String comments;
	private String email;
	private String mobileNumber;
	//input for submit
	//ValidateSecondData
	private String previousReadDateTime;
	private String previousReadingKWH;
	private String previousReadingKVH;
	private String checkKVAHValue;
	private int leftDigit;
	private int rightDigit;
	private int leftDigitMD;
	private int rightDigitMD;
	//trustmeterread
	private String badgeNumber;
	private String meterSerialNumber;
	private String manufacturerCode; 
	private String consumerName;
	
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
	public String getConnectionType() {
		return connectionType;
	}
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}
	public String getSanctionedLoad() {
		return sanctionedLoad;
	}
	public void setSanctionedLoad(String sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}
	public String getCurrentReading() {
		return currentReading;
	}
	public void setCurrentReading(String currentReading) {
		this.currentReading = currentReading;
	}
	public String getDemand() {
		return demand;
	}
	public void setDemand(String demand) {
		this.demand = demand;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPreviousReadDateTime() {
		return previousReadDateTime;
	}
	public void setPreviousReadDateTime(String previousReadDateTime) {
		this.previousReadDateTime = previousReadDateTime;
	}
	public String getPreviousReadingKWH() {
		return previousReadingKWH;
	}
	public void setPreviousReadingKWH(String previousReadingKWH) {
		this.previousReadingKWH = previousReadingKWH;
	}
	public String getPreviousReadingKVH() {
		return previousReadingKVH;
	}
	public void setPreviousReadingKVH(String previousReadingKVH) {
		this.previousReadingKVH = previousReadingKVH;
	}
	public String getCheckKVAHValue() {
		return checkKVAHValue;
	}
	public void setCheckKVAHValue(String checkKVAHValue) {
		this.checkKVAHValue = checkKVAHValue;
	}
	public int getLeftDigit() {
		return leftDigit;
	}
	public void setLeftDigit(int leftDigit) {
		this.leftDigit = leftDigit;
	}
	public int getRightDigit() {
		return rightDigit;
	}
	public void setRightDigit(int rightDigit) {
		this.rightDigit = rightDigit;
	}
	public int getLeftDigitMD() {
		return leftDigitMD;
	}
	public void setLeftDigitMD(int leftDigitMD) {
		this.leftDigitMD = leftDigitMD;
	}
	public int getRightDigitMD() {
		return rightDigitMD;
	}
	public void setRightDigitMD(int rightDigitMD) {
		this.rightDigitMD = rightDigitMD;
	}
	public String getBadgeNumber() {
		return badgeNumber;
	}
	public void setBadgeNumber(String badgeNumber) {
		this.badgeNumber = badgeNumber;
	}
	public String getMeterSerialNumber() {
		return meterSerialNumber;
	}
	public void setMeterSerialNumber(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}
	public String getManufacturerCode() {
		return manufacturerCode;
	}
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	
}