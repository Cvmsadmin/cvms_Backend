package org.ss.vendorapi.entity;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Where(clause="ACTIVE=1")
@Table(name="resource_master")
@Entity
public class ResourceMasterEntity  extends ParentEntity implements Serializable{


	private static final long serialVersionUID=1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String resourceName;
	private String resourceUrl;
	private String resourceCode;
	
    private Integer active;
    
    @Column(name="create_date")
	private Date createDate;
	
	@Column(name="update_date")
	private Date updateDate;



}
