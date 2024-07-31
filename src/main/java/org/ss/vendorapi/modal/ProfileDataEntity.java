package org.ss.vendorapi.modal;

import java.io.Serializable;
import java.util.Date;

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
@Table(name = "PROFILE_DATA")
public class ProfileDataEntity implements Serializable{

	private static final long serialVersionUID = 2L;
	@Id
	private String accountId;
	private String propertyKey;
	private String propertyValue;
	private Date recordDate;
	private Integer status;
	private String discomName;

}
