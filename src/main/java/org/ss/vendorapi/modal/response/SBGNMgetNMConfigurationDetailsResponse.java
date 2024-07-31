package org.ss.vendorapi.modal.response;

import java.util.ArrayList;

import org.ss.vendorapi.modal.TrustMeterNMDetailsAccDTO;

public class SBGNMgetNMConfigurationDetailsResponse {
	private String meterStatus;
	private String supplyType;
	private String purposeOfSupply;
	private int leftDigit;
	private int rightDigit;
	private int leftDigitMD;
	private int rightDigitMD;
	private String meterSerialNumber;
	private String meterConfigType;
	private String manufacturerCode;
	private String badgeNumber;
	private String previousReadingKWH;
	private String previousReadingKVH;
	private String previousReadDateTime;
	private ArrayList<TrustMeterNMDetailsAccDTO> trustAccDTOs;
	private String sanctionLoad;
	
	public String getMeterStatus() {
		return meterStatus;
	}
	public void setMeterStatus(String meterStatus) {
		this.meterStatus = meterStatus;
	}
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
	public String getMeterSerialNumber() {
		return meterSerialNumber;
	}
	public void setMeterSerialNumber(String meterSerialNumber) {
		this.meterSerialNumber = meterSerialNumber;
	}
	public String getMeterConfigType() {
		return meterConfigType;
	}
	public void setMeterConfigType(String meterConfigType) {
		this.meterConfigType = meterConfigType;
	}
	public String getManufacturerCode() {
		return manufacturerCode;
	}
	public void setManufacturerCode(String manufacturerCode) {
		this.manufacturerCode = manufacturerCode;
	}
	public String getBadgeNumber() {
		return badgeNumber;
	}
	public void setBadgeNumber(String badgeNumber) {
		this.badgeNumber = badgeNumber;
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
	public String getPreviousReadDateTime() {
		return previousReadDateTime;
	}
	public void setPreviousReadDateTime(String previousReadDateTime) {
		this.previousReadDateTime = previousReadDateTime;
	}
	public ArrayList<TrustMeterNMDetailsAccDTO> getTrustAccDTOs() {
		return trustAccDTOs;
	}
	public void setTrustAccDTOs(ArrayList<TrustMeterNMDetailsAccDTO> trustAccDTOs) {
		this.trustAccDTOs = trustAccDTOs;
	}
	public String getSanctionLoad() {
		return sanctionLoad;
	}
	public void setSanctionLoad(String sanctionLoad) {
		this.sanctionLoad = sanctionLoad;
	}
}