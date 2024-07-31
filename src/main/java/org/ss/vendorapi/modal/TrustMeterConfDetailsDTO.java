package org.ss.vendorapi.modal;

import java.util.ArrayList;

import lombok.Data;
@Data
public class TrustMeterConfDetailsDTO {

	private String supplyType;
    private String purposeOfSupply;
    private String demand;
    private String comments;
    private String sanctionLoad;
    private String mobileNo;
    private String emailId;
    private String accountNo;
    private String badgeNumber;
    private String meterSerialNumber;
    private String manufacturerCode;
    private String meterConfigType;
    private String checkKVAHValue;
    private String meterStatus;
    private String previousReadingKWH;
    private String currentReading;
    private String previousReadDateTime;
    private int leftDigit;
    private int rightDigit;
    private int leftDigitMD;
    private int rightDigitMD;
    private String previousReadingKVH;
    private ArrayList<TrustMeterNMDetailsAccDTO> trustAccDTOs;
    
}