/**
 * Implemented CR No 10-22/NOV/2023 and Request Reference No C72.
 * 
 * This code contains proprietary information of R-APDRP project and has been developed by
 * for UP Power Corporation Limited (UPPCL) and its 4 Discoms viz. MVVNL, PVVNL, DVVNL and PuVVNL.
 * No part of this code may be reproduced, stored, copied, or transmitted in any form
 * or by means of electronic, mechanical, photocopying or otherwise, without the consent of Program Director, HCL
 * Technologies Ltd.
 * 
 * This code is intended only for internal circulation within, UPPCL and 4 Discoms.
 * 
 *@author @author Sonee
 * 
 * 
 */

package org.ss.vendorapi.modal;

public class FeedbackDTO {

    private String track_id;

    public void setTrack_id(String trackId) {
        track_id = trackId;
    }

    private String name;

    private String address;

    private String accNo;

    private String servConnNo;

    private String pinCode;

    private String city;

    private String state;

    private String country;

    private String email;

    private String comments;

    private String col1;

    private String col2;

    private String col3;

    private String col4;

    private String col5;
    
    private String discomName;


	/**
     * @return the track_id
     */
    public String getTrack_id() {
        return track_id;
    }

    /**
     * @param trackId
     *            the track_id to set
     */

    /**
     * @return the col1
     */
    public String getCol1() {
        return col1;
    }

    /**
     * @param col1
     *            the col1 to set
     */
    public void setCol1(String col1) {
        this.col1 = col1;
    }

    /**
     * @return the col2
     */
    public String getCol2() {
        return col2;
    }

    /**
     * @param col2
     *            the col2 to set
     */
    public void setCol2(String col2) {
        this.col2 = col2;
    }

    /**
     * @return the col3
     */
    public String getCol3() {
        return col3;
    }

    /**
     * @param col3
     *            the col3 to set
     */
    public void setCol3(String col3) {
        this.col3 = col3;
    }

    /**
     * @return the col4
     */
    public String getCol4() {
        return col4;
    }

    /**
     * @param col4
     *            the col4 to set
     */
    public void setCol4(String col4) {
        this.col4 = col4;
    }

    /**
     * @return the col5
     */
    public String getCol5() {
        return col5;
    }

    /**
     * @param col5
     *            the col5 to set
     */
    public void setCol5(String col5) {
        this.col5 = col5;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     *            the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the accNo
     */
    public String getAccNo() {
        return accNo;
    }

    /**
     * @param accNo
     *            the accNo to set
     */
    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    /**
     * @return the servConnNo
     */
    public String getServConnNo() {
        return servConnNo;
    }

    /**
     * @param servConnNo
     *            the servConnNo to set
     */
    public void setServConnNo(String servConnNo) {
        this.servConnNo = servConnNo;
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
        this.pinCode = pinCode;
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
        this.city = city;
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
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country
     *            the country to set
     */
    public void setCountry(String country) {
        this.country = country;
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

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments
     *            the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}
}
