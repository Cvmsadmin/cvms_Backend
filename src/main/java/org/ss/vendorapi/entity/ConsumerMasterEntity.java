//package org.ss.vendorapi.entity;
//
//import java.io.Serializable;
//import java.util.Collection;
//import java.util.Date;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.IdClass;
//import javax.persistence.Table;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.ss.vendorapi.modal.ConsumerMasterID;
//
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@NoArgsConstructor
//@Entity
//@Table(name = "CONSUMERMASTER")
//@IdClass(ConsumerMasterID.class)
//public class ConsumerMasterEntity implements Serializable, UserDetails{
//
//	private static final long serialVersionUID = 2L;
//	
//	@Id
//	@Column(name = "KNO")
//	private String kno;
//	
//	@Id
//	@Column(name = "DISCOM_NAME")
//    private String discomName;
//	
//	@Column(name = "U_PASSWORD")
//	private String upassword;
//	@Column(name = "SECRETQUESTION")
//	private String securityQuestion;
//	@Column(name = "SECRETANSWER")
//    private String securityAnswer;
//	@Column(name = "EMAIL_ID")
//    private String email;
//	@Column(name = "MOBILE")
//    private Long mobileNo;
//	@Column(name = "SOURCEOFREGISTRATION")
//    private String sourceOfRegistration;
//	@Column(name = "CONFIRMEMAIL")
//    private String confirmEmail;
//    //private String phoneNo;////
//    private String phoneNumber;
//    @Column(name = "OTP")
//	private String otp;
//   // @Column(name="CREATED_DATE")
//	//private Date createdDate;	
// 
//	@Column(name = "MODIFIED_DATE")
//    private Date modifiedDate;
//	
//	 @Column(name = "LASTLOGIN")
//	 private String lastLogin;
//	 
//	 @Column(name = "CONFIRMMOBILE")
//	 private String confirmMobile;
//	 // change password 
//	
//    
//		
//    /*private String billNo;
//    private String billType;
//    private String sbmBillNo;
//	
//	private String dateOfBirth; 
//	private String onlineBillingStatus;
//	
//	private String verifyCode;
//    private String knoDiscom;
//   
//    private String updMobNoPgName;*/
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getPassword() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		String kNo=String.valueOf(this.kno);
//		return kNo;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//	public void setModifiedDate(Object modifiedDate2) {
//		// TODO Auto-generated method stub
//		
//	}
//	
//}
