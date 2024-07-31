package org.ss.vendorapi.modal;

import java.util.ArrayList;

public class SelfBillGenNMModel {
	
	private String kno;
	private String discom;
	private String connectionType;
	//For validateNMFirstData
	private String sanctionedLoad;
	//For validateNMSecondData
	private String meterConfigType;
	private String currentReadingKWH;
	private String currentReadingKWHE;
	private String currentReadingKVAH;
	private String currentReadingKVAHE;
	private String demandKW;
	//For Submit
	private String badgeNumber;
	private String meterSerialNumber;
	private String manufacturerCode;
	private String previousReadDateTime;
	private String email;
	private String mobileNo;
	private String remarks;
	private String cumulativeEnergyKVE;
	private String demandKVA;
	private String consumerName;
	private ArrayList<TrustMeterNMDetailsAccDTO> previousReads;
	
	public String getKno() {
		return kno;
	}
	public void setKno(String kno) {
		this.kno = kno;
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
	public String getMeterConfigType() {
		return meterConfigType;
	}
	public void setMeterConfigType(String meterConfigType) {
		this.meterConfigType = meterConfigType;
	}
	public String getCurrentReadingKWH() {
		return currentReadingKWH;
	}
	public void setCurrentReadingKWH(String currentReadingKWH) {
		this.currentReadingKWH = currentReadingKWH;
	}
	public String getCurrentReadingKWHE() {
		return currentReadingKWHE;
	}
	public void setCurrentReadingKWHE(String currentReadingKWHE) {
		this.currentReadingKWHE = currentReadingKWHE;
	}
	public String getCurrentReadingKVAH() {
		return currentReadingKVAH;
	}
	public void setCurrentReadingKVAH(String currentReadingKVAH) {
		this.currentReadingKVAH = currentReadingKVAH;
	}
	public String getCurrentReadingKVAHE() {
		return currentReadingKVAHE;
	}
	public void setCurrentReadingKVAHE(String currentReadingKVAHE) {
		this.currentReadingKVAHE = currentReadingKVAHE;
	}
	public String getDemandKW() {
		return demandKW;
	}
	public void setDemandKW(String demandKW) {
		this.demandKW = demandKW;
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
	public String getPreviousReadDateTime() {
		return previousReadDateTime;
	}
	public void setPreviousReadDateTime(String previousReadDateTime) {
		this.previousReadDateTime = previousReadDateTime;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCumulativeEnergyKVE() {
		return cumulativeEnergyKVE;
	}
	public void setCumulativeEnergyKVE(String cumulativeEnergyKVE) {
		this.cumulativeEnergyKVE = cumulativeEnergyKVE;
	}
	public ArrayList<TrustMeterNMDetailsAccDTO> getPreviousReads() {
		return previousReads;
	}
	public void setPreviousReads(ArrayList<TrustMeterNMDetailsAccDTO> previousReads) {
		this.previousReads = previousReads;
	}
	public String getDemandKVA() {
		return demandKVA;
	}
	public void setDemandKVA(String demandKVA) {
		this.demandKVA = demandKVA;
	}
	public String getConsumerName() {
		return consumerName;
	}
	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}
	
}