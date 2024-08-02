//package org.ss.vendorapi.entity;
//
//import java.io.Serializable;
//import java.sql.Date;
//import java.time.LocalDateTime;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "refreshtoken")
//
//public class RefreshToken implements Serializable{
//	
//	private static final long serialVersionUID = 1L;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @Column(name = "expiry_date")
//    private LocalDateTime expiryDate;
//
//    @Column(name = "token")
//    private String token;
//
//    @Column(name = "user_id")
//    private Long userId;
//
//    
//	
//	
//
//}
