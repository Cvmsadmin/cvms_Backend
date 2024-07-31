package org.ss.vendorapi.modal;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

public class ConsumerMasterID implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2L;
	@Id
	@Column(name = "KNO")
	private String kno;
	@Id
	@Column(name = "DISCOM_NAME")
    private String discomName;	
}