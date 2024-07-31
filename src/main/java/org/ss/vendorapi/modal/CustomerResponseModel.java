package org.ss.vendorapi.modal;

import java.io.Serializable;

public class CustomerResponseModel extends UPPCLModel implements Serializable {

    private static final long serialVersionUID = 1L;

	private String knumber;

    private String consumerName;

    private String relationName;

    private String mobileNo;

    private String phoneNo;

    private String email;

    private String dateOfBirth;

    private AddressResDTO premiseAddress;

    private AddressResDTO billingAddress;

    private String category;

    private String subCategory;

    private String subDivision;

    private String division;

    private String discom;

    private String bookNo;

    private String connectionStatus;

    private String sanctionLoad;

    private String personId;

    private String onlineBillingStatus;

    private String supplyType;

    private String customerIndexNo;

    private String securityAmount;

    private String connectionType;
    
    private String sanctionedLoadBHP; 
    
    private String sanctionedLoadKVA;
    
    private String sanctionedLoadKW;

    private String accountInfo;

    private String consumerType;
    
    private String mothersName;
    
	private String typeOfMeter;
    
    private String typeOfPhase;
    
    private String typeOfPlace;
    
   
    private String sanctionedLoad;
    private String purposeOfSupply;
    private String typeOfConnectionPoint;
    private String chequeDshnrCount;

   

	public String getKnumber() {
		return knumber;
	}

	public void setKnumber(String knumber) {
		this.knumber = knumber;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public AddressResDTO getPremiseAddress() {
		return premiseAddress;
	}

	public void setPremiseAddress(AddressResDTO premiseAddress) {
		this.premiseAddress = premiseAddress;
	}

	public AddressResDTO getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(AddressResDTO billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getDiscom() {
		return discom;
	}

	public void setDiscom(String discom) {
		this.discom = discom;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public String getConnectionStatus() {
		return connectionStatus;
	}

	public void setConnectionStatus(String connectionStatus) {
		this.connectionStatus = connectionStatus;
	}

	public String getSanctionLoad() {
		return sanctionLoad;
	}

	public void setSanctionLoad(String sanctionLoad) {
		this.sanctionLoad = sanctionLoad;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getOnlineBillingStatus() {
		return onlineBillingStatus;
	}

	public void setOnlineBillingStatus(String onlineBillingStatus) {
		this.onlineBillingStatus = onlineBillingStatus;
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public String getCustomerIndexNo() {
		return customerIndexNo;
	}

	public void setCustomerIndexNo(String customerIndexNo) {
		this.customerIndexNo = customerIndexNo;
	}

	public String getSecurityAmount() {
		return securityAmount;
	}

	public void setSecurityAmount(String securityAmount) {
		this.securityAmount = securityAmount;
	}

	public String getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	public String getSanctionedLoadBHP() {
		return sanctionedLoadBHP;
	}

	public void setSanctionedLoadBHP(String sanctionedLoadBHP) {
		this.sanctionedLoadBHP = sanctionedLoadBHP;
	}

	public String getSanctionedLoadKVA() {
		return sanctionedLoadKVA;
	}

	public void setSanctionedLoadKVA(String sanctionedLoadKVA) {
		this.sanctionedLoadKVA = sanctionedLoadKVA;
	}

	public String getSanctionedLoadKW() {
		return sanctionedLoadKW;
	}

	public void setSanctionedLoadKW(String sanctionedLoadKW) {
		this.sanctionedLoadKW = sanctionedLoadKW;
	}

	public String getAccountInfo() {
		return accountInfo;
	}

	public void setAccountInfo(String accountInfo) {
		this.accountInfo = accountInfo;
	}

	public String getConsumerType() {
		return consumerType;
	}

	public void setConsumerType(String consumerType) {
		this.consumerType = consumerType;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public String getTypeOfMeter() {
		return typeOfMeter;
	}

	public void setTypeOfMeter(String typeOfMeter) {
		this.typeOfMeter = typeOfMeter;
	}

	public String getTypeOfPhase() {
		return typeOfPhase;
	}

	public void setTypeOfPhase(String typeOfPhase) {
		this.typeOfPhase = typeOfPhase;
	}

	public String getTypeOfPlace() {
		return typeOfPlace;
	}

	public void setTypeOfPlace(String typeOfPlace) {
		this.typeOfPlace = typeOfPlace;
	}

	public String getSanctionedLoad() {
		return sanctionedLoad;
	}

	public void setSanctionedLoad(String sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}

	public String getPurposeOfSupply() {
		return purposeOfSupply;
	}

	public void setPurposeOfSupply(String purposeOfSupply) {
		this.purposeOfSupply = purposeOfSupply;
	}

	public String getTypeOfConnectionPoint() {
		return typeOfConnectionPoint;
	}

	public void setTypeOfConnectionPoint(String typeOfConnectionPoint) {
		this.typeOfConnectionPoint = typeOfConnectionPoint;
	}

	public String getChequeDshnrCount() {
		return chequeDshnrCount;
	}

	public void setChequeDshnrCount(String chequeDshnrCount) {
		this.chequeDshnrCount = chequeDshnrCount;
	}
	
	

}
