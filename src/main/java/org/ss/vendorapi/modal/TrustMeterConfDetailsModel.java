package org.ss.vendorapi.modal;

public class TrustMeterConfDetailsModel {

    private String supplyType;

    private String purposeOfSupply;

    private String demand;

    private String comments;

    private String sanctionLoad;

    private String mobileNo;

    private String emailId;

    private String accountNo;

    private String BadgeNumber;

    private String MeterSerialNumber;

    private String ManufacturerCode;

    private String meterConfigType;

    /**
     * @return the meterConfigType
     */
    public String getMeterConfigType() {
        return meterConfigType;
    }

    /**
     * @param meterConfigType
     *            the meterConfigType to set
     */
    public void setMeterConfigType(String meterConfigType) {
        this.meterConfigType = meterConfigType;
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

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getSanctionLoad() {
        return sanctionLoad;
    }

    public void setSanctionLoad(String sanctionLoad) {
        this.sanctionLoad = sanctionLoad;
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

    private String meterStatus;

    private String previousReadingKW;

    private String currentReading;

    private String previousReadDateTime;

    private int leftDigit;

    private int rightDigit;

    private int LeftDigitMD;

    private int RightDigitMD;

    private String previousReadingKVH;

    public int getLeftDigitMD() {
        return LeftDigitMD;
    }

    public void setLeftDigitMD(int leftDigitMD) {
        LeftDigitMD = leftDigitMD;
    }

    public int getRightDigitMD() {
        return RightDigitMD;
    }

    public void setRightDigitMD(int rightDigitMD) {
        RightDigitMD = rightDigitMD;
    }

    public String getPreviousReadDateTime() {
        return previousReadDateTime;
    }

    public void setPreviousReadDateTime(String previousReadDateTime) {
        this.previousReadDateTime = previousReadDateTime;
    }

    public String getSupplyType() {
        return supplyType;
    }

    public void setSupplyType(String supplyType) {
        this.supplyType = supplyType;
    }

    public String getMeterStatus() {
        return meterStatus;
    }

    public void setMeterStatus(String meterStatus) {
        this.meterStatus = meterStatus;
    }

    public String getPurposeOfSupply() {
        return purposeOfSupply;
    }

    public void setPurposeOfSupply(String purposeOfSupply) {
        this.purposeOfSupply = purposeOfSupply;
    }

    public String getPreviousReadingKW() {
        return previousReadingKW;
    }

    public void setPreviousReadingKW(String previousReadingKW) {
        this.previousReadingKW = previousReadingKW;
    }

    public String getPreviousReadingKVH() {
        return previousReadingKVH;
    }

    public void setPreviousReadingKVH(String previousReadingKVH) {
        this.previousReadingKVH = previousReadingKVH;
    }

    public String getCurrentReading() {
        return currentReading;
    }

    public void setCurrentReading(String currentReading) {
        this.currentReading = currentReading;
    }

}
