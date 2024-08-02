package org.ss.vendorapi.entity;

<<<<<<< HEAD
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REFRESHTOKEN")
public class RefreshToken {
	
	  @Id
	  @Column(name = "id")
=======
import java.io.Serializable;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "REFRESHTOKEN")
public class RefreshToken implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
>>>>>>> branch 'main' of https://github.com/gusainanukriti/CVMS_SERVICES.git
	  private int id;

	  @Column(name = "user_id")
	  private String user;

	  @Column(nullable = false, unique = true)
	  private String token;

	  @Column(nullable = false)
	  private Instant expiryDate;

}
