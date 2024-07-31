package org.ss.vendorapi.modal;

import java.util.ArrayList;

public class CustomerDetailsModel {

    private String kno;

    private String bookNo;

    private String firstName;

    private String lastName;

    private String mobileNo;

    private String status;

    private String phoneNo;

    private String currentAddress;

    private String billingAddress;

    private AddressResDTO installationAddress;

    private String email;

    private String confirmEmail;

    private String category;

    private String subCategory;

    private String dueAmount;

    private String dueDate;

    private String billNo;

    private String dateOfBirth;

    private String onlineBillingStatus;

    private String discomName;

    private String groupName;

    private String division;

    private String subDivision;

    private String supplyType;

    private String sanctionedLoad;

    private String securityAmount;

    private String customerIndexNumber;

    private String name;

    private String currentLoad;

    //private HashMap mapForEmailTemplates;

    private ArrayList<String> secondaryNumber;

    private Double paymentMade;

    private String personId;

    private String paymentDate;

    private String accountInfo;

    private String consumerType;

    private String dueBillAmt;

    private String mothersName;

    private String typeOfMeter;

    private String typeOfPhase;

    private String typeOfPlace;

    private String billDate;

    private String lastLogin;
    
    private String lastPaidAmount = "0";
	
    private String lastPaymentDate="";
	
    private String typeOfConnection;  
	
	private String payAmtBeforeDueDt;
    
    private String payAmtAfterDueDt;
    
    private String divCode;

	public String getKno() {
		return kno;
	}

	public void setKno(String kno) {
		this.kno = kno;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public AddressResDTO getInstallationAddress() {
		return installationAddress;
	}

	public void setInstallationAddress(AddressResDTO installationAddress) {
		this.installationAddress = installationAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getConfirmEmail() {
		return confirmEmail;
	}

	public void setConfirmEmail(String confirmEmail) {
		this.confirmEmail = confirmEmail;
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

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getOnlineBillingStatus() {
		return onlineBillingStatus;
	}

	public void setOnlineBillingStatus(String onlineBillingStatus) {
		this.onlineBillingStatus = onlineBillingStatus;
	}

	public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getSubDivision() {
		return subDivision;
	}

	public void setSubDivision(String subDivision) {
		this.subDivision = subDivision;
	}

	public String getSupplyType() {
		return supplyType;
	}

	public void setSupplyType(String supplyType) {
		this.supplyType = supplyType;
	}

	public String getSanctionedLoad() {
		return sanctionedLoad;
	}

	public void setSanctionedLoad(String sanctionedLoad) {
		this.sanctionedLoad = sanctionedLoad;
	}

	public String getSecurityAmount() {
		return securityAmount;
	}

	public void setSecurityAmount(String securityAmount) {
		this.securityAmount = securityAmount;
	}

	public String getCustomerIndexNumber() {
		return customerIndexNumber;
	}

	public void setCustomerIndexNumber(String customerIndexNumber) {
		this.customerIndexNumber = customerIndexNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCurrentLoad() {
		return currentLoad;
	}

	public void setCurrentLoad(String currentLoad) {
		this.currentLoad = currentLoad;
	}

	public ArrayList<String> getSecondaryNumber() {
		return secondaryNumber;
	}

	public void setSecondaryNumber(ArrayList<String> secondaryNumber) {
		this.secondaryNumber = secondaryNumber;
	}

	public Double getPaymentMade() {
		return paymentMade;
	}

	public void setPaymentMade(Double paymentMade) {
		this.paymentMade = paymentMade;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
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

	public String getDueBillAmt() {
		return dueBillAmt;
	}

	public void setDueBillAmt(String dueBillAmt) {
		this.dueBillAmt = dueBillAmt;
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

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getLastPaidAmount() {
		return lastPaidAmount;
	}

	public void setLastPaidAmount(String lastPaidAmount) {
		this.lastPaidAmount = lastPaidAmount;
	}

	public String getLastPaymentDate() {
		return lastPaymentDate;
	}

	public void setLastPaymentDate(String lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public String getTypeOfConnection() {
		return typeOfConnection;
	}

	public void setTypeOfConnection(String typeOfConnection) {
		this.typeOfConnection = typeOfConnection;
	}

	public String getPayAmtBeforeDueDt() {
		return payAmtBeforeDueDt;
	}

	public void setPayAmtBeforeDueDt(String payAmtBeforeDueDt) {
		this.payAmtBeforeDueDt = payAmtBeforeDueDt;
	}

	public String getPayAmtAfterDueDt() {
		return payAmtAfterDueDt;
	}

	public void setPayAmtAfterDueDt(String payAmtAfterDueDt) {
		this.payAmtAfterDueDt = payAmtAfterDueDt;
	}

	public String getDivCode() {
		return divCode;
	}

	public void setDivCode(String divCode) {
		this.divCode = divCode;
	}

	
}
