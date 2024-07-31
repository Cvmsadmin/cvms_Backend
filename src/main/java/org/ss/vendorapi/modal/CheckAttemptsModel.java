//package org.ss.vendorapi.modal;
//
//import java.sql.Timestamp;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.IdClass;
//import jakarta.persistence.Table;
//
//
//@Entity
//@Table(name="CHECK_ATTEMPTS")
//@IdClass(CheckAttemptsID.class)
//public class CheckAttemptsModel {
//	
//	@Id
//	private String acc_Num;
//	
//	//@Id
//	private String discomname;
//	private int counter;//
//	private String at_Flag;
//	private Timestamp create_date;//
//	private String r1;
//	private String r2;
//	public String getAcc_Num() {
//		return acc_Num;
//	}
//	public void setAcc_Num(String acc_Num) {
//		this.acc_Num = acc_Num;
//	}
//	public String getDiscomname() {
//		return discomname;
//	}
//	public void setDiscomname(String discomname) {
//		this.discomname = discomname;
//	}
//	
//	public int getCounter() {
//		return counter;
//	}
//	public void setCounter(int counter) {
//		this.counter = counter;
//	}
//	public String getAt_Flag() {
//		return at_Flag;
//	}
//	public void setAt_Flag(String at_Flag) {
//		this.at_Flag = at_Flag;
//	}
//	
//	
//	public Timestamp getCreate_date() {
//		return create_date;
//	}
//	public void setCreate_date(Timestamp create_date) {
//		this.create_date = create_date;
//	}
//	public String getR1() {
//		return r1;
//	}
//	public void setR1(String r1) {
//		this.r1 = r1;
//	}
//	public String getR2() {
//		return r2;
//	}
//	public void setR2(String r2) {
//		this.r2 = r2;
//	}
//}