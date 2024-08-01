package org.ss.vendorapi.entity;



import java.util.Date;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class ParentEntity {
	
	@Column(name="CREATE_DATE")
	private Date createDate;
	
	@Column(name="UPDATE_DATE")
	private Date updateDate;
	
	@Column(name="CREATED_BY")
	private String createdBy;
	
	@Column(name="UPDATED_BY")
	private String updatedBy;
	
	@Column(name="ACTIVE")
	private Integer active;

}


