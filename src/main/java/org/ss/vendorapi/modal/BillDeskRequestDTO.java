/**
* This code contains copyright information which is the proprietary property
* of RADRP No part of this code may be reproduced,
* stored or transmitted in any form without the prior written permission of RADRP
* 
* Copyright (C) RAPDRP
* All rights reserved.
* 
*@author Sanjeev Agarwal 
* 
*/
package org.ss.vendorapi.modal;

public class BillDeskRequestDTO {
	private String url;
	private String message;
	private String paymentMode;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
		
	}
}