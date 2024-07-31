package org.ss.vendorapi.modal;

//import org.apache.xmlbeans.XmlAnySimpleType;

public class AddressResDTO {

//    public XmlAnySimpleType id;

    public String addressLine1;

    public String addressLine2;

    public String addressLine3;

    public String addressLine4;

    public String zipCode;

    private String city;

    private String state;

    private String pinCode;

    private String phone;

    /**
     * @return the id
     */
//    public XmlAnySimpleType getId() {
//        return id;
//    }
//
//    /**
//     * @param id
//     *            the id to set
//     */
//    public void setId(XmlAnySimpleType id) {
//        this.id = id;
//    }

    /**
     * @return the addressLine1
     */
    public String getAddressLine1() {
        return addressLine1;
    }

    /**
     * @param addressLine1
     *            the addressLine1 to set
     */
    public void setAddressLine1(String addressLine1) {
    	if(addressLine1!=null){
        this.addressLine1 = addressLine1;
    	}
    	else {
    		this.addressLine1 = "";
    	}
    }

    /**
     * @return the addressLine2
     */
    public String getAddressLine2() {
        return addressLine2;
    }

    /**
     * @param addressLine2
     *            the addressLine2 to set
     */
    public void setAddressLine2(String addressLine2) {
    	if(addressLine2!=null){
        this.addressLine2 = addressLine2;
    	}
    	else{
    		this.addressLine2 = "";
    	}
    }

    /**
     * @return the addressLine3
     */
    public String getAddressLine3() {
        return addressLine3;
    }

    /**
     * @param addressLine3
     *            the addressLine3 to set
     */
    public void setAddressLine3(String addressLine3) {
    	if(addressLine3!=null){
        this.addressLine3 = addressLine3;
    	}
    	else{
    		this.addressLine3 = "";
    	}
    }

    /**
     * @return the addressLine4
     */
    public String getAddressLine4() {
        return addressLine4;
    }

    /**
     * @param addressLine4
     *            the addressLine4 to set
     */
    public void setAddressLine4(String addressLine4) {
    	if(addressLine4!=null){
        this.addressLine4 = addressLine4;
    	}
    	else {
    		this.addressLine4 = "";
    	}
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city
     *            the city to set
     */
    public void setCity(String city) {
    	if(city != null){
        this.city = city;
    	}
    	else {
    		this.city = "";
    	}
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state
     *            the state to set
     */
    public void setState(String state) {
    	if(state != null){
        this.state = state;
    	}
    	else{
    		this.state = "";
    	}
    }

    /**
     * @return the pinCode
     */
    public String getPinCode() {
        return pinCode;
    }

    /**
     * @param pinCode
     *            the pinCode to set
     */
    public void setPinCode(String pinCode) {
    	if(pinCode != null){
        this.pinCode = pinCode;
    	}
    	else{
    		this.pinCode = "";
    	}
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     *            the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the zipCode
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * @param zipCode
     *            the zipCode to set
     */
    public void setZipCode(String zipCode) {
    	if(zipCode != null){
        this.zipCode = zipCode;
    	}
    	else{
    		this.zipCode = "";
    	}
    }

}
