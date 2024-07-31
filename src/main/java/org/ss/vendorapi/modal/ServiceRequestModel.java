package org.ss.vendorapi.modal;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "servicerequestdata")
public class ServiceRequestModel implements Serializable{

	private static final long serialVersionUID = 2L;
	@Id
	@Column(name = "track_id", nullable = false) 
    private String trackId;
	
	@Column(name = "SERVICEREQUESTTYPE")
    private String srType;
	
	@Column(name = "ACC_NO")
    private String accountNumber;
	
	@Column(name = "discom_name")
    private String discomName;

	@Column(name = "COMMENTS")
    private String comments;
	
	@Column(name = "PARAM1")
    private String param1;
	
	@Column(name = "PARAM2")
    private String param2;
	
	@Column(name = "PARAM3")
    private String param3;
	
	@Column(name = "PARAM4")
    private String param4;
	
	@Column(name = "PARAM5")
    private String param5;
	
	@Column(name = "CHARGES1")
    private int charges1;
	
	@Column(name = "CHARGES2")
    private int charges2;
	
	@Column(name = "CREATEDATE")
    private Date createdDate;
	
	

	public String getTrackId() {
		return trackId;
	}

	public void setTrackId(String trackId) {
		this.trackId = trackId;
	}

	public String getSrType() {
		return srType;
	}

	public void setSrType(String srType) {
		this.srType = srType;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public String getParam5() {
		return param5;
	}

	public void setParam5(String param5) {
		this.param5 = param5;
	}

	public int getCharges1() {
		return charges1;
	}

	public void setCharges1(int charges1) {
		this.charges1 = charges1;
	}

	public int getCharges2() {
		return charges2;
	}

	public void setCharges2(int charges2) {
		this.charges2 = charges2;
	}

	
	public String getDiscomName() {
		return discomName;
	}

	public void setDiscomName(String discomName) {
		this.discomName = discomName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	
	
	
}
