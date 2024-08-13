package org.ss.vendorapi.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Table(name="city_master")
@Entity
public class CityMasterEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	
	private String cityName;
	private String districtId;
	private Integer active;
	private Date createDate;
	private Date updateDate;
}
