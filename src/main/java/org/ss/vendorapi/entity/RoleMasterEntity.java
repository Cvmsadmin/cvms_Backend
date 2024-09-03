package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Table(name="Role_Master")
@Where(clause="ACTIVE=1")
@Entity
public class RoleMasterEntity implements Serializable{
	
	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="ROLE_NAME")
	private String roleName;
	
	@Column(name="ROLE_CODE")
	private String roleCode;
	
	@Column(name="ACTIVE")
	private Integer active;
	
	@Column(name="CREATE_DATE")
	private Date 	createDate;
	
	@Column(name="UPDATE_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@Transient
	private List<ResourceMasterEntity> resourceMasterEntities;

}
