package org.ss.vendorapi.modal;

import java.util.List;

public class PayBillHomeDTO {


    private String amount;

    private String paymentMode;

    private String kno;

    private String excessAmount;

    private String email;

    private String mobile;

    private String ServiceName;
    
    private String searchOption;
    
    private String searchParameter;

    private String partialPayment;
	
	private String otp;
	
	private String paymentType;
	private String discomName;
	
	private String districtName;
	
	private String payableAmt;
	
	private CustomerDetailsDTO customerDetailsDTO;
	
	private PayBillDetailsDTO payBillDetailsDTO;
	
	private ServiceRequestModel ServiceRequestDTO;
	
	private List<String> accountIdList;
	
	private String verifyStatus;
	
	private String reportName;
	
	private String billOnHold;
	
	private String empRefNo;
	
	public String getEmpRefNo() {
		return empRefNo;
	}

	public void setEmpRefNo(String empRefNo) {
		this.empRefNo = empRefNo;
	}


	public List<String> getAccountIdList() {
		return accountIdList;
	}

	public void setAccountIdList(List<String> accountIdList) {
		this.accountIdList = accountIdList;
	}

	public ServiceRequestModel getServiceRequestDTO() {
		return ServiceRequestDTO;
	}

	public void setServiceRequestDTO(ServiceRequestModel serviceRequestDTO) {
		ServiceRequestDTO = serviceRequestDTO;
	}

	public PayBillDetailsDTO getPayBillDetailsDTO() {
		return payBillDetailsDTO;
	}

	public void setPayBillDetailsDTO(PayBillDetailsDTO payBillDetailsDTO) {
		this.payBillDetailsDTO = payBillDetailsDTO;
	}

	public CustomerDetailsDTO getCustomerDetailsDTO() {
		return customerDetailsDTO;
	}

	public void setCustomerDetailsDTO(CustomerDetailsDTO customerDetailsDTO) {
		this.customerDetailsDTO = customerDetailsDTO;
	}

	public String getPayableAmt() {
		return payableAmt;
	}

	public void setPayableAmt(String payableAmt) {
		this.payableAmt = payableAmt;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

    public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
    
    public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
    
    public String getSearchOption() {
		return searchOption;
	}

	public void setSearchOption(String searchOption) {
		this.searchOption = searchOption;
	}


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String getExcessAmount() {
        return excessAmount;
    }

    public void setExcessAmount(String excessAmount) {
        this.excessAmount = excessAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    private String totalAmount;

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getKno() {
        return kno;
    }

    public void setKno(String kno) {
        this.kno = kno;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

	public String getSearchParameter() {
		return searchParameter;
	}

	public void setSearchParameter(String searchParameter) {
		this.searchParameter = searchParameter;
	}

	public void setPartialPayment(String partialPayment) {
		this.partialPayment = partialPayment;
	}

	public String getPartialPayment() {
		return partialPayment;
	}

	public String getServiceName() {
		return ServiceName;
	}

	public void setServiceName(String serviceName) {
		ServiceName = serviceName;
	}

	public String getVerifyStatus() {
		return verifyStatus;
	}

	public void setVerifyStatus(String verifyStatus) {
		this.verifyStatus = verifyStatus;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getBillOnHold() {
		return billOnHold;
	}

	public void setBillOnHold(String billOnHold) {
		this.billOnHold = billOnHold;
	}



}