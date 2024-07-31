package org.ss.vendorapi.modal;
import lombok.Data;

@Data
public class ConsumerDetailsResponseModel{

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
    
}