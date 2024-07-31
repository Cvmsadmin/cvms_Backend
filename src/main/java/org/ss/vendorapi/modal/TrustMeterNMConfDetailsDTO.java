package org.ss.vendorapi.modal;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TrustMeterNMConfDetailsDTO {
	
	private String supplyType;
    private String purposeOfSupply;
    private String demandKW;
    private String demandKVA ;
    private String comments;
    private String sanctionLoad;
    private String mobileNo;
    private String emailId;
    private String accountNo;
    private String BadgeNumber;
    private String MeterSerialNumber;
    private String ManufacturerCode;
    private String meterConfigType;
    private BigDecimal maximumDemandKW;
    private BigDecimal cumulativeEnergyKWH;
    private BigDecimal maximumDemandKVA;
    private BigDecimal cumulativeEnergyKVAH;
    private String exportRead;
    private String exportDemand;
    private BigDecimal cumulativeEnergyKVE;
    private ArrayList<TrustMeterNMDetailsAccDTO> trustAccDTOs;
    private String meterStatus;
    private String previousReadingKW;
    private String currentReadingKWH;
    private String currentReadingKWHE;
    private String currentReadingKVAH ;
    private String currentReadingKVAHE;
    private String previousReadDateTime;
    
	public String getSupplyType() {
		return supplyType;
	}
	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}
	public String getPurposeOfSupply() {
		return purposeOfSupply;
	}
	public void setPurposeOfSupply(String purposeOfSupply) {
		this.purposeOfSupply = purposeOfSupply;
	}
	public String getDemandKW() {
		return demandKW;
	}
	public void setDemandKW(String demandKW) {
		this.demandKW = demandKW;
	}
	public String getDemandKVA() {
		return demandKVA;
	}
	public void setDemandKVA(String demandKVA) {
		this.demandKVA = demandKVA;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getSanctionLoad() {
		return sanctionLoad;
	}
	public void setSanctionLoad(String sanctionLoad) {
		this.sanctionLoad = sanctionLoad;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getBadgeNumber() {
		return BadgeNumber;
	}
	public void setBadgeNumber(String badgeNumber) {
		BadgeNumber = badgeNumber;
	}
	public String getMeterSerialNumber() {
		return MeterSerialNumber;
	}
	public void setMeterSerialNumber(String meterSerialNumber) {
		MeterSerialNumber = meterSerialNumber;
	}
	public String getManufacturerCode() {
		return ManufacturerCode;
	}
	public void setManufacturerCode(String manufacturerCode) {
		ManufacturerCode = manufacturerCode;
	}
	public String getMeterConfigType() {
		return meterConfigType;
	}
	public void setMeterConfigType(String meterConfigType) {
		this.meterConfigType = meterConfigType;
	}
	public BigDecimal getMaximumDemandKW() {
		return maximumDemandKW;
	}
	public void setMaximumDemandKW(BigDecimal maximumDemandKW) {
		this.maximumDemandKW = maximumDemandKW;
	}
	public BigDecimal getCumulativeEnergyKWH() {
		return cumulativeEnergyKWH;
	}
	public void setCumulativeEnergyKWH(BigDecimal cumulativeEnergyKWH) {
		this.cumulativeEnergyKWH = cumulativeEnergyKWH;
	}
	public BigDecimal getMaximumDemandKVA() {
		return maximumDemandKVA;
	}
	public void setMaximumDemandKVA(BigDecimal maximumDemandKVA) {
		this.maximumDemandKVA = maximumDemandKVA;
	}
	public BigDecimal getCumulativeEnergyKVAH() {
		return cumulativeEnergyKVAH;
	}
	public void setCumulativeEnergyKVAH(BigDecimal cumulativeEnergyKVAH) {
		this.cumulativeEnergyKVAH = cumulativeEnergyKVAH;
	}
	public String getExportRead() {
		return exportRead;
	}
	public void setExportRead(String exportRead) {
		this.exportRead = exportRead;
	}
	public String getExportDemand() {
		return exportDemand;
	}
	public void setExportDemand(String exportDemand) {
		this.exportDemand = exportDemand;
	}
	public BigDecimal getCumulativeEnergyKVE() {
		return cumulativeEnergyKVE;
	}
	public void setCumulativeEnergyKVE(BigDecimal cumulativeEnergyKVE) {
		this.cumulativeEnergyKVE = cumulativeEnergyKVE;
	}
	public ArrayList<TrustMeterNMDetailsAccDTO> getTrustAccDTOs() {
		return trustAccDTOs;
	}
	public void setTrustAccDTOs(ArrayList<TrustMeterNMDetailsAccDTO> trustAccDTOs) {
		this.trustAccDTOs = trustAccDTOs;
	}
	public String getMeterStatus() {
		return meterStatus;
	}
	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}
	public String getPreviousReadingKW() {
		return previousReadingKW;
	}
	public void setPreviousReadingKW(String previousReadingKW) {
		this.previousReadingKW = previousReadingKW;
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
	public String getPreviousReadDateTime() {
		return previousReadDateTime;
	}
	public void setPreviousReadDateTime(String previousReadDateTime) {
		this.previousReadDateTime = previousReadDateTime;
	}
}