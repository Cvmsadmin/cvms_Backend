package org.ss.vendorapi.entity;

import java.io.Serializable;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor @Setter @Getter @AllArgsConstructor
@Entity
@Where(clause="ACTIVE=1")
@Table(name="USER_MASTER")
public class UserMasterEntity extends ParentEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="base_location")
	private String baseLocation;
	
	@Column(name="email")
	private String email;
	
	@Column(name="employee_id")
	private String employeeId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="middle_name")
	private String middleName;
	
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="USERID")
	private String userId;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="physical_location")
	private String physicalLocation;
	
	@Column(name="role")
	private String role;

	
	
	

}
